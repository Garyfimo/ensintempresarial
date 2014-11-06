package empresarial.synapsesdk.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.adapter.ProjectAdapter;
import empresarial.synapsesdk.com.model.Project;
import empresarial.synapsesdk.com.service.GsonRequest;
import empresarial.synapsesdk.com.service.VolleyApplication;
import empresarial.synapsesdk.com.util.AccountUtils;

public class ProjectActivity extends Activity {

    public static final String PROPERTY_REG_ID = "linear-time-712";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    String SENDER_ID = "523530939055";
    static final String TAG = "GCM Demo";
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    Context context;
    String regid;
    String username;

    public ArrayList<Project> lista_proyectos;

    @InjectView(R.id.project_gridview) GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!AccountUtils.isAuthenticated(this)){
            AccountUtils.startAuthenticationFlow(this);
            finish();
            return;
        }
        setTitle(getString(R.string.title_activity_project));
        setContentView(R.layout.activity_project);

        ButterKnife.inject(ProjectActivity.this);
        lista_proyectos = new ArrayList<Project>();
        context = getApplicationContext();


        setData(); //intensive hardcode

       // Log.i("size", lista_proyectos.size() + "");

        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }


        //Log.i("id", lista_proyectos.get(1).getIdProyecto() + "");



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(ProjectActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProjectActivity.this, DescriptionActivity.class);
                intent.putExtra("id",lista_proyectos.get(position).getIdProyecto()+"");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;
                    sendRegistrationIdToBackend();
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                //mDisplay.append(msg + "\n");
            }
        }.execute(null, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(ProjectActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private void sendRegistrationIdToBackend() {

//        Log.i("username",":" + username);
        String url = String.format("http://upcsistemas.com/ensint/api/auth/gcm?username=%s",username);
  //      Log.i("regid",regid);
        String body = String.format("{\"gcmCode\":\"%s\"}", regid);
        GsonRequest request = new GsonRequest(Request.Method.POST,url, body,Boolean.class,null,new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                //  Log.i("response",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyApplication.getInstance().getRequestQueue().add(request);

    }

     void setData(){
        String url = "http://upcsistemas.com/ensint/api/proyectos";
        JsonObjectRequest request = new JsonObjectRequest(url, null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();


                //    Log.i("JSONObject",response.toString());

                try {
                //    Log.i("hayMasItems",response.getString("hayMasItems"));
                    boolean a = response.getString("hayMasItems").equals("false");
                //    Log.i("boolean", a+"");
                    if(!response.getString("hayMasItems").equals("false")){

                    }else{
                //    Log.i("JSONObject2",response.getString("proyectos") + "");
                //    Log.i("JSONObject2[0]",response.getJSONArray("proyectos").get(0)  + "");
                    JSONArray proyectos = response.getJSONArray("proyectos");
                    for(int i = 0; i < proyectos.length(); i++)
                        {
                            JSONObject proyectoJson = (JSONObject) proyectos.get(i);
                            Project proyecto = gson.fromJson(proyectoJson.toString(),Project.class);
                            lista_proyectos.add(proyecto);
                           // Log.i("imagenGoogleMaps",proyectoJson.getString("imagenGoogleMaps"));
                //            Log.i("id", lista_proyectos.get(i).getIdProyecto() + "");
                        }
                    gridView.setAdapter(new ProjectAdapter(ProjectActivity.this,lista_proyectos));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR: ", error.toString());
                    }
                }
        );


        VolleyApplication.getInstance().getRequestQueue().add(request);

        //Log.i("id", lista_proyectos.get(0).getIdProyecto() + "");

    }

}

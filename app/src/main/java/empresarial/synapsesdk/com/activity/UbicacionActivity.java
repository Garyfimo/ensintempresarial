package empresarial.synapsesdk.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.Config;
import empresarial.synapsesdk.com.activity.R;
import empresarial.synapsesdk.com.model.Project;
import empresarial.synapsesdk.com.model.Screen;
import empresarial.synapsesdk.com.service.VolleyApplication;
import empresarial.synapsesdk.com.util.Utilitario;

public class UbicacionActivity extends BaseActivity {

    Project project;
    @InjectView(R.id.ubicacion_image)
    ImageView imagen_google_maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        ButterKnife.inject(UbicacionActivity.this);
        setProject(project_id);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return Utilitario.UBICACION;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ubicacion, menu);
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

    @Override
    public String getScreenName() {
        return Screen.UBICACION.toString();
    }

    @Override
    public String getScreenUrl() {
        return "api/proyectos/"+project_id;
    }

    void setProject(String id) {
        String url = String.format(Config.BASE_URL + "proyectos/%s",
                id);

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //      Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_LONG).show();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                project = gson.fromJson(response.toString(), Project.class);
                //     Log.i("project.getNombre(): ", project.getNombre());
                Picasso.with(UbicacionActivity.this).load(project.getImagenComplejoURL()).into(imagen_google_maps);
                //      Log.i("indicadores", project.getIndicadores() + "");

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

    }
}

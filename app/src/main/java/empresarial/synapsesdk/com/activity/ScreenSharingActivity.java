package empresarial.synapsesdk.com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.Config;
import empresarial.synapsesdk.com.model.ScreenSharingBody;
import empresarial.synapsesdk.com.model.User;
import empresarial.synapsesdk.com.service.GsonRequest;
import empresarial.synapsesdk.com.service.VolleyApplication;
import empresarial.synapsesdk.com.util.AccountUtils;

public abstract class ScreenSharingActivity extends Activity {

    User[] users;

    ScreenSharingBody body;
    private ArrayAdapter<User> adapter;

    String userPos = "1_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        body = new ScreenSharingBody();
        getUsuarios();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.button_share:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View dialogLayout = LayoutInflater.from(this).inflate(R.layout.screen_sharing_dialog, null);
                builder.setTitle("Screen Sharing");
                builder.setView(dialogLayout);
                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        postSS();
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                //builder.show();

                SSDialogHolder holder = new SSDialogHolder(builder.show());
                holder.setAdapters();

                final Dialog dialog = new Dialog(ScreenSharingActivity.this);
                dialog.setContentView(R.layout.screen_sharing_dialog);
                dialog.setTitle("Screen Sharing");
                //dialog.show();
        }

        return true;
    }

    class SSDialogHolder {
        @InjectView(R.id.ss_1_1)
        AutoCompleteTextView autoText11;
        @InjectView(R.id.ss_1_2)
        AutoCompleteTextView autoText12;

        @InjectView(R.id.ss_2_1)
        AutoCompleteTextView autoText21;
        @InjectView(R.id.ss_2_2)
        AutoCompleteTextView autoText22;

        SSDialogHolder(Dialog view) {
            ButterKnife.inject(this, view);
        }

        public void setAdapters() {
            autoText11.setAdapter(adapter);
            User user = AccountUtils.getUserData(ScreenSharingActivity.this);
            autoText11.setText(user.getUsername());
            pickUser(user, "1_1");

            autoText12.setAdapter(adapter);
            autoText12.requestFocus();
            //autoText13.setAdapter(adapter);

            autoText21.setAdapter(adapter);
            autoText22.setAdapter(adapter);
            //autoText23.setAdapter(adapter);


            autoText11.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pickUser((User) adapterView.getAdapter().getItem(i), "1_1");
                }
            });

            autoText12.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pickUser((User) adapterView.getAdapter().getItem(i), "1_2");
                }
            });


            autoText21.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pickUser((User) adapterView.getAdapter().getItem(i), "2_1");
                }
            });

            autoText22.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pickUser((User) adapterView.getAdapter().getItem(i), "2_2");
                }
            });


        }

        public void pickUser(User user, String pos) {
            if (AccountUtils.getAccountName(ScreenSharingActivity.this).equals(user.getUsername())) {
                userPos = pos;
            }
            ScreenSharingBody.SSUser ssUser = new ScreenSharingBody.SSUser();
            ssUser.usuario = user.getUsername();
            ssUser.pos = pos;
            body.addUser(ssUser);
        }
    }

    void postSS() {
        String url = Config.BASE_URL + "notificacion/screensharing";
        Gson gson = new Gson();
        body.check(AccountUtils.getAccountName(ScreenSharingActivity.this));
        GsonRequest request = new GsonRequest(Request.Method.POST, url, gson.toJson(body), Boolean.class, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyApplication.getInstance().getRequestQueue().add(request);

        zoomImageFromThumb(userPos);
    }

    void getUsuarios() {
        String url = Config.BASE_URL + "usuarios";
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                try {
                    users = gson.fromJson(response.getJSONArray("usuarios").toString(), User[].class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new ArrayAdapter<User>(ScreenSharingActivity.this, android.R.layout.simple_list_item_1, users);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR: ", error.toString());
            }
        });
        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    public abstract void zoomImageFromThumb(String pos);
}

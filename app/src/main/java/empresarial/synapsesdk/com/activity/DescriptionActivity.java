package empresarial.synapsesdk.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.model.Project;
import empresarial.synapsesdk.com.model.User;
import empresarial.synapsesdk.com.service.VolleyApplication;


public class DescriptionActivity extends Activity {

    //@InjectView(R.id.servicios_title) TextView servicios_title;
    @InjectView(R.id.servicios_description) TextView servicios_description;
    //@InjectView(R.id.acabados_title) TextView acabados_title;
    @InjectView(R.id.acabados_description) TextView acabados_description;
    //@InjectView(R.id.infraestructura_title) TextView infraestructura_title;
    @InjectView(R.id.infraestructura_description) TextView infraestructura_description;
    @InjectView(R.id.project_title_description) TextView project_title_description;
    @InjectView(R.id.project_subtitle_description) TextView project_subtitle_description;
    @InjectView(R.id.project_resume_description) TextView project_resume_description;


    String project_id ;
    Project project;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);



        Intent intent = getIntent();
        project_id = intent.getStringExtra("id");
        setProject(intent.getStringExtra("id"));
        ButterKnife.inject(DescriptionActivity.this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.description, menu);
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

    void setProject(String id)
    {
        String url = String.format("http://upcsistemas.com/ensint/api/proyectos/%s",
                id);
        Log.i("id from context",id);
        JsonObjectRequest request = new JsonObjectRequest(url,null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //      Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_LONG).show();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                project = gson.fromJson(response.toString(),Project.class);
                Log.i("project.getNombre(): ", project.getNombre());
                servicios_description.setText(project.getDescripcionServicios());
                acabados_description.setText(project.getDescripcionAcabados());
                infraestructura_description.setText(project.getDescripcionPabellones());
                project_title_description.setText(project.getNombre());
                project_resume_description.setText(project.getDescripcion());
                Log.i("indicadores", project.getIndicadores() + "");

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

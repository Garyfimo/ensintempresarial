package empresarial.synapsesdk.com.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.Config;
import empresarial.synapsesdk.com.model.Project;
import empresarial.synapsesdk.com.model.Screen;
import empresarial.synapsesdk.com.model.User;
import empresarial.synapsesdk.com.service.VolleyApplication;
import empresarial.synapsesdk.com.util.Utilitario;


public class DescriptionActivity extends BaseActivity {


    @InjectView(R.id.servicios_description)
    TextView servicios_description;
    @InjectView(R.id.acabados_description)
    TextView acabados_description;
    @InjectView(R.id.infraestructura_description)
    TextView infraestructura_description;
    @InjectView(R.id.project_title_description)
    TextView project_title_description;
    @InjectView(R.id.project_resume_description)
    TextView project_resume_description;
    @InjectView(R.id.project_image)
    ImageView project_image;
    @InjectView(R.id.rendimiento)
    TextView rendimiento;
    @InjectView(R.id.infraestructura)
    TextView infraestructura;
    @InjectView(R.id.disponibilidad)
    TextView disponibilidad;
    public ArrayList<User> lista_compartir;

    String project_id;
    String user_name;
    Project project;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent intent = getIntent();
        project_id = intent.getStringExtra("id");
        user_name = intent.getStringExtra("username");
        setProject(intent.getStringExtra("id"));
        ButterKnife.inject(DescriptionActivity.this);

        lista_compartir = new ArrayList<User>();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return Utilitario.VER;
    }

    @Override
    public String getScreenName() {
        return Screen.DETALLE.toString();
    }

    @Override
    public String getScreenUrl() {
        return "api/proyectos/"+project_id;
    }


    void setProject(String id) {
        String url = String.format(Config.BASE_URL + "proyectos/%s",
                id);
       // Log.i("id from context", id);
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //      Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_LONG).show();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                project = gson.fromJson(response.toString(), Project.class);
           //     Log.i("project.getNombre(): ", project.getNombre());
                servicios_description.setText(project.getDescripcionServicios());
                acabados_description.setText(project.getDescripcionAcabados());
                infraestructura_description.setText(project.getDescripcionPabellones());
                project_title_description.setText(project.getNombre());
                project_resume_description.setText(project.getDescripcion());
                disponibilidad.setText(project.getIndicadores()[0].getValor());
                rendimiento.setText(project.getIndicadores()[1].getValor());
                infraestructura.setText(project.getIndicadores()[2].getValor());
                Picasso.with(DescriptionActivity.this).load(project.getImagenComplejoURL()).into(project_image);
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

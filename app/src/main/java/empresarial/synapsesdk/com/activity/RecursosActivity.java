package empresarial.synapsesdk.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.Config;
import empresarial.synapsesdk.com.activity.R;
import empresarial.synapsesdk.com.adapter.RecursoAdapter;
import empresarial.synapsesdk.com.model.Project;
import empresarial.synapsesdk.com.model.Recurso;
import empresarial.synapsesdk.com.model.Screen;
import empresarial.synapsesdk.com.service.VolleyApplication;
import empresarial.synapsesdk.com.util.Utilitario;

public class RecursosActivity extends BaseActivity {

    Recurso recurso;
    @InjectView(R.id.recursos_gridview)
    GridView recursos_gridview;
    ArrayList<Recurso> lista_recurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recursos);
        ButterKnife.inject(RecursosActivity.this);
        lista_recurso = new ArrayList<Recurso>();
        setRecurso(project_id);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return Utilitario.RECURSOS;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getScreenName() {
        return Screen.RECURSOS.toString();
    }

    @Override
    public String getScreenUrl() {
        return "api/proyectos/"+project_id+"/recursos";
    }

    void setRecurso(String id) {
        String url = String.format(Config.BASE_URL + "proyectos/%s/recursos",
                id);

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                try {
                    JSONArray recursos = response.getJSONArray("recursos");

                    for(int i = 0; i < recursos.length(); i++) {
                        JSONObject recursoJson = (JSONObject) recursos.get(i);
                        Recurso recurso = gson.fromJson(recursoJson.toString(),Recurso.class);
                        Log.i("cantidad: ",recurso.getCantidad());
                        Log.i("titulo: ",recurso.getDescripcion());
                        lista_recurso.add(recurso);
                    }
                   recursos_gridview.setAdapter(new RecursoAdapter(lista_recurso,RecursosActivity.this));
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

    }
}

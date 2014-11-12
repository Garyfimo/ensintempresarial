package empresarial.synapsesdk.com.activity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.Config;
import empresarial.synapsesdk.com.adapter.PlanAdapter;
import empresarial.synapsesdk.com.model.Inversion;
import empresarial.synapsesdk.com.model.Plan;
import empresarial.synapsesdk.com.model.Screen;
import empresarial.synapsesdk.com.service.VolleyApplication;
import empresarial.synapsesdk.com.util.Utilitario;

public class ProgressActivity extends BaseActivity {

    public ArrayList<Inversion> lista_inversion;


    @InjectView(R.id.invertido_servicios)
    TextView invertido_servicios;
    @InjectView(R.id.invertido_materiales)
    TextView invertido_materiales;
    @InjectView(R.id.invertido_licencias)
    TextView invertido_licencias;
    @InjectView(R.id.invertido_total)
    TextView invertido_total;
    @InjectView(R.id.invertido_salarios)
    TextView invertido_salarios;

    @InjectView(R.id.faltante_servicios)
    TextView faltante_servicios;
    @InjectView(R.id.faltante_materiales)
    TextView faltante_materiales;
    @InjectView(R.id.faltante_licencias)
    TextView faltante_licencias;
    @InjectView(R.id.faltante_total)
    TextView faltante_total;
    @InjectView(R.id.faltante_salarios)
    TextView faltante_salarios;

    @InjectView(R.id.total_materiales)
    TextView total_materiales;
    @InjectView(R.id.total_licencias)
    TextView total_licencias;
    @InjectView(R.id.total_total)
    TextView total_total;
    @InjectView(R.id.total_servicios)
    TextView total_servicios;
    @InjectView(R.id.total_salarios)
    TextView total_salarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.inject(ProgressActivity.this);

        setInversion(project_id);
    }


    public void setInversion(String id) {
        lista_inversion = new ArrayList<Inversion>();
        String url = String.format(Config.BASE_URL + "proyectos/%s/inversion",
                id);
        Log.i("project_id", id + "");
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //      Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_LONG).show();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                try {
                    JSONArray inversiones = response.getJSONArray("inversion");
                    Log.i("Numero de planes", inversiones.length() + "");
                    for (int i = 0; i < inversiones.length(); i++) {
                        JSONObject proyectoJson = (JSONObject) inversiones.get(i);
                        Inversion inversion = gson.fromJson(proyectoJson.toString(), Inversion.class);

                        lista_inversion.add(inversion);
                        fillInversiones(lista_inversion);

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

    }

    public void fillInversiones(ArrayList<Inversion> inversions) {
        Double invertido = 0.00, faltante = 0.00, total = 0.00;
        for (int i = 0; i < inversions.size(); i++) {
            if ("Material".equals(inversions.get(i).getNombre())) {
                invertido_materiales.setText(inversions.get(i).getTotalDestinado());
                faltante_materiales.setText(inversions.get(i).getTotalUsado());
                total_materiales.setText(sumarMontos(inversions.get(i).getTotalDestinado(), inversions.get(i).getTotalUsado()));
            } else if ("Licencias".equals(inversions.get(i).getNombre())) {
                invertido_licencias.setText(inversions.get(i).getTotalDestinado());
                faltante_licencias.setText(inversions.get(i).getTotalUsado());
                total_licencias.setText(sumarMontos(inversions.get(i).getTotalDestinado(), inversions.get(i).getTotalUsado()));
            } else if ("Salarios".equals(inversions.get(i).getNombre())) {

                invertido_salarios.setText(inversions.get(i).getTotalDestinado());
                faltante_salarios.setText(inversions.get(i).getTotalUsado());
                total_salarios.setText(sumarMontos(inversions.get(i).getTotalDestinado(), inversions.get(i).getTotalUsado()));
            } else if ("Servicios".equals(inversions.get(i).getNombre())) {
                invertido_servicios.setText(inversions.get(i).getTotalDestinado());
                faltante_servicios.setText(inversions.get(i).getTotalUsado());
                total_servicios.setText(sumarMontos(inversions.get(i).getTotalDestinado(), inversions.get(i).getTotalUsado()));
            }
            invertido += invertido + Double.parseDouble(inversions.get(i).getTotalDestinado());
            faltante += faltante+ Double.parseDouble(inversions.get(i).getTotalDestinado());
            }
        invertido_total.setText(invertido+"");
        faltante_total.setText(faltante+"");
        total  = invertido + faltante;

    }

    public String sumarMontos(String invertido, String faltante) {
        return (Double.parseDouble(invertido) + Double.parseDouble(faltante)) + "";
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return Utilitario.AVANCE;
    }

    @Override
    public String getScreenName() {
        return Screen.AVANCE.toString();
    }

    @Override
    public String getScreenUrl() {
        return "api/proyectos/" + project_id + "/plan";
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
}

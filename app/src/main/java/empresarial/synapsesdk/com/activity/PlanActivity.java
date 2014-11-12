package empresarial.synapsesdk.com.activity;

import android.os.Bundle;
import android.util.Log;
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
import empresarial.synapsesdk.com.adapter.PlanAdapter;
import empresarial.synapsesdk.com.model.Plan;
import empresarial.synapsesdk.com.model.Screen;
import empresarial.synapsesdk.com.service.VolleyApplication;
import empresarial.synapsesdk.com.util.Utilitario;

public class PlanActivity extends BaseActivity {

    @InjectView(R.id.grid_plan)
    GridView gridView;

    public ArrayList<Plan> lista_planes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ButterKnife.inject(PlanActivity.this);


        setPlan(project_id);


    }

    @Override
    protected int getSelfNavDrawerItem() {
        return Utilitario.PLAN;
    }

    void setPlan(String id)
    {
        lista_planes = new ArrayList<Plan>();
        String url = String.format(Config.BASE_URL + "proyectos/%s/plan",
                id);
        Log.i("project_id",id+"");
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //      Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_LONG).show();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                try{
                    JSONArray planes = response.getJSONArray("fases");
                    Log.i("Numero de planes", planes.length()+"");
                    for(int i = 0; i < planes.length(); i++)
                    {
                        JSONObject proyectoJson = (JSONObject) planes.get(i);
                        Plan plan = gson.fromJson(proyectoJson.toString(),Plan.class);

                       lista_planes.add(plan);
                        Log.i("Nombre de Plan", planes.get(i)+"");
                    }

                    gridView.setAdapter(new PlanAdapter(PlanActivity.this, lista_planes));

                }catch (JSONException e) {
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

    @Override
    public String getScreenName() {
        return Screen.PLAN.toString();
    }

    @Override
    public String getScreenUrl() {
        return "api/"+project_id+"/plan";
    }
}

package empresarial.synapsesdk.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.Config;
import empresarial.synapsesdk.com.adapter.GalleryAdapter;
import empresarial.synapsesdk.com.model.Galeria;
import empresarial.synapsesdk.com.model.Screen;
import empresarial.synapsesdk.com.service.VolleyApplication;
import empresarial.synapsesdk.com.util.Utilitario;

public class GalleryActivity extends BaseActivity {

    @InjectView(R.id.grid)
    GridView galleryGrid;
    GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.inject(this);
        adapter = new GalleryAdapter(this);
        galleryGrid.setAdapter(adapter);
        galleryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Galeria item = (Galeria) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(GalleryActivity.this, ImageActivity.class);
                intent.putExtra("id", Integer.toString(item.id));
                intent.putExtra("url", item.url);
                startActivity(intent);
            }
        });
        getGallery();

    }

    private void getGallery() {
        String url = String.format(Config.BASE_URL + "proyectos/%s/galeria", project_id);
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Galeria[] list = null;
                try {
                    list = gson.fromJson(response.getJSONArray("galeria").toString(), Galeria[].class);
                } catch (JSONException ignored) {
                }
                if (list != null) {
                    adapter.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR: ", error.toString());
            }
        });


        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return Utilitario.GALERIA;
    }

    @Override
    public String getScreenName() {
        return Screen.GALERIA.toString();
    }

    @Override
    public String getScreenUrl() {
        return "api/proyectos/" + project_id + "/galeria";
    }
}

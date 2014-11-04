package empresarial.synapsesdk.com.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import empresarial.synapsesdk.com.adapter.ProjectAdapter;
import empresarial.synapsesdk.com.model.Project;
import empresarial.synapsesdk.com.model.User;
import empresarial.synapsesdk.com.service.VolleyApplication;


public class DescriptionActivity extends Activity implements
        AdapterView.OnItemClickListener {

    //@InjectView(R.id.servicios_title) TextView servicios_title;
    @InjectView(R.id.servicios_description)
    TextView servicios_description;
    //@InjectView(R.id.acabados_title) TextView acabados_title;
    @InjectView(R.id.acabados_description)
    TextView acabados_description;
    //@InjectView(R.id.infraestructura_title) TextView infraestructura_title;
    @InjectView(R.id.infraestructura_description)
    TextView infraestructura_description;
    @InjectView(R.id.project_title_description)
    TextView project_title_description;
    @InjectView(R.id.project_subtitle_description)
    TextView project_subtitle_description;
    @InjectView(R.id.project_resume_description)
    TextView project_resume_description;
    @InjectView(R.id.project_image)
    ImageView project_image;

    public ArrayList<String> lista_compartir;
    private DrawerLayout mDrawer;
    private ListView mDrawerOptions;
    private static final String[] values = {"Drawer 1", "Drawer 2", "Drawer 3"};


    String project_id;
    Project project;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        mTitle = mDrawerTitle = getTitle();
        Intent intent = getIntent();
        project_id = intent.getStringExtra("id");
        setProject(intent.getStringExtra("id"));
        ButterKnife.inject(DescriptionActivity.this);

        lista_compartir = new ArrayList<String>();
        setupNavDrawer();
    }

    public void setupNavDrawer() {
        mDrawerOptions = (ListView) findViewById(R.id.left_drawer);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        mDrawer.setDrawerListener(mDrawerToggle);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerOptions.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values));
        mDrawerOptions.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (id) {
            case R.id.button_share:
                final Dialog dialog = new Dialog(DescriptionActivity.this);
                dialog.setContentView(R.layout.share_layout);
                dialog.setTitle("Compartir con...");

                Button button_enviar = (Button) dialog.findViewById(R.id.button_enviar);
                Button button_cancelar = (Button) dialog.findViewById(R.id.button_cancelar);
                RadioGroup radio_share = (RadioGroup) dialog.findViewById(R.id.radio_share);
                RadioButton radio_choose = (RadioButton) dialog.findViewById(radio_share.getCheckedRadioButtonId());
                AutoCompleteTextView search = (AutoCompleteTextView) dialog.findViewById(R.id.search);
                GridView usuarios = (GridView) dialog.findViewById(R.id.grid_share);
                getUsuarios(search, usuarios);
                Log.i("radio", radio_share + "");
                String[] lista_autocomplete = new String[lista_compartir.size()];
                fromArrayListToArray(lista_compartir, lista_autocomplete);


                Toast.makeText(DescriptionActivity.this, radio_choose.getText(), Toast.LENGTH_LONG).show();
                ArrayAdapter adapter = new ArrayAdapter
                        (this, android.R.layout.simple_list_item_1, lista_autocomplete);

                for (int i = 0; i < lista_compartir.size(); i++) {
                    Log.i("Nombre", lista_compartir.get(i));
                }

                //search.setAdapter(adapter);


                button_enviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Toast.makeText(DescriptionActivity.this,"Leslie <3",Toast.LENGTH_LONG).show();


                    }
                });
                button_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    void getUsuarios(final AutoCompleteTextView search, final GridView usuarios_gridview) {
        String url = "http://upcsistemas.com/ensint/api/usuarios";
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();


                Log.i("JSONObject", response.toString());

                try {
                    Log.i("hayMasItems", response.getString("hayMasItems"));
                    boolean a = response.getString("hayMasItems").equals("false");
                    Log.i("boolean", a + "");
                    if (!response.getString("hayMasItems").equals("false")) {

                    } else {
                        Log.i("JSONObject2", response.getString("usuarios") + "");
                        Log.i("JSONObject2[0]", response.getJSONArray("usuarios").get(0) + "");
                        JSONArray usuarios = response.getJSONArray("usuarios");

                        for (int i = 0; i < usuarios.length(); i++) {
                            JSONObject usuarioJson = (JSONObject) usuarios.get(i);
                            User usuario = gson.fromJson(usuarioJson.toString(), User.class);
                            lista_compartir.add(usuario.getNombre());

                            Log.i("id", lista_compartir.get(i) + "");
                        }
                        //gridView.setAdapter(new ProjectAdapter(ProjectActivity.this,lista_proyectos));
                        search.setAdapter(new ArrayAdapter
                                (DescriptionActivity.this, android.R.layout.simple_list_item_1, lista_compartir));

                        final ArrayList<String> nombres = new ArrayList<String>();

                        final ArrayAdapter<String> adapter_gridview = new ArrayAdapter<String>(DescriptionActivity.this,
                                android.R.layout.simple_list_item_1, nombres);

                        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Log.i("Adapter", adapterView.getAdapter().getItem(i).toString());
                                nombres.add(adapterView.getAdapter().getItem(i).toString());
                                usuarios_gridview.setAdapter(adapter_gridview);
                                search.setText("");
                            }
                        });


                        usuarios_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                nombres.remove(i);
                                adapter_gridview.notifyDataSetChanged();
                            }
                        });

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


    void setProject(String id) {
        String url = String.format("http://upcsistemas.com/ensint/api/proyectos/%s",
                id);
        Log.i("id from context", id);
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //      Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_LONG).show();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                project = gson.fromJson(response.toString(), Project.class);
                Log.i("project.getNombre(): ", project.getNombre());
                servicios_description.setText(project.getDescripcionServicios());
                acabados_description.setText(project.getDescripcionAcabados());
                infraestructura_description.setText(project.getDescripcionPabellones());
                project_title_description.setText(project.getNombre());
                project_resume_description.setText(project.getDescripcion());
                Picasso.with(DescriptionActivity.this).load(project.getImagenComplejoURL()).into(project_image);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "Pulsado " + values[i], Toast.LENGTH_SHORT).show();
        Intent intent;
        switch (i) {
            case 0:
                intent = new Intent(DescriptionActivity.this, ProgressActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;

        }
        mDrawer.closeDrawers();

    }

    void fromArrayListToArray(ArrayList<String> arrayList, String[] array) {
        for (int i = 0; i < arrayList.size(); i++) {
            array[i] = arrayList.get(i);
        }
    }

}

package empresarial.synapsesdk.com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import empresarial.synapsesdk.com.Config;
import empresarial.synapsesdk.com.adapter.DispositivoAdapter;
import empresarial.synapsesdk.com.adapter.UserAdapter;
import empresarial.synapsesdk.com.model.Body;
import empresarial.synapsesdk.com.model.Dispositivo;
import empresarial.synapsesdk.com.model.User;
import empresarial.synapsesdk.com.service.GsonRequest;
import empresarial.synapsesdk.com.service.VolleyApplication;
import empresarial.synapsesdk.com.util.AccountUtils;
import empresarial.synapsesdk.com.util.Utilitario;

public abstract class BaseActivity extends Activity {

    private UserAdapter userAdapter;
    private DispositivoAdapter dispositivoAdapter;
    public ArrayList<User> lista_compartir_usuarios;
    public ArrayList<Dispositivo> lista_compartir_dispositivos;
    public boolean shareWithUsers = true;
    String project_id;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ArrayList<Integer> mNavDrawerItems;
    private ViewGroup mDrawerItemsListContainer;
    private View mNavDrawerItemViews[];
    private Handler mHandler;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userAdapter = new UserAdapter(this, android.R.layout.simple_list_item_1);
        dispositivoAdapter = new DispositivoAdapter(this, android.R.layout.simple_list_item_1);
        lista_compartir_usuarios = new ArrayList<User>();
        lista_compartir_dispositivos = new ArrayList<Dispositivo>();
        project_id = getIntent().getStringExtra("id");
        user_name = AccountUtils.getAccountName(this);
        mNavDrawerItems = new ArrayList<Integer>();
        mHandler = new Handler();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mTitle = mDrawerTitle = getTitle();
        setupNavDrawer();
    }

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private void setupNavDrawer() {
        int i = getSelfNavDrawerItem();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navdrawer);
        if (mDrawerLayout != null) {
            if (i == -1) {
                View view = mDrawerLayout.findViewById(R.id.navdrawer);
                if (view != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                mDrawerLayout = null;
                return;
            }

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.ic_drawer,
                    R.string.app_name,
                    R.string.app_name) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    invalidateOptionsMenu();
                    getActionBar().setDisplayUseLogoEnabled(false);
                    getActionBar().setDisplayShowTitleEnabled(true);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    invalidateOptionsMenu();
                    getActionBar().setDisplayUseLogoEnabled(true);
                    getActionBar().setDisplayShowTitleEnabled(false);
                }
            };

            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
            populateNavDrawer();
            mDrawerToggle.syncState();
            mDrawerLayout.setDrawerListener(mDrawerToggle);
        }
    }

    private void populateNavDrawer() {
        mNavDrawerItems.clear();
        mNavDrawerItems.add(Utilitario.HOME);
        mNavDrawerItems.add(Utilitario.VER);
        mNavDrawerItems.add(Utilitario.UBICACION);
        mNavDrawerItems.add(Utilitario.GALERIA);
        mNavDrawerItems.add(Utilitario.PLAN);
        mNavDrawerItems.add(Utilitario.RECURSOS);
        mNavDrawerItems.add(Utilitario.INVERSION);
        mNavDrawerItems.add(Utilitario.PABELLONES);
        createNavDrawerItems();
    }

    private void createNavDrawerItems() {
        mDrawerItemsListContainer = (ViewGroup) findViewById(R.id.navdrawer_items_list);
        if (mDrawerItemsListContainer != null) {
            mNavDrawerItemViews = new View[mNavDrawerItems.size()];
            mDrawerItemsListContainer.removeAllViews();
            int i = 0;
            for (Integer mNavDrawerItem : mNavDrawerItems) {
                mNavDrawerItemViews[i] = makeNavDrawerItem(mNavDrawerItem, mDrawerItemsListContainer);
                mDrawerItemsListContainer.addView(mNavDrawerItemViews[i]);
                i++;
            }
        }
    }

    private String[] NAVDRAWER_TITLE_RES_ID = {
            "Inicio", "Ver detalle", "Ubicacion", "Galeria", "Plan de proyecto", "Recursos", "Inversión", "Pabellones"
    };
    // references to our images
    private Integer[] NAVDRAWER_ICON_RES_ID = {
            R.drawable.icon_home, R.drawable.icon_ver,
            R.drawable.icon_ubicacion, R.drawable.icon_galeria,
            R.drawable.icon_grafica_barras, R.drawable.icon_recursos,
            R.drawable.icon_grafica_pastel, R.drawable.icon_edificio
    };

    private View makeNavDrawerItem(final int itemId, ViewGroup viewgroup) {
        boolean flag;
        int layoutIdView;
        View view;

        flag = getSelfNavDrawerItem() == itemId;
        layoutIdView = R.layout.navdrawer_item;

        view = getLayoutInflater().inflate(layoutIdView, viewgroup, false);
        ImageView imageview = (ImageView) view.findViewById(R.id.icon);
        TextView textview = (TextView) view.findViewById(R.id.title);

        int idIcon;
        String idTitle;
        int visibility;

        if (itemId >= 0 && itemId < NAVDRAWER_ICON_RES_ID.length) {
            idIcon = NAVDRAWER_ICON_RES_ID[itemId];
        } else {
            idIcon = 0;
        }
        if (itemId >= 0 && itemId < NAVDRAWER_TITLE_RES_ID.length) {
            idTitle = NAVDRAWER_TITLE_RES_ID[itemId];
        } else {
            idTitle = "";
        }
        visibility = View.VISIBLE;
        if (idIcon <= 0) {
            visibility = View.GONE;
        }
        imageview.setVisibility(visibility);

        if (idIcon > 0) {
            imageview.setImageResource(idIcon);
        }

        textview.setText(idTitle);

        formatNavDrawerItem(view, itemId, flag);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onNavDrawerItemClicked(itemId);
            }
        });
        return view;
    }

    private void onNavDrawerItemClicked(final int itemId) {
        if (itemId == getSelfNavDrawerItem()) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNavDrawerItem(itemId);
            }
        }, 250L);
        setSelectedNavDrawerItem(itemId);
        View mainView = findViewById(R.id.main_content);
        if (mainView != null) {
            mainView.animate().alpha(0.0F).setDuration(150L);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void setSelectedNavDrawerItem(int itemId) {
        if (mNavDrawerItemViews != null) {
            int j = 0;
            while (j < mNavDrawerItemViews.length) {
                if (j < mNavDrawerItems.size()) {
                    int k = mNavDrawerItems.get(j);
                    View view = mNavDrawerItemViews[j];
                    boolean flag = itemId == k;
                    formatNavDrawerItem(view, k, flag);
                }
                j++;
            }
        }
    }

    private void formatNavDrawerItem(View view, int itemId, boolean selected) {
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView title = (TextView) view.findViewById(R.id.title);
        int titleColor;
        int iconColorFilter;
        if (selected) {
            titleColor = getResources().getColor(R.color.navdrawer_icon_tint_selected);
        } else {
            titleColor = getResources().getColor(R.color.navdrawer_icon_tint);
        }
        title.setTextColor(titleColor);

        if (selected) {
            iconColorFilter = getResources().getColor(R.color.navdrawer_icon_tint_selected);
        } else {
            iconColorFilter = getResources().getColor(R.color.navdrawer_icon_tint);
        }
        icon.setColorFilter(iconColorFilter);
    }

    private void goToNavDrawerItem(int i) {
        Intent intent = new Intent();
        intent.putExtra("id", project_id);
        switch (i) {
            case Utilitario.HOME:
                Intent intentHome = new Intent(BaseActivity.this, ProjectActivity.class);
                startActivity(intentHome);
                finish();
                break;
            case Utilitario.VER:
                Intent intentVer = new Intent(BaseActivity.this, DescriptionActivity.class);
                intentVer.putExtra("id", project_id);
                startActivity(intentVer);
                finish();
                break;
            case Utilitario.UBICACION:
                intent.setClass(BaseActivity.this, UbicacionActivity.class);
                startActivity(intent);
                finish();
                break;
            case Utilitario.GALERIA:
                intent.setClass(BaseActivity.this, GalleryActivity.class);
                startActivity(intent);
                finish();
                break;
            case Utilitario.PLAN:
                intent.setClass(BaseActivity.this, PlanActivity.class);
                startActivity(intent);
                finish();
                break;
            case Utilitario.RECURSOS:
                intent.setClass(BaseActivity.this, RecursosActivity.class);
                startActivity(intent);
                finish();
                break;
            case Utilitario.INVERSION:
                Intent intentAvance = new Intent(BaseActivity.this, ProgressActivity.class);
                intentAvance.putExtra("id", project_id);
                startActivity(intentAvance);
                finish();
                break;
            case Utilitario.PABELLONES:
                Intent intentPabellon = new Intent(BaseActivity.this, PabellonListActivity.class);
                intentPabellon.putExtra("id", project_id);
                startActivity(intentPabellon);
                finish();
                break;
            default:
                break;
        }
    }


    protected abstract int getSelfNavDrawerItem();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        switch (id) {
            case R.id.button_share:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View dialog = LayoutInflater.from(this).inflate(R.layout.share_layout, null);
                builder.setTitle("Compartir con...");
                builder.setView(dialog);
                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        postShareScreen();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userAdapter.clear();
                        userAdapter.notifyDataSetChanged();
                    }
                });


                // old
                //final Dialog dialog = new Dialog(BaseActivity.this);
                //dialog.setContentView(R.layout.share_layout);
                //dialog.setTitle("Compartir con...");

                //Button button_enviar = (Button) dialog.findViewById(R.id.button_enviar);
                //Button button_cancelar = (Button) dialog.findViewById(R.id.button_cancelar);
                RadioGroup radio_share = (RadioGroup) dialog.findViewById(R.id.radio_share);
                final AutoCompleteTextView search = (AutoCompleteTextView) dialog.findViewById(R.id.search);
                final GridView grid = (GridView) dialog.findViewById(R.id.grid_share);
                getUsuarios(search, grid);
                String[] lista_autocomplete = new String[lista_compartir_usuarios.size()];
                fromArrayListToArrayUser(lista_compartir_usuarios, lista_autocomplete);

                radio_share.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton radio_button = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                        if ("Usuario".equals(radio_button.getText() + "")) {
                            shareWithUsers = true;
                            lista_compartir_dispositivos.clear();
                            dispositivoAdapter.clear();
                            search.setText("");
                            Log.i("USUARIO", radio_button.getText() + "");
                            getUsuarios(search, grid);
                            String[] lista_autocomplete = new String[lista_compartir_usuarios.size()];
                            fromArrayListToArrayUser(lista_compartir_usuarios, lista_autocomplete);

                        } else if ("TV".equals(radio_button.getText() + "")) {
                            shareWithUsers = false;
                            lista_compartir_usuarios.clear();
                            userAdapter.clear();
                            search.setText("");
                            Log.i("TV", radio_button.getText() + "");
                            getDispositivos(search, grid);
                            String[] lista_autocomplete = new String[lista_compartir_dispositivos.size()];
                            fromArrayListToArrayDispositivo(lista_compartir_dispositivos, lista_autocomplete);
                        }
                    }
                });

                builder.show();

                /*button_enviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        postShareScreen();
                        dialog.dismiss();
                    }
                });
                button_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userAdapter.clear();
                        userAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                */
                //dialog.show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    void fromArrayListToArrayUser(ArrayList<User> arrayList, String[] array) {
        for (int i = 0; i < arrayList.size(); i++) {
            array[i] = arrayList.get(i).toString();
        }
    }

    void fromArrayListToArrayDispositivo(ArrayList<Dispositivo> arrayList, String[] array) {
        for (int i = 0; i < arrayList.size(); i++) {
            array[i] = arrayList.get(i).toString();
        }
    }

    void getUsuarios(final AutoCompleteTextView search, final GridView usuarios_gridview) {
        String url = Config.BASE_URL + "usuarios";
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                lista_compartir_usuarios.clear();

                try {
                    boolean a = response.getString("hayMasItems").equals("false");
                    if (!response.getString("hayMasItems").equals("false")) {

                    } else {
                        final JSONArray usuarios = response.getJSONArray("usuarios");

                        for (int i = 0; i < usuarios.length(); i++) {
                            JSONObject usuarioJson = (JSONObject) usuarios.get(i);
                            User usuario = gson.fromJson(usuarioJson.toString(), User.class);
                            lista_compartir_usuarios.add(usuario);
                        }
                        search.setAdapter(new ArrayAdapter<User>(BaseActivity.this, android.R.layout.simple_list_item_1, lista_compartir_usuarios));

                        //final ArrayList<String> nombres = new ArrayList<String>();

                        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                // Log.i("Adapter", adapterView.getAdapter().getItem(i).toString());
                                userAdapter.add(((User) adapterView.getAdapter().getItem(i)));
                                usuarios_gridview.setAdapter(userAdapter);
                                search.setText("");
                            }
                        });


                        usuarios_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //nombres.remove(i);
                                userAdapter.remove((User) adapterView.getAdapter().getItem(i));
                                userAdapter.notifyDataSetChanged();
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
    }

    void getDispositivos(final AutoCompleteTextView search, final GridView dispositivos_gridview) {

        String url = Config.BASE_URL + "dispositivos";
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                lista_compartir_usuarios.clear();

                try {
                    boolean a = response.getString("hayMasItems").equals("false");
                    if (!response.getString("hayMasItems").equals("false")) {

                    } else {
                        final JSONArray dispositivos = response.getJSONArray("dispositivos");

                        for (int i = 0; i < dispositivos.length(); i++) {
                            JSONObject dispositivoJson = (JSONObject) dispositivos.get(i);
                            Dispositivo dispositivo = gson.fromJson(dispositivoJson.toString(), Dispositivo.class);
                            lista_compartir_dispositivos.add(dispositivo);
                        }
                        search.setAdapter(new ArrayAdapter<Dispositivo>(BaseActivity.this, android.R.layout.simple_list_item_1, lista_compartir_dispositivos));

                        final ArrayList<String> nombres = new ArrayList<String>();

                        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Log.i("Adapter", adapterView.getAdapter().getItem(i).toString());
                                dispositivoAdapter.add(((Dispositivo) adapterView.getAdapter().getItem(i)));
                                dispositivos_gridview.setAdapter(dispositivoAdapter);
                                search.setText("");
                            }
                        });


                        dispositivos_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //nombres.remove(i);
                                dispositivoAdapter.remove((Dispositivo) adapterView.getAdapter().getItem(i));
                                dispositivoAdapter.notifyDataSetChanged();
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
    }

    public abstract String getScreenName();

    public abstract String getScreenUrl();

    void postShareScreen() {
        String url = String.format(Config.BASE_URL + "notificacion");
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ArrayList<String> usuarios_lista = new ArrayList<String>();
        ArrayList<String> dispositivos_lista = new ArrayList<String>();

        if(shareWithUsers) {
            for (int i = 0; i < userAdapter.getCount(); i++) {
                usuarios_lista.add(userAdapter.getItem(i).getUsername());
            }
        } else {
            for (int i = 0; i < dispositivoAdapter.getCount(); i++) {
                dispositivos_lista.add(dispositivoAdapter.getItem(i).getIp());
            }
        }

        String user_name = AccountUtils.getAccountName(this);
        Body body = new Body(dispositivos_lista, usuarios_lista, getScreenName(), getScreenUrl(), "", user_name, project_id);
        //String body = String.format("{\"gcmCode\":\"%s\"}", regid);

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

    }
}

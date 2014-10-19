package empresarial.synapsesdk.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import empresarial.synapsesdk.com.model.User;
import empresarial.synapsesdk.com.service.VolleyApplication;


public class LoginActivity extends Activity {


    @InjectView(R.id.user_text) EditText user_text;
    @InjectView(R.id.password_text) EditText password_text;
    @InjectView(R.id.login_button) Button login_button;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(LoginActivity.this);

    }

    @OnClick(R.id.login_button)
    public void onClick()
    {

    //  Toast.makeText(LoginActivity.this,"hola",Toast.LENGTH_LONG).show();
    //  String url = "http://upcsistemas.com/ensint/api/auth?user=" + user_text.getText().toString() + "&pass=" + password_text.getText().toString();
    String url = String.format("http://upcsistemas.com/ensint/api/auth?user=%s&pass=%s",
            user_text.getText().toString(),password_text.getText().toString());
    JsonObjectRequest request = new JsonObjectRequest(url,null,new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
            //      Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_LONG).show();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            user = gson.fromJson(response.toString(),User.class);
            Log.i("user.getNombre(): ", user.getNombre());
            Log.i("response: ", response.toString());
            if(user.getIdUsuario() != -1) {
                // Toast.makeText(LoginActivity.this, "LOGIN", Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, ProjectActivity.class);
                i.putExtra("user",user.getUsername());
                startActivity(i);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
}

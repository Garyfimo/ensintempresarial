package empresarial.synapsesdk.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import empresarial.synapsesdk.com.activity.R;
import empresarial.synapsesdk.com.model.Screen;
import empresarial.synapsesdk.com.util.Utilitario;

public class ProgressActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
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
        return "api/proyectos/" + project_id + "/plan" ;
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

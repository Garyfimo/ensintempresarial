package empresarial.synapsesdk.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DescriptionActivity extends Activity {

    @InjectView(R.id.servicios_title) TextView servicios_title;
    @InjectView(R.id.servicios_description) TextView servicios_description;
    @InjectView(R.id.acabados_title) TextView acabados_title;
    @InjectView(R.id.acabados_description) TextView acabados_description;
    @InjectView(R.id.infraestructura_title) TextView infraestructura_title;
    @InjectView(R.id.infraestructura_description) TextView infraestructura_description;
    @InjectView(R.id.project_title_description) TextView project_title_description;
    @InjectView(R.id.project_subtitle_description) TextView project_subtitle_description;
    @InjectView(R.id.project_resume_description) TextView project_resume_description;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);


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
}

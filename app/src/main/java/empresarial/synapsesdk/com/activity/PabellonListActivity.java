package empresarial.synapsesdk.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import empresarial.synapsesdk.com.model.Screen;
import empresarial.synapsesdk.com.util.Utilitario;

public class PabellonListActivity extends BaseActivity
        implements PabellonListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pabellon_twopane);

        mTwoPane = true;

        PabellonListFragment fragment = ((PabellonListFragment) getFragmentManager()
                .findFragmentById(R.id.pabellon_list));
        fragment.setActivateOnItemClick(true);
        fragment.setProjectId(project_id);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return Utilitario.PABELLONES;
    }

    @Override
    public String getScreenName() {
        return Screen.PABELLONES.toString();
    }

    @Override
    public String getScreenUrl() {
        return "api/proyectos/" + project_id + "/pabellones";
    }

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            /* fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(PabellonDetailFragment.ARG_ITEM_URL, id);
            PabellonDetailFragment fragment = new PabellonDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pabellon_detail_container, fragment)
                    .commit();
                    */

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, PabellonDetailActivity.class);
            detailIntent.putExtra(PabellonDetailFragment.ARG_ITEM_URL, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onChildSelected(String url) {
        Bundle arguments = new Bundle();
        arguments.putString(PabellonDetailFragment.ARG_ITEM_URL, url);
        PabellonDetailFragment fragment = new PabellonDetailFragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction()
                .replace(R.id.pabellon_detail_container, fragment)
                .commit();
    }
}

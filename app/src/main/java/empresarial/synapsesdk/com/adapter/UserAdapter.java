package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import empresarial.synapsesdk.com.activity.R;
import empresarial.synapsesdk.com.model.User;

/**
 * Created by Garyfimo on 11/6/14.
 */
public class UserAdapter extends ArrayAdapter<User> {

    Context context;


    public UserAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(User object) {
        for (int i = 0; i < getCount(); i++)
        {
            User user = getItem(i);
            if(user.getUsername().equals(object.getUsername()))
                return;
        }
        super.add(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}

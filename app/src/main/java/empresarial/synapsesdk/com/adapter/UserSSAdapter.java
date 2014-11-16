package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import empresarial.synapsesdk.com.model.User;

public class UserSSAdapter extends ArrayAdapter<User> {

    public UserSSAdapter(Context context, int resource, int textViewResourceId, User[] objects) {
        super(context, resource, textViewResourceId, objects);
    }


}

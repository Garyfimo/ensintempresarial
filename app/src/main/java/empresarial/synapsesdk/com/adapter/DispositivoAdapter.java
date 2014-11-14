package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import empresarial.synapsesdk.com.model.Dispositivo;
import empresarial.synapsesdk.com.model.User;

/**
 * Created by Garyfimo on 11/11/14.
 */
public class DispositivoAdapter extends ArrayAdapter<Dispositivo> {

    Context context;


    public DispositivoAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Dispositivo object) {
        for (int i = 0; i < getCount(); i++)
        {
            Dispositivo dispositivo = getItem(i);
            if(dispositivo.getNombre().equals(object.getNombre()))
                return;
        }
        super.add(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}

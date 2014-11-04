package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.activity.R;

/**
 * Created by Garyfimo on 10/16/14.
 */
public class MenuAdapter extends BaseAdapter {
    private Context mContext;

    public MenuAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View view, ViewGroup parent) {
        MenuViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view != null){
            holder = (MenuViewHolder) view.getTag();
        }else{
            view = inflater.inflate(R.layout.menu_row, parent, false);
            holder = new MenuViewHolder(view);
            view.setTag(holder);
        }

        holder.menu_title.setText(mText[position]);
        holder.menu_icon.setImageResource(mThumbIds[position]);

        return view;
    }

    private String[] mText = {
            "Inicio", "Ver detalle", "Ubicacion", "Galeria", "Plan de proyecto" , "Recursos", "Avance", "Pabellones"
    };
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.icon_home, R.drawable.icon_ver,
            R.drawable.icon_ubicacion,R.drawable.icon_galeria,
            R.drawable.icon_grafica_barras, R.drawable.icon_recursos,
            R.drawable.icon_grafica_pastel, R.drawable.icon_edificio
    };

    static class MenuViewHolder{
        @InjectView(R.id.icon_menu) ImageView menu_icon;
        @InjectView(R.id.text_menu) TextView menu_title;

        public MenuViewHolder(View view){
            ButterKnife.inject(this, view);
        }

    }
}
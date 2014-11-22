package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.activity.R;
import empresarial.synapsesdk.com.model.Recurso;

/**
 * Created by Garyfimo on 11/16/14.
 */
public class RecursoAdapter extends BaseAdapter {
    ArrayList<Recurso> recursos;
    Context context;


    public RecursoAdapter(ArrayList<Recurso> recursos, Context context) {
        this.recursos = recursos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recursos.size();
    }

    @Override
    public Object getItem(int i) {
        return recursos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecursoViewHolder holder;

        if(view != null){
            holder = (RecursoViewHolder) view.getTag();
        }else{
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.recursos_row, viewGroup, false);
            holder = new RecursoViewHolder(view);
            view.setTag(holder);
        }


        Log.i("Adapter-> cantidad -> ", recursos.get(i).getCantidad());
        holder.cantidad.setText(recursos.get(i).getCantidad());
        holder.titulo.setText(recursos.get(i).getDescripcion());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0 , 10, 0);
        for(int j = 0; j < recursos.get(i).getNumImagenes();j++)
        {
            ImageView image = new ImageView(context);
            image.setLayoutParams(lp);
            Picasso.with(context).load(recursos.get(i).getUrlImagen()).into(image);
            holder.linear.addView(image);
        }
        return view;
    }

    static class RecursoViewHolder{
        @InjectView(R.id.recursos_cantidad)
        TextView cantidad;
        @InjectView(R.id.recursos_title)
        TextView titulo;
        @InjectView(R.id.linear_layout_recursos_row)
        LinearLayout linear;

        public RecursoViewHolder(View view)
        {
            ButterKnife.inject(this,view);
        }
    }
}

package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.internal.id;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.activity.R;
import empresarial.synapsesdk.com.model.Hito;
import empresarial.synapsesdk.com.model.Plan;

/**
 * Created by Garyfimo on 11/9/14.
 */
public class PlanAdapter extends BaseAdapter {

    ArrayList<Plan> plans;
    Context context;

    public PlanAdapter(Context context, ArrayList<Plan> plans ) {
        this.plans = plans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return plans.size();
    }

    @Override
    public Object getItem(int i) {
        return plans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PlanViewHolder holder;

        if(view != null){
            holder = (PlanViewHolder) view.getTag();
        }else{
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.plan_row, viewGroup, false);
            holder = new PlanViewHolder(view);
            view.setTag(holder);
        }

        holder.avance.setProgress(plans.get(i).getPorcAvance());
        holder.fecha_desde.setText(plans.get(i).getFechaInicio());
        holder.fecha_hasta.setText(plans.get(i).getFechaFin());
        holder.title.setText(plans.get(i).getNombre());
        holder.lista_hito.setAdapter(new ArrayAdapter<Hito>(context,android.R.layout.simple_list_item_1,plans.get(i).getHitos()));
        Log.i("Hitos", plans.get(i).getHitos().toString() + "");

        return view;
    }

    static class PlanViewHolder {
    @InjectView(R.id.text_plan_fecha_desde)
    TextView fecha_desde;
    @InjectView(R.id.text_plan_fecha_hasta)
    TextView fecha_hasta;
    @InjectView(R.id.progress_bar_plan)
    ProgressBar avance;
    @InjectView(R.id.text_plan_title)
    TextView title;
    @InjectView(R.id.list_view_plan)
    ListView lista_hito;

        public PlanViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.activity.R;
import empresarial.synapsesdk.com.model.Hito;
import empresarial.synapsesdk.com.model.Plan;
import empresarial.synapsesdk.com.util.Truss;

/**
 * Created by Garyfimo on 11/9/14.
 */
public class PlanAdapter extends BaseAdapter {

    ArrayList<Plan> plans;
    Context context;

    public PlanAdapter(Context context, ArrayList<Plan> plans) {
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

        if (view != null) {
            holder = (PlanViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.plan_row, viewGroup, false);
            holder = new PlanViewHolder(view);
            view.setTag(holder);
        }

        holder.avanceBar.setProgress(plans.get(i).getPorcAvance());
        holder.avanceLabel.setText("Avance al " + plans.get(i).getPorcAvance() + "%");
        holder.fecha.setText(plans.get(i).getFechaInicio());
        buildDateString(holder.fecha, plans.get(i));
        holder.title.setText(plans.get(i).getNombre());
        Log.i("Hitos", plans.get(i).getHitos().toString() + "");

        return view;
    }

    void buildDateString(TextView dateRange, Plan plan){
        Truss t  = new Truss()
                .pushSpan(new ForegroundColorSpan(context.getResources().getColor(android.R.color.darker_gray)))
                .pushSpan(new RelativeSizeSpan(0.7F))
                .append("DEL ")
                .popAllSpan()
                .append(plan.getFechaInicio())
                .pushSpan(new ForegroundColorSpan(context.getResources().getColor(android.R.color.darker_gray)))
                .pushSpan(new RelativeSizeSpan(0.7F))
                .append(" AL ")
                .popAllSpan()
                .append(plan.getFechaFin());

        dateRange.setText(t.build());
    }

    static class PlanViewHolder {
        @InjectView(R.id.text_plan_fecha_desde)
        TextView fecha;
        @InjectView(R.id.progress_bar_plan)
        ProgressBar avanceBar;
        @InjectView(R.id.text_plan_title)
        TextView title;
        @InjectView(R.id.avance)
        TextView avanceLabel;

        public PlanViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import empresarial.synapsesdk.com.activity.R;
import empresarial.synapsesdk.com.model.Project;

/**
 * Created by Garyfimo on 10/19/14.
 */
public class ProjectAdapter extends BaseAdapter {

    ArrayList<Project> projects;

    private Context mContext;

    public ProjectAdapter(Context context, ArrayList<Project> projects) {
        mContext = context;
        this.projects = projects;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int i) {
        return projects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ProjectViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view != null){
            holder = (ProjectViewHolder) view.getTag();
        }else{
            view = inflater.inflate(R.layout.project_row, parent, false);
            holder = new ProjectViewHolder(view);
            view.setTag(holder);
        }

        holder.project_image.setImageResource(projects.get(position).getImage());
        holder.project_title.setText(projects.get(position).getTitle());
        holder.project_subtitle.setText(projects.get(position).getSub_title());
        holder.project_resume.setText(projects.get(position).getResume());

        return view;
    }

    static class ProjectViewHolder{
       @InjectView(R.id.project_image) ImageView project_image;
       @InjectView(R.id.project_title) TextView project_title;
       @InjectView(R.id.project_subtitle) TextView project_subtitle;
       @InjectView(R.id.project_resume) TextView project_resume;

       public ProjectViewHolder(View view){
           ButterKnife.inject(this,view);
       }
    }
}

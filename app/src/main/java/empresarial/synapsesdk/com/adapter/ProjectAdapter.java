package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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

        //Log.i("Estoy en ", "ProjectAdaparte");
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


        Picasso.with(mContext).load(projects.get(position).getImagenComplejoURL()).into(holder.project_image);

        //holder.project_image.setImageDrawable(getImageFromURL(projects.get(position).getImagenComplejoURL()));
        holder.project_title.setText(projects.get(position).getNombre());
       // holder.project_subtitle.setText(projects.get(position).getDescripcion());
        holder.project_resume.setText(projects.get(position).getDescripcion());
        Log.i("getDescription", projects.get(position).getDescripcion());

        //String fontPath = "fonts/Bebas Neue Bold.ttf";
        //Typeface tf = Typeface.createFromAsset(mContext.getAssets(), fontPath);

        //holder.project_title.setTypeface(tf);

        return view;
    }

    static class ProjectViewHolder {
        @InjectView(R.id.project_image)
        ImageView project_image;
        @InjectView(R.id.project_title)
        TextView project_title;
        @InjectView(R.id.project_resume)
        TextView project_resume;


        public ProjectViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}

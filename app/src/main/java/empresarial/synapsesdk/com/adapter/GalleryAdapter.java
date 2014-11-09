package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import empresarial.synapsesdk.com.activity.R;
import empresarial.synapsesdk.com.model.Galeria;

public class GalleryAdapter extends ArrayAdapter<Galeria> {
    public GalleryAdapter(Context context) {
        super(context, android.R.layout.list_content);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ImageView image;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.gallery_row, parent, false);
            image = (ImageView) view.findViewById(R.id.gallery_image);
            view.setTag(image);
        } else {
            image = (ImageView) view.getTag();
        }
        Picasso.with(getContext())
                .load(getItem(position).url)
                .resizeDimen(R.dimen.gallery_item_width, R.dimen.gallery_item_height)
                .centerCrop()
                .into(image);
        return view;
    }
}

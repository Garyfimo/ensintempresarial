package empresarial.synapsesdk.com.activity;

import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

public class PabellonDetailFragment extends Fragment {

    public static final String ARG_ITEM_URL = "item_id";

    private String url;

    public PabellonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_URL)) {
            url = getArguments().getString(ARG_ITEM_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pabellon_detail, container, false);
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(getActivity())
                    .load(url)
                    .into((android.widget.ImageView) rootView.findViewById(R.id.pabellon_detail));
        }

        return rootView;
    }
}

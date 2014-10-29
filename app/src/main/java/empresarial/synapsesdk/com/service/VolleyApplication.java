package empresarial.synapsesdk.com.service;

import android.app.Application;
import android.graphics.Typeface;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyApplication extends Application {

    private static VolleyApplication sInstance;

    private RequestQueue mRequestQueue;

    private Typeface bebasNeueLigthFont;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);

        sInstance = this;

        bebasNeueLigthFont = Typeface.createFromAsset(getAssets(), "fonts/bebas_neue_regular.ttf");
    }

    public synchronized static VolleyApplication getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public Typeface getBebasNeueLigthFont() {
        return bebasNeueLigthFont;
    }
}

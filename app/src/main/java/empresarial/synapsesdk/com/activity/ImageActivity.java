package empresarial.synapsesdk.com.activity;

import empresarial.synapsesdk.com.util.SystemUiHider;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ImageActivity extends ScreenSharingActivity {

    private static final boolean AUTO_HIDE = true;

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    String gallery_id;
    String url;

    ImageView imageGallery;
    private boolean zoomed;
    private int posX;
    private int posY;
    private int widthScreen;
    private int heightScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        gallery_id = getIntent().getStringExtra("id");
        url = getIntent().getStringExtra("url");

        setupActionBar();

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        imageGallery = (ImageView) findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, imageGallery, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        imageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });


        //setDimens("1_2");
        body.url = url;

        if(getIntent().hasExtra("screenSharing")){
            url = getIntent().getStringExtra("url");
            zoomImageFromThumb(getIntent().getStringExtra("pos"));
        }
        Picasso.with(this).load(url).into(imageGallery);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void setDimens(String pos){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        widthScreen = size.x;
        heightScreen = size.y;

        String x = pos.split("_")[1];
        String y = pos.split("_")[0];
        Toast.makeText(this, "x: "+x + " y:" + y, Toast.LENGTH_SHORT).show();
        posX = Integer.parseInt(x);
        posY = Integer.parseInt(y);
    }

    int NUM_DIS_HORIZONTAL = 2;
    int NUM_DIS_VERTICAL = 2;

    @Override
    public void zoomImageFromThumb(String pos) {
        setDimens(pos);
        RelativeLayout rll = (RelativeLayout) findViewById(R.id.container);
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        imageGallery.getLayoutParams().height = heightScreen * NUM_DIS_VERTICAL;//startBounds.height() * 3;
        imageGallery.getLayoutParams().width = widthScreen * NUM_DIS_HORIZONTAL;//startBounds.width() * 4;
        rll.getLayoutParams().height = heightScreen * NUM_DIS_VERTICAL;//startBounds.height() * 3;
        rll.getLayoutParams().width = widthScreen * NUM_DIS_HORIZONTAL;//startBounds.width() * 4;
        float startScale;

        int centerX = (posX - 1) * (widthScreen);//expandedImageView.getWidth()//(finalImageView.getWidth() / 4)*
        int centerY = (posY - 1) * (heightScreen);//expandedImageView.getHeight()//(finalImageView.getHeight() / 3)*

        imageGallery.setX(centerX * -1);
        imageGallery.setY(centerY * -1);

        zoomed = true;

        /*
        RelativeLayout rll = (RelativeLayout) view.findViewById(R.id.imageLayout);
        ImageView finalImageView = (ImageView) view.findViewById(R.id.imageViewExpanded);
        finalImageView.setImageResource(getResources().getIdentifier(name, "drawable", getActivity().getPackageName()));

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        //expandedImageView.getGlobalVisibleRect(startBounds);
        finalImageView.getLayoutParams().height = heightScreen * 3;//startBounds.height() * 3;
        finalImageView.getLayoutParams().width = widthScreen * 4;//startBounds.width() * 4;
        rll.getLayoutParams().height = heightScreen * 3;//startBounds.height() * 3;
        rll.getLayoutParams().width = widthScreen * 4;//startBounds.width() * 4;
        float startScale;

        //expandedImageView.setAlpha(0f);
        finalImageView.setVisibility(View.VISIBLE);

        Log.d("Fragment", "pos(x,y): " + posX  + ", " + posY);
        int centerX = (posX - 1) * (widthScreen);//expandedImageView.getWidth()//(finalImageView.getWidth() / 4)*
        int centerY = (posY - 1) * (heightScreen);//expandedImageView.getHeight()//(finalImageView.getHeight() / 3)*

        finalImageView.setX(centerX * -1);
        finalImageView.setY(centerY * -1);

        zoomed = true;
        */
    }
}

package empresarial.synapsesdk.com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import empresarial.synapsesdk.com.service.VolleyApplication;

public class TextViewFont extends TextView {
    public TextViewFont(Context context) {
        this(context, null);
    }

    public TextViewFont(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
            setTypeface(VolleyApplication.getInstance().getBebasNeueLigthFont());
    }
}

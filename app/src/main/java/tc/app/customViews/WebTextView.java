package tc.app.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.google.common.util.concurrent.SettableFuture;

public abstract class WebTextView extends TextView {
    protected SettableFuture<String> _settableFuture = SettableFuture.create();

    public WebTextView(Context context) {
        super(context);
    }

    public WebTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void fetchFrom(String webUrl) {
        _settableFuture.set(webUrl);//todo: replace by cache
    }
}

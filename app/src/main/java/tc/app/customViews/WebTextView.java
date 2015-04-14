package tc.app.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import tc.app.R;
import tc.app.ToastMaker;
import tc.app.executor.TCExecutors;
import tc.app.http.HttpClient;

public abstract class WebTextView extends TextView {
    protected SettableFuture<String> _settableFuture = SettableFuture.create();
    private String _webUrl;
    private HttpClient _httpClient = HttpClient.getInstance();
    protected ToastMaker _toastMaker;

    public WebTextView(Context context) {
        super(context);
        init();
    }

    public WebTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        _toastMaker = ToastMaker.getInstance();
    }

    public void fetchFrom(String webUrl) {
        if (isNewRequest(webUrl)) {
            _webUrl = webUrl;
            ListenableFuture<String> contentFuture = _httpClient.getContent(webUrl);
            Futures.addCallback(contentFuture, new FutureCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    _settableFuture.set(result);
                }

                @Override
                public void onFailure(Throwable t) {
                    _settableFuture.setException(t);
                    _toastMaker.showToast(R.string.home_screen_query_failed_toast_message);
                }
            }, TCExecutors.ui);
        }
    }

    private boolean isNewRequest(String webUrl) {
        return _webUrl == null || !_webUrl.equalsIgnoreCase(webUrl);
    }
}

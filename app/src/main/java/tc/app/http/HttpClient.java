package tc.app.http;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.Arrays;

import tc.app.executor.ExecutorWrapper;
import tc.app.executor.TCExecutors;

public class HttpClient {
    private AsyncHttpClient client = new AsyncHttpClient();
    private ExecutorWrapper _executor = TCExecutors.http;
    private static HttpClient _this;
    private Cache<String, ListenableFuture<String>> _cache = new Cache<>(); // can replace by proper cache with eviction policy

    public synchronized static HttpClient getInstance() {
        if (_this == null) {
            _this = new HttpClient();
        }
        return _this;
    }

    public ListenableFuture<String> getContent(String webUrl) {
        if (_cache.containsKey(webUrl)) {
            return _cache.get(webUrl);
        } else {
            SettableFuture<String> settableFuture = SettableFuture.create();
            _cache.put(webUrl, settableFuture);
            fetchUrl(settableFuture, webUrl);
            return settableFuture;
        }
    }

    private void fetchUrl(final SettableFuture<String> settableFuture, final String webUrl) {
        _executor.submit(new Runnable() {
            @Override
            public void run() {
                client.get(webUrl, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        settableFuture.set(Arrays.toString(responseBody));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });
    }
}
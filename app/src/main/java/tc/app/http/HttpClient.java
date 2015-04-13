package tc.app.http;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.concurrent.Executor;

import tc.app.executor.TCExecutors;
import tc.app.logger.ILogger;
import tc.app.logger.LOG_LEVEL;
import tc.app.logger.Logger;

public class HttpClient {
    private AsyncHttpClient client = new AsyncHttpClient();
    private Executor _executor = TCExecutors.ui;
    private static HttpClient _this;
    private final ILogger _logger = new Logger("HttpClient");
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
            fetchUrl(webUrl, settableFuture);
            return settableFuture;
        }
    }

    private void fetchUrl(final String webUrl, final SettableFuture<String> settableFuture) {
        _executor.execute(new Runnable() {
            @Override
            public void run() {
                client.get(webUrl, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String body = new String(responseBody);
                        _logger.log(LOG_LEVEL.DEBUG, body);
                        settableFuture.set(body);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        String body;
                        if (responseBody != null) {
                            body = "http response body:" + new String(responseBody);
                        } else {
                            body = "No http response body";
                        }
                        _logger.log(LOG_LEVEL.ERROR, body, error);
                        settableFuture.setException(error);
                        _cache.remove(webUrl);
                    }
                });
            }
        });
    }
}
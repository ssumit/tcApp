package tc.app.customViews;

import android.content.Context;
import android.util.AttributeSet;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import tc.app.WordCount;
import tc.app.executor.TCExecutors;

//In general but not as per current requirements, web-link can change at any time therefore word count is populated every time
public class WordCounterTextView extends WebTextView {
    private WordCount _wordCount = new WordCount();

    public WordCounterTextView(Context context) {
        super(context);
    }

    public WordCounterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void showFrequencyFor(final String word) {
        setText("");
        if (word != null && !word.isEmpty()) {
            ListenableFuture<Void> transform = Futures.transform(_settableFuture, new AsyncFunction<String, Void>() {
                @Override
                public ListenableFuture<Void> apply(String input) throws Exception {
                    return _wordCount.populate(input);
                }
            });
            Futures.addCallback(transform, new FutureCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    setText(Integer.toString(_wordCount.getCount(word)));
                }

                @Override
                public void onFailure(Throwable t) {
                }
            }, TCExecutors.ui);
        }
    }
}

package tc.app.customViews;

import android.content.Context;
import android.util.AttributeSet;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

public class CharacterIteratorTextView extends WebTextView {
    public CharacterIteratorTextView(Context context) {
        super(context);
    }

    public CharacterIteratorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void showWithCharFrequency(int charFreq) {
        Futures.addCallback(_settableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ;
            }

            @Override
            public void onFailure(Throwable t) {
                ;
            }
        });
    }
}

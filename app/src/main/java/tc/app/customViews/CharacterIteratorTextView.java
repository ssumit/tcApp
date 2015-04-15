package tc.app.customViews;

import android.content.Context;
import android.util.AttributeSet;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

public class CharacterIteratorTextView extends WebTextView {
    private int position = 0;

    public CharacterIteratorTextView(Context context) {
        super(context);
    }

    public CharacterIteratorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void showWithCharFrequency(final int charFreq) {
        Futures.addCallback(_settableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                position = position + charFreq;
                if (position > result.length()) {
                    if (charFreq < result.length()) {
                        position = charFreq;
                    } else {
                        position = 0;
                        _toastMaker.showToast("Very short text on url. Showing first character");
                    }
                }
                setText(Character.toString(result.charAt(position)));
            }

            @Override
            public void onFailure(Throwable t) {
                ;
            }
        });
    }
}

package tc.app.customViews;

import android.content.Context;
import android.util.AttributeSet;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

public class CharacterRequestTextView extends WebTextView {
    public CharacterRequestTextView(Context context) {
        super(context);
    }

    public CharacterRequestTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CharacterRequestTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showCharacter(final int index) {
        Futures.addCallback(_settableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                setText(result.charAt(index));
            }

            @Override
            public void onFailure(Throwable t) {
                //todo: show toast
            }
        });
    }
}
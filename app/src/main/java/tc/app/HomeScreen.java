package tc.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import tc.app.customViews.CharacterRequestTextView;

public class HomeScreen extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        initRequestButtonListener();
    }

    private void initRequestButtonListener() {
        findViewById(R.id.home_screen_request_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharacterRequestTextView view = (CharacterRequestTextView) findViewById(R.id.home_screen_character_count_view);
                        view.fetchFrom(BuildConfig.QUERY_URL);
                        view.showCharacter(BuildConfig.FIRST_QUERY_CHARACTER_POSITION);
                    }
                });
    }
}
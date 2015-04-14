package tc.app;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import tc.app.customViews.CharacterIteratorTextView;
import tc.app.customViews.CharacterRequestTextView;
import tc.app.customViews.WordCounterTextView;

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
                        CharacterRequestTextView view = (CharacterRequestTextView) findViewById(R.id.home_screen_simple_character_view);
                        view.fetchFrom(BuildConfig.QUERY_URL);
                        view.showCharacter(BuildConfig.FIRST_QUERY_CHARACTER_POSITION);

                        CharacterIteratorTextView characterIteratorTextView =
                                (CharacterIteratorTextView) findViewById(R.id.home_screen_character_iterator_view);
                        characterIteratorTextView.fetchFrom(BuildConfig.QUERY_URL);
                        characterIteratorTextView.showWithCharFrequency(BuildConfig.CHARACTER_ITERATING_INTERVAL);

                        WordCounterTextView counterTextView = (WordCounterTextView) findViewById(R.id.home_screen_character_count_view);
                        counterTextView.fetchFrom(BuildConfig.QUERY_URL);
                        EditText queryWord = (EditText) findViewById(R.id.home_screen_word_count_query);
                        String text = queryWord.getText().toString();
                        counterTextView.showFrequencyFor(text);
                    }
                });
    }
}
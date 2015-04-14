package tc.app;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import java.util.HashMap;
import java.util.Map;

import tc.app.executor.TCExecutors;

public class WordCount {
    private final Map<String, Integer> countMap = new HashMap<>();

    public ListenableFuture<Void> populate(final String listOfSpaceSeparatedWords) {
        final SettableFuture<Void> settableFuture = SettableFuture.create();
        TCExecutors.app.execute(new Runnable() {
            @Override
            public void run() {
                countMap.clear();
                String[] split = listOfSpaceSeparatedWords.split(" ");
                for (String word : split) {
                    countMap.put(word, getCount(word) + 1);
                }
                settableFuture.set(null);
            }
        });
        return settableFuture;
    }

    public int getCount(String word) {
        return countMap.containsKey(word) ? countMap.get(word) : 0;
    }
}

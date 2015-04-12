package tc.app.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class TCExecutors {
    public static ExecutorWrapper app = getExecutor(1, "app");
    public static ExecutorWrapper app = getExecutor(1, "http");
    public static Executor ui = new Executor() {
        @Override
        public void execute(Runnable command) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                command.run();
            } else {
                new Handler(Looper.getMainLooper()).post(command);
            }
        }
    };

    public static ExecutorWrapper getExecutor(int poolSize, String label) {
        return new ExecutorWrapper(poolSize, label);
    }
}

package tc.app.executor;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

import tc.app.logger.ILogger;
import tc.app.logger.LOG_LEVEL;
import tc.app.logger.Logger;

public class ExecutorWrapper extends ScheduledThreadPoolExecutor {

    private static final String LOG_MSG = "Crash in executor: ";
    private ILogger _logger;

    public ExecutorWrapper(int corePoolSize, String label) {
        super(corePoolSize, ThreadFactoryMaker.get(label));
        _logger = new Logger(label);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r != null && r instanceof Future<?>) {
            Future<?> future = (Future<?>) r;
            if (future.isDone()) {
                try {
                    future.get();
                } catch (CancellationException e) {
                    t = null; //ignoring cancellable exceptions
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // ignore/reset
                } catch (ExecutionException e) {
                    t = e.getCause();
                }
            }
        }
        if (t != null) {
            _logger.log(LOG_LEVEL.ERROR, LOG_MSG, t);
        }
    }

    public static class ThreadFactoryMaker {
        public static ThreadFactory get(final String name) {
            return new ThreadFactory() {
                private int count = 0;

                @Override
                public Thread newThread(Runnable runnable) {
                    count++;
                    return new Thread(runnable, name + "-" + count);
                }
            };
        }
    }
}

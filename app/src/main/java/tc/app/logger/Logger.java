package tc.app.logger;

import android.util.Log;

//minimally implemented - dont have time
public class Logger implements ILogger {
    private String _logTag;

    public Logger(String logTag) {
        _logTag = logTag;
    }

    @Override
    public void log(LOG_LEVEL log_level, String s) {
        switch (log_level) {
            case ERROR:
                Log.e(_logTag, s);
                break;
            default:
                Log.d(_logTag, s);
        }
    }

    @Override
    public void log(LOG_LEVEL log_level, String s, Throwable throwable) {
        switch (log_level) {
            case ERROR:
                Log.e(_logTag, s, throwable);
                break;
            default:
                Log.d(_logTag, s, throwable);
        }
    }

    @Override
    public void log(LOG_LEVEL log_level, String s, Object... objects) {
    }
}

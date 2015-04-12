package tc.app.logger;

public interface ILogger {
    void log(LOG_LEVEL log_level, java.lang.String s);

    void log(LOG_LEVEL log_level, java.lang.String s, java.lang.Throwable throwable);

    void log(LOG_LEVEL log_level, java.lang.String s, java.lang.Object... objects);
}

package tc.app;

import android.app.Application;

public class TCApplication extends Application {
    public void onCreate() {
        super.onCreate();
        ToastMaker.init(getApplicationContext());
    }
}

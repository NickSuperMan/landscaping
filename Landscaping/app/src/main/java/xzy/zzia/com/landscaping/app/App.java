package xzy.zzia.com.landscaping.app;

import android.app.Application;

/**
 * Created by roy on 16/4/21.
 */
public class App extends Application {

    public static boolean isLogin;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static boolean isLogin() {
        isLogin = true;

        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        App.isLogin = isLogin;
    }
}

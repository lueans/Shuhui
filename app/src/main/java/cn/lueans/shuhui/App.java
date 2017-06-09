package cn.lueans.shuhui;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by 24277 on 2017/5/8.
 */

public class App extends Application {
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        FlowManager.init(this);
        instance = this;
    }


    public static App getInstance() {
        if (instance == null){
            synchronized (App.class){
                if (instance == null) {
                    instance = new App();
                }
            }
        }
        return instance;
    }

}

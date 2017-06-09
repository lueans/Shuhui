package cn.lueans.shuhui.retrofit;

/**
 * Created by 24277 on 2017/5/22.
 */

public class ShuhuiSingle {

    private static ShuhuiServer instance;

    public static ShuhuiServer getInstance(){
        if (instance == null) {
           synchronized (ShuhuiSingle.class){
               if (instance == null) {
                   instance =new ShuhuiRetrofit().createService(ShuhuiServer.class);
               }
           }
        }
        return instance;
    }

}

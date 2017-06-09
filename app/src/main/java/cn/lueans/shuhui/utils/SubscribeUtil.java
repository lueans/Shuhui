package cn.lueans.shuhui.utils;

import android.content.Context;

/**
 * Created by 24277 on 2017/6/5.
 */

public class SubscribeUtil {

    private Context context;

    private static SubscribeUtil instance;

    private SubscribeUtil(Context context){
        this.context = context;
    }


    public static SubscribeUtil getInstance(Context context){
        if (instance == null || instance.context == null){
            synchronized (SubscribeUtil.class){
                if (instance == null || instance.context == null ){
                    instance = new SubscribeUtil(context);
                }
            }
        }
        return instance;
    }

    public void subscribe(int bookId, boolean subscribe) {
        SharedPreferencesUtils.getInstance(instance.context).start().put(key(bookId), subscribe).apply();
    }

    public  boolean isSubscribed(int bookId) {
        return SharedPreferencesUtils.getInstance(instance.context).getBoolean(key(bookId), false);
    }


    public  static String key(int bookId) {
        return "bookId" + bookId + "isSubscribed";
    }

}

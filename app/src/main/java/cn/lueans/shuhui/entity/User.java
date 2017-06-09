package cn.lueans.shuhui.entity;

import android.content.Context;

import cn.lueans.shuhui.utils.SharedPreferencesUtils;

import static cn.lueans.shuhui.constant.UserConstant.COOKIE_KEY;
import static cn.lueans.shuhui.constant.UserConstant.USER_KEY_EMAIL;
import static cn.lueans.shuhui.constant.UserConstant.USER_KEY_ID;
import static cn.lueans.shuhui.constant.UserConstant.USER_KEY_IMG;
import static cn.lueans.shuhui.constant.UserConstant.USER_KEY_NICKNAME;

/**
 * Created by 24277 on 2017/5/8.
 */

public class User {

    private String Set_Cookie = null;

    private String id;
    private String email;
    private String NickName;
    private String imageUrl;
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCookie() {
        return Set_Cookie;
    }

    public void setCookie(String set_Cookie) {
        Set_Cookie = set_Cookie;
    }

    private static User instance;

    public String getId() {
        return id;
    }
    public String getNickName() {
        return NickName;
    }
    public String getEmail() {
        return email;
    }

    private User(String set_Cookie) {
        Set_Cookie = set_Cookie;
    }

    public static User getInstance(Context context){

        if (instance == null){
            synchronized (User.class){
                if(instance == null){
                    //获取本地数据
                    instance = new User(SharedPreferencesUtils.getInstance(context).getString(COOKIE_KEY,null));
                    instance.email = SharedPreferencesUtils.getInstance(context).getString(USER_KEY_EMAIL,null);
                    instance.id = SharedPreferencesUtils.getInstance(context).getString(USER_KEY_EMAIL, null);
                    instance.NickName = SharedPreferencesUtils.getInstance(context).getString(USER_KEY_NICKNAME, null);
                    instance.imageUrl = SharedPreferencesUtils.getInstance(context).getString(USER_KEY_IMG, "/Content/images/touxi.jpg");
                }
            }
        }
        return  instance;
    }

    public boolean isLogin(){
        return instance.Set_Cookie != null;
    }

    public void notifyLogin() {
        instance = null; //先将install 置空 ，getInstance 的时候更新新数据
    }


    public void logout(Context context)
    {
        instance = null;
        this.Set_Cookie = null;
        this.email = null;
        SharedPreferencesUtils.getInstance(context)
                .start()
                .remove(COOKIE_KEY)
                .remove(USER_KEY_EMAIL)
                .remove(USER_KEY_ID)
                .remove(USER_KEY_NICKNAME)
                .apply();
    }



}

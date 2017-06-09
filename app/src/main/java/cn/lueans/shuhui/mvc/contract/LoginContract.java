package cn.lueans.shuhui.mvc.contract;

import android.content.Context;

import cn.lueans.shuhui.entity.LoginEntity;

/**
 * Created by 24277 on 2017/5/23.
 */

public interface LoginContract {

    interface View {
        void showLogin();
        void hideLogin();
        void showError(Exception e);
        void onLoginSeccess(LoginEntity loginEntity);
    }

    interface Model {
        void login(Context context, String Email, String password, String fromType);
        void unSubscribe();   //销毁订阅,防止activity已经销毁，操作还在继续
        interface loginListener{
            void onError(Exception e);
            void onSeccess(LoginEntity loginEntity);
        }
    }

    interface Presenter {
        void login(Context context, String Email, String password, String fromType);
        void unSubscribe();   //销毁订阅,防止activity已经销毁，操作还在继续
    }
}

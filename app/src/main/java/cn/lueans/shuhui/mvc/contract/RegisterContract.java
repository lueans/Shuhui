package cn.lueans.shuhui.mvc.contract;

import cn.lueans.shuhui.entity.RegisterEntity;

/**
 * Created by 24277 on 2017/5/24.
 */

public interface RegisterContract {
    interface View {
        void showRegister();
        void hideRegister();
        void showError(Exception e);
        void onRegisterSeccess(RegisterEntity registerEntity);
    }

    interface Model {
        void register(String email,String password,String fromType);
        void unSubscribe();   //销毁订阅,防止activity已经销毁，操作还在继续
        interface registerListener{
            void onError(Exception e);
            void onSeccess(RegisterEntity registerEntity);
        }
    }

    interface Presenter {
        void register(String email,String password,String fromType);
        void unSubscribe();   //销毁订阅,防止activity已经销毁，操作还在继续
    }
}

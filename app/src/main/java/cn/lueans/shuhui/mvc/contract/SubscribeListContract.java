package cn.lueans.shuhui.mvc.contract;

import cn.lueans.shuhui.entity.ComicsEntity;

/**
 * Created by 24277 on 2017/5/23.
 */

public interface SubscribeListContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showError(Exception e);
        void setSubscribeList(ComicsEntity data);
    }



    interface Model {
        void getSubscribeList();
        void unSubscribe();   //销毁订阅
        interface LoadListener {
            void onSuccess(ComicsEntity data);
            void onError(Exception e);
        }
    }

    interface Presenter {
        void getSubscribeList();
        void unSubscribe();   //销毁订阅
    }
}

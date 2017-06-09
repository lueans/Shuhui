package cn.lueans.shuhui.mvc.contract;

import cn.lueans.shuhui.entity.SubscribeResultEntity;

/**
 * Created by 24277 on 2017/5/25.
 */

public interface SubscribeContract {
    interface View {
        void showSubscribeLOadig();
        void hideLoading();
        void showErroe(Exception e);
        void showSuccess(SubscribeResultEntity date);
    }

    interface Model {
        void subscribe(String bookid,boolean isSubscribe ,String fromType);
        void cancel();   //取消操作
        interface SubscribeListener{
            void onError(Exception e);
            void onSuccess(SubscribeResultEntity date);
        }
    }

    interface Presenter {
        void subscribe(String bookid,boolean isSubscribe ,String fromType);
        void cancel();  //取消操作
    }
}

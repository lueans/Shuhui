package cn.lueans.shuhui.mvc.contract;

import cn.lueans.shuhui.entity.ComicsEntity;

/**
 * Created by 24277 on 2017/5/25.
 */

public interface SearchContract {

    interface Model {
        void search(String search);
        void unSubscribe();   //销毁订阅
        interface searchListener {
            void onSuccess(ComicsEntity data);
            void onError(Exception e);
        }
    }

    interface View {
        void showLoading();
        void hideLoading();
        void showError(Exception e);
        void showSearchComic(ComicsEntity data);
    }

    interface Presenter {
        void search(String search);
        void unSubscribe();   //销毁订阅
    }
}

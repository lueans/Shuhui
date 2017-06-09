package cn.lueans.shuhui.mvc.contract;


import cn.lueans.shuhui.entity.ComicsEntity;

/**
 * Created by 24277 on 2017/5/22.
 */

public interface ComicContract {
    interface Model {
        void getComicFromInternet(boolean isTop, String ClassifyId,int page);
        void getComicDataFromDB(String ClassifyId);
        void unSubscribe();   //销毁订阅
        interface LoadListener {
            void onSuccess(boolean isTop, ComicsEntity date);
            void onError(Exception e);
        }
    }

    interface View {
        void showLoading();
        void hideLoading();
        void showError(Exception e);
        void setRefreshComic(ComicsEntity date);
        void setMoreComic(ComicsEntity date);
    }

    interface Presenter {
        void getComicFromInternet(boolean isTop, String ClassifyId,int page);
        void getComicDataFromDB(String ClassifyId);
        void unSubscribe();   //销毁订阅
    }
}

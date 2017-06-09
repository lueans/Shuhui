package cn.lueans.shuhui.mvc.contract;

import cn.lueans.shuhui.entity.ChapterListEntity;

/**
 * Created by 24277 on 2017/5/23.
 */

public interface ChapterContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showError(Exception e);
        void setRefreshChapter(ChapterListEntity data);
        void setMoreChapter(ChapterListEntity data);
    }

    interface Model {
        void getChapterFromInternet(boolean isTop, String id,int page);
        void getChapterDataFromDB(String id);

        void unSubscribe();   //销毁订阅

        interface LoadListener {
            void onSuccess(boolean isTop, ChapterListEntity data);
            void onError(Exception e);
        }

    }

    interface Presenter {
        void getChapterFromInternet(boolean isTop, String id,int page);
        void getChapterFromDB(String id);
        void unSubscribe();   //销毁订阅
    }
}

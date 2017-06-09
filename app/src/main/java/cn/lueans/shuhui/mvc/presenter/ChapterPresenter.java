package cn.lueans.shuhui.mvc.presenter;

import android.util.Log;

import cn.lueans.shuhui.entity.ChapterListEntity;
import cn.lueans.shuhui.mvc.contract.ChapterContract;
import cn.lueans.shuhui.mvc.model.ChapterModel;

/**
 * Created by 24277 on 2017/5/23.
 */

public class ChapterPresenter implements ChapterContract.Presenter, ChapterContract.Model.LoadListener {

    private static final String TAG = "ChapterPresenter";

    private ChapterContract.View mView;

    private ChapterModel chapterModel;

    public ChapterPresenter(ChapterContract.View mView) {
        this.mView = mView;
        this.chapterModel = new ChapterModel();
    }

    public void getChapterFromInternet(boolean isTop, String id, int page) {

        Log.i(TAG, "getChapterFromInternet: ");

        this.chapterModel.setListener(this);
        this.chapterModel.getChapterFromInternet(isTop,id,page);
    }

    @Override
    public void getChapterFromDB(String id) {

    }

    @Override
    public void unSubscribe() {
        this.chapterModel.unSubscribe();
    }


    //----------------------------------------------------------

    @Override
    public void onSuccess(boolean isTop, ChapterListEntity data) {
        this.mView.hideLoading();
        if (isTop){
            this.mView.setRefreshChapter(data);
        }else{
            this.mView  .setMoreChapter(data);
        }
    }



    @Override
    public void onError(Exception e) {
        this.mView.hideLoading();
        this.mView.showError(e);
    }
}

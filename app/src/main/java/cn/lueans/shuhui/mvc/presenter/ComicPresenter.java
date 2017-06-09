package cn.lueans.shuhui.mvc.presenter;

import cn.lueans.shuhui.entity.ComicsEntity;
import cn.lueans.shuhui.mvc.contract.ComicContract;
import cn.lueans.shuhui.mvc.model.ComicModel;

/**
 * Created by 24277 on 2017/5/22.
 */

public class ComicPresenter implements ComicContract.Presenter, ComicContract.Model.LoadListener {


    private ComicContract.View mView;
    private ComicModel mComicModel;

    public ComicPresenter(ComicContract.View mView) {
        this.mView = mView;
        this.mComicModel = new ComicModel();
    }

    public void getComicFromInternet(boolean isTop, String classifyId, int page) {
        /**
         * 暂时不用
         */
        //this.mView.showLoading();

        this.mComicModel.setListener(this);
        this.mComicModel.getComicFromInternet(isTop,classifyId,page);
    }

    @Override
    public void getComicDataFromDB(String ClassifyId) {
        this.mComicModel.setListener(this);
    }

    @Override
    public void unSubscribe() {
        this.mComicModel.unSubscribe();
    }

    //--------------------------------------------------

    @Override
    public void onSuccess(boolean isTop, ComicsEntity data) {
        this.mView.hideLoading();
        if(isTop){
            this.mView.setRefreshComic(data);
        }else{
            this.mView.setMoreComic(data);
        }
    }

    @Override
    public void onError(Exception e) {
        this.mView.hideLoading();
        this.mView.showError(e);
    }

}

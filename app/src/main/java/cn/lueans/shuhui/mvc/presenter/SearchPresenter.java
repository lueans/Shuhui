package cn.lueans.shuhui.mvc.presenter;

import cn.lueans.shuhui.entity.ComicsEntity;
import cn.lueans.shuhui.mvc.contract.SearchContract;
import cn.lueans.shuhui.mvc.model.SearchModel;

/**
 * Created by 24277 on 2017/5/25.
 */

public class SearchPresenter implements SearchContract.Presenter, SearchContract.Model.searchListener {

    private SearchContract.View mView;
    private SearchModel searchModel;

    public SearchPresenter(SearchContract.View mView) {

        this.mView = mView;
        this.searchModel = new SearchModel();
    }

    public void search(String search) {
        this.mView.showLoading();
        this.searchModel.setListenr(this);
        this.searchModel.search(search);
    }

    @Override
    public void unSubscribe() {
        this.searchModel.unSubscribe();
    }

    @Override
    public void onSuccess(ComicsEntity data) {
       this.mView.hideLoading();
        this.mView.showSearchComic(data);
    }

    @Override
    public void onError(Exception e) {
        this.mView.hideLoading();
        this.mView.showError(e);
    }
}

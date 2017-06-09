package cn.lueans.shuhui.mvc.presenter;

import cn.lueans.shuhui.entity.ComicsEntity;
import cn.lueans.shuhui.mvc.contract.SubscribeListContract;
import cn.lueans.shuhui.mvc.model.SubscribeListModel;

/**
 * Created by 24277 on 2017/5/23.
 */

public class SubscribeListPresenter implements SubscribeListContract.Presenter, SubscribeListContract.Model.LoadListener {

    private SubscribeListContract.View mView;
    private SubscribeListModel subscribeListModel;

    public SubscribeListPresenter(SubscribeListContract.View mView) {
        this.mView = mView;
        this.subscribeListModel = new SubscribeListModel();
    }

    public void getSubscribeList() {
        this.subscribeListModel.setListener(this);
        this.subscribeListModel.getSubscribeList();
    }

    @Override
    public void unSubscribe() {
        this.subscribeListModel.unSubscribe();
    }

    @Override
    public void onSuccess(ComicsEntity data) {
        this.mView.hideLoading();
        this.mView.setSubscribeList(data);
    }

    @Override
    public void onError(Exception e) {
        this.mView.hideLoading();
        this.mView.showError(e);
    }
}

package cn.lueans.shuhui.mvc.presenter;

import cn.lueans.shuhui.entity.SubscribeResultEntity;
import cn.lueans.shuhui.mvc.contract.SubscribeContract;
import cn.lueans.shuhui.mvc.model.SubscribeModel;

/**
 * Created by 24277 on 2017/5/25.
 */

public class SubscribePresenter implements SubscribeContract.Presenter, SubscribeContract.Model.SubscribeListener {

    private SubscribeContract.View mView;
    private SubscribeModel model;

    public SubscribePresenter(SubscribeContract.View mView) {
        this.mView = mView;
        this.model = new SubscribeModel();
    }


    public void subscribe(String bookid, boolean isSubscribe, String fromType) {
        this.model.setListener(this);
        this.model.subscribe(bookid,isSubscribe,fromType);

    }

    @Override
    public void cancel() {
        this.model.cancel();
    }

    @Override
    public void onError(Exception e) {
        this.mView.hideLoading();
        this.mView.showErroe(e);
    }

    @Override
    public void onSuccess(SubscribeResultEntity data) {
        this.mView.hideLoading();
        this.mView.showSuccess(data);
    }
}

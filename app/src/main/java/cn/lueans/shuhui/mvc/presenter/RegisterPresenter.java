package cn.lueans.shuhui.mvc.presenter;

import cn.lueans.shuhui.entity.RegisterEntity;
import cn.lueans.shuhui.mvc.contract.RegisterContract;
import cn.lueans.shuhui.mvc.model.RegisterModel;

/**
 * Created by 24277 on 2017/5/24.
 */

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.Model.registerListener {

    private RegisterContract.View mView;
    private RegisterModel model;

    public RegisterPresenter(RegisterContract.View mView) {
        this.mView = mView;
        this.model = new RegisterModel();
    }

    @Override
    public void register(String email, String password, String fromType){
        this.model.setListener(this);
        this.model.register(email, password, fromType);
    }

    @Override
    public void unSubscribe() {
        this.model.unSubscribe();
    }

    @Override
    public void onError(Exception e) {
        this.mView.hideRegister();
        this.mView.showError(e);
    }

    @Override
    public void onSeccess(RegisterEntity registerEntity) {
        this.mView.hideRegister();
        this.mView.onRegisterSeccess(registerEntity);
    }
}

package cn.lueans.shuhui.mvc.presenter;

import android.content.Context;

import cn.lueans.shuhui.entity.LoginEntity;
import cn.lueans.shuhui.mvc.contract.LoginContract;
import cn.lueans.shuhui.mvc.model.LoginModel;

/**
 * Created by 24277 on 2017/5/23.
 */

public class LoginPresenter implements LoginContract.Presenter, LoginContract.Model.loginListener {


    private LoginContract.View mView;
    private LoginModel loginModel;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        this.loginModel = new LoginModel();
    }

    public void login(Context context,String Email, String password, String fromType) {
        this.mView.showLogin();

        this.loginModel.setListener(this);
        this.loginModel.login(context,Email, password, fromType);
    }

    @Override
    public void unSubscribe() {
        this.loginModel.unSubscribe();
    }

    @Override
    public void onError(Exception e) {
        this.mView.hideLogin();
        this.mView.showError(e);
    }

    @Override
    public void onSeccess(LoginEntity loginEntity) {
        this.mView.hideLogin();
        this.mView.onLoginSeccess(loginEntity);
    }
}

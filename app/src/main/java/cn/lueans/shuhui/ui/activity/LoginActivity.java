package cn.lueans.shuhui.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.constant.AppConstant;
import cn.lueans.shuhui.entity.LoginEntity;
import cn.lueans.shuhui.mvc.contract.LoginContract;
import cn.lueans.shuhui.mvc.presenter.LoginPresenter;
import cn.lueans.shuhui.utils.MD5Utils;

/**
 * Created by 24277 on 2017/5/24.
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private static final String TAG = "LoginActivity";

    private EditText inputEmail;
    private EditText inputPassword;
    private AppCompatButton btnLogin;
    private TextView linkSignup;

    private LoginPresenter loginPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this);
        assignViews();

    }

    private void assignViews() {
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);
        linkSignup = (TextView) findViewById(R.id.link_signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Snackbar.make(v,"账号或密码不为空",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                loginPresenter.login(LoginActivity.this,email, MD5Utils.createMd5(password), AppConstant.FROM_TYPE);

            }
        });

        linkSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //------------------------------------------ start
    @Override
    public void showLogin() {
        this.btnLogin.setEnabled(false);
    }

    @Override
    public void hideLogin() {
        this.btnLogin.setEnabled(true);
    }

    @Override
    public void showError(Exception e) {
        Snackbar.make(btnLogin,e.toString(),Snackbar.LENGTH_SHORT).show();
        Log.i(TAG, "showError: " + e.toString());
    }

    @Override
    public void onLoginSeccess(LoginEntity loginEntity) {
        Snackbar.make(btnLogin,"登陆成功",Snackbar.LENGTH_SHORT).show();
        Log.i(TAG, "onLoginSeccess: " + loginEntity.toString());
        finish();
    }

    //------------------------------------------ end


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.loginPresenter.unSubscribe();
    }
}

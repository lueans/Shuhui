package cn.lueans.shuhui.ui.activity;

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
import cn.lueans.shuhui.entity.RegisterEntity;
import cn.lueans.shuhui.mvc.contract.RegisterContract;
import cn.lueans.shuhui.mvc.presenter.RegisterPresenter;
import cn.lueans.shuhui.utils.EmailUtils;
import cn.lueans.shuhui.utils.MD5Utils;

/**
 * Created by 24277 on 2017/5/24.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    private static final String TAG = "RegisterActivity";

    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputConfirmPassword;
    private AppCompatButton btnSignup;
    private TextView linkLogin;

    private RegisterPresenter registerPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
        assignViews();
    }

    private void initData() {
        registerPresenter = new RegisterPresenter(this);
    }


    private void assignViews() {
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputConfirmPassword = (EditText) findViewById(R.id.input_Confirm_password);
        btnSignup = (AppCompatButton) findViewById(R.id.btn_signup);
        linkLogin = (TextView) findViewById(R.id.link_login);

        this.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ----------------------");
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String confirmPassword = inputConfirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
                    Snackbar.make(v,"邮箱、密码、确认密码不为空",Snackbar.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick:    邮箱、密码、确认密码不为空");
                    return;
                }

                if (!EmailUtils.isEmail(email)){
                    Snackbar.make(v,"邮箱不合法",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)){
                    Snackbar.make(v,"密码与确认密码不同！",Snackbar.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick:    邮箱、密码、确认密码不为空");
                    return;
                }
                //注册操作
                registerPresenter.register(email, MD5Utils.createMd5(password), AppConstant.FROM_TYPE);
            }
        });

        this.linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //-----------   start---------------------------------

    @Override
    public void showRegister() {
        this.btnSignup.setEnabled(false);
    }

    @Override
    public void hideRegister() {
        this.btnSignup.setEnabled(true);
    }

    @Override
    public void showError(Exception e) {
        Snackbar.make(btnSignup,e.toString(),Snackbar.LENGTH_SHORT).show();
        Log.i(TAG, "showError: " +e.toString());
    }

    @Override
    public void onRegisterSeccess(RegisterEntity registerEntity) {
        Snackbar.make(btnSignup,registerEntity.getErrMsg(),Snackbar.LENGTH_SHORT).show();
        Log.i(TAG, "showError: " +registerEntity.toString());
    }

    //-----------   end---------------------------------


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.registerPresenter.unSubscribe();
    }
}

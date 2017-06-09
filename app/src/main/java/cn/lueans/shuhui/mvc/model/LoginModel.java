package cn.lueans.shuhui.mvc.model;

import android.content.Context;
import android.util.Log;

import cn.lueans.shuhui.constant.UserConstant;
import cn.lueans.shuhui.entity.LoginEntity;
import cn.lueans.shuhui.entity.User;
import cn.lueans.shuhui.mvc.contract.LoginContract;
import cn.lueans.shuhui.retrofit.ShuhuiSingle;
import cn.lueans.shuhui.utils.SharedPreferencesUtils;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 24277 on 2017/5/23.
 */

public class LoginModel implements LoginContract.Model {

    private static final String TAG = "LoginModel";
    LoginContract.Model.loginListener listener;
    private Call<LoginEntity> call;

    public void setListener(loginListener listener) {
        this.listener = listener;
    }



    @Override
    public void login(final Context context, String Email, String password, String fromType) {
        call = ShuhuiSingle.
                getInstance().
                login(Email, password, fromType);
        call.enqueue(new Callback<LoginEntity>() {
            @Override
            public void onFailure(Call<LoginEntity> call, Throwable t) {
                if (listener != null) {
                    listener.onError(new Exception(t.toString()));
                }
            }

            @Override
            public void onResponse(Call<LoginEntity> call, Response<LoginEntity> response) {

                LoginEntity result = response.body();
                int ErrCode = -2;
                try {
                    ErrCode = Integer.parseInt(result.getErrCode());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (ErrCode == 0) {
                    Headers headers = response.headers();
                    SharedPreferencesUtils.getInstance(context)
                            .start()
                            .put(UserConstant.COOKIE_KEY, headers.get("Set-Cookie"))
                            .put(UserConstant.USER_KEY_EMAIL, result.getReturn().getEmail())
                            .put(UserConstant.USER_KEY_ID, result.getReturn().getId())
                            .put(UserConstant.USER_KEY_NICKNAME,(String)(result.getReturn().getNickName()))
                            .put(UserConstant.USER_KEY_IMG, result.getReturn().getAvatar())
                            .commit();

                    //更新login
                    User.getInstance(context).notifyLogin();
                    //------------------------------------------

                    Log.i(TAG, "cookie: " + headers.get("Set-Cookie"));

                    listener.onSeccess(result);
                } else {
                    listener.onError(new Exception(result.toString()));
                }
            }
        });

    }

    @Override
    public void unSubscribe() {
        if (call != null  && !call.isCanceled()) {
            call.cancel();
        }
    }
}

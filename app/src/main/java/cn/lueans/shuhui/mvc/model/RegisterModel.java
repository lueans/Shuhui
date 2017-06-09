package cn.lueans.shuhui.mvc.model;

import android.util.Log;

import cn.lueans.shuhui.entity.RegisterEntity;
import cn.lueans.shuhui.mvc.contract.RegisterContract;
import cn.lueans.shuhui.retrofit.ShuhuiSingle;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 24277 on 2017/5/24.
 */

public class RegisterModel implements RegisterContract.Model {

    private static final String TAG = "RegisterModel";

    private RegisterContract.Model.registerListener listener;
    private Subscription subscription;

    public void setListener(registerListener listener) {
        this.listener = listener;
    }


    public void register(String email, String password, String fromType) {

        Observable<RegisterEntity> register = ShuhuiSingle.
                getInstance().
                register(email, password, fromType);
        subscription = register.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");

                        if (listener != null) {
                            listener.onError(new Exception(e.toString()));
                        }

                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        Log.i(TAG, "onNext: ");
                        if (listener != null) {
                            listener.onSeccess(registerEntity);
                        }
                    }
                });
    }

    @Override
    public void unSubscribe() {
        if( subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}

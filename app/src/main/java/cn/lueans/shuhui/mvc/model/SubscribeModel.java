package cn.lueans.shuhui.mvc.model;

import android.util.Log;

import cn.lueans.shuhui.entity.SubscribeResultEntity;
import cn.lueans.shuhui.mvc.contract.SubscribeContract;
import cn.lueans.shuhui.retrofit.ShuhuiServer;
import cn.lueans.shuhui.retrofit.ShuhuiSingle;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 24277 on 2017/5/25.
 */

public class SubscribeModel implements SubscribeContract.Model {

    private static final String TAG = "SubscribeModel";

    private SubscribeContract.Model.SubscribeListener listener;
    private Subscription subscription;

    public void setListener(SubscribeListener listener) {
        this.listener = listener;
    }

    public void subscribe(String bookid, boolean isSubscribe, String fromType) {
        ShuhuiServer instance = ShuhuiSingle.getInstance();
        Observable<SubscribeResultEntity> subscribe = instance.subscribe(bookid, isSubscribe, fromType);
        subscription = subscribe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SubscribeResultEntity>() {
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
                    public void onNext(SubscribeResultEntity jsonObject) {
                        Log.i(TAG, "onNext: ");
                        if (listener == null) {
                            listener.onSuccess(jsonObject);
                        }
                    }
                });
    }

    @Override
    public void cancel() {
        if (subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}

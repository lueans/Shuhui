package cn.lueans.shuhui.mvc.model;

import android.util.Log;

import cn.lueans.shuhui.entity.ComicsEntity;
import cn.lueans.shuhui.mvc.contract.SubscribeListContract;
import cn.lueans.shuhui.retrofit.ShuhuiServer;
import cn.lueans.shuhui.retrofit.ShuhuiSingle;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 24277 on 2017/5/23.
 */

public class SubscribeListModel implements SubscribeListContract.Model {

    private static final String TAG = "SubscribeListModel";


    private SubscribeListContract.Model.LoadListener listener;
    private Subscription subscription;

    public void setListener(LoadListener listener) {
        this.listener = listener;
    }

    public void getSubscribeList() {
        ShuhuiServer instance = ShuhuiSingle.getInstance();
        Observable<ComicsEntity> subscribedComics = instance.getSubscribedComics();
        subscription = subscribedComics.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ComicsEntity>() {
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
                    public void onNext(ComicsEntity comicsEntity) {
                        Log.i(TAG, "onNext: ");
                        if (listener != null) {
                            listener.onSuccess(comicsEntity);
                        }
                    }
                });
    }

    @Override
    public void unSubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}

package cn.lueans.shuhui.mvc.model;

import android.util.Log;

import cn.lueans.shuhui.entity.ComicsEntity;
import cn.lueans.shuhui.mvc.contract.SearchContract;
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

public class SearchModel implements SearchContract.Model {

    private static final String TAG = "SearchModel";
    private SearchContract.Model.searchListener listenr;
    private Subscription subscription;

    public void setListenr(searchListener listenr) {
        this.listenr = listenr;
    }

    public void search(String search) {
        ShuhuiServer instance = ShuhuiSingle.getInstance();
        Observable<ComicsEntity> searchComic = instance.search(search);
        subscription = searchComic.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ComicsEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");
                        if (listenr != null) {
                            listenr.onError(new Exception(e.toString()));
                        }
                    }

                    @Override
                    public void onNext(ComicsEntity comicsEntity) {
                        Log.i(TAG, "onNext: ");
                        if (listenr != null) {
                            listenr.onSuccess(comicsEntity);
                        }

                    }
                });
    }

    @Override
    public void unSubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}

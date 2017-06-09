package cn.lueans.shuhui.mvc.model;

import android.util.Log;

import cn.lueans.shuhui.entity.ComicsEntity;
import cn.lueans.shuhui.mvc.contract.ComicContract;
import cn.lueans.shuhui.retrofit.ShuhuiServer;
import cn.lueans.shuhui.retrofit.ShuhuiSingle;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 24277 on 2017/5/22.
 */

public class ComicModel implements ComicContract.Model {

    private static final String TAG = "ComicModel";

    private ComicContract.Model.LoadListener listener;
    private Subscription subscription;

    public void setListener(LoadListener listener) {
        this.listener = listener;
    }

    public void getComicFromInternet(final boolean isTop, String ClassifyId, int page) {

        ShuhuiServer instance = ShuhuiSingle.getInstance();

        final Observable<ComicsEntity> comics = instance.getComics(ClassifyId, page);
        subscription = comics.subscribeOn(Schedulers.io())
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
                    public void onNext(ComicsEntity comics) {
                        Log.i(TAG, "onNext: ");
                        if (listener != null) {
                            listener.onSuccess(isTop, comics);
                        }
                    }
                });
    }
    @Override
    public void getComicDataFromDB(String ClassifyId) {

    }

    @Override
    public void unSubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}

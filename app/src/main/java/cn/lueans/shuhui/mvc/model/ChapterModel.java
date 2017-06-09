package cn.lueans.shuhui.mvc.model;

import android.util.Log;

import cn.lueans.shuhui.entity.ChapterListEntity;
import cn.lueans.shuhui.mvc.contract.ChapterContract;
import cn.lueans.shuhui.retrofit.ShuhuiSingle;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 24277 on 2017/5/23.
 */

public class ChapterModel implements ChapterContract.Model {

    private static final String TAG = "ChapterModel";

    ChapterContract.Model.LoadListener listener;
    private Subscription subscription;

    public void setListener(LoadListener listener) {
        this.listener = listener;
    }

    public void getChapterFromInternet(final boolean isTop, String id, int page) {
        Observable<ChapterListEntity> comicChapters = ShuhuiSingle.getInstance().getComicChapters(id, page);
        subscription = comicChapters.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChapterListEntity>() {
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
                    public void onNext(ChapterListEntity chapterListEntity) {
                        Log.i(TAG, "onNext: ");
                        if (chapterListEntity != null) {
                            listener.onSuccess(isTop, chapterListEntity);
                        }
                    }
                });
    }

    @Override
    public void getChapterDataFromDB(String id) {

    }

    @Override
    public void unSubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}

package cn.lueans.shuhui.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.constant.AppConstant;
import cn.lueans.shuhui.entity.ChapterListEntity;
import cn.lueans.shuhui.entity.SubscribeResultEntity;
import cn.lueans.shuhui.entity.User;
import cn.lueans.shuhui.mvc.contract.ChapterContract;
import cn.lueans.shuhui.mvc.presenter.ChapterPresenter;
import cn.lueans.shuhui.retrofit.ShuhuiServer;
import cn.lueans.shuhui.retrofit.ShuhuiSingle;
import cn.lueans.shuhui.ui.adapter.DetailAdapter;
import cn.lueans.shuhui.ui.view.SpaceItemDecoration;
import cn.lueans.shuhui.utils.PrintUtils;
import cn.lueans.shuhui.utils.SharedPreferencesUtils;
import cn.lueans.shuhui.utils.SubscribeUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity implements ChapterContract.View {

    private static final String TAG = "DetailActivity";

    private static final String TITLE_KEY = "title";
    private static final String IMAGE_URL_KEY = "imageurl";
    private static final String DOSC_KEY = "dosc";
    private static final String ID_KEY = "id";


    private String mTitle;
    private String imageUrl;
    private String dosc;
    private String mId;


    private ImageView ivHead;
    private SwipyRefreshLayout swipyRefreshLayout;
    private RecyclerView recyclerView;

    private ChapterPresenter chapterPresenter;

    private DetailAdapter detailAdapter;

    private static int POSITION_PAGE = 0;


    //是否已经订阅

    private boolean isSubscribe = false;
    private FloatingActionButton fab;

    public static Intent startIntent(Context context, String title, String imageUrl, String dosc, String id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(TITLE_KEY, title);
        intent.putExtra(IMAGE_URL_KEY, imageUrl);
        intent.putExtra(DOSC_KEY, dosc);
        intent.putExtra(ID_KEY, id);
        context.startActivity(intent);

        return intent;
    }

    private void parseIntent() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(TITLE_KEY);
        imageUrl = intent.getStringExtra(IMAGE_URL_KEY);
        dosc = intent.getStringExtra(DOSC_KEY);
        mId = intent.getStringExtra(ID_KEY);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        parseIntent();

        initBar();
        initRecyclerView();
        initSwipyRefreshLayout();

        initData();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        updata();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                final User instance = User.getInstance(DetailActivity.this);
                if (!instance.isLogin()) {
                    Snackbar.make(recyclerView, "请先登陆！", Snackbar.LENGTH_SHORT)
                            .setAction("登陆", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                    return;
                }
                subscribe(mId, !isSubscribe, AppConstant.FROM_TYPE);
            }
        });


    }

    private void updata() {

        Log.i(TAG, "update: ----------------------------");

        isSubscribe = SharedPreferencesUtils.getInstance(DetailActivity.this).getBoolean(SubscribeUtil.key(Integer.parseInt(mId)), false);
        if (isSubscribe) {
            Log.i(TAG, "updata: "  + "订阅");
            fab.setImageResource(R.drawable.ic_done_black_24dp);
        } else {
            Log.i(TAG, "updata:    添加");
            fab.setImageResource(R.drawable.ic_add_black_24dp);
        }
    }

    private void initBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mTitle);

        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        //头部图片
        ivHead = (ImageView) findViewById(R.id.iv_detial_head);
        Glide.with(this).load(imageUrl).into(ivHead);
    }

    private void initData() {


        chapterPresenter = new ChapterPresenter(this);
        chapterPresenter.getChapterFromInternet(true, mId, POSITION_PAGE);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分割线
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(1);
        recyclerView.addItemDecoration(spaceItemDecoration);
        //设置布局风格
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        detailAdapter = new DetailAdapter(this, null);
        recyclerView.setAdapter(detailAdapter);
    }

    private void initSwipyRefreshLayout() {
        swipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        swipyRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent
        );
        swipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);

        swipyRefreshLayout.measure(0, 0);
        swipyRefreshLayout.setRefreshing(true);

        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                boolean isTop = direction == SwipyRefreshLayoutDirection.TOP ? true : false;
                if (isTop) {
                    POSITION_PAGE = 0;
                }
                //请求网络
                chapterPresenter.getChapterFromInternet(isTop, mId, POSITION_PAGE);
            }
        });
    }

    @Override
    public void showLoading() {
        //暂时不用
    }

    @Override
    public void hideLoading() {
        if (swipyRefreshLayout.isRefreshing()) {
            swipyRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showError(Exception e) {
        Snackbar.make(recyclerView, e.toString(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setRefreshChapter(ChapterListEntity data) {
        PrintUtils.printDetail(data);

        ArrayList<ChapterListEntity.ReturnBean.ListBean> list = data.getReturn().getList();
        if (list.size() > 0) {
            detailAdapter.setRefreshChapter(list);
            POSITION_PAGE++;
            Snackbar.make(recyclerView, "已经加载最新章节", Snackbar.LENGTH_SHORT).show();
        } else {

        }

    }

    @Override
    public void setMoreChapter(ChapterListEntity data) {
        PrintUtils.printDetail(data);
        ArrayList<ChapterListEntity.ReturnBean.ListBean> list = data.getReturn().getList();
        if (list.size() > 0) {
            detailAdapter.setMoreChapter(list);
            POSITION_PAGE++;
            Snackbar.make(recyclerView, "已经加载更多章节", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(recyclerView, "已经加载全部章节", Snackbar.LENGTH_SHORT).show();
        }
        if (detailAdapter.getItemCount() >= data.getReturn().getListCount()) {
            //已经获取到了全部数据，可以关闭上啦加载更多
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chapterPresenter.unSubscribe();
    }


    public void subscribe(final String bookid, final boolean isSubscribe, String fromType) {
        ShuhuiServer instance = ShuhuiSingle.getInstance();
        Observable<SubscribeResultEntity> subscribe = instance.subscribe(bookid, isSubscribe, fromType);
        subscribe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SubscribeResultEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");
                        Snackbar.make(recyclerView, e.toString(), Snackbar.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(SubscribeResultEntity jsonObject) {
                        Log.i(TAG, "onNext: ");
                        if (!jsonObject.isIsError()) {
                            if (isSubscribe) {
                                Snackbar.make(recyclerView, "订阅成功", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(recyclerView, "取消订阅订阅成功", Snackbar.LENGTH_SHORT).show();
                            }
                            SubscribeUtil.getInstance(DetailActivity.this).subscribe(Integer.parseInt(mId), isSubscribe);
                            updata();

                        } else {
                            Log.i(TAG, "onNext: " + jsonObject.toString());
                        }
                    }
                });
    }


}

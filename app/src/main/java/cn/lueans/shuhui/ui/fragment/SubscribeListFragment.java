package cn.lueans.shuhui.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.entity.ComicsEntity;
import cn.lueans.shuhui.mvc.contract.SubscribeListContract;
import cn.lueans.shuhui.mvc.presenter.SubscribeListPresenter;
import cn.lueans.shuhui.ui.adapter.ComicAdapter;
import cn.lueans.shuhui.ui.view.SpaceItemDecoration;

/**
 * Created by 24277 on 2017/5/25.
 */

public class SubscribeListFragment extends Fragment implements SubscribeListContract.View {


    private static final String TAG = "SubscribeListFragment";

    private RecyclerView recyclerView;
    private SwipyRefreshLayout swipyRefreshLayout;
    private ComicAdapter comicAdapter;


    private SubscribeListPresenter subscribePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化数据
        subscribePresenter = new SubscribeListPresenter(this);
        subscribePresenter.getSubscribeList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comic, container, false);
        initRecyclerView(v);
        initSwipyRefreshLayout(v);
        return v;
    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分割线
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(5);
        recyclerView.addItemDecoration(spaceItemDecoration);

        //设置布局风格
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        //设置适配器

        comicAdapter = new ComicAdapter(null,getContext());
        recyclerView.setAdapter(comicAdapter);
    }

    private void initSwipyRefreshLayout(View v) {
        swipyRefreshLayout = (SwipyRefreshLayout) v.findViewById(R.id.swipyrefreshlayout);

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
//                boolean isTop = (direction == SwipyRefreshLayoutDirection.TOP ? true : false);
                subscribePresenter.getSubscribeList();

            }
        });

    }

    @Override
    public void showLoading() {
        //显示加载
    }

    @Override
    public void hideLoading() {
        if (swipyRefreshLayout.isRefreshing()){
            swipyRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showError(Exception e) {
        Snackbar.make(this.recyclerView,e.toString(),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setSubscribeList(ComicsEntity data) {
//        PrintUtils.printComic(data);
        Log.i(TAG, "setSubscribeList: " + data.toString());
        ComicsEntity.ReturnBean aReturn = data.getReturn();
        comicAdapter.setRefreshComic(aReturn.getList());
        if (aReturn.getList().size() > 0){
            comicAdapter.setRefreshComic(aReturn.getList());
            Snackbar.make(recyclerView,"已经加载最新漫画",Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(recyclerView,"你没有订阅的漫画！",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscribePresenter.unSubscribe();
    }
}

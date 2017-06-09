package cn.lueans.shuhui.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.entity.ComicsEntity;
import cn.lueans.shuhui.mvc.contract.SearchContract;
import cn.lueans.shuhui.mvc.presenter.SearchPresenter;
import cn.lueans.shuhui.ui.adapter.ComicAdapter;
import cn.lueans.shuhui.ui.view.SpaceItemDecoration;
import cn.lueans.shuhui.utils.PrintUtils;

/**
 * Created by 24277 on 2017/5/25.
 */

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ComicAdapter comicAdapter;

    private SearchPresenter searchPresenter;

    private FrameLayout flLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchPresenter = new SearchPresenter(this);
        initBar();
        initRecyclerView();
        initSearchView();
    }

    private void initBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置返回按钮
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }


    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分割线
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(5);
        recyclerView.addItemDecoration(spaceItemDecoration);

        //设置布局风格
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        //设置适配器

        comicAdapter = new ComicAdapter(null,SearchActivity.this);
        recyclerView.setAdapter(comicAdapter);

        //-------------------   加载动画  初始化  ------------
        flLoading = (FrameLayout) findViewById(R.id.fl_search_load);
        flLoading.setVisibility(View.GONE);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initSearchView() {
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //点击搜索的时候触发
                searchPresenter.search(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // 当搜索内容改变时触发该方法
                return true;
            }
        });
    }


    @Override
    public void showLoading() {
        this.searchView.setEnabled(false);
        this.flLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.searchView.setEnabled(true);
        this.flLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError(Exception e) {
        Snackbar.make(this.searchView,e.toString(),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSearchComic(ComicsEntity data) {
        PrintUtils.printComic(data);
        ComicsEntity.ReturnBean aReturn = data.getReturn();
        if (aReturn.getListCount() > 0){
            comicAdapter.setRefreshComic(aReturn.getList());
            Snackbar.make(recyclerView,"已经加载最新漫画",Snackbar.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.searchPresenter.unSubscribe();
    }
}

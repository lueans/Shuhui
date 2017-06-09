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
import cn.lueans.shuhui.mvc.contract.ComicContract;
import cn.lueans.shuhui.mvc.presenter.ComicPresenter;
import cn.lueans.shuhui.ui.adapter.ComicAdapter;
import cn.lueans.shuhui.ui.view.SpaceItemDecoration;
import cn.lueans.shuhui.utils.ClassifyIdUtils;
import cn.lueans.shuhui.utils.PrintUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by 24277 on 2017/5/22.
 */

public class ComicFragment extends Fragment implements ComicContract.View {

    public static final String COMIC_TITLE = "title";

    private String mTitle;          //标题
    private int mClassifyId;     //类别id

    private static int NOW_PAGE_NUM = 0;

    private boolean isTop = false;

    private ComicPresenter comicPresenter;

    private RecyclerView recyclerView;
    private SwipyRefreshLayout swipyRefreshLayout;

    private ComicAdapter comicAdapter;

    /**
     * The RecyclerView is not currently scrolling.（静止没有滚动）
     */
    public static final int SCROLL_STATE_IDLE = 0;

    /**
     * The RecyclerView is currently being dragged by outside input such as user touch input.
     *（正在被外部拖拽,一般为用户正在用手指滚动）
     */
    public static final int SCROLL_STATE_DRAGGING = 1;

    /**
     * The RecyclerView is currently animating to a final position while not under outside control.
     *（自动滚动）
     */
    public static final int SCROLL_STATE_SETTLING = 2;


    //fragment 生成器
    public static ComicFragment getInstance(String title) {
        ComicFragment comicFra = new ComicFragment();
        Bundle bundle = new Bundle();
        bundle.putString(COMIC_TITLE, title);
        comicFra.setArguments(bundle);
        return comicFra;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(COMIC_TITLE);
        mClassifyId = ClassifyIdUtils.getInstance().get(mTitle);  //获取类别ID
        comicPresenter = new ComicPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comic, container, false);
        Log.i(TAG, "onCreateView: ----------" + mTitle);
        initBar(v);
        initRecyclerView(v);
        initSwipyRefreshLayout(v);
        Log.i(TAG, "onResume: ---------------------");
        comicPresenter.getComicFromInternet(true,mClassifyId+"",0);
        return v;
    }

    private void initBar(View v) {
        //后续会实现
       /* FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.scrollToPosition(0);
            }
        });*/

    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分割线
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(5);
        recyclerView.addItemDecoration(spaceItemDecoration);

        //设置布局风格
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        //设置适配器

        comicAdapter = new ComicAdapter(null,getContext());
        recyclerView.setAdapter(comicAdapter);

        //设置滚动监听
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滚动状态改变

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //正在滚动

            }
        });
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
                isTop = (direction == SwipyRefreshLayoutDirection.TOP ? true : false);
                if (isTop){
                    NOW_PAGE_NUM = 0;
                }
                comicPresenter.getComicFromInternet(isTop,mClassifyId+"",NOW_PAGE_NUM);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //销毁视图的时候，先取消订阅。
        comicPresenter.unSubscribe();
    }

    @Override
    public void showLoading() {
        //暂时不用，如果要调用，去修改父类注释

    }



    @Override
    public void hideLoading() {
        if (swipyRefreshLayout.isRefreshing()){
            swipyRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void showError(Exception e) {
        Snackbar.make(recyclerView,e.toString(),Snackbar.LENGTH_SHORT).show();
        Log.i(TAG, "showError: ------------------" + e.toString());
    }

    @Override
    public void setRefreshComic(ComicsEntity data) {
        PrintUtils.printComic(data);
        ComicsEntity.ReturnBean aReturn = data.getReturn();
        if (aReturn.getListCount() > 0){
            comicAdapter.setRefreshComic(aReturn.getList());
            Snackbar.make(recyclerView,"已经加载最新漫画",Snackbar.LENGTH_SHORT).show();
            NOW_PAGE_NUM++;
        }
    }

    @Override
    public void setMoreComic(ComicsEntity data) {
        ComicsEntity.ReturnBean aReturn = data.getReturn();
        if (aReturn.getList().size() > 0){
            comicAdapter.setMoreComic(aReturn.getList());
            Snackbar.make(recyclerView,"已经加载更多漫画",Snackbar.LENGTH_SHORT).show();
            NOW_PAGE_NUM++ ;
        }else{
            Snackbar.make(recyclerView,"已经加载全部漫画",Snackbar.LENGTH_SHORT).show();
        }
    }
}

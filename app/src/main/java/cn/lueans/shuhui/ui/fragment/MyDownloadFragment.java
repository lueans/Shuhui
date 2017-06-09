package cn.lueans.shuhui.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.db.ChapterDao;
import cn.lueans.shuhui.entity.ChapterListEntity;
import cn.lueans.shuhui.ui.adapter.DetailAdapter;
import cn.lueans.shuhui.ui.view.SpaceItemDecoration;

import static android.content.ContentValues.TAG;

/**
 * Created by 24277 on 2017/6/6.
 */

public class MyDownloadFragment extends Fragment{



    private RecyclerView recyclerView;
    private SwipyRefreshLayout swipyRefreshLayout;

    private DetailAdapter detailAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(5);
        recyclerView.addItemDecoration(spaceItemDecoration);

        //设置布局风格
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置适配器
        detailAdapter = new DetailAdapter(getContext(), null);
        recyclerView.setAdapter(detailAdapter);
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
        loadData();
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                loadData();
            }
        });

    }

    private void loadData() {
        ArrayList<ChapterListEntity.ReturnBean.ListBean> chapterList = ChapterDao.getChapterList();


        Log.i(TAG, "onClick: ------    " + chapterList.size() +"   ---------");
        for(ChapterListEntity.ReturnBean.ListBean table:chapterList) {
            Log.i(TAG, "onClick: " + table.toString());
        }

        if (!chapterList.isEmpty()){
            detailAdapter.setRefreshChapter(chapterList);
        }else{
            Toast.makeText(getContext(), "没有下载好的漫画", Toast.LENGTH_SHORT).show();
        }

        if (swipyRefreshLayout.isRefreshing()){
            swipyRefreshLayout.setRefreshing(false);
        }
    }


}


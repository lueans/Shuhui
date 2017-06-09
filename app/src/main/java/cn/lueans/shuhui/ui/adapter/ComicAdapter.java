package cn.lueans.shuhui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.entity.ComicsEntity;
import cn.lueans.shuhui.ui.activity.DetailActivity;

/**
 * Created by 24277 on 2017/5/22.
 */

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicHolder> {

    private ArrayList<ComicsEntity.ReturnBean.ListBean> list;
    private Context mContext;

    public ComicAdapter(ArrayList<ComicsEntity.ReturnBean.ListBean> list, Context mContext) {
        this.mContext = mContext;
        if (list == null){
            this.list = new ArrayList<>();
        }else{
            this.list = list;
        }
    }

    /**
     * 获取适配器的数据
     * @return
     */
    public ArrayList<ComicsEntity.ReturnBean.ListBean> getList() {
        return list;
    }

    /**
     * 设置更多数据
     * @param list
     */
    public void setMoreComic(ArrayList<ComicsEntity.ReturnBean.ListBean> list){
        if (list != null){
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置刷新数据
     * @param list
     */
    public void setRefreshComic(ArrayList<ComicsEntity.ReturnBean.ListBean> list){
        if (list != null){
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }


    @Override
    public ComicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic, parent,false);
        ComicHolder holder = new ComicHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(ComicHolder holder, int position) {

        final ComicsEntity.ReturnBean.ListBean listBean = this.list.get(position);
        holder.bindData(listBean);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--------------------------------
                DetailActivity.startIntent(
                        mContext,
                        listBean.getTitle(),
                        listBean.getFrontCover(),
                        listBean.getExplain(),
                        listBean.getId()+""
                        );
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class ComicHolder extends RecyclerView.ViewHolder {

        private TextView tvSection;
        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvTime;
        private TextView tvAuthor;
        private ImageView ivComic;
        private View itemView;

        public ComicHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.tvSection = (TextView) itemView.findViewById(R.id.tv_section);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            this.tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            this.tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            this.ivComic = (ImageView) itemView.findViewById(R.id.iv_comic);
        }

        public void bindData(ComicsEntity.ReturnBean.ListBean listBean){
            Glide.with(mContext).load(listBean.getFrontCover()).diskCacheStrategy(DiskCacheStrategy.ALL).into(this.ivComic);
            this.tvSection.setText(listBean.getLastChapter().getTitle());
            this.tvTitle.setText(listBean.getTitle());
            this.tvDesc.setText(listBean.getExplain());
            this.tvTime.setText(listBean.getRefreshTimeStr().substring(0,10));
            this.tvAuthor.setText(TextUtils.isEmpty(listBean.getAuthor())?"未知的你":listBean.getAuthor());
        }
    }
}

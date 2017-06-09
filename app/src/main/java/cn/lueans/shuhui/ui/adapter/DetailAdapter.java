package cn.lueans.shuhui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.entity.ChapterListEntity;
import cn.lueans.shuhui.ui.activity.WebActivity;

/**
 * Created by 24277 on 2017/5/23.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailHolder>{

    private  ArrayList<ChapterListEntity.ReturnBean.ListBean> list;
    private Context mContext;


    public DetailAdapter(Context mContext,ArrayList<ChapterListEntity.ReturnBean.ListBean> list) {
        this.mContext = mContext;
        if (list == null) {
            this.list = new ArrayList<>();
        }else{
            this.list = list;
        }
    }

    public ArrayList<ChapterListEntity.ReturnBean.ListBean> getList() {
        return list;
    }

    /**
     * 刷新最新章节
     * @param list
     */
    public void setRefreshChapter(ArrayList<ChapterListEntity.ReturnBean.ListBean> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加更多章节
     * @param list
     */
    public void setMoreChapter(ArrayList<ChapterListEntity.ReturnBean.ListBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public DetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_detail, parent, false);
        DetailHolder detailHolder = new DetailHolder(inflate);
        return detailHolder;
    }

    @Override
    public void onBindViewHolder(DetailHolder holder, int position) {
        final ChapterListEntity.ReturnBean.ListBean listBean = this.list.get(position);
        holder.bindData(listBean);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------- 点击事件
                WebActivity.startIntent(
                        mContext,
                        listBean.getTitle(),
                        listBean.getId()+"",
                        listBean.getSort(),
                        listBean.getFrontCover()
                );

            }
        });


    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class DetailHolder extends RecyclerView.ViewHolder{

        private ImageView ivChapterIcon;
        private TextView tvName;
        private TextView tvNumber;
        private TextView tvTime;
        private View itemView;

        public DetailHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.ivChapterIcon = (ImageView) itemView.findViewById(R.id.iv_chapter_icon);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_chapter_name);
            this.tvNumber = (TextView) itemView.findViewById(R.id.tv_chapter_number);
            this.tvTime = (TextView) itemView.findViewById(R.id.tv_chapter_date);

        }

        public void bindData(ChapterListEntity.ReturnBean.ListBean listBean) {
            Glide.with(mContext).load(listBean.getFrontCover()).diskCacheStrategy(DiskCacheStrategy.ALL).into(this.ivChapterIcon);
            this.tvName.setText(listBean.getTitle());
            this.tvTime.setText(listBean.getRefreshTimeStr());
            this.tvNumber.setText(listBean.getSort()+"话");
        }
    }
}

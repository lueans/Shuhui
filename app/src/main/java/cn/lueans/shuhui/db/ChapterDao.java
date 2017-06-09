package cn.lueans.shuhui.db;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

import cn.lueans.shuhui.entity.ChapterListEntity;

/**
 * Created by 24277 on 2017/6/6.
 */

public class ChapterDao {

    private static final String TAG = "ChapterDao";

    public static void saveChapter(int chapterId,String title,String time,String imageUrl){
        ChapterTable chapterTable = new ChapterTable();
        chapterTable.chapterId = chapterId;
        chapterTable.title = title;
        chapterTable.refreshTimeStr = time;
        chapterTable.frontCover = imageUrl;
        chapterTable.save();
        Log.i(TAG, "saveChapter:    保存成功");
    }

    public static  boolean isEmpty(int chapterId){
        List<ChapterTable> chapterTables = new Select()
                .from(ChapterTable.class)
                .where(ChapterTable_Table.chapterId.eq(chapterId))
                .queryList();
        if (chapterTables != null && chapterTables.size() > 0){
            return false;
        }
        return true;
    }

    public static ArrayList<ChapterListEntity.ReturnBean.ListBean> getChapterList(){
        ArrayList<ChapterListEntity.ReturnBean.ListBean> lists = new ArrayList<>();
        List<ChapterTable> chapterTables = new Select()
                .from(ChapterTable.class)
                .queryList();
        ChapterListEntity.ReturnBean.ListBean listBean;
        for (ChapterTable table: chapterTables){
            listBean = new ChapterListEntity.ReturnBean.ListBean();
            listBean.setId(table.chapterId);
            listBean.setRefreshTimeStr(table.refreshTimeStr);
            listBean.setSort(table.sort);
            listBean.setTitle(table.title);
            listBean.setFrontCover(table.frontCover);
            lists.add(listBean);
        }
        return lists;
    }

}

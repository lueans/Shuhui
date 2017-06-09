package cn.lueans.shuhui.utils;

import android.util.Log;

import java.util.ArrayList;

import cn.lueans.shuhui.entity.ChapterListEntity;
import cn.lueans.shuhui.entity.ComicsEntity;

/**
 * Created by 24277 on 2017/5/22.
 */

public class PrintUtils {
    private static final String TAG = "PrintUtils";

    public static void printComic(ComicsEntity data){
        ComicsEntity.ReturnBean aReturn = data.getReturn();
        Log.i(TAG, "printComic:---  "+aReturn.getListCount());
        if (aReturn.getListCount() > 0){
            ArrayList<ComicsEntity.ReturnBean.ListBean> list = aReturn.getList();

            for (int i = 0; i < list.size(); i++) {
                Log.i(TAG, "printComic: " + list.get(i).toString());
            }
        }
        Log.i(TAG, "setRefreshComic:---  \n\n");
    }

    public static void printDetail(ChapterListEntity data) {
        ArrayList<ChapterListEntity.ReturnBean.ListBean> list = data.getReturn().getList();

        Log.i(TAG, "printDetail: ------------------");
        Log.i(TAG, "printDetail: ------------------  " + list.size());

        for (int i = 0; i < list.size(); i++) {
            Log.i(TAG, "printDetail: " + list.get(i));
        }
    }

}

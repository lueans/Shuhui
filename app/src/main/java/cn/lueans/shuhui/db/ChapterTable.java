package cn.lueans.shuhui.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by 24277 on 2017/6/6.
 */

@Table(database = LShuhuiDB.class)
public class ChapterTable extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long _id;

    @Column
    public int chapterId;

    @Column
    public String title;

    @Column
    public int sort;

    @Column
    public String frontCover;

    @Column
    public String refreshTimeStr;

    @Override
    public String toString() {
        return "ChapterTable{" +
                "_id=" + _id +
                ", chapterId=" + chapterId +
                ", title='" + title + '\'' +
                ", sort=" + sort +
                ", frontCover='" + frontCover + '\'' +
                ", refreshTimeStr='" + refreshTimeStr + '\'' +
                '}';
    }
}

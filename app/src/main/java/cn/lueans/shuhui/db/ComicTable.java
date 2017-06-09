package cn.lueans.shuhui.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by 24277 on 2017/6/6.
 */
@Table(database = LShuhuiDB.class)
public class ComicTable extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long _id;

    @Column
    public int chapterId;

    @Column
    public String title;
}

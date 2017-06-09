package cn.lueans.shuhui.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by 24277 on 2017/6/5.
 */

@Database(name = LShuhuiDB.NAME,version = LShuhuiDB.VERSION)

public class LShuhuiDB {
    //数据库名
    public static final String NAME = "AppDatabase";
    //数据库的版本号
    public static final int VERSION = 1;
}
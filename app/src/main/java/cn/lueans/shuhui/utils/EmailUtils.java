package cn.lueans.shuhui.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 24277 on 2017/5/24.
 */

public class EmailUtils {
    public static boolean isEmail(String email){

        if (TextUtils.isEmpty(email)){
            return false;
        }
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}

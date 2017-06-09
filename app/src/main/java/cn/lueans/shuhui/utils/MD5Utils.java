package cn.lueans.shuhui.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * 借用iSHuhui的MD5加密
 * Created by 24277 on 2017/5/24.
 */

public class MD5Utils {
    public static String createMd5(String... strParams) {
        StringBuilder strBuffer = new StringBuilder();
        for (String strParam : strParams) {
            strBuffer.append(strParam);
        }
        return encryptionFor32(strBuffer.toString());
    }

    private static String encryptionFor32(String plainText) {
        String re_md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
}

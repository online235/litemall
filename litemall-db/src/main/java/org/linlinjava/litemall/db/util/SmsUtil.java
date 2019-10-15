package org.linlinjava.litemall.db.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsUtil {
    public static final String DATE_TYPE = "yyyy-MM-dd HH:mm:ss";

    public static boolean isMatch(String phone){
        Pattern p = null;
        Matcher m = null;
        boolean isMatch = false;
        //制定验证条件
        String regex1 = "^[1][3,4,5,7,8][0-9]{9}$";
        String regex2 = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$";

        p = Pattern.compile(regex2);
        m = p.matcher(phone);
        return isMatch = m.matches();
    }
    /**
     * 获取当前日期
     * @return
     */
    public static String presenttime(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(DATE_TYPE);
        return dateTime.format(formatter2);
    }

}

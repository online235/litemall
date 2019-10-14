package org.linlinjava.litemall.wx.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HandleUtil {
    public static final String HANDLE_MES = "无法进行操作！请检查是否正确！";
    public static final Integer HANDLE_CODE = 1292;
    public static final String BASE = "0123456789";
    public static final String DATE_TYPE = "yyyy-MM-dd HH:mm:ss";
    public static final String REQUEST_WAS_ABORTED_MAS = "验证码错误";
    public static final Integer REQUEST_WAS_ABORTED_CODE = 0;
    public static final String SUCCESSFUL_REQUEST_MAS = "验证码正确";
    public static final Integer SUCCESSFUL_REQUEST_CODE = 1;
    public static final String CERTIFICATION_SUCCESS_MAS = "认证成功！登陆成功";
    public static final Integer CERTIFICATION_SUCCESS_CODE = 1;
    public static final String AUTHENTICATION_FAILED_MAS = "认证失败！请重新登陆";
    public static final Integer AUTHENTICATION_FAILED_CODE = 0;
    public static final String LANDING_STATUS_FAIL_MAS = "登陆失败，账号或密码错误！";
    public static final Integer LANDING_STATUS_FAIL_CODE = 0;
    public static final String LANDING_STATUS_SUCCESS_MAS = "登陆成功";
    public static final Integer LANDING_STATUS_SUCCESS_CODE = 1;


    /**
     * 提示错误信息
     * @return
     */
    public static Map<String ,Object> handleUtil(String MAS,Integer CODE){
        Map<String ,Object> map = new HashMap<>();
        map.put("mes",MAS);
        map.put("result",CODE);
        return map;
    }

    /**
     * 获取4位随机码
     * @return
     */
    public static String numder(){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int number = random.nextInt(BASE.length());
            sb.append(BASE.charAt(number));
        }
        return sb.toString();
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

    /**
     * json数据
     * @param phone
     * @param numder
     * @return
     */
    public static String JsonData(String phone ,String numder){
        String url = RestUtil.interfaceUtil("http://api.51welink.com/json/sms/g_Submit?sname=dlhqin00&spwd=Ha999999&scorpid=" +
                        "&sprdid=1012888&sdst="+phone+"&smsg=【海露物联】您的验证码是："+numder
                , "");
        return url;
    }

    public static boolean ExpirationDate(String time){
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(DATE_TYPE);
        LocalDateTime time1 = LocalDateTime.parse(time,dtf2);
        LocalDateTime time12 = LocalDateTime.parse(presenttime(),dtf2);
        Duration duration = Duration.between(time1,time12);
        long millis = duration.toMillis();//相差毫秒数
        if (millis >= 60000)
            return true;
        else
            return false;
    }

}

package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.AppXinanMapper;
import org.linlinjava.litemall.db.domain.LitemallSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AppXinanService{

    @Autowired
    private AppXinanMapper appXinanMapper;


    /**
     * 添加短信验证
     * @param sms
     * @return
     */
    public int addSmsByPhoneAndCode(LitemallSms sms) {
        if (sms==null)
            return 0;
        else
            return appXinanMapper.addSmsByPhoneAndCode(sms);
    }

    /**
     * 更新短信验证
     * @param sms
     * @return
     */
    public int updSmsByPhoneAndCode(LitemallSms sms) {
        if (sms!=null)
            return appXinanMapper.updSmsByPhoneAndCode(sms);
        else
            return 0;
    }


    public int findLitemallList(String phone,String code) {

        Pattern p = null;
        Matcher m = null;
        boolean isMatch = false;
        //制定验证条件
        String regex1 = "^[1][3,4,5,7,8][0-9]{9}$";
        String regex2 = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$";

        p = Pattern.compile(regex2);
        m = p.matcher(phone);
        isMatch = m.matches();

        int num = -1;
        if (isMatch && code.equals("")){
            List list = appXinanMapper.findLitemallList(phone,"");
            if (null == list || list.size() ==0 ){
                num = 0;
                return num;
            }else{
                num = 1;
                return num;
            }
        }else if (isMatch && !code.equals("")){
            List list = appXinanMapper.findLitemallList(phone,code);
            if (null == list || list.size() ==0 ){
                num = 0;
                return num;
            }else{
                num = 1;
                return num;
            }
        }
        else{
            return num;
        }

    }
}

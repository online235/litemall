package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallMerChantMapper;
import org.linlinjava.litemall.db.domain.LitemallMerSms;
import org.linlinjava.litemall.db.util.SmsUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallMerSmsService {

    @Resource
    private LitemallMerChantMapper litemallMerChantMapper;


    /**
     * 添加短信验证
     * @param sms
     * @return
     */
    public int addSmsByPhoneAndCode(LitemallMerSms sms) {
        if (sms==null)
            return 0;
        else
            return litemallMerChantMapper.addMerSmsByPhoneAndCode(sms);
    }

    /**
     * 更新短信验证
     * @param sms
     * @return
     */
    public int updSmsByPhoneAndCode(LitemallMerSms sms) {
        if (sms!=null)
            return litemallMerChantMapper.updMerSmsByPhoneAndCode(sms);
        else
            return 0;
    }


    public int findLitemallList(String phone,String code) {



        int num = -1;
        if (SmsUtil.isMatch(phone) && code.equals("")){
            List list = litemallMerChantMapper.findLitemallMerList(phone,"");
            if (list.equals("[]")){
                num = 0;
                return num;
            }else{
                num = 1;
                return num;
            }
        }else if (SmsUtil.isMatch(phone) && !code.equals("")){
            System.out.println(10);
            List list = litemallMerChantMapper.findLitemallMerList(phone,code);
            if (list.equals("[]")){
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

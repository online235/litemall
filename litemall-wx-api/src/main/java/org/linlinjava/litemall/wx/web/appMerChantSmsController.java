package org.linlinjava.litemall.wx.web;

import com.alibaba.fastjson.JSON;
import org.linlinjava.litemall.db.domain.LitemallMerSms;
import org.linlinjava.litemall.db.service.LitemallMerSmsService;
import org.linlinjava.litemall.wx.util.HandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pc/merchant")
@Validated
public class appMerChantSmsController {

    @Autowired
    private LitemallMerSmsService litemallMerSmsService;

    /**
     *
     * @param phone
     * @return 是否存在验证码
     * @throws ParseException
     */
    @RequestMapping("existsms")
    @ResponseBody
    public String DoesItExist(String phone) {
        int num = litemallMerSmsService.findLitemallList(phone,"");
        if (num == 0){
            //获取验证码
            System.out.println(123);
            return SmsVerification(phone);
        }else if(num == 1){
            //修改验证码
            System.out.println(456);
            return UpdSms(phone);
        }else{
            //提示错误信息
            return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.HANDLE_MES,HandleUtil.HANDLE_CODE));
        }

    }

    /**
     *
     * @param phone 手机号码
     * @return
     * "MsgState": "提交成功",
     * 	"State": 0,
     * 	"MsgID": "0123456789",
     * 	"Reserve": 1  （失败返回 0）
     */
    @RequestMapping("selsms")
    @ResponseBody
    public String SmsVerification(String phone) {
        String numder = HandleUtil.numder();
        String url = HandleUtil.JsonData(phone,numder);
        String date = HandleUtil.presenttime();
        LitemallMerSms sms = new LitemallMerSms();
        sms.setPhone(phone);
        sms.setVerification_code(numder);
        sms.setCreatedat(date);
        sms.setUpdatedat(date);
        int result = litemallMerSmsService.addSmsByPhoneAndCode(sms);
        if (result > 0)
            return url;
        else
            return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.HANDLE_MES,HandleUtil.HANDLE_CODE));
    }

    /**
     *
     * @param phone
     * @return 重发验证码
     */
    @RequestMapping("updsms")
    @ResponseBody
    public String UpdSms(String phone){
        String numder = HandleUtil.numder();
        String url = HandleUtil.JsonData(phone,numder);
        String date = HandleUtil.presenttime();
        Map<String , Map<String , Object>> map = new HashMap<>();
        LitemallMerSms sms = new LitemallMerSms();
        sms.setUpdatedat(date);
        sms.setVerification_code(numder);
        sms.setPhone(phone);
        int result = litemallMerSmsService.updSmsByPhoneAndCode(sms);
        if (result > 0)
            return url;
        else
            return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.HANDLE_MES,HandleUtil.HANDLE_CODE));
    }

    @RequestMapping("verification")
    @ResponseBody
    public String Verification_Code(String phone , String code){
        int num = litemallMerSmsService.findLitemallList(phone,code);
        if (num == 0){
            //查询验证码错误时
            return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.REQUEST_WAS_ABORTED_MAS,HandleUtil.REQUEST_WAS_ABORTED_CODE));
        }else if(num == 1){
            //查询验证码正确时
            return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.SUCCESSFUL_REQUEST_MAS,HandleUtil.SUCCESSFUL_REQUEST_CODE));
        }else{
            //提示错误信息
            return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.HANDLE_MES,HandleUtil.HANDLE_CODE));
        }
    }
}

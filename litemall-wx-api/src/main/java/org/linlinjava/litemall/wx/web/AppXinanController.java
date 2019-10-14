package org.linlinjava.litemall.wx.web;

import com.alibaba.fastjson.JSON;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.db.domain.LitemallSms;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.AppXinanService;
import org.linlinjava.litemall.wx.service.UserInfoService;
import org.linlinjava.litemall.wx.util.HandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

import static org.linlinjava.litemall.wx.util.WxResponseCode.AUTH_INVALID_ACCOUNT;

@RestController
@RequestMapping("/app/auth")
@Validated
public class AppXinanController {
    @Autowired
    private AppXinanService appXinanService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 账号秘密登陆、生成token至数据库
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("loginuser")
    @ResponseBody
    public Object LoginLitemall(String username,String password){
        if (username != null && password != null){
            LitemallUser litemallUser = userInfoService.LoginLitemall(username);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(password, litemallUser.getPassword())) {
                return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, HandleUtil.LANDING_STATUS_FAIL_MAS);
            }else {
                    return ResponseUtil.okUser(litemallUser);
            }
        }else{
            return JSON.toJSONString(HandleUtil.handleUtil("账号或密码未输入",-1));
        }

    }

    /**
     * token验证登陆
     * @param token
     * @return
     */
    @RequestMapping("logintoken")
    @ResponseBody
    public Object LoginToken(String token){
        if (token != null){
            LitemallUser litemallUser = userInfoService.LoginToken(token);
            if (litemallUser != null){
                return ResponseUtil.okUser(litemallUser);
            }else{
                return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.AUTHENTICATION_FAILED_MAS,HandleUtil.AUTHENTICATION_FAILED_CODE));
            }
        }else{
            return JSON.toJSONString(HandleUtil.handleUtil("信息错误重新登陆",-1));
        }
    }




    /**
     *
     * @param phone
     * @return 是否存在验证码
     * @throws ParseException
     */
    @RequestMapping("existsms")
    @ResponseBody
    public String DoesItExist(String phone) {
        int num = appXinanService.findLitemallList(phone,"");
        if (num == 0){
            //获取验证码
            return SmsVerification(phone);
        }else if(num == 1){
            //修改验证码
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
        LitemallSms sms = new LitemallSms();
        sms.setPhone(phone);
        sms.setVerification_code(numder);
        sms.setCreatedat(date);
        sms.setUpdatedat(date);
        int result = appXinanService.addSmsByPhoneAndCode(sms);
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
        Map<String ,Map<String , Object>> map = new HashMap<>();
        LitemallSms sms = new LitemallSms();
        sms.setUpdatedat(date);
        sms.setVerification_code(numder);
        sms.setPhone(phone);
        int result = appXinanService.updSmsByPhoneAndCode(sms);
        if (result > 0)
            return url;
        else
            return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.HANDLE_MES,HandleUtil.HANDLE_CODE));
    }

    @RequestMapping("verification")
    @ResponseBody
    public String Verification_Code(String phone , String code){
        int num = appXinanService.findLitemallList(phone,code);
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

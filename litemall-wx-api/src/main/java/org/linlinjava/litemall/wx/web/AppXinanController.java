package org.linlinjava.litemall.wx.web;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.db.domain.LitemallSms;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.AppXinanService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.service.UserInfoService;
import org.linlinjava.litemall.wx.util.HandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.util.*;

import static org.linlinjava.litemall.wx.util.WxResponseCode.AUTH_INVALID_ACCOUNT;

@RestController
@RequestMapping("/app/auth")
@Validated
@Api(value = "心安APP接口", tags = "心安APP接口")
@Slf4j
public class AppXinanController {
    @Autowired
    private AppXinanService appXinanService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LitemallUserService userService;


    /**
     * 账号秘密登陆、生成token至数据库
     * @param username
     * @param password
     * @return
     */
    @PostMapping("loginuser")
    @ResponseBody
    @ApiOperation(notes = "", value = "账号秘密登陆、生成token至数据库")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value = "用户名", required = true, paramType="query"),
            @ApiImplicitParam(name="password", value = "用户密码", required = true, paramType="query")
    })
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
    @PostMapping("logintoken")
    @ResponseBody
    @ApiOperation(notes = "", value = "token验证登陆")
    @ApiImplicitParam(name="token", value = "token密钥", required = true, paramType="query")
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
    @PostMapping("existsms")
    @ResponseBody
    @ApiOperation(notes = "", value = "验证码请求（以方式请求：号码存在方式、号码不存在方式）")
    @ApiImplicitParam(name="phone", value = "手机号", required = true, paramType="query")
    public String DoesItExist(@Pattern(regexp = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$", message = "手机号不正确") String phone) {
        System.out.println("=====aaa===");
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
    @PostMapping("selsms")
    @ResponseBody
    @ApiOperation(notes = "", value = "验证码请求（以方式请求：码不存在方式）")
    @ApiImplicitParam(name="phone", value = "手机号", required = true, paramType="query")
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
    @PostMapping("updsms")
    @ResponseBody
    @ApiOperation(notes = "", value = "验证码请求（以方式请求：号码存在方式）")
    @ApiImplicitParam(name="phone", value = "手机号", required = true, paramType="query")
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

    /**
     * 根据手机号和验证码验证并且注册
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    @ApiOperation(notes = "", value = "根据手机号和验证码验证并且注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name="phone", value = "手机号", required = true, paramType="query"),
            @ApiImplicitParam(name="code", value = "验证码" , required = true, paramType = "query")
    })
    public Object Verification_Code(@Pattern(regexp = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$", message = "手机号不正确")String phone , String code){
        int byphone = userService.selectLitemallByphone(phone);
        if(byphone == 0) {
            int num = appXinanService.findLitemallList(phone,code);
            if (num == 0) {
                //查询验证码错误时
                return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.REQUEST_WAS_ABORTED_MAS, HandleUtil.REQUEST_WAS_ABORTED_CODE));
            } else if (num > 0) {
                log.info("输入的号码："+phone);
                //查询验证码正确时
                return userInfoService.AddLitemallUser(phone);
            } else {
                //提示错误信息
                return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.HANDLE_MES, HandleUtil.HANDLE_CODE));
            }
        }else{
            return JSON.toJSONString(HandleUtil.handleUtil("手机号码以绑定用户！不能重复绑定！",1));

        }
    }

    /**
     * 根据手机号查询是否绑定用户
     * @param phone
     * @return
     */
    @PostMapping("selphone")
    @ResponseBody
    @ApiOperation(notes = "", value = "根据手机号查询是否绑定用户（不绑定继续获取验证码、绑定则提示）")
    @ApiImplicitParam(name="phone", value = "手机号", required = true, paramType="query")
    public Object selphone(@Pattern(regexp = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$", message = "手机号不正确")String phone){
        int byphone = userService.selectLitemallByphone(phone);
        if(byphone > 0){
            return JSON.toJSONString(HandleUtil.handleUtil("手机号码以绑定用户！不能重复绑定！",1));
        }else{
            return DoesItExist(phone);
        }
    }

    /**
     * 根据手机号和验证码验证并且登陆
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("loginphone")
    @ResponseBody
    @ApiOperation(notes = "", value = "根据手机号和验证码验证并且登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name="phone", value = "手机号", required = true, paramType="query"),
            @ApiImplicitParam(name="code", value = "验证码" , required = true, paramType = "query")
    })
    public Object LoginPhone(@Pattern(regexp = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$", message = "手机号不正确")String phone , String code){
        int byphone = userService.selectLitemallByphone(phone);
        if (byphone > 0) {
            int num = appXinanService.findLitemallList(phone, code);
            if (num > 0){
                return userInfoService.LoginByphone(phone);
            }else{
                return JSON.toJSONString(HandleUtil.handleUtil("验证码错误",1));
            }
        }
        return JSON.toJSONString(HandleUtil.handleUtil("手机号码未注册！",1));

    }

    @PostMapping("outlogin")
    @ResponseBody
    @ApiOperation(notes = "", value = "退出登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name="phone", value = "手机号",  paramType="query"),
            @ApiImplicitParam(name="token", value = "密钥" ,  paramType = "query"),
            @ApiImplicitParam(name="username", value = "用户名" , paramType = "query")
    })
    public Object OutLoginLitemallUser(@Pattern(regexp = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$", message = "手机号不正确")String phone,String token,String username){
        return userInfoService.updOutLoginLitemallUser(phone, token, username);
    }



}

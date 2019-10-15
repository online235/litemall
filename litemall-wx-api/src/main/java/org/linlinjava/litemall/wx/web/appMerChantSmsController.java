package org.linlinjava.litemall.wx.web;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.linlinjava.litemall.db.domain.LitemallMerSms;
import org.linlinjava.litemall.db.service.LitemallMerSmsService;
import org.linlinjava.litemall.wx.util.HandleUtil;
import org.linlinjava.litemall.wx.vo.CouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pc/merchant")
@Validated
@Api(value = "商户PC接口",tags = "商户PC接口")
@Slf4j
public class appMerChantSmsController {
    @Autowired
    private LitemallMerSmsService litemallMerSmsService;

    /**
     *
     * @param phone
     * @return 验证码请求
     * @throws ParseException
     */
    @PostMapping("existsms")
    @ResponseBody
    @ApiOperation(notes = "", value = "验证码请求（以方式请求：号码存在方式、号码不存在方式）")
    @ApiImplicitParam(name="phone", value = "手机号", required = true, paramType="query")
    public String DoesItExist(@Pattern(regexp = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$", message = "手机号不正确")String phone) {
        log.info("----------");
        int num = litemallMerSmsService.findLitemallList(phone,"");
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
     *  获取验证码
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
        LitemallMerSms sms = new LitemallMerSms();
        sms.setPhone(phone);
        sms.setVerification_code(numder);
        sms.setCreatedat(date);
        sms.setUpdatedat(date);
        int result = litemallMerSmsService.addSmsByPhoneAndCode(sms);
        if (result > 0) {
            return url;
        }else {
            return JSON.toJSONString(HandleUtil.handleUtil(HandleUtil.HANDLE_MES, HandleUtil.HANDLE_CODE));
        }
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

    /**
     * 验证码验证
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("verification")
    @ResponseBody
    @ApiOperation(notes = "", value = "根据手机号和验证码验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name="phone", value = "手机号", required = true, paramType="query"),
            @ApiImplicitParam(name="code", value = "验证码" , required = true, paramType = "query")
    })
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

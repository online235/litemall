package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.dto.UserInfo;
import org.linlinjava.litemall.wx.util.HandleUtil;
import org.linlinjava.litemall.wx.util.TokenProccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {
    @Autowired
    private LitemallUserService userService;


    public UserInfo getInfo(Integer userId) {
        LitemallUser user = userService.findById(userId);
        Assert.state(user != null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }

    public LitemallUser LoginLitemall(String username){
        TokenProccessor tokenProccessor = TokenProccessor.getInstance();
        int result = userService.addOrTokenLogin(tokenProccessor.makeToken(), HandleUtil.presenttime(),username);
        LitemallUser litemallUser = userService.selectLitemallLogin(username);
        if (litemallUser != null && result == 1){
            return litemallUser;
        }
        else{
            return null;
        }
    }

    public LitemallUser LoginToken(String token){
        LitemallUser litemallUser = userService.selectLitemallLoginToekn(token);
        if (litemallUser != null) {
            boolean bo = HandleUtil.ExpirationDate(litemallUser.getTokencreate());
            int result = 0;
            if (!bo) {
                result = userService.updTokenCreate(token, HandleUtil.presenttime());
                if (litemallUser != null && result == 1) {
                    return litemallUser;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    public Object AddLitemallUser(String phone){
        if (!phone.isEmpty()) {
            TokenProccessor tokenProccessor = TokenProccessor.getInstance();
            String token = tokenProccessor.makeToken();
            String time = HandleUtil.presenttime();
            int result = userService.AddLitemallUser(phone,token,time);
            if (result > 0){
                LitemallUser litemallUser = new LitemallUser();
                litemallUser.setTokencode(token);
                litemallUser.setMobile(phone);
                litemallUser.setUsername(phone);
                return ResponseUtil.okUser(litemallUser);
            }
        }
        return ResponseUtil.fail(-1,"注册失败请检查是否正确！");
    }

    public Object LoginByphone(String phone){
        TokenProccessor tokenProccessor = TokenProccessor.getInstance();
        if (phone != null) {
            int result = userService.updTokenLitemallUser(phone,tokenProccessor.makeToken(),HandleUtil.presenttime());
            if (result > 0)
                return ResponseUtil.okUser(userService.LoginPhone(phone));
        }
        return null;
    }

    public Object updOutLoginLitemallUser(String phone,String token,String username){
        int result = userService.updOutLoginLitemallUser(phone, token, username);
        if (result > 0){
            return ResponseUtil.ok();
        }else{
            return ResponseUtil.fail();
        }
    }
}

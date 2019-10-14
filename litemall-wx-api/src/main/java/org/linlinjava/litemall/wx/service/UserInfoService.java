package org.linlinjava.litemall.wx.service;

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
        boolean bo = HandleUtil.ExpirationDate(litemallUser.getTokencreate());
        int result = 0;
        if (!bo) {
            result = userService.updTokenCreate(token, HandleUtil.presenttime());
            if (litemallUser != null && result == 1){
                return litemallUser;
            }
            else{
                return null;
            }
        }else{
            return null;
        }
    }

}

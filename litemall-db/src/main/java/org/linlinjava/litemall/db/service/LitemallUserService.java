package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallUserMapper;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.LitemallUserExample;
import org.linlinjava.litemall.db.domain.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LitemallUserService {
    @Resource
    private LitemallUserMapper userMapper;

    public LitemallUser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public UserVo findUserVoById(Integer userId) {
        LitemallUser user = findById(userId);
        UserVo userVo = new UserVo();
        userVo.setNickname(user.getNickname());
        userVo.setAvatar(user.getAvatar());
        return userVo;
    }

    public LitemallUser queryByOid(String openId) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    public void add(LitemallUser user) {
        user.setAddTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insertSelective(user);
    }

    public int updateById(LitemallUser user) {
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public List<LitemallUser> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order) {
        LitemallUserExample example = new LitemallUserExample();
        LitemallUserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (!StringUtils.isEmpty(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

    public int count() {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andDeletedEqualTo(false);

        return (int) userMapper.countByExample(example);
    }

    public List<LitemallUser> queryByUsername(String username) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public boolean checkByUsername(String username) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.countByExample(example) != 0;
    }

    public List<LitemallUser> queryByMobile(String mobile) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public List<LitemallUser> queryByOpenid(String openid) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andWeixinOpenidEqualTo(openid).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        userMapper.logicalDeleteByPrimaryKey(id);
    }

    public int addOrTokenLogin(String token ,String time,String username){
        if(username != null && token != null && time != null)
            return userMapper.addOrTokenLogin(token,time,username);
        else
            return 0;
    }

    public LitemallUser selectLitemallLogin(String username){
        if(username != null)
            return userMapper.selectLitemallLogin(username);
        else
            return null;
    }

    public LitemallUser selectLitemallLoginToekn(String token){
        if(!token.isEmpty()) {
            return userMapper.selectLitemallLoginToekn(token);
        }else {
            return null;
        }
    }

    public int updTokenCreate (String token, String time){
        if (token != null && time != null){
            return userMapper.updTokenCreate(token,time);
        }else{
            return 0;
        }
    }

    public int selectLitemallByphone(String phone){
        int reuslt = userMapper.selectLitemallByPhone(phone);
        if(reuslt > 0){
            return 1;
        }
        return 0;
    }

    public int AddLitemallUser (String phone,String token,String time){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time,dateTimeFormatter);
        LitemallUser litemallUser = new LitemallUser();
        litemallUser.setUsername(phone);
        litemallUser.setNickname(phone);
        litemallUser.setMobile(phone);
        litemallUser.setAddTime(localDateTime);
        litemallUser.setUpdateTime(localDateTime);
        litemallUser.setTokencode(token);
        litemallUser.setTokencreate(time);
        return userMapper.AddLitemallUser(litemallUser);
    }

    public LitemallUser LoginPhone(String phone){
        if(phone != null){
            return userMapper.LoginPhone(phone);
        }
        return null;
    }

    public int updTokenLitemallUser(String phone,String token ,String time){
        if(phone.isEmpty()||token.isEmpty()||time.isEmpty()){
            return 0;
        }
            int result = userMapper.updTokenLitemallUser(phone, token, time);
        if(result > 0)
            return 1;
        else
            return 0;
    }

    public int updOutLoginLitemallUser(String phone,String token,String username){
        if(phone == null&&token==null&&username==null){
            return 0;
        }
        int result = userMapper.updOutLoginLitemallUser(phone, token, username);
        if (result > 0)
            return 1;
        else
            return 0;
    }

}

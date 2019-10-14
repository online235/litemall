package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallSms;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppXinanMapper {
    int addSmsByPhoneAndCode(LitemallSms sms);
    int updSmsByPhoneAndCode(LitemallSms sms);
    List<LitemallSms> findLitemallList(@Param("phone") String phone
            ,@Param("code") String Code);
//    Map<String,Object> findLitemallById(@Param("phone") String phone);
}

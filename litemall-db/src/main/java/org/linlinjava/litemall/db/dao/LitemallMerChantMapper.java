package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallMerChant;
import org.linlinjava.litemall.db.domain.LitemallMerSms;

import java.util.List;

public interface LitemallMerChantMapper {
    List<LitemallMerChant> findLitemallMerChant(@Param("username")String username,@Param("phone")String phone);
    int addLitemallMerChant(LitemallMerChant litemallMerChant);
    int updLitemallMerChantByphone(LitemallMerChant litemallMerChant);
    int delLitemallMerChantByphone(@Param("phone")String phone);
    int selectOneLitemallMerChantByPhone(@Param("phone") String phone);
    int addMerSmsByPhoneAndCode(LitemallMerSms sms);
    int updMerSmsByPhoneAndCode(LitemallMerSms sms);
    List<LitemallMerSms> findLitemallMerList(@Param("phone") String phone
            ,@Param("code") String Code);

}

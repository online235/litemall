package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallMerChant;
import org.linlinjava.litemall.db.service.LitemallMerChantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.GOODS_NAME_EXIST;

@Service
public class AdminLitemallMerChantService {
    @Autowired
    private LitemallMerChantService litemallMerChantService;

    public Object cretaMerChant (LitemallMerChant litemallMerChant){
        System.out.println(litemallMerChant.getPhone());
        if (litemallMerChantService.selectMerChantAdd(litemallMerChant.getPhone()) != 0){
            System.out.println(123);
            return ResponseUtil.fail(GOODS_NAME_EXIST, "手机号码已注册");
        }
        System.out.println(456);
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        litemallMerChant.setCreatedat(dateTime.format(formatter2));

        int ruselt = litemallMerChantService.addLitemallMerChant(litemallMerChant);
        if (ruselt > 0){
            System.out.println(789);
            return ResponseUtil.ok();
        }

        return null;
    }
}

package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallMerChantMapper;
import org.linlinjava.litemall.db.domain.LitemallMerChant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallMerChantService {

    @Resource
    private LitemallMerChantMapper litemallMerChantMapper;

    public List<LitemallMerChant> findLitemallMerChant(String username, String phone){
        return litemallMerChantMapper.findLitemallMerChant(username,phone);
    }
    public int addLitemallMerChant(LitemallMerChant litemallMerChant){
            return litemallMerChantMapper.addLitemallMerChant(litemallMerChant);
    }
    public int updLitemallMerChantByphone(LitemallMerChant litemallMerChant){
            return litemallMerChantMapper.updLitemallMerChantByphone(litemallMerChant);
    }
    public int delLitemallMerChantByphone(String phone){
            return litemallMerChantMapper.delLitemallMerChantByphone(phone);
    }

    public int selectMerChantAdd(String phone){
        int litemallMerChant = litemallMerChantMapper.selectOneLitemallMerChantByPhone(phone);
        if (litemallMerChant > 0)
            return 1;
        else
            return 0;
    }

}

package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.dto.GoodsAllinone;
import org.linlinjava.litemall.admin.service.AdminLitemallMerChantService;
import org.linlinjava.litemall.admin.util.Pager;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallMerChant;
import org.linlinjava.litemall.db.service.LitemallMerChantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.GOODS_NAME_EXIST;

@RestController
@RequestMapping("/admin/merchant")
@Validated
public class AmdinMerChantController {

    @Autowired
    private LitemallMerChantService litemallMerChantService;

    @Autowired
    private AdminLitemallMerChantService adminLitemallMerChantService;

    @RequiresPermissions("admin:merchant:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "商户管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String username, String phone ,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit){
        List<LitemallMerChant> litemallMerChants = litemallMerChantService.findLitemallMerChant(username,phone);
        Pager<LitemallMerChant> litemallMerChantPager = new Pager<>();
        litemallMerChantPager.setCurentPageIndex(page);
        litemallMerChantPager.setCountPerpage(limit);
        litemallMerChantPager.setBigList(litemallMerChants);
        Integer total = litemallMerChantPager.getRecordCount();
        Integer pageIndex = litemallMerChantPager.getCurentPageIndex();
        Integer pages = litemallMerChantPager.getPageCount();
        System.out.println(ResponseUtil.oktotal(litemallMerChantPager.getSmallList(),total,pageIndex,pages,limit));
        return ResponseUtil.oktotal(litemallMerChantPager.getSmallList(),total,pageIndex,pages,limit);
    }

    /**
     * 添加商户
     *
     * @param litemallMerChant
     * @return
     */
    @RequiresPermissions("admin:merchant:addmer")
    @RequiresPermissionsDesc(menu = {"用户管理", "商户管理"}, button = "添加")
    @PostMapping("/addmer")
    public Object create(@RequestBody LitemallMerChant litemallMerChant) {
        return adminLitemallMerChantService.cretaMerChant(litemallMerChant);
    }
}

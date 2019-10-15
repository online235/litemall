package org.linlinjava.litemall.wx.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GrouponRuleVo {
    private Integer id;
    private String name;
    private String brief;
    private String picUrl;
    private BigDecimal counterPrice;
    private BigDecimal retailPrice;
    private BigDecimal grouponPrice;
    private BigDecimal grouponDiscount;
    private Integer grouponMember;
}

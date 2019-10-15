package org.linlinjava.litemall.wx.vo;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class CouponVo {
    private Integer id;
    private String name;
    private String desc;
    private String tag;
    private String min;
    private String discount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

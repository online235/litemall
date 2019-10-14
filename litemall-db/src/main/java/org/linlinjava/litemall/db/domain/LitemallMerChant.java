package org.linlinjava.litemall.db.domain;

public class LitemallMerChant {
    private  Integer id; //商户id
    private String username ; //商户名称
    private String phone;      //商户手机号码
    private String createdat;   //商户创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }
}

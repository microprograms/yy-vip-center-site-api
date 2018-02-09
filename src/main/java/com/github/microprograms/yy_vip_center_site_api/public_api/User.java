package com.github.microprograms.yy_vip_center_site_api.public_api;

import com.github.microprograms.micro_relational_data_model_runtime.MicroRelationalDataModel;
import com.github.microprograms.micro_relational_data_model_runtime.Comment;
import com.github.microprograms.micro_relational_data_model_runtime.Required;

@MicroRelationalDataModel(version = "v0.0.11")
public class User {

    @Comment(value = "用户ID")
    @Required(value = true)
    private String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Comment(value = "Token")
    @Required(value = true)
    private String token = "";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Comment(value = "手机号")
    @Required(value = true)
    private String phone = "";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Comment(value = "昵称")
    @Required(value = true)
    private String nickname = "";

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Comment(value = "钱包 - 余额(分)")
    @Required(value = true)
    private Integer walletAmount = 0;

    public Integer getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(Integer walletAmount) {
        this.walletAmount = walletAmount;
    }

    @Comment(value = "等级(0普通用户,1一级代理,2二级代理,3三级代理)")
    @Required(value = true)
    private Integer level = 0;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Comment(value = "是否禁用(0否1是)")
    @Required(value = true)
    private Integer isDisable = 0;

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    @Comment(value = "注册时间")
    @Required(value = true)
    private Long dtCreate = 0L;

    public Long getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Long dtCreate) {
        this.dtCreate = dtCreate;
    }

    @Comment(value = "上次修改时间")
    @Required(value = true)
    private Long dtLastModify = 0L;

    public Long getDtLastModify() {
        return dtLastModify;
    }

    public void setDtLastModify(Long dtLastModify) {
        this.dtLastModify = dtLastModify;
    }
}

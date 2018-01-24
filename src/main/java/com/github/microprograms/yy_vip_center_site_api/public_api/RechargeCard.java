package com.github.microprograms.yy_vip_center_site_api.public_api;

import com.github.microprograms.micro_relational_data_model_runtime.MicroRelationalDataModel;
import com.github.microprograms.micro_relational_data_model_runtime.Comment;
import com.github.microprograms.micro_relational_data_model_runtime.Required;

@MicroRelationalDataModel(version = "v0.0.6")
public class RechargeCard {

    @Comment(value = "ID")
    @Required(value = true)
    private String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Comment(value = "面额(元)")
    @Required(value = true)
    private Integer amount = 0;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Comment(value = "原始密码序列编码串")
    @Required(value = true)
    private String rawPasswordSeriesCode = "";

    public String getRawPasswordSeriesCode() {
        return rawPasswordSeriesCode;
    }

    public void setRawPasswordSeriesCode(String rawPasswordSeriesCode) {
        this.rawPasswordSeriesCode = rawPasswordSeriesCode;
    }

    @Comment(value = "入库时间")
    @Required(value = true)
    private Long dtCreate = 0L;

    public Long getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Long dtCreate) {
        this.dtCreate = dtCreate;
    }

    @Comment(value = "入库人ID")
    @Required(value = true)
    private String createrId = "";

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    @Comment(value = "入库人登录名")
    @Required(value = true)
    private String createrLoginName = "";

    public String getCreaterLoginName() {
        return createrLoginName;
    }

    public void setCreaterLoginName(String createrLoginName) {
        this.createrLoginName = createrLoginName;
    }

    @Comment(value = "使用时间")
    @Required(value = true)
    private Long dtUse = 0L;

    public Long getDtUse() {
        return dtUse;
    }

    public void setDtUse(Long dtUse) {
        this.dtUse = dtUse;
    }

    @Comment(value = "使用人ID")
    @Required(value = true)
    private String userId = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Comment(value = "使用人登录名")
    @Required(value = true)
    private String userLoginName = "";

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
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

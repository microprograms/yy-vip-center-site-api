package com.github.microprograms.yy_vip_center_site_api.public_api;

import com.github.microprograms.micro_relational_data_model_runtime.MicroRelationalDataModel;
import com.github.microprograms.micro_relational_data_model_runtime.Comment;
import com.github.microprograms.micro_relational_data_model_runtime.Required;

@MicroRelationalDataModel(version = "v0.0.10")
public class GoodsCategory {

    @Comment(value = "商品分类ID")
    @Required(value = true)
    private String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Comment(value = "名称")
    @Required(value = true)
    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Comment(value = "描述")
    @Required(value = true)
    private String desc = "";

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Comment(value = "排序号(小的在前)")
    @Required(value = true)
    private Integer reorder = 0;

    public Integer getReorder() {
        return reorder;
    }

    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    @Comment(value = "创建时间")
    @Required(value = true)
    private Long dtCreate = 0L;

    public Long getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Long dtCreate) {
        this.dtCreate = dtCreate;
    }

    @Comment(value = "创建人ID")
    @Required(value = true)
    private String createrId = "";

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    @Comment(value = "创建人登录名")
    @Required(value = true)
    private String createrLoginName = "";

    public String getCreaterLoginName() {
        return createrLoginName;
    }

    public void setCreaterLoginName(String createrLoginName) {
        this.createrLoginName = createrLoginName;
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

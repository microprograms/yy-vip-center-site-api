package com.github.microprograms.yy_vip_center_site_api.public_api;

import com.github.microprograms.micro_relational_data_model_runtime.MicroRelationalDataModel;
import com.github.microprograms.micro_relational_data_model_runtime.Comment;
import com.github.microprograms.micro_relational_data_model_runtime.Required;

@MicroRelationalDataModel(version = "v0.0.10")
public class Goods {

    @Comment(value = "商品ID")
    @Required(value = true)
    private String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Comment(value = "商品分类ID")
    @Required(value = true)
    private String categoryId = "";

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Comment(value = "商品分类名称")
    @Required(value = true)
    private String categoryName = "";

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    @Comment(value = "图片URL列表(JsonArray)")
    @Required(value = true)
    private String pictureUrls = "";

    public String getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(String pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    @Comment(value = "商品名称")
    @Required(value = true)
    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Comment(value = "商品描述/属性")
    @Required(value = true)
    private String desc = "";

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Comment(value = "商品价格(分)")
    @Required(value = true)
    private Integer price = 0;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Comment(value = "商品价格(分) - 一级代理商")
    @Required(value = true)
    private Integer priceLevel1 = 0;

    public Integer getPriceLevel1() {
        return priceLevel1;
    }

    public void setPriceLevel1(Integer priceLevel1) {
        this.priceLevel1 = priceLevel1;
    }

    @Comment(value = "商品价格(分) - 二级代理商")
    @Required(value = true)
    private Integer priceLevel2 = 0;

    public Integer getPriceLevel2() {
        return priceLevel2;
    }

    public void setPriceLevel2(Integer priceLevel2) {
        this.priceLevel2 = priceLevel2;
    }

    @Comment(value = "商品价格(分) - 三级代理商")
    @Required(value = true)
    private Integer priceLevel3 = 0;

    public Integer getPriceLevel3() {
        return priceLevel3;
    }

    public void setPriceLevel3(Integer priceLevel3) {
        this.priceLevel3 = priceLevel3;
    }

    @Comment(value = "商品库存")
    @Required(value = true)
    private Integer stock = 0;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

    @Comment(value = "是否下架(0否1是)")
    @Required(value = true)
    private Integer isSoldOut = 0;

    public Integer getIsSoldOut() {
        return isSoldOut;
    }

    public void setIsSoldOut(Integer isSoldOut) {
        this.isSoldOut = isSoldOut;
    }

    @Comment(value = "下架时间")
    @Required(value = true)
    private Long dtSoldOut = 0L;

    public Long getDtSoldOut() {
        return dtSoldOut;
    }

    public void setDtSoldOut(Long dtSoldOut) {
        this.dtSoldOut = dtSoldOut;
    }

    @Comment(value = "下架人ID")
    @Required(value = true)
    private String soldOutOperatorId = "";

    public String getSoldOutOperatorId() {
        return soldOutOperatorId;
    }

    public void setSoldOutOperatorId(String soldOutOperatorId) {
        this.soldOutOperatorId = soldOutOperatorId;
    }

    @Comment(value = "下架人登录名")
    @Required(value = true)
    private String soldOutOperatorLoginName = "";

    public String getSoldOutOperatorLoginName() {
        return soldOutOperatorLoginName;
    }

    public void setSoldOutOperatorLoginName(String soldOutOperatorLoginName) {
        this.soldOutOperatorLoginName = soldOutOperatorLoginName;
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

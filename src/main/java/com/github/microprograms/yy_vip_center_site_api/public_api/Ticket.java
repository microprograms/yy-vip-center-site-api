package com.github.microprograms.yy_vip_center_site_api.public_api;

import com.github.microprograms.micro_relational_data_model_runtime.MicroRelationalDataModel;
import com.github.microprograms.micro_relational_data_model_runtime.Comment;
import com.github.microprograms.micro_relational_data_model_runtime.Required;

@MicroRelationalDataModel(version = "v0.0.18")
public class Ticket {

    @Comment(value = "卡密ID")
    @Required(value = true)
    private String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Comment(value = "卡密")
    @Required(value = true)
    private String ticket = "";

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Comment(value = "卡密商品ID")
    @Required(value = true)
    private String ticketGoodsId = "";

    public String getTicketGoodsId() {
        return ticketGoodsId;
    }

    public void setTicketGoodsId(String ticketGoodsId) {
        this.ticketGoodsId = ticketGoodsId;
    }

    @Comment(value = "是否已售出(0否1是)")
    @Required(value = true)
    private Integer isSoldOut = 0;

    public Integer getIsSoldOut() {
        return isSoldOut;
    }

    public void setIsSoldOut(Integer isSoldOut) {
        this.isSoldOut = isSoldOut;
    }

    @Comment(value = "售出时间")
    @Required(value = true)
    private Long dtSoldOut = 0L;

    public Long getDtSoldOut() {
        return dtSoldOut;
    }

    public void setDtSoldOut(Long dtSoldOut) {
        this.dtSoldOut = dtSoldOut;
    }

    @Comment(value = "卡密订单ID")
    @Required(value = true)
    private String ticketGoodsOrderId = "";

    public String getTicketGoodsOrderId() {
        return ticketGoodsOrderId;
    }

    public void setTicketGoodsOrderId(String ticketGoodsOrderId) {
        this.ticketGoodsOrderId = ticketGoodsOrderId;
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

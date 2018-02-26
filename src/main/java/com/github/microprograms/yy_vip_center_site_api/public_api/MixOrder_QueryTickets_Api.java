package com.github.microprograms.yy_vip_center_site_api.public_api;

import java.util.List;
import java.util.stream.Collectors;
import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.utils.MicroApiUtils;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;
import com.github.microprograms.micro_oss_core.MicroOss;
import com.github.microprograms.micro_oss_core.exception.MicroOssException;
import com.github.microprograms.micro_oss_core.model.dml.Condition;

@MicroApi(comment = "商品订单 - 提取卡密", type = "read", version = "v0.0.18")
public class MixOrder_QueryTickets_Api {

    private static List<Ticket> getTicketList(String orderId) throws MicroOssException {
        return MicroOss.queryAll(Ticket.class, Condition.build("ticketGoodsOrderId=", orderId));
    }

    private static void core(Req req, Resp resp) throws Exception {
        resp.setData(getTicketList(req.getOrderId()).stream().map(x -> x.getTicket()).collect(Collectors.toList()));
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getToken(), "token");
        MicroApiUtils.throwExceptionIfBlank(req.getOrderId(), "orderId");
        Resp resp = new Resp();
        core(req, resp);
        return resp;
    }

    public static class Req extends Request {

        @Comment(value = "Token")
        @Required(value = true)
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Comment(value = "商品订单ID")
        @Required(value = true)
        private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

    public static class Resp extends Response {

        @Comment(value = "卡密列表")
        @Required(value = true)
        private java.util.List<String> data;

        public java.util.List<String> getData() {
            return data;
        }

        public void setData(java.util.List<String> data) {
            this.data = data;
        }
    }
}

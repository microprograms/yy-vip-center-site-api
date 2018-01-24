package com.github.microprograms.yy_vip_center_site_api.public_api;

import java.util.ArrayList;
import java.util.List;

import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_api_runtime.exception.MicroApiPassthroughException;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.utils.MicroApiUtils;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;
import com.github.microprograms.micro_oss_core.MicroOss;
import com.github.microprograms.micro_oss_core.model.Field;
import com.github.microprograms.micro_oss_core.model.dml.Condition;
import com.github.microprograms.yy_vip_center_site_api.utils.Fn;

@MicroApi(comment = "商品订单 - 申请退货", type = "read", version = "v0.0.8")
public class MixOrder_RefundRequest_Api {

    private static Condition buildFinalCondition(Req req) {
        return Condition.build("id=", req.getOrderId());
    }

    private static List<Field> buildFieldsToUpdate(Req req) throws Exception {
        User user = Fn.queryUserByToken(req.getToken());
        if (user == null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.invalid_token);
        }
        List<Field> fields = new ArrayList<>();
        // 退货申请 - 状态(0未申请,1未审核,2已同意,3已拒绝)
        fields.add(new Field("refundRequestStatus", 1));
        fields.add(new Field("refundRequestComment", req.getRefundRequestComment()));
        fields.add(new Field("dtRefundRequest", System.currentTimeMillis()));
        return Fn.buildFieldsIgnoreBlank(fields);
    }

    private static void core(Req req, Response resp) throws Exception {
        Condition finalCondition = buildFinalCondition(req);
        List<Field> fields = buildFieldsToUpdate(req);
        MicroOss.updateObject(MixOrder.class, fields, finalCondition);
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getToken(), "token");
        MicroApiUtils.throwExceptionIfBlank(req.getOrderId(), "orderId");
        Response resp = new Response();
        core(req, resp);
        return resp;
    }

    public static class Req extends Request {

        @Comment(value = "Token") @Required(value = true) private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Comment(value = "商品订单ID") @Required(value = true) private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        @Comment(value = "退货申请 - 备注") @Required(value = false) private String refundRequestComment;

        public String getRefundRequestComment() {
            return refundRequestComment;
        }

        public void setRefundRequestComment(String refundRequestComment) {
            this.refundRequestComment = refundRequestComment;
        }
    }
}

package com.github.microprograms.yy_vip_center_site_api.public_api;

import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_oss_core.model.dml.Condition;
import com.github.microprograms.micro_oss_core.MicroOss;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.utils.MicroApiUtils;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;

@MicroApi(comment = "商品 - 查询详情", type = "read", version = "v0.0.5")
public class Goods_QueryDetail_Api {

    private static Condition buildFinalCondition(Req req) {
        return Condition.build("id=", req.getGoodsId());
    }

    private static void core(Req req, Resp resp) throws Exception {
        Condition finalCondition = buildFinalCondition(req);
        resp.setData(MicroOss.queryObject(Goods.class, finalCondition));
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getGoodsId(), "goodsId");
        Resp resp = new Resp();
        core(req, resp);
        return resp;
    }

    public static class Req extends Request {

        @Comment(value = "商品ID")
        @Required(value = true)
        private String goodsId;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }
    }

    public static class Resp extends Response {

        @Comment(value = "商品详情")
        @Required(value = true)
        private Goods data;

        public Goods getData() {
            return data;
        }

        public void setData(Goods data) {
            this.data = data;
        }
    }
}

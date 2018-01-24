package com.github.microprograms.yy_vip_center_site_api.public_api;

import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_api_runtime.exception.MicroApiPassthroughException;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.utils.MicroApiUtils;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;
import com.github.microprograms.micro_oss_core.MicroOss;
import com.github.microprograms.yy_vip_center_site_api.utils.Fn;

@MicroApi(comment = "商品订单 - 购买", type = "read", version = "v0.0.6")
public class MixOrder_Buy_Api {

    private static MixOrder buildMixOrder(Req req) throws Exception {
        User user = Fn.queryUserByToken(req.getToken());
        if (user == null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.invalid_token);
        }
        Goods goods = Fn.queryGoodsById(req.getGoodsId());
        if (goods == null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.goods_not_exist);
        }
        if (goods.getStock() <= 0) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.low_stock);
        }
        MixOrder mixOrder = new MixOrder();
        mixOrder.setId(UUID.randomUUID().toString());
        mixOrder.setUserId(user.getId());
        mixOrder.setUserNickname(user.getNickname());
        mixOrder.setOrderAmount(goods.getPrice());
        mixOrder.setGoodsId(goods.getId());
        mixOrder.setGoodsName(goods.getName());
        mixOrder.setGoodsDetail(JSON.toJSONString(goods));
        mixOrder.setComment(null);
        mixOrder.setDtCreate(System.currentTimeMillis());
        return mixOrder;
    }

    private static void core(Req req, Response resp) throws Exception {
        MicroOss.insertObject(buildMixOrder(req));
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getToken(), "token");
        MicroApiUtils.throwExceptionIfBlank(req.getGoodsId(), "goodsId");
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

        @Comment(value = "商品ID") @Required(value = true) private String goodsId;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }
    }
}

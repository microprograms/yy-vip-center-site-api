package com.github.microprograms.yy_vip_center_site_api.public_api;

import java.util.ArrayList;
import java.util.List;
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
import com.github.microprograms.micro_oss_core.model.Field;
import com.github.microprograms.micro_oss_core.model.dml.Condition;
import com.github.microprograms.yy_vip_center_site_api.utils.Fn;

@MicroApi(comment = "商品订单 - 购买", type = "read", version = "v0.0.15")
public class MixOrder_Buy_Api {

    private static void core(Req req, Response resp) throws Exception {
        User user = Fn.queryUserByToken(req.getToken());
        if (user == null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.invalid_token);
        }
        Goods goods = Fn.queryGoodsById(req.getGoodsId());
        if (goods == null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.goods_not_exist);
        }
        if (goods.getIsSoldOut() == 1) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.goods_not_exist);
        }
        if (goods.getStock() <= 0) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.low_stock);
        }
        int orderAmount = getOrderAmount(user, goods);
        int oldWalletAmount = user.getWalletAmount();
        int newWalletAmount = oldWalletAmount - orderAmount;
        if (newWalletAmount < 0) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.low_wallet_amount);
        }
        String mixOrderId = Fn.genNewMixOrderId();
        // 订单
        MixOrder mixOrder = new MixOrder();
        mixOrder.setId(mixOrderId);
        mixOrder.setUserId(user.getId());
        mixOrder.setUserNickname(user.getNickname());
        mixOrder.setOrderAmount(orderAmount);
        mixOrder.setGoodsId(goods.getId());
        mixOrder.setGoodsName(goods.getName());
        mixOrder.setGoodsCategoryId(goods.getCategoryId());
        mixOrder.setGoodsCategoryName(goods.getCategoryName());
        mixOrder.setGoodsCommentTemplate(goods.getCommentTemplate());
        mixOrder.setGoodsDetail(JSON.toJSONString(goods));
        mixOrder.setComment(req.getComment());
        mixOrder.setDtCreate(System.currentTimeMillis());
        MicroOss.insertObject(mixOrder);
        // 用户
        List<Field> userFields = new ArrayList<>();
        userFields.add(new Field("walletAmount", newWalletAmount));
        MicroOss.updateObject(User.class, userFields, Condition.build("id=", user.getId()));
        // 钱包账单
        WalletBill walletBill = new WalletBill();
        walletBill.setId(UUID.randomUUID().toString());
        walletBill.setUserId(user.getId());
        walletBill.setUserNickname(user.getNickname());
        // 类型(1入账,2消费)
        walletBill.setType(2);
        walletBill.setDtCreate(System.currentTimeMillis());
        walletBill.setAmount(orderAmount);
        walletBill.setOldWalletAmount(oldWalletAmount);
        walletBill.setNewWalletAmount(newWalletAmount);
        walletBill.setOutOrderGoodsId(goods.getId());
        walletBill.setOutOrderGoodsName(goods.getName());
        walletBill.setOutOrderId(mixOrderId);
        MicroOss.insertObject(walletBill);
    }

    private static int getOrderAmount(User user, Goods goods) {
        switch(user.getLevel()) {
            case 0:
                return goods.getPrice();
            case 1:
                return goods.getPriceLevel1();
            case 2:
                return goods.getPriceLevel2();
            case 3:
                return goods.getPriceLevel3();
            default:
                return goods.getPrice();
        }
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getToken(), "token");
        MicroApiUtils.throwExceptionIfBlank(req.getGoodsId(), "goodsId");
        MicroApiUtils.throwExceptionIfBlank(req.getComment(), "comment");
        Response resp = new Response();
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

        @Comment(value = "商品ID")
        @Required(value = true)
        private String goodsId;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        @Comment(value = "订单备注(JsonObject)")
        @Required(value = true)
        private String comment;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}

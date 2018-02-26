package com.github.microprograms.yy_vip_center_site_api.public_api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.alibaba.fastjson.JSON;
import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_api_runtime.exception.MicroApiPassthroughException;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.utils.MicroApiUtils;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;
import com.github.microprograms.micro_oss_core.MicroOss;
import com.github.microprograms.micro_oss_core.Transaction;
import com.github.microprograms.micro_oss_core.exception.MicroOssException;
import com.github.microprograms.micro_oss_core.model.Field;
import com.github.microprograms.micro_oss_core.model.dml.Condition;
import com.github.microprograms.micro_oss_core.model.dml.InsertCommand;
import com.github.microprograms.micro_oss_core.model.dml.PagerRequest;
import com.github.microprograms.micro_oss_core.model.dml.UpdateCommand;
import com.github.microprograms.micro_oss_core.model.dml.Where;
import com.github.microprograms.yy_vip_center_site_api.utils.Fn;

@MicroApi(comment = "商品订单 - 购买卡密", type = "read", version = "v0.0.18")
public class MixOrder_BuyTicketGoods_Api {

    private static synchronized void core(Req req, Response resp) throws Exception {
        User user = Fn.queryUserByToken(req.getToken());
        if (user == null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.invalid_token);
        }
        TicketGoods ticketGoods = Fn.queryTicketGoodsById(req.getTicketGoodsId());
        if (ticketGoods == null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.goods_not_exist);
        }
        if (ticketGoods.getIsSoldOut() == 1) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.goods_not_exist);
        }
        int buyAmount = req.getAmount();
        if (getStock(ticketGoods) < buyAmount) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.low_stock);
        }
        int orderAmount = getPrice(user, ticketGoods) * buyAmount;
        int oldWalletAmount = user.getWalletAmount();
        int newWalletAmount = oldWalletAmount - orderAmount;
        if (newWalletAmount < 0) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.low_wallet_amount);
        }
        try (Transaction transaction = MicroOss.beginTransaction()) {
            String mixOrderId = Fn.genNewMixOrderId();
            // 卡密
            List<Ticket> tickets = getUsableTicketList(ticketGoods, buyAmount);
            for (Ticket ticket : tickets) {
                List<Field> ticketFields = new ArrayList<>();
                ticketFields.add(new Field("isSoldOut", 1));
                ticketFields.add(new Field("dtSoldOut", System.currentTimeMillis()));
                ticketFields.add(new Field("ticketGoodsOrderId", mixOrderId));
                transaction.updateObject(new UpdateCommand(Ticket.class.getSimpleName(), ticketFields, Condition.build("id=", ticket.getId())));
            }
            // 订单
            MixOrder mixOrder = new MixOrder();
            mixOrder.setId(mixOrderId);
            mixOrder.setUserId(user.getId());
            mixOrder.setUserNickname(user.getNickname());
            mixOrder.setIsTicketGoodsOrder(1);
            mixOrder.setTicketGoodsOrder_buyAmount(buyAmount);
            mixOrder.setTicketGoodsOrder_tickets(JSON.toJSONString(tickets.stream().map(x -> x.getTicket()).collect(Collectors.toList())));
            mixOrder.setOrderAmount(orderAmount);
            mixOrder.setGoodsId(ticketGoods.getId());
            mixOrder.setGoodsName(ticketGoods.getName());
            mixOrder.setGoodsDetail(JSON.toJSONString(ticketGoods));
            mixOrder.setDtCreate(System.currentTimeMillis());
            transaction.insertObject(new InsertCommand(MicroOss.buildEntity(mixOrder)));
            // 用户
            List<Field> userFields = new ArrayList<>();
            userFields.add(new Field("walletAmount", newWalletAmount));
            transaction.updateObject(new UpdateCommand(User.class.getSimpleName(), userFields, Condition.build("id=", user.getId())));
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
            walletBill.setOutOrderGoodsId(ticketGoods.getId());
            walletBill.setOutOrderGoodsName(ticketGoods.getName());
            walletBill.setOutOrderId(mixOrderId);
            transaction.insertObject(new InsertCommand(MicroOss.buildEntity(walletBill)));
            transaction.commit();
        }
    }

    private static int getStock(TicketGoods ticketGoods) throws MicroOssException {
        return MicroOss.queryCount(Ticket.class, Where.and(Condition.build("ticketGoodsId=", ticketGoods.getId()), Condition.build("isSoldOut=", 0)));
    }

    private static int getPrice(User user, TicketGoods ticketGoods) {
        switch(user.getLevel()) {
            case 0:
                return ticketGoods.getPrice();
            case 1:
                return ticketGoods.getPriceLevel1();
            case 2:
                return ticketGoods.getPriceLevel2();
            case 3:
                return ticketGoods.getPriceLevel3();
            default:
                return ticketGoods.getPrice();
        }
    }

    private static List<Ticket> getUsableTicketList(TicketGoods ticketGoods, int count) throws MicroOssException {
        PagerRequest pager = new PagerRequest(0, count);
        Condition finalCondition = Where.and(Condition.build("ticketGoodsId=", ticketGoods.getId()), Condition.build("isSoldOut=", 0));
        return MicroOss.queryAll(Ticket.class, finalCondition, null, pager);
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getToken(), "token");
        MicroApiUtils.throwExceptionIfBlank(req.getTicketGoodsId(), "ticketGoodsId");
        MicroApiUtils.throwExceptionIfBlank(req.getAmount(), "amount");
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

        @Comment(value = "卡密商品ID")
        @Required(value = true)
        private String ticketGoodsId;

        public String getTicketGoodsId() {
            return ticketGoodsId;
        }

        public void setTicketGoodsId(String ticketGoodsId) {
            this.ticketGoodsId = ticketGoodsId;
        }

        @Comment(value = "购买数量")
        @Required(value = true)
        private Integer amount;

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }
    }
}

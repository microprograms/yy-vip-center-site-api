package com.github.microprograms.yy_vip_center_site_api.public_api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

@MicroApi(comment = "我的 - 充值", type = "read", version = "v0.0.5")
public class My_Recharge_Api {

    private static void core(Req req, Response resp) throws Exception {
        User user = Fn.queryUserByToken(req.getToken());
        if (user == null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.invalid_token);
        }
        RechargeCard rechargeCard = MicroOss.queryObject(RechargeCard.class, Condition.build("id=", req.getRawPasswordSeriesCode()));
        if (rechargeCard == null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.invalid_recharge_card);
        }
        if (rechargeCard.getDtUse() > 0) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.recharge_card_already_used);
        }
        // 充值卡
        List<Field> rechargeCardFields = new ArrayList<>();
        rechargeCardFields.add(new Field("userId", user.getId()));
        rechargeCardFields.add(new Field("userLoginName", user.getNickname()));
        rechargeCardFields.add(new Field("dtUse", System.currentTimeMillis()));
        MicroOss.updateObject(RechargeCard.class, rechargeCardFields, Condition.build("id=", req.getRawPasswordSeriesCode()));
        // 用户
        int amount = rechargeCard.getAmount() * 100;
        int oldWalletAmount = user.getWalletAmount();
        int newWalletAmount = oldWalletAmount + amount;
        List<Field> userFields = new ArrayList<>();
        userFields.add(new Field("walletAmount", newWalletAmount));
        MicroOss.updateObject(User.class, userFields, Condition.build("id=", user.getId()));
        // 钱包账单
        WalletBill walletBill = new WalletBill();
        walletBill.setId(UUID.randomUUID().toString());
        walletBill.setUserId(user.getId());
        walletBill.setUserNickname(user.getNickname());
        // 类型(1入账,2消费)
        walletBill.setType(1);
        walletBill.setDtCreate(System.currentTimeMillis());
        walletBill.setAmount(amount);
        walletBill.setOldWalletAmount(oldWalletAmount);
        walletBill.setNewWalletAmount(newWalletAmount);
        walletBill.setInRechargeCardId(rechargeCard.getId());
        walletBill.setInRechargeCardRawPasswordSeriesCode(req.getRawPasswordSeriesCode());
        walletBill.setInRechargeCardAmount(String.valueOf(rechargeCard.getAmount()));
        MicroOss.insertObject(walletBill);
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getToken(), "token");
        MicroApiUtils.throwExceptionIfBlank(req.getRawPasswordSeriesCode(), "rawPasswordSeriesCode");
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

        @Comment(value = "原始密码序列编码串") @Required(value = true) private String rawPasswordSeriesCode;

        public String getRawPasswordSeriesCode() {
            return rawPasswordSeriesCode;
        }

        public void setRawPasswordSeriesCode(String rawPasswordSeriesCode) {
            this.rawPasswordSeriesCode = rawPasswordSeriesCode;
        }
    }
}

package com.github.microprograms.yy_vip_center_site_api.public_api;

import com.github.microprograms.micro_api_runtime.model.ResponseCode;

public enum ErrorCodeEnum implements ResponseCode {

    /**Token已失效，请重新登录*/
    invalid_token(1010, "Token已失效，请重新登录"), /**没有字段需要更新，请填写需要更新的字段*/
    no_fields_need_to_be_updated(1011, "没有字段需要更新，请填写需要更新的字段"), /**手机号未注册*/
    phone_unregistered(1012, "手机号未注册"), /**手机号已被注册*/
    phone_already_registered(1013, "手机号已被注册"), /**验证码错误*/
    invalid_verification_code(1014, "验证码错误"), /**无效的充值卡*/
    invalid_recharge_card(1015, "无效的充值卡"), /**充值卡已被使用*/
    recharge_card_already_used(1016, "充值卡已被使用"), /**商品已下架*/
    goods_not_exist(1017, "商品已下架"), /**库存不足*/
    low_stock(1018, "库存不足"), /**余额不足*/
    low_wallet_amount(1019, "余额不足");

    private ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;

    public int getCode() {
        return code;
    }

    private final String message;

    public String getMessage() {
        return message;
    }
}

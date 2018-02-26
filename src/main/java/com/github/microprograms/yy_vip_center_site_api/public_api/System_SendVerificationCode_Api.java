package com.github.microprograms.yy_vip_center_site_api.public_api;

import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_api_runtime.exception.MicroApiPassthroughException;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.utils.MicroApiUtils;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;
import com.github.microprograms.yy_vip_center_site_api.utils.VerificationCodeUtils;

@MicroApi(comment = "系统 - 发送短信验证码", type = "read", version = "v0.0.18")
public class System_SendVerificationCode_Api {

    private static void core(Req req, Response resp) throws Exception {
        boolean isSuccess = VerificationCodeUtils.sendVerificationCode(req.getPhone(), VerificationCodeUtils.genVerificationCode());
        if (!isSuccess) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.sms_send_fail);
        }
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getPhone(), "phone");
        Response resp = new Response();
        core(req, resp);
        return resp;
    }

    public static class Req extends Request {

        @Comment(value = "手机号")
        @Required(value = true)
        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}

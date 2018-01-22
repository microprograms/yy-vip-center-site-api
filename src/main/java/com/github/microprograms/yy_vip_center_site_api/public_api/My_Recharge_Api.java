package com.github.microprograms.yy_vip_center_site_api.public_api;

import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_api_runtime.exception.MicroApiPassthroughException;
import com.github.microprograms.micro_api_runtime.enums.MicroApiReserveResponseCodeEnum;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.utils.MicroApiUtils;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;

@MicroApi(comment = "我的 - 充值", type = "read", version = "v0.0.3")
public class My_Recharge_Api {

    private static void core(Req req, Response resp) throws Exception {
        Object doSomeThingHere;
        throw new MicroApiPassthroughException(MicroApiReserveResponseCodeEnum.api_not_implemented_exception);
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

        @Comment(value = "Token")
        @Required(value = true)
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Comment(value = "原始密码序列编码串")
        @Required(value = true)
        private String rawPasswordSeriesCode;

        public String getRawPasswordSeriesCode() {
            return rawPasswordSeriesCode;
        }

        public void setRawPasswordSeriesCode(String rawPasswordSeriesCode) {
            this.rawPasswordSeriesCode = rawPasswordSeriesCode;
        }
    }
}

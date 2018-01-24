package com.github.microprograms.yy_vip_center_site_api.public_api;

import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_api_runtime.exception.MicroApiPassthroughException;
import com.github.microprograms.micro_api_runtime.enums.MicroApiReserveResponseCodeEnum;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.utils.MicroApiUtils;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;

@MicroApi(comment = "系统 - 修改我的密码", type = "read", version = "v0.0.6")
public class System_UpdateMyPassword_Api {

    private static void core(Req req, Response resp) throws Exception {
        Object doSomeThingHere;
        throw new MicroApiPassthroughException(MicroApiReserveResponseCodeEnum.api_not_implemented_exception);
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getToken(), "token");
        MicroApiUtils.throwExceptionIfBlank(req.getOldLoginPassword(), "oldLoginPassword");
        MicroApiUtils.throwExceptionIfBlank(req.getNewLoginPassword(), "newLoginPassword");
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

        @Comment(value = "旧的登录密码")
        @Required(value = true)
        private String oldLoginPassword;

        public String getOldLoginPassword() {
            return oldLoginPassword;
        }

        public void setOldLoginPassword(String oldLoginPassword) {
            this.oldLoginPassword = oldLoginPassword;
        }

        @Comment(value = "新的登录密码")
        @Required(value = true)
        private String newLoginPassword;

        public String getNewLoginPassword() {
            return newLoginPassword;
        }

        public void setNewLoginPassword(String newLoginPassword) {
            this.newLoginPassword = newLoginPassword;
        }
    }
}

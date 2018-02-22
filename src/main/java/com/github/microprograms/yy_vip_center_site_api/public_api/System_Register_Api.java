package com.github.microprograms.yy_vip_center_site_api.public_api;

import java.util.UUID;
import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_api_runtime.exception.MicroApiPassthroughException;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.utils.MicroApiUtils;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;
import com.github.microprograms.micro_oss_core.MicroOss;
import com.github.microprograms.yy_vip_center_site_api.utils.Fn;
import com.github.microprograms.yy_vip_center_site_api.utils.VerificationCodeUtils;

@MicroApi(comment = "系统 - 注册", type = "read", version = "v0.0.15")
public class System_Register_Api {

    private static User buildUser(Req req) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setPhone(req.getPhone());
        user.setToken(UUID.randomUUID().toString());
        user.setNickname(req.getNickname());
        user.setDtCreate(System.currentTimeMillis());
        return user;
    }

    private static void core(Req req, Resp resp) throws Exception {
        if (!VerificationCodeUtils.isValid(req.getPhone(), req.getVerificationCode())) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.invalid_verification_code);
        }
        if (Fn.queryUserByPhone(req.getPhone()) != null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.phone_already_registered);
        }
        if (Fn.queryUserByNickname(req.getNickname()) != null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.nickname_already_registered);
        }
        MicroOss.insertObject(buildUser(req));
        resp.setData(Fn.queryUserByPhone(req.getPhone()));
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getPhone(), "phone");
        MicroApiUtils.throwExceptionIfBlank(req.getNickname(), "nickname");
        MicroApiUtils.throwExceptionIfBlank(req.getVerificationCode(), "verificationCode");
        Resp resp = new Resp();
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

        @Comment(value = "昵称")
        @Required(value = true)
        private String nickname;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        @Comment(value = "验证码")
        @Required(value = true)
        private String verificationCode;

        public String getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
        }
    }

    public static class Resp extends Response {

        @Comment(value = "个人资料详情")
        @Required(value = true)
        private User data;

        public User getData() {
            return data;
        }

        public void setData(User data) {
            this.data = data;
        }
    }
}

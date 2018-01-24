package com.github.microprograms.yy_vip_center_site_api.public_api;

import java.util.Arrays;
import java.util.List;

import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_api_runtime.exception.MicroApiPassthroughException;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.utils.MicroApiUtils;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;
import com.github.microprograms.micro_oss_core.MicroOss;
import com.github.microprograms.micro_oss_core.model.dml.Condition;
import com.github.microprograms.micro_oss_core.model.dml.PagerRequest;
import com.github.microprograms.micro_oss_core.model.dml.PagerResponse;
import com.github.microprograms.micro_oss_core.model.dml.Sort;
import com.github.microprograms.micro_oss_core.model.dml.Where;
import com.github.microprograms.yy_vip_center_site_api.utils.Fn;

@MicroApi(comment = "商品订单 - 查询我的订单列表", type = "read", version = "v0.0.9")
public class MixOrder_QueryList_Api {

    private static Condition buildFinalCondition(Req req) throws Exception {
        User user = Fn.queryUserByToken(req.getToken());
        if (user == null) {
            throw new MicroApiPassthroughException(ErrorCodeEnum.invalid_token);
        }
        Condition userId = Condition.build("userId=", user.getId());
        Condition searchStatus = buildSearchStatusCondition(req.getSearchStatus());
        return Where.and(userId, searchStatus);
    }

    private static Condition buildSearchStatusCondition(int searchStatus) {
        switch (searchStatus) {
        case 0:
            return null;
        case 1:
            return Condition.build("isDispose=", 0);
        case 2:
            return Where.and(Condition.build("isDispose=", 1), Condition.build("refundRequestStatus=", 0));
        case 3:
            return Condition.build("refundRequestStatus=", 1);
        case 4:
            return Condition.build("refundRequestStatus=", 2);
        case 5:
            return Condition.build("refundRequestStatus=", 3);
        }
        return null;
    }

    private static List<Sort> buildSort(Req req) {
        return Arrays.asList(Sort.desc("dtCreate"));
    }

    private static void core(Req req, Resp resp) throws Exception {
        PagerRequest pager = new PagerRequest(req.getPageIndex(), req.getPageSize());
        Condition finalCondition = buildFinalCondition(req);
        List<Sort> sorts = buildSort(req);
        resp.setData(MicroOss.queryAll(MixOrder.class, finalCondition, sorts, pager));
        resp.setPager(new PagerResponse(pager, MicroOss.queryCount(MixOrder.class, finalCondition)));
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getToken(), "token");
        MicroApiUtils.throwExceptionIfBlank(req.getPageIndex(), "pageIndex");
        MicroApiUtils.throwExceptionIfBlank(req.getPageSize(), "pageSize");
        MicroApiUtils.throwExceptionIfBlank(req.getSearchStatus(), "searchStatus");
        Resp resp = new Resp();
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

        @Comment(value = "页码(从0开始)") @Required(value = true) private Integer pageIndex;

        public Integer getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(Integer pageIndex) {
            this.pageIndex = pageIndex;
        }

        @Comment(value = "页大小") @Required(value = true) private Integer pageSize;

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        @Comment(value = "状态(0全部,1未处理,2已处理,3退款审核中,4已退款,5已拒绝退款)") @Required(value = true) private Integer searchStatus;

        public Integer getSearchStatus() {
            return searchStatus;
        }

        public void setSearchStatus(Integer searchStatus) {
            this.searchStatus = searchStatus;
        }
    }

    public static class Resp extends Response {

        @Comment(value = "商品订单列表") @Required(value = true) private java.util.List<MixOrder> data;

        public java.util.List<MixOrder> getData() {
            return data;
        }

        public void setData(java.util.List<MixOrder> data) {
            this.data = data;
        }

        @Comment(value = "分页") @Required(value = true) private com.github.microprograms.micro_oss_core.model.dml.PagerResponse pager;

        public com.github.microprograms.micro_oss_core.model.dml.PagerResponse getPager() {
            return pager;
        }

        public void setPager(com.github.microprograms.micro_oss_core.model.dml.PagerResponse pager) {
            this.pager = pager;
        }
    }
}

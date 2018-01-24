package com.github.microprograms.yy_vip_center_site_api.public_api;

import java.util.Arrays;
import java.util.List;
import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
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

@MicroApi(comment = "商品 - 查询列表", type = "read", version = "v0.0.8")
public class Goods_QueryList_Api {

    private static Condition buildFinalCondition(Req req) {
        return Where.and(Condition.build("isSoldOut=", 0), Condition.build("categoryId=", req.getCategoryId()));
    }

    private static List<Sort> buildSort(Req req) {
        return Arrays.asList(Sort.asc("reorder"), Sort.desc("dtCreate"));
    }

    private static void core(Req req, Resp resp) throws Exception {
        PagerRequest pager = new PagerRequest(req.getPageIndex(), req.getPageSize());
        Condition finalCondition = buildFinalCondition(req);
        List<Sort> sorts = buildSort(req);
        resp.setData(MicroOss.queryAll(Goods.class, finalCondition, sorts, pager));
        resp.setPager(new PagerResponse(pager, MicroOss.queryCount(Goods.class, finalCondition)));
    }

    public static Response execute(Request request) throws Exception {
        Req req = (Req) request;
        MicroApiUtils.throwExceptionIfBlank(req.getPageIndex(), "pageIndex");
        MicroApiUtils.throwExceptionIfBlank(req.getPageSize(), "pageSize");
        MicroApiUtils.throwExceptionIfBlank(req.getCategoryId(), "categoryId");
        Resp resp = new Resp();
        core(req, resp);
        return resp;
    }

    public static class Req extends Request {

        @Comment(value = "页码(从0开始)")
        @Required(value = true)
        private Integer pageIndex;

        public Integer getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(Integer pageIndex) {
            this.pageIndex = pageIndex;
        }

        @Comment(value = "页大小")
        @Required(value = true)
        private Integer pageSize;

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        @Comment(value = "商品类别ID")
        @Required(value = true)
        private String categoryId;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }
    }

    public static class Resp extends Response {

        @Comment(value = "商品列表")
        @Required(value = true)
        private java.util.List<Goods> data;

        public java.util.List<Goods> getData() {
            return data;
        }

        public void setData(java.util.List<Goods> data) {
            this.data = data;
        }

        @Comment(value = "分页")
        @Required(value = true)
        private com.github.microprograms.micro_oss_core.model.dml.PagerResponse pager;

        public com.github.microprograms.micro_oss_core.model.dml.PagerResponse getPager() {
            return pager;
        }

        public void setPager(com.github.microprograms.micro_oss_core.model.dml.PagerResponse pager) {
            this.pager = pager;
        }
    }
}

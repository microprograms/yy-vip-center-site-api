package com.github.microprograms.yy_vip_center_site_api.public_api;

import java.util.Arrays;
import java.util.List;
import com.github.microprograms.micro_api_runtime.annotation.MicroApi;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_nested_data_model_runtime.Comment;
import com.github.microprograms.micro_nested_data_model_runtime.Required;
import com.github.microprograms.micro_oss_core.MicroOss;
import com.github.microprograms.micro_oss_core.model.dml.Condition;
import com.github.microprograms.micro_oss_core.model.dml.Sort;

@MicroApi(comment = "商品类别 - 查询全部", type = "read", version = "v0.0.12")
public class GoodsCategory_QueryAll_Api {

    private static Condition buildFinalCondition(Request req) {
        return null;
    }

    private static List<Sort> buildSort(Request req) {
        return Arrays.asList(Sort.asc("reorder"), Sort.desc("dtCreate"));
    }

    private static void core(Request req, Resp resp) throws Exception {
        Condition finalCondition = buildFinalCondition(req);
        List<Sort> sorts = buildSort(req);
        resp.setData(MicroOss.queryAll(GoodsCategory.class, finalCondition, sorts));
    }

    public static Response execute(Request request) throws Exception {
        Request req = request;
        Resp resp = new Resp();
        core(req, resp);
        return resp;
    }

    public static class Resp extends Response {

        @Comment(value = "商品类别列表(全部)")
        @Required(value = true)
        private java.util.List<GoodsCategory> data;

        public java.util.List<GoodsCategory> getData() {
            return data;
        }

        public void setData(java.util.List<GoodsCategory> data) {
            this.data = data;
        }
    }
}

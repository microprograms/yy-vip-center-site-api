package com.github.microprograms.yy_vip_center_site_api.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.microprograms.micro_oss_core.MicroOss;
import com.github.microprograms.micro_oss_core.exception.MicroOssException;
import com.github.microprograms.micro_oss_core.model.Field;
import com.github.microprograms.micro_oss_core.model.dml.Condition;
import com.github.microprograms.micro_oss_mysql.Config;
import com.github.microprograms.micro_oss_mysql.MysqlMicroOssProvider;
import com.github.microprograms.yy_vip_center_site_api.public_api.Goods;
import com.github.microprograms.yy_vip_center_site_api.public_api.User;
import com.typesafe.config.ConfigFactory;

public class Fn {
    private static final Logger log = LoggerFactory.getLogger(Fn.class);

    public static synchronized String genNewMixOrderId() throws InterruptedException {
        Thread.sleep(1);
        Date date = new Date();
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date) + date.getTime();
    }

    public static Goods queryGoodsById(String goodsId) throws MicroOssException {
        return MicroOss.queryObject(Goods.class, Condition.build("id=", goodsId));
    }

    public static User queryUserByToken(String token) throws MicroOssException {
        return MicroOss.queryObject(User.class, Condition.build("token=", token));
    }

    public static User queryUserByPhone(String phone) throws MicroOssException {
        return MicroOss.queryObject(User.class, Condition.build("phone=", phone));
    }

    public static void initOss() {
        com.typesafe.config.Config applicationConfig = getApplicationConfig();
        Config config = new Config();
        config.setDriver(applicationConfig.getString("jdbc_driver"));
        config.setUrl(applicationConfig.getString("jdbc_url"));
        config.setUser(applicationConfig.getString("jdbc_user"));
        config.setPassword(applicationConfig.getString("jdbc_password"));
        log.info("initOss> {}", JSON.toJSONString(config));
        MicroOss.set(new MysqlMicroOssProvider(config));
    }

    public static com.typesafe.config.Config getApplicationConfig() {
        return ConfigFactory.load();
    }

    public static List<Field> buildFieldsIgnoreBlank(Collection<Field> fields) {
        List<Field> list = new ArrayList<>();
        for (Field field : fields) {
            String name = field.getName();
            Object value = field.getValue();
            if (StringUtils.isBlank(name) || value == null || StringUtils.isBlank(value.toString())) {
                continue;
            }
            list.add(field);
        }
        return list;
    }
}

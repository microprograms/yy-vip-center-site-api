package com.github.microprograms.yy_vip_center_site_api.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.microprograms.micro_oss_core.MicroOss;
import com.github.microprograms.micro_oss_core.exception.MicroOssException;
import com.github.microprograms.micro_oss_core.model.Field;
import com.github.microprograms.micro_oss_core.model.dml.Condition;
import com.github.microprograms.micro_oss_ignite.Config;
import com.github.microprograms.micro_oss_ignite.IgniteMicroOssProvider;
import com.github.microprograms.yy_vip_center_site_api.public_api.User;
import com.typesafe.config.ConfigFactory;

public class Fn {
    private static final Logger log = LoggerFactory.getLogger(Fn.class);

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
        log.info("initOss> {}", JSON.toJSONString(config));
        MicroOss.set(new IgniteMicroOssProvider(config));
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

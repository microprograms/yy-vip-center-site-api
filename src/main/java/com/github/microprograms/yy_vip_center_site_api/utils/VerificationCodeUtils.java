package com.github.microprograms.yy_vip_center_site_api.utils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class VerificationCodeUtils {
    private final static int max_count = 5;

    private final static Cache<String, VerificationCode> cache = CacheBuilder.newBuilder()
            // 设置cache的初始大小为10，要合理设置该值
            .initialCapacity(10)
            // 设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
            .concurrencyLevel(5)
            // 设置cache中的数据在写入之后的存活时间为10秒
            .expireAfterWrite(5, TimeUnit.MINUTES)
            // 构建cache实例
            .build();

    public static String genVerificationCode() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(genVerificationCode());
        }
    }

    public static boolean sendVerificationCode(String phone, String verificationCode) throws ClientException {
        JSONObject templateParam = new JSONObject();
        templateParam.put("code", verificationCode);
        SendSmsResponse response = AliyunSmsUtils.sendSms(phone, templateParam);
        if (!response.getCode().equals("OK")) {
            return false;
        }
        cache.put(phone, new VerificationCode(verificationCode));
        return true;
    }

    public static boolean exist(String phone) {
        return cache.getIfPresent(phone) != null;
    }

    public static boolean isValid(String phone, String verificationCode) {
        VerificationCode code = cache.getIfPresent(phone);
        code.setCount(code.getCount() + 1);
        if (code.getCount() >= max_count) {
            cache.invalidate(phone);
        }
        return code.getVerificationCode().equals(verificationCode);
    }

    public static class VerificationCode {
        private String verificationCode;
        private int count;
        private long timestamp;

        public VerificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
            this.timestamp = System.currentTimeMillis();
        }

        public String getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}

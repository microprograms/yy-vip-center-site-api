package com.github.microprograms.yy_vip_center_site_api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MicroHttpServerListener implements com.github.microprograms.micro_http_server_runtime.MicroHttpServerListener {
    private static final Logger log = LoggerFactory.getLogger(MicroHttpServerListener.class);

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void onServerStartup() throws Exception {
        log.info("onServerStartup");
        Fn.initOss();
    }
}

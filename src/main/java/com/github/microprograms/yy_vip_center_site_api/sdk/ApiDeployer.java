package com.github.microprograms.yy_vip_center_site_api.sdk;

import com.github.microprograms.micro_api_sdk.MicroApiSdk;
import com.github.microprograms.micro_api_sdk.model.EngineDefinition;
import com.github.microprograms.micro_api_sdk.utils.ApiDeployUtils;

public class ApiDeployer {
    public static void main(String[] args) throws Exception {
        EngineDefinition engineDefinition = MicroApiSdk.buildEngineDefinition("design/public-api.json");
        ApiDeployUtils.stop(engineDefinition);
        ApiDeployUtils.deploy(engineDefinition);
        ApiDeployUtils.start(engineDefinition);
        Thread.sleep(2 * 1000);
        ApiDeployUtils.showLatestServerOut(engineDefinition);
    }
}

package com.veseyar.monitor.client.config;

import ru.tinkoff.piapi.core.InvestApi;

public class ApiConnector {

    private final Parameters parameters;
    private InvestApi api;

    public ApiConnector(Parameters parameters) {
        this.parameters = parameters;
    }

    public synchronized InvestApi getApi() {
        if (api == null) {
            if (parameters.isSandBoxMode()) {
                api = InvestApi.createSandbox(parameters.getToken());
            } else {
                api = InvestApi.create(parameters.getToken());
            }
        }
        return api;
    }
}

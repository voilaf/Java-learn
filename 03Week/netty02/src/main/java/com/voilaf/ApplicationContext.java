package com.voilaf;

import com.voilaf.rpcClient.HttpClient;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ApplicationContext {

    private ApplicationContext(){}

    private static ApplicationContext instance;

    private HttpClient httpClient;

    public static ApplicationContext getInstance() {
        if (!(instance instanceof ApplicationContext)) {
            instance = new ApplicationContext();
        }
        return instance;
    }

}

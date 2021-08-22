package com.voilaf.service;


import java.util.Random;

public class ServiceDispatcher {

    private static class ServiceDispatcherInner {
        private static ServiceDispatcher instance = new ServiceDispatcher();
    }

    public static ServiceDispatcher getInstance() {
        return ServiceDispatcherInner.instance;
    }

    private String[] services;

    public void setServiceConfig(String serviceConfig) throws Exception {
        if (serviceConfig == null || "".equals(serviceConfig)) {
            throw new Exception("routeConfig参数错误");
        }

        // 多个路由地址按;切割
        String[] services = serviceConfig.split(";");
        if (services.length == 0) {
            throw new Exception("route配置失败，请检查配置参数");
        }

        this.services = services;
    }

    /**
     * 随机获取路由地址
     * @return
     */
    public String getRandomServiceAddress() {
        Random r = new Random();
        int index = r.nextInt(this.services.length);
        return this.services[index];
    }

    /**
     * 打印路由地址
     */
    public void printServiceAddress() {
        int length = this.services.length;
        for (int i=0; i<length; i++) {
            System.out.println("serviceAddress:" + this.services[i]);
        }
    }
}

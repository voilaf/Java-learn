package com.example.springboot.config;

import com.example.springboot.enums.DataSourceEnum;

public class DataSourceContext {

    private final static ThreadLocal<DataSourceEnum> threadLocal = new ThreadLocal<>();

    public static void set(DataSourceEnum dataSourceEnum) {
        threadLocal.set(dataSourceEnum);
    }

    public static DataSourceEnum get() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}

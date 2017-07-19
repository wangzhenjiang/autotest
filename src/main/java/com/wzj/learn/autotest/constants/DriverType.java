package com.wzj.learn.autotest.constants;

import org.apache.commons.lang3.StringUtils;

 public enum DriverType {

    CHROME, FIREFOX, IE;

    public static DriverType parse(String type) {
        if (StringUtils.isNotBlank(type)) {
            switch (type.trim().toLowerCase()) {
                case "chrome":
                    return CHROME;
                case "firefox":
                    return FIREFOX;
                case "ie":
                    return IE;
                default:
                    return null;
            }
        }
        return null;
    }
}

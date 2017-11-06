package com.wzj.learn.autotest.utils;

import com.wzj.learn.autotest.constants.DriverType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties;

    static {
        properties = new Properties();
        InputStream in = Object.class.getResourceAsStream("/application.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDriverPath(DriverType driverType) {
        String path;
        switch (driverType) {
            case CHROME:
                path = properties.getProperty("driver.path.chrome").trim();
                break;
            case FIREFOX:
                path = properties.getProperty("driver.path.firefox").trim();
                break;
            case IE:
                path = properties.getProperty("driver.path.ie").trim();
                break;
            default:
                path = "";
        }
        return path;
    }

    public static DriverType getDriverType() {
        String type = properties.getProperty("driver.type");
        return DriverType.parse(type);
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}

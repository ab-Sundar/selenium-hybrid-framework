package com.example.framework.config;

import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties PROPERTIES = new Properties();
    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) throw new IllegalStateException("config.properties not found");
            PROPERTIES.load(in);
        } catch (Exception e) { throw new ExceptionInInitializerError(e); }
    }
    private Config() {}
    public static String get(String key) { return System.getProperty(key, PROPERTIES.getProperty(key, "")); }
    public static int waitSeconds() { return Integer.parseInt(get("explicit.wait.seconds")); }
}

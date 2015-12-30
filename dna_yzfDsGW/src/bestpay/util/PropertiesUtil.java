package bestpay.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties值缓存类
 */
public class PropertiesUtil {
    public static Properties properties = null;
    public static String cert = null;
    public static String url = null;

    public static String getCert() {
        if (cert == null) {
            cert = properties.getProperty("fas.cert");
        }
        return cert;
    }

    public static String getUrl() {
        if (url == null) {
            url = properties.getProperty("fas.url");
        }
        return url;
    }

    static {
        InputStream ips = null;
        try {
            ips = PropertiesUtil.class.getClassLoader().getResourceAsStream("config/config.properties");
            properties = new Properties();
            properties.load(ips);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
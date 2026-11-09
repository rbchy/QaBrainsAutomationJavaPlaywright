package utils;

import java.io.InputStream;
import java.util.Properties;

/** Reads config.properties from the classpath; -Dkey=value overrides any entry. */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found in src/test/resources");
            }
            PROPS.load(in);
        } catch (Exception e) {
            throw new IllegalStateException("❌ Config file not loaded", e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        return System.getProperty(key, PROPS.getProperty(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key).trim());
    }
}

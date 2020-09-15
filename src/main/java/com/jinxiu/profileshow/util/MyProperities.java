package com.jinxiu.profileshow.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyProperities {
    private static final Logger logger = LoggerFactory.getLogger(MyProperities.class);

    private static final Properties properties = new Properties();
    private static final String CONFIG_PROPERTIES = "production".equals(System.getProperty("spring.profiles.active"))
            ? "production-conf.properties" : "development-conf.properties";

    private static final Map<String, String> propertiesMaps = loadFromClassPath(MyProperities.CONFIG_PROPERTIES);

    public static String findValueByKey(String key) {
        return propertiesMaps.get(key);
    }

    /**
     * Store a properties file in the root folder.
     *
     * @param fileName      the file name
     * @param propertiesMap a map containing the property name as key and it's value
     * @throws IllegalArgumentException when the file name is <code>null</code> or empty.
     */
    public synchronized static void store(final String fileName, final Map<String, String> propertiesMap) throws IllegalArgumentException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("The file name is null or empty");
        }
        try (final OutputStream outputStream = new FileOutputStream(fileName)) {
            if (propertiesMap != null) {
                for (Map.Entry<String, String> property : propertiesMap.entrySet()) {
                    properties.setProperty(property.getKey(), property.getValue());
                }
            }
            try {
                properties.store(outputStream, null);
            } catch (final IOException e) {
                logger.error("Properties could not be written to file {}", fileName, e);
            }

        } catch (final FileNotFoundException e) {
            logger.error("Properties file {} could not be found", fileName, e);
        } catch (final IOException e) {
            logger.error("OutputStream could not be closed", e);
        }
    }

    /**
     * Loads Properties into a map containing the property name as key and it's value.
     *
     * @return an empty map when no properties are found.
     */
    private synchronized static Map<String, String> load() {
        final Enumeration<?> propertyNames = properties.propertyNames();
        Map<String, String> propertiesMap = new HashMap<String, String>();
        while (propertyNames.hasMoreElements()) {
            final String propertyName = (String) propertyNames.nextElement(),
                    propertyValue = properties.getProperty(propertyName);
            propertiesMap.put(propertyName, propertyValue);
        }
        return propertiesMap;
    }

    /**
     * Load a properties file data from the root folder.
     *
     * @param fileName the file name
     * @return a map containing the property name as key and it's value
     * @throws IllegalArgumentException when the file name is <code>null</code> or empty.
     */
    public synchronized static Map<String, String> load(final String fileName) throws IllegalArgumentException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("The file name is null or empty");
        }
        try (final InputStream inputStream = new FileInputStream(fileName)) {
            properties.load(inputStream);
        } catch (final FileNotFoundException e) {
            logger.error("Properties file {} could not be found", fileName, e);
        } catch (final IOException e) {
            logger.error("InputStream could not be closed", e);
        }
        return load();
    }

    /**
     * Load a properties file data from the classpath.
     *
     * @param fileName the file name
     * @return a map containing the property name as key and it's value
     * @throws IllegalArgumentException when the file name is <code>null</code> or empty.
     */
    public synchronized static Map<String, String> loadFromClassPath(final String fileName) throws IllegalArgumentException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("The file name is null or empty");
        }
        try (final InputStream inputStream = MyProperities.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(inputStream);
        } catch (final FileNotFoundException e) {
            logger.error("Properties file {} could not be found", fileName, e);
        } catch (final IOException e) {
            logger.error("InputStream could not be closed", e);
        }
        return load();
    }

    public static Map<String, String> getPropertiesMaps() {
        return propertiesMaps;
    }
}

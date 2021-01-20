package com.fms.messages;

import com.fms.exception.ApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utlitiy file to load messages from properties file
 *
 * @author  Fatih Soylemez
 * @version 1.0
 * @since   2018-04-18
 */
public class Messages {

    private static final String FILE_NAME = "messages.properties";

    private static Properties properties;

    private Messages(){}

    public static String getMessage(String bundleKey){
        if(properties==null)
            loadMessagesFile();
        return properties.getProperty(bundleKey);
    }

    private static void loadMessagesFile() {
        properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(FILE_NAME);
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new ApiException("Could not find messages file.");
        }
    }
}

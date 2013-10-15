package com.webshrub.cpagenie.server.common.email;

import com.webshrub.cpagenie.core.common.email.EmailSender;
import com.webshrub.cpagenie.server.common.properties.ServerProperties;

import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 23, 2010
 * Time: 3:49:07 PM
 */
public class ServerEmailSender extends EmailSender {
    private static final ServerEmailSender instance = new ServerEmailSender();
    private static final String EMAIL_SENDER_KEY = "emailSender";

    private static Properties getEmailProperties() {
        Properties emailProperties = new Properties();
        Properties allProperties = ServerProperties.getInstance().getAllProperties();
        for (Map.Entry entry : allProperties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.startsWith(EMAIL_SENDER_KEY)) {
                key = key.substring(12);//Strip emailSender from the keys
                emailProperties.setProperty(key, value);
            }
        }
        return emailProperties;
    }

    private ServerEmailSender() {
        super(getEmailProperties());
    }

    public static ServerEmailSender getInstance() {
        return instance;
    }
}

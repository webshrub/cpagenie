package com.webshrub.cpagenie.server.common.logger.appender;

import com.webshrub.cpagenie.core.common.logger.CoreSMTPAppender;
import com.webshrub.cpagenie.server.common.properties.ServerProperties;

import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Nov 8, 2010
 * Time: 8:28:36 PM
 */
public class ServerSMTPAppender extends CoreSMTPAppender {
    private static final String EMAIL_SENDER_KEY = "emailSender";

    public ServerSMTPAppender() {
        super();
    }

    @Override
    public Properties getEmailProperties() {
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
}

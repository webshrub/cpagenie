package com.webshrub.cpagenie.app.common.logger.appender;

import com.webshrub.cpagenie.app.common.properties.AppProperties;
import com.webshrub.cpagenie.core.common.logger.CoreSMTPAppender;

import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Nov 8, 2010
 * Time: 8:28:36 PM
 */
public class AppSMTPAppender extends CoreSMTPAppender {
    private static final String EMAIL_SENDER_KEY = "emailSender";

    public AppSMTPAppender() {
        super();
    }

    @Override
    public Properties getEmailProperties() {
        Properties emailProperties = new Properties();
        Properties allProperties = AppProperties.getInstance().getAllProperties();
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

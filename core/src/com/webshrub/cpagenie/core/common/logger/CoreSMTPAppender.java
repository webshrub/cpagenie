package com.webshrub.cpagenie.core.common.logger;

import com.webshrub.cpagenie.core.common.properties.CoreProperties;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.net.SMTPAppender;

import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Nov 8, 2010
 * Time: 7:35:54 PM
 */
public class CoreSMTPAppender extends SMTPAppender {
    private static final String EMAIL_SENDER_KEY = "emailSender";
    private static final String MAIL_EVALUATOR_CLASS_KEY = "mailEvaluatorClass";
    private static final String MAIL_LAYOUT_PATTERN_KEY = "mailLayoutPattern";
    private static final String MAIL_HOST_KEY = "mailHost";
    private static final String MAIL_USER_KEY = "mailUser";
    private static final String MAIL_PASSWORD_KEY = "mailPassword";
    private static final String MAIL_FROM_KEY = "mailFrom";
    private static final String MAIL_TO_KEY = "mailTo";
    private static final String MAIL_CC_KEY = "mailCc";
    private static final String MAIL_BCC_KEY = "mailBcc";
    private static final String MAIL_SUBJECT_KEY = "mailSubject";
    private static final String MAIL_BUFFER_SIZE_KEY = "mailBufferSize";
    private static final String MAIL_LOCATION_INFO_KEY = "mailLocationInfo";
    private static final String MAIL_SMTPDEBUG_KEY = "mailSMTPDebug";

    private Properties emailProperties;

    public CoreSMTPAppender() {
        super();
        emailProperties = getEmailProperties();
        setSystemProperties();
        setAppenderProperties();
    }

    public void setSystemProperties() {
        for (Map.Entry entry : emailProperties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            System.setProperty(key, value);
        }
    }

    public void setAppenderProperties() {
        setEvaluatorClass(emailProperties.getProperty(MAIL_EVALUATOR_CLASS_KEY));
        setLayout(new PatternLayout(emailProperties.getProperty(MAIL_LAYOUT_PATTERN_KEY)));
        setSMTPHost(emailProperties.getProperty(MAIL_HOST_KEY));
        setSMTPUsername(emailProperties.getProperty(MAIL_USER_KEY));
        setSMTPPassword(emailProperties.getProperty(MAIL_PASSWORD_KEY));
        setFrom(emailProperties.getProperty(MAIL_FROM_KEY));
        setTo(emailProperties.getProperty(MAIL_TO_KEY));
        setCc(emailProperties.getProperty(MAIL_CC_KEY));
        setBcc(emailProperties.getProperty(MAIL_BCC_KEY));
        setSubject(emailProperties.getProperty(MAIL_SUBJECT_KEY));
        setBufferSize(Integer.parseInt(emailProperties.getProperty(MAIL_BUFFER_SIZE_KEY)));
        setLocationInfo(Boolean.parseBoolean(emailProperties.getProperty(MAIL_LOCATION_INFO_KEY)));
        setSMTPDebug(Boolean.parseBoolean(emailProperties.getProperty(MAIL_SMTPDEBUG_KEY)));
    }

    public Properties getEmailProperties() {
        Properties emailProperties = new Properties();
        Properties allProperties = CoreProperties.getInstance().getAllProperties();
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

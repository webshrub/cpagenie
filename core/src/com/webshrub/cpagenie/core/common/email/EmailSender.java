package com.webshrub.cpagenie.core.common.email;

import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 22, 2010
 * Time: 10:07:58 PM
 */
public class EmailSender {
    private static final Logger LOGGER = Logger.getLogger(EmailSender.class);
    private static final String RELATED = "related";
    private static final String ALTERNATIVE = "alternative";
    private static final String TEXT_HTML = "text/html";
    private static final String MAIL_HOST_KEY = "mailHost";
    private static final String MAIL_PORT_KEY = "mailPort";
    private static final String MAIL_USER_KEY = "mailUser";
    private static final String MAIL_PASSWORD_KEY = "mailPassword";

    private Properties properties;

    public EmailSender(Properties properties) {
        this.properties = properties;
    }

    private Message buildMessage(String from, String to, String bcc, String cc, String subject, Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setSentDate(new Date());
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        if (cc != null) {
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
        }
        if (bcc != null) {
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false));
        }
        message.setSubject(subject);
        return message;
    }

    private void addAlternativeBodyPart(String body, String htmlBody, MimeMultipart mimeMultipart) throws MessagingException {
        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(body);
        mimeMultipart.addBodyPart(bodyPart);
        BodyPart htmlBodyPart = new MimeBodyPart();
        htmlBodyPart.setContent(htmlBody, TEXT_HTML);
        mimeMultipart.addBodyPart(htmlBodyPart);
    }

    private void addAttachmentPart(List<InputStreamAttachment> inputStreamAttachments, List<FileAttachment> fileAttachments, MimeMultipart mimeMultipart) throws IOException, MessagingException {
        if (inputStreamAttachments != null) {
            for (InputStreamAttachment inputStreamAttachment : inputStreamAttachments) {
                String attachmentName = inputStreamAttachment.getAttachmentName();
                String contentType = inputStreamAttachment.getContentType();
                InputStream inputStream = inputStreamAttachment.getInputStream();
                InputStreamDataSource dataSource = new InputStreamDataSource(attachmentName, contentType, inputStream);
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setDataHandler(new DataHandler(dataSource));
                bodyPart.setFileName(attachmentName);
                mimeMultipart.addBodyPart(bodyPart);
            }
        }
        if (fileAttachments != null) {
            for (FileAttachment fileAttachment : fileAttachments) {
                DataSource dataSource = new FileDataSource(fileAttachment.getAttachmentPath());
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setDataHandler(new DataHandler(dataSource));
                bodyPart.setFileName(fileAttachment.getAttachmentName());
                mimeMultipart.addBodyPart(bodyPart);
            }
        }
    }

    private void sendMessage(Session session, Message message) throws MessagingException {
        String mailHost = properties.getProperty(MAIL_HOST_KEY);
        Integer mailPort = Integer.parseInt(properties.getProperty(MAIL_PORT_KEY));
        String mailUser = properties.getProperty(MAIL_USER_KEY);
        String mailPassword = properties.getProperty(MAIL_PASSWORD_KEY);
        Transport transport = session.getTransport();
        transport.connect(mailHost, mailPort, mailUser, mailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /*
    Following should be the structure of the attachment in a mail for supporting alternative multi parts
    MimeMultipart: "multipart/relative"
    -----MimeMultipart BodyPart: "multipart/alternative"
    ----------Text BodyPart: "text/plain"
    ----------HTML BodyPart: "text/html"
    -----Attachment BodyPart: "application/octet-stream" (or "application/pdf" etc)
    */

    public void sendMail(String from, String to, String cc, String bcc, String subject, String body, String htmlBody, List<InputStreamAttachment> inputStreamAttachments, List<FileAttachment> fileAttachments) throws Exception {
        try {
            LOGGER.debug("Sending email to " + to + " from " + from + " with subject = " + subject);
            //Get a session from configuration properties supplied.
            Session session = Session.getInstance(properties, null);
            //Build the message instance.
            Message message = buildMessage(from, to, bcc, cc, subject, session);
            //This is the root MimeMultipart to hold both alternative MimeMultipart as well as Attachment MimeBodyPart.
            MimeMultipart rootMultipart = new MimeMultipart(RELATED);
            //This is the BodyPart to contain Alternative MimeMultipart that itself contains text BodyPart and html BodyPart.
            MimeBodyPart alternativeMultipartRoot = new MimeBodyPart();
            //This is the alternative MimeMultiPart
            MimeMultipart alternativeMultipart = new MimeMultipart(ALTERNATIVE);
            //add alternative message
            addAlternativeBodyPart(body, htmlBody, alternativeMultipart);
            //Build the hierarchy
            alternativeMultipartRoot.setContent(alternativeMultipart);
            rootMultipart.addBodyPart(alternativeMultipartRoot);
            //Add the attachments to rootMultipart
            addAttachmentPart(inputStreamAttachments, fileAttachments, rootMultipart);
            //Set the content of message from rootMultipart
            message.setContent(rootMultipart);
            //Send the message now.
            sendMessage(session, message);
            LOGGER.debug("EMail sent to " + to + " from " + from + " with subject = " + subject);
        } catch (Exception e) {
            LOGGER.warn("EMail could not be sent to " + to + " from " + from + " with subject = " + subject + " due to an error " + e);
            throw e;
        }
    }
}


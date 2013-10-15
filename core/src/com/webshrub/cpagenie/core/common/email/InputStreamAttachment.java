package com.webshrub.cpagenie.core.common.email;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 23, 2010
 * Time: 2:45:42 AM
 */
public class InputStreamAttachment {
    private String attachmentName;
    private String contentType;
    private InputStream inputStream;

    public InputStreamAttachment(String attachmentName, String contentType, InputStream inputStream) {
        this.attachmentName = attachmentName;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}

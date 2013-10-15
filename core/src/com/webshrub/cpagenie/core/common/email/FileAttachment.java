package com.webshrub.cpagenie.core.common.email;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 23, 2010
 * Time: 2:45:21 AM
 */
class FileAttachment {
    private String attachmentName;
    private String attachmentPath;

    public FileAttachment(String attachmentName, String attachmentPath) {
        this.attachmentName = attachmentName;
        this.attachmentPath = attachmentPath;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }
}

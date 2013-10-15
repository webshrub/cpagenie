package com.webshrub.cpagenie.core.common.email;

import javax.activation.DataSource;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 23, 2010
 * Time: 2:44:51 AM
 */
public class InputStreamDataSource implements DataSource {
    private String name;
    private String contentType;
    private ByteArrayOutputStream byteArrayOutputStream;

    public InputStreamDataSource(String name, String contentType, InputStream inputStream) throws IOException {
        this.name = name;
        this.contentType = contentType;
        byteArrayOutputStream = new ByteArrayOutputStream();
        int read;
        byte[] buff = new byte[256];
        while ((read = inputStream.read(buff)) != -1) {
            byteArrayOutputStream.write(buff, 0, read);
        }
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public String getName() {
        return name;
    }

    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Cannot write to this read-only resource");
    }
}

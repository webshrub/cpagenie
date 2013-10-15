/**
 *
 */
package com.webshrub.cpagenie.server.common.properties;

import com.webshrub.cpagenie.core.common.exception.DataException;
import com.webshrub.cpagenie.core.common.properties.Properties;

import java.io.IOException;

/**
 * Copyright (c) 2008 Core, Inc. All rights reserved.
 *
 * @author: Ahsan.Javed
 */
public class ServerProperties extends Properties {
    private static final String SERVER_PROPERTIES_FILE_NAME = "/server.properties";
    private static ServerProperties instance;

    private ServerProperties() throws IOException, DataException {
        super(SERVER_PROPERTIES_FILE_NAME);
    }

    public static synchronized ServerProperties getInstance() {
        if (instance == null) {
            try {
                instance = new ServerProperties();
            } catch (DataException e) {
                throw new RuntimeException("Data exception in server.properties prevents application from continuing...", e);
            } catch (IOException e) {
                throw new RuntimeException("IO exception in server.properties prevents application from continuing...", e);
            }
        }
        return instance;
    }

    public static String rootdir() {
        try {
            String name = System.getProperty("rootdir") == null ? getInstance().getPropertyValue("rootdir") : System.getProperty("rootdir");
            if (!name.endsWith("/")) {
                name = name + "/";
            }
            return name;
        } catch (Exception e) {
            throw new RuntimeException("Data exception in Core.properties prevents application from continuing...", e);
        }
    }
}
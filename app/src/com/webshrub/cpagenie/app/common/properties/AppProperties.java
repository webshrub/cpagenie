/**
 *
 */
package com.webshrub.cpagenie.app.common.properties;

import com.webshrub.cpagenie.core.common.exception.DataException;
import com.webshrub.cpagenie.core.common.properties.Properties;

import java.io.IOException;

/**
 * Copyright (c) 2008 Core, Inc. All rights reserved.
 *
 * @author: Ahsan.Javed
 */
public class AppProperties extends Properties {
    public static final String APP_PROPERTIES_FILE_NAME = "/app.properties";
    private static AppProperties instance = null;

    private AppProperties() throws IOException, DataException {
        super(APP_PROPERTIES_FILE_NAME);
    }

    public static AppProperties getInstance() {
        if (instance == null) {
            try {
                instance = new AppProperties();
            } catch (DataException e) {
                throw new RuntimeException("Data exception in app.properties prevents application from continuing...", e);
            } catch (IOException e) {
                throw new RuntimeException("IO exception in app.properties prevents application from continuing...", e);
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
            throw new RuntimeException("Data exception in app.properties prevents application from continuing...", e);
        }
    }
}
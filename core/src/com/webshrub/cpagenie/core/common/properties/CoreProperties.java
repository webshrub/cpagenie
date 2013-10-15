/**
 *
 */
package com.webshrub.cpagenie.core.common.properties;

import com.webshrub.cpagenie.core.common.exception.DataException;

import java.io.IOException;

/**
 * Copyright (c) 2008 Core, Inc. All rights reserved.
 *
 * @author: Ahsan.Javed
 */
public class CoreProperties extends Properties {
    private static final String CORE_PROPERTIES_FILE_NAME = "/core.properties";
    private static CoreProperties instance;

    private CoreProperties() throws IOException, DataException {
        super(CORE_PROPERTIES_FILE_NAME);
    }

    public static synchronized CoreProperties getInstance() {
        if (instance == null) {
            try {
                instance = new CoreProperties();
            } catch (DataException e) {
                throw new RuntimeException("Data exception in Core.properties prevents application from continuing...", e);
            } catch (IOException e) {
                throw new RuntimeException("IO exception in Core.properties prevents application from continuing...", e);
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
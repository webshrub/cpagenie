/**
 *
 */
package com.webshrub.cpagenie.core.common.properties;

import com.webshrub.cpagenie.core.common.exception.DataException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright (c) 2008 Core, Inc. All rights reserved.
 *
 * @author: Ahsan.Javed
 */
public abstract class Properties {
    private String fileName;
    private java.util.Properties properties;

    protected Properties(String propertyFileName) throws DataException, IOException {
        loadProperties(propertyFileName);
    }

    protected void loadProperties(String propertyFileName) throws DataException, IOException {
        fileName = propertyFileName;
        java.util.Properties properties = new java.util.Properties();
        InputStream stream = this.getClass().getResourceAsStream(propertyFileName);
        if (stream == null) {
            throw new DataException("Cannot find resource " + propertyFileName);
        }
        properties.load(stream);
        this.properties = properties;
    }

    public java.util.Properties getAllProperties() {
        if (properties == null || fileName == null) {
            throw new RuntimeException("Properties are not initialized. Please call the constructor first.");
        }
        return properties;
    }

    public String getPropertyValue(String name) throws DataException {
        String value = properties.getProperty(name);
        if (value == null) {
            throw new DataException("Property " + name + " is not found or null in " + fileName);
        }
        return value;
    }
}
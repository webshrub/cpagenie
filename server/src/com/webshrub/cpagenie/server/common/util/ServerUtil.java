package com.webshrub.cpagenie.server.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 23, 2010
 * Time: 2:04:34 PM
 */
public class ServerUtil {
    private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String getFormattedDateStr(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }
}

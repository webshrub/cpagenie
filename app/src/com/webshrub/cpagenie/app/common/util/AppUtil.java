package com.webshrub.cpagenie.app.common.util;

import org.apache.commons.lang.math.NumberUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 14, 2010
 * Time: 3:10:46 PM
 */
public class AppUtil {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-+]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean validEmail(final String emailString) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailString);
        return matcher.matches();
    }

    public static boolean validPhone(final String phoneString) {
        return NumberUtils.isDigits(phoneString);
    }

    public static Date getStartDate(Date startDate) {
        try {
            return DATE_FORMAT.parse(DATE_FORMAT.format(startDate));
        } catch (ParseException e) {
            throw new RuntimeException("Unable to parse start date" + startDate);
        }
    }

    public static Date getEndDate(Date endDate) {
        try {
            endDate = DATE_FORMAT.parse(DATE_FORMAT.format(endDate));
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.HOUR, 23);
            calendar.add(Calendar.MINUTE, 59);
            calendar.add(Calendar.SECOND, 59);
            return calendar.getTime();
        } catch (ParseException e) {
            throw new RuntimeException("Unable to parse end date" + endDate);
        }
    }

    public static boolean isNaNOrInfinity(double value) {
        return (Double.isNaN(value)) || Double.isInfinite(value) || Double.POSITIVE_INFINITY <= value || (Double.NEGATIVE_INFINITY >= value);
    }

    public static boolean isEmpty(String inputString) {
        return inputString == null || inputString.equals("");
    }
}

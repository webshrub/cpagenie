package com.webshrub.cpagenie.server.servlet;

import com.webshrub.cpagenie.server.job.loader.CPAGenieImpressionReportLoader;
import org.quartz.SchedulerException;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 15, 2010
 * Time: 11:41:28 PM
 */
public class CPAGenieImpressionReportLoaderServlet extends GenericServlet {
    private static final int REPEAT_DURATION_IN_MINUTES = 30;
    private static final String REPEAT_DURATION_IN_MINUTES_KEY = "repeatDurationInMinutes";

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Integer repeatDurationInMinutes = REPEAT_DURATION_IN_MINUTES;
            String value = config.getInitParameter(REPEAT_DURATION_IN_MINUTES_KEY);
            if (value != null) {
                repeatDurationInMinutes = Integer.parseInt(value);
            }
            new CPAGenieImpressionReportLoader(repeatDurationInMinutes);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}

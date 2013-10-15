package com.webshrub.cpagenie.app.mvc.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {
    private static final Logger LOGGER = Logger.getLogger(DashboardController.class);
    private static final String DASHBOARD_URL_KEY = "/secure/dashboard.htm";
    private static final String DASHBOARD_VIEW_KEY = "secure/dashboard";

    @RequestMapping(value = DASHBOARD_URL_KEY, method = RequestMethod.GET)
    public ModelAndView handleRequest() {
        LOGGER.info("Inside DashboardController.handleRequest");
        return new ModelAndView(DASHBOARD_VIEW_KEY);
    }
}
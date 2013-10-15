package com.webshrub.cpagenie.server.servlet;

import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponseType;
import com.webshrub.cpagenie.server.request.CPAGenieRequestBuilder;
import com.webshrub.cpagenie.server.request.CPAGenieRequestProcessor;
import com.webshrub.cpagenie.server.request.lead.LeadRequestBuilder;
import com.webshrub.cpagenie.server.request.lead.LeadRequestProcessor;
import com.webshrub.cpagenie.server.request.tracking.TrackingRequestBuilder;
import com.webshrub.cpagenie.server.request.tracking.TrackingRequestProcessor;
import com.webshrub.cpagenie.server.response.CPAGenieResponse;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 11, 2010
 * Time: 7:03:38 PM
 */
public class CPAGenieServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CPAGenieServlet.class);
    private static CPAGenieRequestProcessor trackingProcessor;
    private static CPAGenieRequestProcessor leadProcessor;
    private static CPAGenieRequestBuilder trackingRequestBuilder;
    private static CPAGenieRequestBuilder leadRequestBuilder;

    @Override
    public void init() throws ServletException {
        super.init();
        trackingProcessor = TrackingRequestProcessor.getInstance();
        leadProcessor = LeadRequestProcessor.getInstance();
        trackingRequestBuilder = TrackingRequestBuilder.getInstance();
        leadRequestBuilder = LeadRequestBuilder.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String servletPath = req.getServletPath();
            CPAGenieResponse response = null;
            if (servletPath.equalsIgnoreCase(CPAGenieServletConstants.TRACKING_PATH_INFO_STR)) {
                response = trackingProcessor.processRequest(trackingRequestBuilder.buildRequest(req));
            } else if (servletPath.equalsIgnoreCase(CPAGenieServletConstants.SUBMIT_PATH_INFO_STR)) {
                response = leadProcessor.processRequest(leadRequestBuilder.buildRequest(req));
            }
            handleResponse(response, req, resp);
        } catch (Exception e) {
            LOGGER.warn("An exception occurred while handling the response " + e);
            e.printStackTrace();
        }
    }

    private void handleResponse(CPAGenieResponse response, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        CPAGenieCampaignResponseType responseType = response.getResponseType();
        switch (responseType) {
            case NORMAL:
                httpServletResponse.getOutputStream().write(response.getResponse().getBytes());
                break;
            case REDIRECT:
                httpServletResponse.sendRedirect(response.getResponse());
                break;
            case FORWARD:
                RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher(response.getResponse());
                dispatcher.forward(httpServletRequest, httpServletResponse);
                break;
            default:
                LOGGER.warn("No such responseHandler found for responseType " + responseType);
        }
    }
}

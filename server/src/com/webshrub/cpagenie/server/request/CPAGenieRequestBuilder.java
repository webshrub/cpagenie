package com.webshrub.cpagenie.server.request;

import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.server.db.util.ServerDataUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 16, 2010
 * Time: 1:19:49 AM
 */
public abstract class CPAGenieRequestBuilder {

    protected DbDataUtil dataUtil;

    public CPAGenieRequestBuilder() {
        dataUtil = ServerDataUtil.getInstance();
    }

    public abstract CPAGenieRequest buildRequest(HttpServletRequest servletRequest);
}

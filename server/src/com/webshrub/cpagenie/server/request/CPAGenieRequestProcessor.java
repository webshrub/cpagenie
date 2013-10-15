package com.webshrub.cpagenie.server.request;

import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.server.db.util.ServerDataUtil;
import com.webshrub.cpagenie.server.response.CPAGenieResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 13, 2010
 * Time: 6:25:05 PM
 */
public abstract class CPAGenieRequestProcessor {
    protected DbDataUtil dataUtil;

    protected CPAGenieRequestProcessor() {
        super();
        dataUtil = ServerDataUtil.getInstance();
    }

    public abstract CPAGenieResponse processRequest(CPAGenieRequest request);
}

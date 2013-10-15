package com.webshrub.cpagenie.app.mvc.service;

import com.webshrub.cpagenie.app.mvc.dto.Campaign;
import com.webshrub.cpagenie.app.mvc.dto.ImpressionReportRow;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * CPAGenieUser: Ahsan.Javed
 * Date: Jul 19, 2010
 * Time: 2:53:04 PM
 */
public interface ReportService {

    public List<ImpressionReportRow> getImpressionReportRowList(Campaign campaign, Date startDate, Date endDate);
}
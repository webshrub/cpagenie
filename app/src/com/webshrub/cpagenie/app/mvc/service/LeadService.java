package com.webshrub.cpagenie.app.mvc.service;

import com.webshrub.cpagenie.app.mvc.dto.Campaign;
import com.webshrub.cpagenie.app.mvc.dto.Lead;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * CPAGenieUser: Ahsan.Javed
 * Date: Jul 19, 2010
 * Time: 2:53:04 PM
 */
public interface LeadService {

    public Lead getLead(Integer id);

    public void updateLead(Lead lead);

    public void deleteLead(Lead lead);

    public List<Lead> getLeadList(Campaign campaign, Date startDate, Date endDate);
}

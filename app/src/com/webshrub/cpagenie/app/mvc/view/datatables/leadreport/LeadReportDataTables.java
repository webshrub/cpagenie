package com.webshrub.cpagenie.app.mvc.view.datatables.leadreport;

import com.webshrub.cpagenie.app.common.util.AppUtil;
import com.webshrub.cpagenie.app.hibernate.criterion.order.OrderByClause;
import com.webshrub.cpagenie.app.mvc.dto.Lead;
import com.webshrub.cpagenie.app.mvc.dto.LeadReportFilter;
import com.webshrub.cpagenie.app.mvc.view.datatables.DataTables;
import com.webshrub.cpagenie.app.mvc.view.datatables.DataTablesSortInfo;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;
import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 5, 2010
 * Time: 2:12:35 AM
 */
@Component
public class LeadReportDataTables extends DataTables {
    private static final String ID_KEY = "id";
    private static final String CAPTURE_TIME_KEY = "captureTime";
    private static final String FIRST_NAME_KEY = "firstName";
    private static final String LAST_NAME_KEY = "lastName";
    private static final String EMAIL_KEY = "email";
    private static final String ADDRESS1_KEY = "address1";
    private static final String ADDRESS2_KEY = "address2";
    private static final String CITY_KEY = "city";
    private static final String STATE_KEY = "state";
    private static final String HOME_PHONE_KEY = "homePhone";
    private static final String WORK_PHONE_KEY = "workPhone";
    private static final String MOBILE_PHONE_KEY = "mobilePhone";
    private static final String STATUS_KEY = "status";
    private static final String CAMPAIGN_KEY = "campaign";

    private LeadReportFilter leadReportFilter;

    @Autowired
    public LeadReportDataTables(DbDataUtil dataUtil) {
        super(dataUtil);
    }

    public void setLeadReportFilter(LeadReportFilter leadReportFilter) {
        this.leadReportFilter = leadReportFilter;
    }

    /*
    List of all searchable columns. Includes only value type. Currently excludes all association types.
    However user can write logic to include associations as well. Also excludes id field.
    */

    private List<String> getSearchFields() {
        //Search/Filtering is not supported
        return new ArrayList<String>();
    }

    private String getIdField() {
        return ID_KEY;
    }

    /*
    Contains the sorting information. Don't put columns here for which you don't want sorting.
    Must contain valid value types only for the sorted domain object as key value pair.
    Must be consistent with the columns shown on UI. All fields should be in the same order as on UI dataTables.
    */

    private Map<String, OrderByClause> getSortColumnMap() {
        Map<String, OrderByClause> sortColumnMap = new HashMap<String, OrderByClause>();
        sortColumnMap.put("0", OrderByClause.property(ID_KEY));
        sortColumnMap.put("1", OrderByClause.property(CAPTURE_TIME_KEY));
        sortColumnMap.put("2", OrderByClause.property(FIRST_NAME_KEY));
        sortColumnMap.put("3", OrderByClause.property(LAST_NAME_KEY));
        sortColumnMap.put("4", OrderByClause.property((EMAIL_KEY)));
        sortColumnMap.put("5", OrderByClause.property((ADDRESS1_KEY)));
        sortColumnMap.put("6", OrderByClause.property((ADDRESS2_KEY)));
        sortColumnMap.put("7", OrderByClause.property((CITY_KEY)));
        sortColumnMap.put("8", OrderByClause.property((STATE_KEY)));
        sortColumnMap.put("9", OrderByClause.property((HOME_PHONE_KEY)));
        sortColumnMap.put("10", OrderByClause.property((WORK_PHONE_KEY)));
        sortColumnMap.put("11", OrderByClause.property((MOBILE_PHONE_KEY)));
        sortColumnMap.put("12", OrderByClause.property((STATUS_KEY)));
        return sortColumnMap;
    }

    private List<Criterion> getCustomFilters() {
        List<Criterion> filters = new ArrayList<Criterion>();
        CPAGenieCampaign campaign = getDbCampaign(leadReportFilter.getCampaign().getId());
        Date startDate = AppUtil.getStartDate(leadReportFilter.getStartDate());
        Date endDate = AppUtil.getEndDate(leadReportFilter.getEndDate());
        filters.add(Restrictions.eq(CAMPAIGN_KEY, campaign));
        filters.add(Restrictions.between(CAPTURE_TIME_KEY, startDate, endDate));
        return filters;
    }

    public CPAGenieCampaign getDbCampaign(Integer id) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieCampaign dbCampaign = (CPAGenieCampaign) sessionHolder.getSession().get(CPAGenieCampaign.class, id);
            sessionHolder.commitTransaction();
            return dbCampaign;
        } catch (HibernateException e) {
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getDbCampaign()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    @Override
    public Class getInputType() {
        return CPAGenieLead.class;
    }

    public void applyInitialFilters(Criteria criteria) {
        for (Criterion filter : getCustomFilters()) {
            criteria.add(filter);
        }
    }

    public boolean applySearchFilters(Criteria criteria, String searchTerm) {
        boolean filterAdded = false;
        //If search string is integer type, it only searches on Id field
        if (NumberUtils.isDigits(searchTerm)) {
            criteria.add(Restrictions.eq(getIdField(), Integer.parseInt(searchTerm)));
            filterAdded = true;
        } else if (getSearchFields() != null && getSearchFields().size() != 0) {
            //Text filtering is applied only if you have specified getSearchFields().
            String likeTerm = "%" + searchTerm + "%";
            Junction disjunction = Restrictions.disjunction();
            for (String field : getSearchFields()) {
                //Performs case insensitive search on all search fields.
                //Also does not searches on associations. Only value types are searched.
                disjunction.add(Restrictions.ilike(field, likeTerm));
            }
            criteria.add(disjunction);
            filterAdded = true;
        }
        return filterAdded;
    }

    public void applySorting(Criteria criteria, Map<Integer, DataTablesSortInfo> dataTablesSortInfoMap) {
        for (int i = 0; i < dataTablesSortInfoMap.size(); i++) {
            String iSortCol_i = dataTablesSortInfoMap.get(i).iSortCol_i;
            String sSortDir_i = dataTablesSortInfoMap.get(i).sSortDir_i;
            //Fetch the actual sort column. This column must be consistent with the index of UI dataTables.
            //Hence you can not put additional columns in this map. Put only the columns which are shown on UI.
            OrderByClause orderByClause = getSortColumnMap().get(iSortCol_i);
            //sortField must be a valid value type in the sorted domain object.
            if (orderByClause != null) {
                if (sSortDir_i.equalsIgnoreCase("asc")) {
                    criteria.addOrder(orderByClause.asc());
                } else {
                    criteria.addOrder(orderByClause.desc());
                }
            }
        }
    }

    @Override
    public Object transform(Object inputRecord) {
        return new Lead().fill((CPAGenieLead) inputRecord);
    }
}
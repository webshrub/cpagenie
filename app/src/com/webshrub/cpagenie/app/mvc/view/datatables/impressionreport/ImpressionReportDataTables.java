package com.webshrub.cpagenie.app.mvc.view.datatables.impressionreport;

import com.webshrub.cpagenie.app.common.util.AppUtil;
import com.webshrub.cpagenie.app.hibernate.criterion.order.OrderByClause;
import com.webshrub.cpagenie.app.mvc.dto.ImpressionReportFilter;
import com.webshrub.cpagenie.app.mvc.dto.ImpressionReportRow;
import com.webshrub.cpagenie.app.mvc.view.datatables.DataTables;
import com.webshrub.cpagenie.app.mvc.view.datatables.DataTablesSortInfo;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.report.CPAGenieImpressionReportRow;
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
public class ImpressionReportDataTables extends DataTables {
    private static final String ID_KEY = "id";
    private static final String RUN_TIME_KEY = "runTime";
    private static final String IMPRESSIONS_KEY = "impressions";
    private static final String SUBMIT_COUNT_KEY = "submitCount";
    private static final String LEAD_COUNT_KEY = "leadCount";
    private static final String COST_PER_LEAD_KEY = "costPerLead";
    private static final String REVENUE_KEY = "revenue";
    private static final String CAMPAIGN_KEY = "campaign";

    private ImpressionReportFilter impressionReportFilter;

    @Autowired
    public ImpressionReportDataTables(DbDataUtil dataUtil) {
        super(dataUtil);
    }

    public void setImpressionReportFilter(ImpressionReportFilter impressionReportFilter) {
        this.impressionReportFilter = impressionReportFilter;
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
        sortColumnMap.put("0", OrderByClause.property(RUN_TIME_KEY));
        sortColumnMap.put("1", OrderByClause.property(IMPRESSIONS_KEY));
        sortColumnMap.put("2", OrderByClause.property(SUBMIT_COUNT_KEY));
        sortColumnMap.put("3", OrderByClause.property((LEAD_COUNT_KEY)));
        sortColumnMap.put("4", OrderByClause.property((COST_PER_LEAD_KEY)));
        sortColumnMap.put("5", OrderByClause.property((REVENUE_KEY)));
        sortColumnMap.put("6", OrderByClause.formula("(LEAD_COUNT/IMPRESSIONS)"));
        sortColumnMap.put("7", OrderByClause.formula("(REVENUE/IMPRESSIONS)"));
        sortColumnMap.put("8", OrderByClause.formula("((SUBMIT_COUNT-LEAD_COUNT)/LEAD_COUNT)"));
        return sortColumnMap;
    }

    private List<Criterion> getCustomFilters() {
        List<Criterion> filters = new ArrayList<Criterion>();
        CPAGenieCampaign campaign = getDbCampaign(impressionReportFilter.getCampaign().getId());
        Date startDate = AppUtil.getStartDate(impressionReportFilter.getStartDate());
        Date endDate = AppUtil.getEndDate(impressionReportFilter.getEndDate());
        filters.add(Restrictions.eq(CAMPAIGN_KEY, campaign));
        filters.add(Restrictions.between(RUN_TIME_KEY, startDate, endDate));
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
        return CPAGenieImpressionReportRow.class;
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
        return new ImpressionReportRow().fill((CPAGenieImpressionReportRow) inputRecord);
    }
}    

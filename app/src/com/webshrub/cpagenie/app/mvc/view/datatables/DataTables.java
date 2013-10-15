package com.webshrub.cpagenie.app.mvc.view.datatables;

import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import org.hibernate.Criteria;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 5, 2010
 * Time: 4:06:03 AM
 */
public abstract class DataTables {
    protected DbDataUtil dataUtil;

    public DataTables(DbDataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @SuppressWarnings("unchecked")
    public DataTablesResponse getDataTablesResponse(HttpServletRequest request) {
        Integer echo = Integer.parseInt(request.getParameter("sEcho"));

        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        Criteria criteria = sessionHolder.getSession().createCriteria(getInputType());

        //Apply all custom filters first.
        applyInitialFilters(criteria);
        Integer totalRecords = criteria.list().size();

        /* Where Clause*/
        String searchTerm = request.getParameter("sSearch");
        Integer totalDisplayRecords = totalRecords;
        if (searchTerm != null && !searchTerm.equals("")) {
            if (applySearchFilters(criteria, searchTerm)) {
                totalDisplayRecords = criteria.list().size();
            }
        }

        /* Order By Clause*/
        String iSortingCols = request.getParameter("iSortingCols");
        if (iSortingCols != null && !iSortingCols.equals("")) {
            Map<Integer, DataTablesSortInfo> dataTablesSortInfoMap = new LinkedHashMap<Integer, DataTablesSortInfo>();
            for (int i = 0; i < Integer.parseInt(iSortingCols); i++) {
                String iSortCol_i = request.getParameter("iSortCol_" + i);
                String sSortDir_i = request.getParameter("sSortDir_" + i);
                dataTablesSortInfoMap.put(i, new DataTablesSortInfo(iSortCol_i, sSortDir_i));
            }
            applySorting(criteria, dataTablesSortInfoMap);
        }

        /* Paging Clause */
        String iDisplayStart = request.getParameter("iDisplayStart");
        criteria.setFirstResult(Integer.parseInt(iDisplayStart));
        String iDisplayLength = request.getParameter("iDisplayLength");
        criteria.setMaxResults(Integer.parseInt(iDisplayLength));

        List resultList = criteria.list();

        List records = new ArrayList();
        for (Object record : resultList) {
            records.add(transform(record));
        }

        sessionHolder.closeSession();

        return new DataTablesResponse(echo, totalRecords, totalDisplayRecords, records);
    }

    public abstract Class getInputType();

    public abstract void applyInitialFilters(Criteria criteria);

    public abstract boolean applySearchFilters(Criteria criteria, String searchTerm);

    public abstract void applySorting(Criteria criteria, Map<Integer, DataTablesSortInfo> dataTablesSortInfoMap);

    public abstract Object transform(Object inputRecord);
}

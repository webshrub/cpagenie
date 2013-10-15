package com.webshrub.cpagenie.app.mvc.view.datatables;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 5, 2010
 * Time: 4:06:27 AM
 */
public class DataTablesResponse<T> {
    private Integer echo;
    private Integer totalRecords;
    private Integer totalDisplayRecords;
    private List<T> records;

    public DataTablesResponse(Integer echo, Integer totalRecords, Integer totalDisplayRecords, List<T> records) {
        this.echo = echo;
        this.totalRecords = totalRecords;
        this.totalDisplayRecords = totalDisplayRecords;
        this.records = records;
    }

    public Integer getEcho() {
        return echo;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public Integer getTotalDisplayRecords() {
        return totalDisplayRecords;
    }

    public List<T> getRecords() {
        return records;
    }
}

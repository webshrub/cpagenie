package com.webshrub.cpagenie.app.hibernate.criterion.order;

import org.hibernate.criterion.Order;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 25, 2010
 * Time: 4:11:21 PM
 */
public class OrderByClause {
    private static final String ASC_KEY = "asc";
    private static final String DESC_KEY = "desc";

    private String orderByString;
    private boolean isProperty;

    private OrderByClause(String orderByString, boolean isProperty) {
        this.orderByString = orderByString;
        this.isProperty = isProperty;
    }

    public Order asc() {
        if (isProperty) {
            return Order.asc(orderByString);
        } else {
            return OrderBySqlFormula.sqlFormula(orderByString + " " + ASC_KEY);
        }
    }

    public Order desc() {
        if (isProperty) {
            return Order.desc(orderByString);
        } else {
            return OrderBySqlFormula.sqlFormula(orderByString + " " + DESC_KEY);
        }
    }

    public static OrderByClause property(String orderByString) {
        return new OrderByClause(orderByString, true);
    }

    public static OrderByClause formula(String orderByString) {
        return new OrderByClause(orderByString, false);
    }
}

package com.webshrub.cpagenie.app.mvc.service.impl;

import com.webshrub.cpagenie.app.mvc.dto.Vertical;
import com.webshrub.cpagenie.app.mvc.service.VerticalService;
import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import com.webshrub.cpagenie.core.db.vertical.CPAGenieVertical;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * CPAGenieUser: Ahsan.Javed
 * Date: Aug 7, 2010
 * Time: 1:41:54 PM
 */
@Service
public class VerticalServiceImpl implements VerticalService {
    private static final Logger LOGGER = Logger.getLogger(VerticalServiceImpl.class);
    private DbDataUtil dataUtil;

    @Autowired
    public VerticalServiceImpl(DbDataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @SuppressWarnings("unchecked")
    public List<Vertical> getVerticalList() {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            List<Vertical> verticals = new ArrayList<Vertical>();
            List<CPAGenieVertical> verticalList = sessionHolder.getSession().createCriteria(CPAGenieVertical.class).list();
            for (CPAGenieVertical dbVertical : verticalList) {
                verticals.add(new Vertical().fill(dbVertical));
            }
            sessionHolder.commitTransaction();
            return verticals;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getVerticalList()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getVerticalList()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public Vertical getVertical(Integer id) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieVertical dbVertical = (CPAGenieVertical) sessionHolder.getSession().get(CPAGenieVertical.class, id);
            sessionHolder.commitTransaction();
            return new Vertical().fill(dbVertical);
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getVertical()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getVertical()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void saveVertical(Vertical vertical) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieVertical dbVertical = new CPAGenieVertical();
            dbVertical.setName(vertical.getName());
            dbVertical.setDescription(vertical.getDescription());
            dbVertical.setCreationTime(new Date());
            sessionHolder.getSession().saveOrUpdate(dbVertical);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in saveVertical()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in saveVertical()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void updateVertical(Vertical vertical) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieVertical dbVertical = (CPAGenieVertical) sessionHolder.getSession().get(CPAGenieVertical.class, vertical.getId());
            dbVertical.setName(vertical.getName());
            dbVertical.setDescription(vertical.getDescription());
            dbVertical.setUpdateTime(new Date());
            dbVertical.setUpdateComments(vertical.getUpdateComments());
            sessionHolder.getSession().saveOrUpdate(dbVertical);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in updateVertical()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in updateVertical()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void deleteVertical(Vertical vertical) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieVertical dbVertical = (CPAGenieVertical) sessionHolder.getSession().get(CPAGenieVertical.class, vertical.getId());
            sessionHolder.getSession().delete(dbVertical);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in deleteVertical()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in deleteVertical()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }
}

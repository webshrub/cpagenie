package com.webshrub.cpagenie.core.db.util;

import org.apache.log4j.Logger;
import org.hibernate.*;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class DbDataUtil<E> {
    private static final Logger LOGGER = Logger.getLogger(DbDataUtil.class);
    protected SessionFactory sessionFactory;

    public DbDataUtil() {
        sessionFactory = initSessionFactory();
    }

    public abstract SessionFactory initSessionFactory();

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public Session openSession(Connection connection) {
        return sessionFactory.openSession(connection);
    }

    public SessionHolder getOrCreateSessionHolder() throws HibernateException {
        return new SessionHolder(sessionFactory.openSession());
    }

    @SuppressWarnings("unchecked")
    public List<E> getAllEntities(Class entityClass) {
        SessionHolder sessionHolder = getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            List<E> entities;
            Criteria criteria = sessionHolder.getSession().createCriteria(entityClass);
            entities = criteria.list();
            sessionHolder.commitTransaction();
            return entities;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getAllEntities()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getAllEntities()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public Object load(Class entityClass, Serializable id) throws Exception {
        Object obj;
        SessionHolder sessionHolder = getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            obj = sessionHolder.getSession().get(entityClass, id);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in load()", e);
            sessionHolder.rollbackTransaction();
            throw new Exception("Exception occurred in load()", e);
        } finally {
            sessionHolder.closeSession();
        }
        return obj;
    }

    public void save(Object object) throws Exception {
        SessionHolder sessionHolder = getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            sessionHolder.getSession().saveOrUpdate(object);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in save()", e);
            sessionHolder.rollbackTransaction();
            throw new Exception("Exception occurred in save()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void update(Object object) throws Exception {
        save(object);
    }

    public void delete(Object object) throws Exception {
        SessionHolder sessionHolder = getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            sessionHolder.getSession().delete(object);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in delete()", e);
            sessionHolder.rollbackTransaction();
            throw new Exception("Exception occurred in delete()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void setParametersInQuery(Query query, Map queryParameters) throws HibernateException {
        if (queryParameters != null) {
            Set queryParamKeys = queryParameters.keySet();
            for (Object queryParamKey : queryParamKeys) {
                String key = queryParamKey.toString();
                if (queryParameters.get(key) instanceof Collection) {
                    query.setParameterList(key, (Collection) queryParameters.get(key));
                } else {
                    query.setParameter(key, queryParameters.get(key));
                }
            }
        }
    }

    public List executeQuery(Query query, Map queryParameters) throws Exception {
        if (query != null) {
            try {
                getOrCreateSessionHolder();
                setParametersInQuery(query, queryParameters);
                return query.list();
            } catch (HibernateException he) {
                LOGGER.error("Exception occurred in executeQuery()", he);
                throw new Exception("Exception occurred in executeQuery()", he);
            }
        }
        return null;
    }

    public List executeNamedQuery(String queryName, Map queryParameters) throws Exception {
        Query query = getOrCreateSessionHolder().getSession().getNamedQuery(queryName);
        return executeQuery(query, queryParameters);
    }

    public List executeNamedQueryMaxResults(String queryName, Map queryParameters, int maxResults) throws Exception {
        Query query = getOrCreateSessionHolder().getSession().getNamedQuery(queryName).setMaxResults(maxResults);
        return executeQuery(query, queryParameters);
    }

    public List executeNamedQueryCached(String queryName, Map queryParameters) throws Exception {
        Query query = getOrCreateSessionHolder().getSession().getNamedQuery(queryName).setCacheable(true);
        return executeQuery(query, queryParameters);
    }

    public List executeNamedQueryCachedMaxResults(String queryName, Map queryParameters, int maxResults) throws Exception {
        Query query = getOrCreateSessionHolder().getSession().getNamedQuery(queryName).setMaxResults(maxResults).setCacheable(true);
        return executeQuery(query, queryParameters);
    }

    public Object executeUniqueResultNamedQuery(String queryName, Map queryParameters) throws Exception {
        try {
            Query query = getOrCreateSessionHolder().getSession().getNamedQuery(queryName);
            setParametersInQuery(query, queryParameters);
            if (null != query) {
                return query.uniqueResult();
            }
        } catch (HibernateException he) {
            LOGGER.error("Exception occurred in executeUniqueResultNamedQuery()", he);
            throw new Exception("Exception occurred in executeUniqueResultNamedQuery()", he);
        }
        return null;
    }

    public Object executeFirstResultNamedQuery(String queryName, Map queryParameters) throws Exception {
        try {
            Query query = getOrCreateSessionHolder().getSession().getNamedQuery(queryName).setMaxResults(1);
            setParametersInQuery(query, queryParameters);
            if (null != query) {
                if (query.list().size() > 0) {
                    return query.list().get(0);
                } else {
                    return null;
                }
            }
        } catch (HibernateException he) {
            LOGGER.error("Exception occurred in executeUniqueResultNamedQuery()", he);
            throw new Exception("Exception occurred in executeUniqueResultNamedQuery()", he);
        }
        return null;
    }
}
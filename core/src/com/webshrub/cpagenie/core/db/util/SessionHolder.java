/**
 * File			:	SessionHolder.java
 * Date			: 	March 11, 2008
 * Author		:	Poornima
 *
 */
package com.webshrub.cpagenie.core.db.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SessionHolder {
    private Session session;
    private Transaction transaction;

    public SessionHolder(Session session) {
        this.session = session;
        if (session == null) {
            throw new NullPointerException("Session is required in constructor.");
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setTranasction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Transaction beginTransaction() {
        if (transaction == null) {
            transaction = session.beginTransaction();
        }
        return transaction;
    }

    public void commitTransaction() {
        if (transaction != null) {
            transaction.commit();
            setTranasction(null);
        }
    }

    public void rollbackTransaction() {
        if (transaction != null) {
            transaction.rollback();
            setTranasction(null);
        }
    }

    public void flushAndCloseSession() {
        try {
            try {
                session.flush();
            } catch (HibernateException e) {
                session.clear();
            }
        } finally {
            session.close();
        }
    }

    public void closeSession() {
        try {
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public boolean isSessionOpen() {
        return !(session == null || !session.isOpen());
    }
}
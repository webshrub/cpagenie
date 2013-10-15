package com.webshrub.cpagenie.core.db.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 7, 2010
 * Time: 9:04:33 PM
 */
public class CoreDataUtil extends DbDataUtil {
    private static final String CORE_CFG_XML = "core.cfg.xml";
    private static final CoreDataUtil instance = new CoreDataUtil();

    private CoreDataUtil() {
        super();
    }

    public static CoreDataUtil getInstance() {
        return instance;
    }

    public SessionFactory initSessionFactory() {
        try {
            Configuration configuration = new AnnotationConfiguration();
            configuration.configure(CORE_CFG_XML);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new HibernateException("Could not initialize the Hibernate configuration", e);
        }
        return sessionFactory;
    }
}

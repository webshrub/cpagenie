package com.webshrub.cpagenie.app.db.util;

import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 19, 2010
 * Time: 7:59:38 PM
 */
public class AppDataUtil extends DbDataUtil {
    private static final String APP_CFG_XML = "app.cfg.xml";
    private static final AppDataUtil instance = new AppDataUtil();

    private AppDataUtil() {
        super();
    }

    public static AppDataUtil getInstance() {
        return instance;
    }

    public SessionFactory initSessionFactory() {
        try {
            Configuration configuration = new AnnotationConfiguration();
            configuration.configure(APP_CFG_XML);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new HibernateException("Could not initialize the Hibernate configuration", e);
        }
        return sessionFactory;
    }
}

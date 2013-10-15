package com.webshrub.cpagenie.server.db.util;

import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 17, 2010
 * Time: 7:41:20 PM
 */
public class ServerDataUtil extends DbDataUtil {
    private static final String SERVER_CFG_XML = "server.cfg.xml";
    private static final ServerDataUtil instance = new ServerDataUtil();

    private ServerDataUtil() {
        super();
    }

    public static ServerDataUtil getInstance() {
        return instance;
    }

    public SessionFactory initSessionFactory() {
        try {
            Configuration configuration = new AnnotationConfiguration();
            configuration.configure(SERVER_CFG_XML);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new HibernateException("Could not initialize the Hibernate configuration", e);
        }
        return sessionFactory;
    }
}

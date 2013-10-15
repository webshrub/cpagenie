package com.webshrub.cpagenie.app.mvc.service.impl;

import com.webshrub.cpagenie.app.mvc.authentication.AuthenticationUtil;
import com.webshrub.cpagenie.app.mvc.dto.Advertiser;
import com.webshrub.cpagenie.app.mvc.dto.User;
import com.webshrub.cpagenie.app.mvc.service.AdvertiserService;
import com.webshrub.cpagenie.app.mvc.service.UserService;
import com.webshrub.cpagenie.core.db.advertiser.CPAGenieAdvertiser;
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
 * User: Ahsan.Javed
 * Date: Aug 7, 2010
 * Time: 1:41:54 PM
 */
@Service
public class AdvertiserServiceImpl implements AdvertiserService {
    private static final Logger LOGGER = Logger.getLogger(AdvertiserServiceImpl.class);
    private DbDataUtil dataUtil;

    @Autowired
    private AuthenticationUtil authenticationUtil;
    @Autowired
    private UserService userService;

    @Autowired
    public AdvertiserServiceImpl(DbDataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @SuppressWarnings("unchecked")
    public List<Advertiser> getAdvertiserList() {
        List<Advertiser> advertiserList = new ArrayList<Advertiser>();
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            List<CPAGenieAdvertiser> advertisers = sessionHolder.getSession().createCriteria(CPAGenieAdvertiser.class).list();
            for (CPAGenieAdvertiser dbAdvertiser : advertisers) {
                advertiserList.add(new Advertiser().fill(dbAdvertiser));
            }
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getAdvertiserList()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getAdvertiserList()", e);
        } finally {
            sessionHolder.closeSession();
        }
        return advertiserList;
    }

    public List<Advertiser> getAdvertiserList(User user) {
        return new ArrayList<Advertiser>(user.getAdvertisers());
    }

    public Advertiser getAdvertiser(Integer id) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieAdvertiser dbAdvertiser = (CPAGenieAdvertiser) sessionHolder.getSession().get(CPAGenieAdvertiser.class, id);
            sessionHolder.commitTransaction();
            return new Advertiser().fill(dbAdvertiser);
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getAdvertiser()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getAdvertiser()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void saveAdvertiser(Advertiser advertiser) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieAdvertiser dbAdvertiser = new CPAGenieAdvertiser();
            dbAdvertiser.setName(advertiser.getName());
            dbAdvertiser.setDescription(advertiser.getDescription());
            dbAdvertiser.setEmail(advertiser.getEmail());
            dbAdvertiser.setCreationTime(new Date());
            CPAGenieVertical dbVertical = (CPAGenieVertical) sessionHolder.getSession().get(CPAGenieVertical.class, advertiser.getVertical().getId());
            dbAdvertiser.setVertical(dbVertical);
            sessionHolder.getSession().saveOrUpdate(dbAdvertiser);
            sessionHolder.commitTransaction();
            //Assign newly assigned id to the advertiser and assign it to current user.
            advertiser.setId(dbAdvertiser.getId());
            userService.assignAdvertiser(authenticationUtil.getCurrentUser(), advertiser);
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in saveAdvertiser()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in saveAdvertiser()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void updateAdvertiser(Advertiser advertiser) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieAdvertiser dbAdvertiser = (CPAGenieAdvertiser) sessionHolder.getSession().get(CPAGenieAdvertiser.class, advertiser.getId());
            dbAdvertiser.setName(advertiser.getName());
            dbAdvertiser.setDescription(advertiser.getDescription());
            dbAdvertiser.setEmail(advertiser.getEmail());
            dbAdvertiser.setUpdateTime(new Date());
            dbAdvertiser.setUpdateComments(advertiser.getUpdateComments());
            CPAGenieVertical dbVertical = (CPAGenieVertical) sessionHolder.getSession().get(CPAGenieVertical.class, advertiser.getVertical().getId());
            dbAdvertiser.setVertical(dbVertical);
            sessionHolder.getSession().saveOrUpdate(dbAdvertiser);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in updateAdvertiser()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in updateAdvertiser()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void deleteAdvertiser(Advertiser advertiser) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieAdvertiser dbAdvertiser = (CPAGenieAdvertiser) sessionHolder.getSession().get(CPAGenieAdvertiser.class, advertiser.getId());
            sessionHolder.getSession().delete(dbAdvertiser);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in deleteAdvertiser()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in deleteAdvertiser()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }
}

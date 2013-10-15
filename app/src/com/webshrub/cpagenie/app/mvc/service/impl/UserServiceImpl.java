package com.webshrub.cpagenie.app.mvc.service.impl;

import com.webshrub.cpagenie.app.db.authority.CPAGenieAuthority;
import com.webshrub.cpagenie.app.db.authority.CPAGenieAuthorityType;
import com.webshrub.cpagenie.app.db.user.CPAGenieUser;
import com.webshrub.cpagenie.app.db.user.CPAGenieUserStatus;
import com.webshrub.cpagenie.app.db.user.adapter.UserDetailsAdapter;
import com.webshrub.cpagenie.app.mvc.dto.*;
import com.webshrub.cpagenie.app.mvc.service.UserService;
import com.webshrub.cpagenie.core.db.advertiser.CPAGenieAdvertiser;
import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 26, 2010
 * Time: 4:55:40 PM
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private Map<AuthorityType, Authority> authorityMap;
    private DbDataUtil dataUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SaltSource saltSource;

    @Autowired
    public UserServiceImpl(DbDataUtil dataUtil) {
        this.authorityMap = new HashMap<AuthorityType, Authority>();
        this.dataUtil = dataUtil;
    }

    @SuppressWarnings("unchecked")
    public List<User> getUserList() {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            List<User> users = new ArrayList<User>();
            List<CPAGenieUser> userList = sessionHolder.getSession().createCriteria(CPAGenieUser.class).list();
            for (CPAGenieUser dbUser : userList) {
                users.add(new User().fill(dbUser));
            }
            sessionHolder.commitTransaction();
            return users;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getUserList()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getUserList()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public User getUser(Integer id) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieUser dbUser = (CPAGenieUser) sessionHolder.getSession().get(CPAGenieUser.class, id);
            sessionHolder.commitTransaction();
            return new User().fill(dbUser);
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getUser()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getUser()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public User getUser(String userName) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            Criteria criteria = sessionHolder.getSession().createCriteria(CPAGenieUser.class);
            criteria.add(Restrictions.eq("username", userName));
            CPAGenieUser dbUser = (CPAGenieUser) criteria.uniqueResult();
            sessionHolder.commitTransaction();
            return new User().fill(dbUser);
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getUser()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getUser()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void saveUser(User user) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieUser dbUser = new CPAGenieUser();
            dbUser.setUsername(user.getUsername());
            dbUser.setPassword(user.getPassword());
            dbUser.setEmail(user.getEmail());
            dbUser.setFirstName(user.getFirstName());
            dbUser.setLastName(user.getLastName());
            dbUser.setStatus(CPAGenieUserStatus.getStatus(user.getStatus().getId()));
            dbUser.setCreationTime(new Date());
            if (user.getAuthorities().size() != 0) {
                for (Authority authority : user.getAuthorities()) {
                    dbUser.addAuthority(getDbAuthority(authority.getAuthorityType(), sessionHolder));
                }
            } else {
                //Always add ROLE_USER authority to new users.
                dbUser.addAuthority(getDbAuthority(AuthorityType.getAuthorityType(CPAGenieAuthorityType.ROLE_USER.getId()), sessionHolder));
            }
            for (Advertiser advertiser : user.getAdvertisers()) {
                dbUser.addAdvertiser(getDbAdvertiser(advertiser, sessionHolder));
            }
            sessionHolder.getSession().saveOrUpdate(dbUser);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in saveUser()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in saveUser()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void saveUser(String userName, String password, String email, String firstName, String lastName, boolean enabled) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setStatus(UserStatus.getStatus(enabled ? 1 : 0));
        Object salt = saltSource.getSalt(new UserDetailsAdapter(user));
        String encodedPassword = passwordEncoder.encodePassword(password, salt);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.addAuthority(getAuthority(AuthorityType.getAuthorityType(CPAGenieAuthorityType.ROLE_USER.getId())));
        saveUser(user);
    }

    public void updateUser(User user) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieUser dbUser = (CPAGenieUser) sessionHolder.getSession().get(CPAGenieUser.class, user.getId());
            dbUser.setUsername(user.getUsername());
            dbUser.setPassword(user.getPassword());
            dbUser.setEmail(user.getEmail());
            dbUser.setFirstName(user.getFirstName());
            dbUser.setLastName(user.getLastName());
            dbUser.setStatus(CPAGenieUserStatus.getStatus(user.getStatus().getId()));
            dbUser.setUpdateTime(new Date());
            dbUser.setUpdateComments(user.getUpdateComments());
            dbUser.getAuthorities().clear();
            for (Authority authority : user.getAuthorities()) {
                dbUser.addAuthority(getDbAuthority(authority.getAuthorityType(), sessionHolder));
            }
            dbUser.getAdvertisers().clear();
            for (Advertiser advertiser : user.getAdvertisers()) {
                dbUser.addAdvertiser(getDbAdvertiser(advertiser, sessionHolder));
            }
            sessionHolder.getSession().saveOrUpdate(dbUser);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in updateUser()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in updateUser()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void deleteUser(User user) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieUser dbUser = (CPAGenieUser) sessionHolder.getSession().get(CPAGenieUser.class, user.getId());
            sessionHolder.getSession().delete(dbUser);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in deleteUser()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in deleteUser()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void changePassword(User user, String newPassword) {
        String encodedPassword = getEncodedPassword(user, newPassword);
        if (user.getPassword().equals(encodedPassword)) {
            LOGGER.warn("Old password and new password is same for user " + user.getUsername());
        } else {
            user.setPassword(encodedPassword);
            updateUser(user);
        }
    }

    public String getEncodedPassword(User user, String newPassword) {
        return passwordEncoder.encodePassword(newPassword, saltSource.getSalt(new UserDetailsAdapter(user)));
    }

    @SuppressWarnings("unchecked")
    public List<Authority> getAuthorityList() {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            List<Authority> authorities = new ArrayList<Authority>();
            List<CPAGenieAuthority> dbAuthorities = sessionHolder.getSession().createCriteria(CPAGenieAuthority.class).list();
            for (CPAGenieAuthority dbAuthority : dbAuthorities) {
                authorities.add(new Authority().fill(dbAuthority));
            }
            sessionHolder.commitTransaction();
            return authorities;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getAuthorityList()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getAuthorityList()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public Authority getAuthority(Integer id) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieAuthority dbAuthority = (CPAGenieAuthority) sessionHolder.getSession().get(CPAGenieAuthority.class, id);
            sessionHolder.commitTransaction();
            return new Authority().fill(dbAuthority);
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getAuthority()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getAuthority()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    private CPAGenieAuthority getDbAuthority(AuthorityType authorityType, SessionHolder sessionHolder) {
        CPAGenieAuthority dbAuthority;
        Criteria criteria = sessionHolder.getSession().createCriteria(CPAGenieAuthority.class);
        criteria.add(Restrictions.eq("authorityType", CPAGenieAuthorityType.getAuthorityType(authorityType.getId())));
        dbAuthority = (CPAGenieAuthority) criteria.uniqueResult();
        return dbAuthority;
    }

    private CPAGenieAdvertiser getDbAdvertiser(Advertiser advertiser, SessionHolder sessionHolder) {
        CPAGenieAdvertiser dbAdvertiser;
        Criteria criteria = sessionHolder.getSession().createCriteria(CPAGenieAdvertiser.class);
        criteria.add(Restrictions.eq("id", advertiser.getId()));
        dbAdvertiser = (CPAGenieAdvertiser) criteria.uniqueResult();
        return dbAdvertiser;
    }

    public Authority getAuthority(AuthorityType authorityType) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            Authority authority = authorityMap.get(authorityType);
            if (authority == null) {
                authority = new Authority().fill(getDbAuthority(authorityType, sessionHolder));
                authorityMap.put(authorityType, authority);
            }
            sessionHolder.commitTransaction();
            return authority;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getAuthority()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getAuthority()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void saveAuthority(Authority authority) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieAuthority dbAuthority = new CPAGenieAuthority();
            dbAuthority.setAuthorityType(CPAGenieAuthorityType.getAuthorityType(authority.getAuthorityType().getId()));
            dbAuthority.setCreationTime(new Date());
            sessionHolder.getSession().saveOrUpdate(dbAuthority);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in saveAuthority()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in saveAuthority()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void assignAuthority(User user, Authority authority) {
        user.addAuthority(authority);
        updateUser(user);
    }

    public void assignAdvertiser(User user, Advertiser advertiser) {
        user.addAdvertiser(advertiser);
        updateUser(user);
    }
}

package com.webshrub.cpagenie.app.mvc.service;

import com.webshrub.cpagenie.app.mvc.dto.Advertiser;
import com.webshrub.cpagenie.app.mvc.dto.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 7, 2010
 * Time: 1:38:33 PM
 */
public interface AdvertiserService {

    public List<Advertiser> getAdvertiserList();

    public List<Advertiser> getAdvertiserList(User user);

    public Advertiser getAdvertiser(Integer id);

    public void saveAdvertiser(Advertiser advertiser);

    public void updateAdvertiser(Advertiser advertiser);

    public void deleteAdvertiser(Advertiser advertiser);
}

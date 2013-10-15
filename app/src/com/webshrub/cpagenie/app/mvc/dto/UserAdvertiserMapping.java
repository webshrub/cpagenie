package com.webshrub.cpagenie.app.mvc.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 10, 2010
 * Time: 3:45:00 PM
 */
public class UserAdvertiserMapping {
    private User user;
    private List<Advertiser> advertisers = new ArrayList<Advertiser>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Advertiser> getAdvertisers() {
        return advertisers;
    }

    public void setAdvertisers(List<Advertiser> advertisers) {
        this.advertisers = advertisers;
    }
}

package com.webshrub.cpagenie.app.mvc.service;

import java.util.List;

import com.webshrub.cpagenie.app.mvc.dto.Campaign;
import com.webshrub.cpagenie.app.mvc.dto.Source;
import com.webshrub.cpagenie.app.mvc.dto.User;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 7, 2010
 * Time: 1:38:33 PM
 */
public interface CampaignService {

    public List<Campaign> getCampaignList();
    
    public List<Source> getSourceList();

    public List<Campaign> getCampaignList(User user);

    public Campaign getCampaign(Integer id);

    public void saveCampaign(Campaign campaign);

    public void updateCampaign(Campaign campaign);

    public void deleteCampaign(Campaign campaign);
}

package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.Advertiser;
import com.webshrub.cpagenie.app.mvc.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class AdvertiserEditor extends PropertyEditorSupport {

    @Autowired
    private AdvertiserService advertiserService;

    @Override
    public String getAsText() {
        Advertiser advertiser = (Advertiser) getValue();
        if (advertiser == null) {
            return "No advertiser set";
        }
        return advertiser.getId().toString();
    }

    @Override
    public void setAsText(String advertiserIdStr) throws IllegalArgumentException {
        setValue(advertiserService.getAdvertiser(Integer.parseInt(advertiserIdStr)));
    }
}
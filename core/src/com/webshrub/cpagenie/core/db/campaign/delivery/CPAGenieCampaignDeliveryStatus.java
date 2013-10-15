package com.webshrub.cpagenie.core.db.campaign.delivery;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public enum CPAGenieCampaignDeliveryStatus {
    //Never change the order of enum, they will break the ordinal system. To add a new status, just introduce a new enum at the last.
    UNDELIVERED(0, "Undelivered"),
    DELIVERED(1, "Delivered");

    private static Map<Integer, CPAGenieCampaignDeliveryStatus> deliveryStatusMap = new HashMap<Integer, CPAGenieCampaignDeliveryStatus>();

    static {
        for (CPAGenieCampaignDeliveryStatus leadDeliveryStatus : CPAGenieCampaignDeliveryStatus.values()) {
            deliveryStatusMap.put(leadDeliveryStatus.getId(), leadDeliveryStatus);
        }
    }

    private Integer id;
    private String name;

    private CPAGenieCampaignDeliveryStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CPAGenieCampaignDeliveryStatus getStatus(Integer ordinal) {
        return deliveryStatusMap.get(ordinal);
    }

    public static List<CPAGenieCampaignDeliveryStatus> getDeliveryStatusList() {
        List<CPAGenieCampaignDeliveryStatus> values = new ArrayList<CPAGenieCampaignDeliveryStatus>(deliveryStatusMap.values());
        Collections.sort(values);
        return values;
    }
}
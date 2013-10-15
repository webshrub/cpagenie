package com.webshrub.cpagenie.app.mvc.controller;

import com.webshrub.cpagenie.app.mvc.authentication.AuthenticationUtil;
import com.webshrub.cpagenie.app.mvc.dto.*;
import com.webshrub.cpagenie.app.mvc.propertyeditor.CampaignEditor;
import com.webshrub.cpagenie.app.mvc.propertyeditor.AdvertiserEditor;
import com.webshrub.cpagenie.app.mvc.service.CampaignService;
import com.webshrub.cpagenie.app.mvc.service.LeadService;
import com.webshrub.cpagenie.app.mvc.service.AdvertiserService;
import com.webshrub.cpagenie.app.mvc.service.ReportService;
import com.webshrub.cpagenie.app.mvc.view.FlexJSONView;
import com.webshrub.cpagenie.app.mvc.view.datatables.DataTablesResponse;
import com.webshrub.cpagenie.app.mvc.view.datatables.impressionreport.ImpressionReportDataTables;
import com.webshrub.cpagenie.app.mvc.view.datatables.leadreport.LeadReportDataTables;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 12, 2010
 * Time: 10:13:22 AM
 */
@Controller
@SessionAttributes({"impressionReportFilter", "leadReportFilter"})
public class ReportController {
    private static final Logger LOGGER = Logger.getLogger(ReportController.class);
    private static final DateFormat BINDING_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final String REPORT_IMPRESSIONREPORT_URL_KEY = "/secure/report/impressionreport.htm";
    private static final String REPORT_IMPRESSIONREPORT_VIEW_KEY = "secure/report/impressionreport";

    private static final String REPORT_UPDATEIMPRESSIONREPORT_URL_KEY = "/secure/report/updateimpressionreport.htm";
    private static final String REPORT_UPDATELEADREPORT_URL_KEY = "/secure/report/updateleadreport.htm";

    private static final String REPORT_LEADREPORT_URL_KEY = "/secure/report/leadreport.htm";
    private static final String REPORT_LEADREPORT_VIEW_KEY = "secure/report/leadreport";

    private static final String REPORT_GETCAMPAIGNS_URL_KEY = "/secure/report/getcampaigns.htm";

    private static final String ID_KEY = "id";
    private static final String ADVERTISER_KEY = "advertiser";
    private static final String CAMPAIGN_KEY = "campaign";
    private static final String START_DATE_KEY = "startDate";
    private static final String END_DATE_KEY = "endDate";
    private static final String ADVERTISER_LIST_KEY = "advertiserList";
    private static final String CAMPAIGN_LIST_KEY = "campaignList";
    private static final String LEAD_LIST_KEY = "leadList";
    private static final String IMPRESSION_REPORT_ROW_LIST_KEY = "impressionReportRowList";
    private static final String IMPRESSION_REPORT_FILTER_KEY = "impressionReportFilter";
    private static final String IMPRESSION_REPORT_DATA_TABLES_KEY = "impressionReportDataTables";
    private static final String LEAD_REPORT_FILTER_KEY = "leadReportFilter";
    private static final String LEAD_REPORT_DATA_TABLES_KEY = "leadReportDataTables";
    private static final String S_ECHO_KEY = "sEcho";
    private static final String I_TOTAL_RECORDS_KEY = "iTotalRecords";
    private static final String I_TOTAL_DISPLAY_RECORDS_KEY = "iTotalDisplayRecords";

    @Autowired
    @Qualifier(value = IMPRESSION_REPORT_DATA_TABLES_KEY)
    private ImpressionReportDataTables impressionReportDataTables;
    @Autowired
    @Qualifier(value = LEAD_REPORT_DATA_TABLES_KEY)
    private LeadReportDataTables leadReportDataTables;
    @Autowired
    private AuthenticationUtil authenticationUtil;
    @Autowired
    private ReportService reportService;
    @Autowired
    private AdvertiserService advertiserService;
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private LeadService leadService;
    @Autowired
    private AdvertiserEditor advertiserEditor;
    @Autowired
    private CampaignEditor campaignEditor;
    @Autowired
    private FlexJSONView flexJSONView;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setRequiredFields(ADVERTISER_KEY, CAMPAIGN_KEY, START_DATE_KEY, END_DATE_KEY);
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(BINDING_DATE_FORMAT, false));
        dataBinder.registerCustomEditor(Advertiser.class, advertiserEditor);
        dataBinder.registerCustomEditor(Campaign.class, campaignEditor);
    }

    @ModelAttribute(LEAD_REPORT_FILTER_KEY)
    public LeadReportFilter getLeadReportFilter() {
        return new LeadReportFilter();
    }

    @ModelAttribute(IMPRESSION_REPORT_FILTER_KEY)
    public ImpressionReportFilter getImpressionReportFilter() {
        return new ImpressionReportFilter();
    }

    @ModelAttribute
    public Lead getLead() {
        return new Lead();
    }

    @RequestMapping(value = REPORT_IMPRESSIONREPORT_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupImpressionReportForm(SessionStatus status) {
        LOGGER.info("Inside ReportController.setupImpressionReportForm");
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        List<Advertiser> advertiserList = advertiserService.getAdvertiserList(authenticationUtil.getCurrentUser());
        modelMap.addAttribute(ADVERTISER_LIST_KEY, advertiserList);
        if (advertiserList.size() == 0) {
            modelMap.addAttribute(CAMPAIGN_LIST_KEY, new ArrayList<Campaign>());
        } else {
            modelMap.addAttribute(CAMPAIGN_LIST_KEY, getCampaignList(advertiserList.get(0).getId()));
        }
        return new ModelAndView(REPORT_IMPRESSIONREPORT_VIEW_KEY, modelMap);
    }

    //Do not change the signature, It populates the content of impressionReportFilter into session attribute.

    @RequestMapping(value = REPORT_UPDATEIMPRESSIONREPORT_URL_KEY, method = RequestMethod.POST)
    public ModelAndView updateImpressionReport(ImpressionReportFilter impressionReportFilter) {
        LOGGER.info("Inside ReportController.updateImpressionReport");
        return new ModelAndView(flexJSONView, new ModelMap());
    }

    //This is a hack to have bindingResult instance here with errors available and still ignoring them.
    //Need to do it in a cleaner way. The data of the filter is coming from session attribute, not from binding
    //because binding fails due to non submission of form.

    @RequestMapping(value = REPORT_IMPRESSIONREPORT_URL_KEY, method = RequestMethod.POST)
    public ModelAndView getImpressionReport(ImpressionReportFilter impressionReportFilter, BindingResult result, HttpServletRequest request) {
        LOGGER.info("Inside ReportController.getImpressionReport");
        DataTablesResponse response = new DataTablesResponse<ImpressionReportRow>(Integer.parseInt(request.getParameter(S_ECHO_KEY)), 0, 0, new ArrayList<ImpressionReportRow>());
        if (impressionReportFilter.isPopulated()) {
            impressionReportDataTables.setImpressionReportFilter(impressionReportFilter);
            response = impressionReportDataTables.getDataTablesResponse(request);
        }
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(S_ECHO_KEY, response.getEcho());
        modelMap.addAttribute(I_TOTAL_RECORDS_KEY, response.getTotalRecords());
        modelMap.addAttribute(I_TOTAL_DISPLAY_RECORDS_KEY, response.getTotalDisplayRecords());
        modelMap.addAttribute(IMPRESSION_REPORT_ROW_LIST_KEY, response.getRecords());
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = REPORT_LEADREPORT_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupLeadReportForm(SessionStatus status) {
        LOGGER.info("Inside ReportController.setupLeadReportForm");
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        List<Advertiser> advertiserList = advertiserService.getAdvertiserList(authenticationUtil.getCurrentUser());
        modelMap.addAttribute(ADVERTISER_LIST_KEY, advertiserList);
        if (advertiserList.size() == 0) {
            modelMap.addAttribute(CAMPAIGN_LIST_KEY, new ArrayList<Campaign>());
        } else {
            modelMap.addAttribute(CAMPAIGN_LIST_KEY, getCampaignList(advertiserList.get(0).getId()));
        }
        return new ModelAndView(REPORT_LEADREPORT_VIEW_KEY, modelMap);
    }

    //Do not change the signature, It populates the content of impressionReportFilter into session attribute.

    @RequestMapping(value = REPORT_UPDATELEADREPORT_URL_KEY, method = RequestMethod.POST)
    public ModelAndView updateLeadReport(LeadReportFilter leadReportFilter) {
        LOGGER.info("Inside ReportController.updateLeadReport");
        return new ModelAndView(flexJSONView, new ModelMap());
    }

    //This is a hack to have bindingResult instance here with errors available and still ignoring them.
    //Need to do it in a cleaner way. The data of the filter is coming from session attribute, not from binding
    //because binding fails due to non submission of form.

    @RequestMapping(value = REPORT_LEADREPORT_URL_KEY, method = RequestMethod.POST)
    public ModelAndView getLeadReport(LeadReportFilter leadReportFilter, BindingResult result, HttpServletRequest request) {
        LOGGER.info("Inside ReportController.getLeadReport");
        DataTablesResponse response = new DataTablesResponse<Lead>(Integer.parseInt(request.getParameter(S_ECHO_KEY)), 0, 0, new ArrayList<Lead>());
        if (leadReportFilter.isPopulated()) {
            leadReportDataTables.setLeadReportFilter(leadReportFilter);
            response = leadReportDataTables.getDataTablesResponse(request);
        }
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(S_ECHO_KEY, response.getEcho());
        modelMap.addAttribute(I_TOTAL_RECORDS_KEY, response.getTotalRecords());
        modelMap.addAttribute(I_TOTAL_DISPLAY_RECORDS_KEY, response.getTotalDisplayRecords());
        modelMap.addAttribute(LEAD_LIST_KEY, response.getRecords());
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = REPORT_GETCAMPAIGNS_URL_KEY, method = RequestMethod.GET)
    public ModelAndView getCampaigns(@RequestParam(value = ID_KEY, required = true) Integer advertiserId) {
        List<Campaign> campaignList = getCampaignList(advertiserId);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(CAMPAIGN_LIST_KEY, campaignList);
        return new ModelAndView(flexJSONView, modelMap);
    }

    private List<Campaign> getCampaignList(Integer advertiserId) {
        List<Campaign> campaignList = new ArrayList<Campaign>();
        List<Campaign> allCampaigns = campaignService.getCampaignList();
        for (Campaign campaign : allCampaigns) {
            if (campaign.getAdvertiser().getId().equals(advertiserId)) {
                campaignList.add(campaign);
            }
        }
        return campaignList;
    }
}

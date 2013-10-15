package com.webshrub.cpagenie.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignField;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignFieldType;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignFieldValidationType;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import com.webshrub.cpagenie.server.db.util.ServerDataUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 11, 2010
 * Time: 7:03:38 PM
 */
public class CPAGenieJSServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CPAGenieJSServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	SessionHolder sessionHolder = ServerDataUtil.getInstance().getOrCreateSessionHolder();
    	try {
    		
    		String campaignIdStr  = req.getParameter("campaignId");
    		if(campaignIdStr ==null || campaignIdStr.trim().equals("")){
    			LOGGER.warn("cpagenie.js requested but missing campaign id. returning void response");
    			return;
    		}
    		Integer campaignId = Integer.parseInt(campaignIdStr);
    		LOGGER.info("Serving cpagenie.js for campaignId " + campaignId);
    		
    		String sourceIdStr = req.getParameter("sourceId");
    		Integer sourceId = 3; // default source from direct source
    		if(sourceIdStr!=null && !sourceIdStr.trim().equals("")){
    			sourceId = Integer.parseInt(sourceIdStr);
    		}
    		
    		sessionHolder.beginTransaction();
    		CPAGenieCampaign cpaGenieCampaign = (CPAGenieCampaign) sessionHolder.getSession().get(CPAGenieCampaign.class, campaignId);
    		InputStream inputStream =  CPAGenieJSServlet.class.getResourceAsStream("cpagenie.js");
    		resp.setContentType("text/js");
    		String cpagineJs = convertStreamToString(inputStream);
    		JsonObject campaignObject = new JsonObject();
    		campaignObject.addProperty("id", new Integer(campaignId).toString());
    		JsonObject finalObject = new JsonObject();
    		finalObject.add("campaign", campaignObject);
    		JsonArray jsonArray = new JsonArray();
    		CPAGenieCampaignField [] fields = cpaGenieCampaign.getFields().toArray(new CPAGenieCampaignField[cpaGenieCampaign.getFields().size()]);
    		
    		Arrays.sort(fields, new Comparator<CPAGenieCampaignField>() {
				@Override
				public int compare(CPAGenieCampaignField o1,
						CPAGenieCampaignField o2) {
					return o1.getId().compareTo(o2.getId());
				}
			});
    		
    		for(int i=0; i<fields.length; i++){
    			JsonObject field = new JsonObject();
    			if(fields[i].getDescription() == null){
    				field.addProperty("description", fields[i].getField().getDisplayName());
    			}else {
    				field.addProperty("description", fields[i].getDescription());
    			}
    			if(fields[i].getParameter() == null) {
    				field.addProperty("parameter", fields[i].getField().getName());
    			}else {
    				field.addProperty("parameter", fields[i].getParameter());
    			}
    			field.addProperty("required", fields[i].getFieldType().equals(CPAGenieCampaignFieldType.REQUIRED));
    			field.addProperty("validationrequired", fields[i].getFieldValidationType().equals(CPAGenieCampaignFieldValidationType.REQUIRED));
    			jsonArray.add(field);
    		}
    		sessionHolder.commitTransaction();
    		finalObject.add("fields", jsonArray);
    		finalObject.addProperty("sourceId", sourceId);
    		String address = req.getServerName()+":"+req.getServerPort();
    		finalObject.addProperty("serveraddress", address);
    		String finalJS = cpagineJs.replace("$json$", finalObject.toString());
    		resp.getWriter().write(finalJS);
    	} catch (HibernateException e) {
            LOGGER.error("Exception occurred in getCampaign()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getCampaign()", e);
        }catch (Exception e) {
    		LOGGER.error("An exception occurred while handling the response " + e);
    		e.printStackTrace();
    	} finally {
            sessionHolder.closeSession();
        }
    }
    
    public String convertStreamToString(InputStream is)
            throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
 
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {        
            return "";
        }
    }

    public static void main(String[] args) {
    	JsonObject campaignObject = new JsonObject();
		campaignObject.addProperty("id", new Integer(34).toString());
		JsonObject finalObject = new JsonObject();
		finalObject.add("campaign", campaignObject);
		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("k", "dd");
		jsonArray.add(jsonObject);
		 jsonObject = new JsonObject();
		jsonObject.addProperty("kk", "dfd");
		jsonArray.add(jsonObject);
		finalObject.add("fields", jsonArray);
		System.out.println(finalObject.toString());
		
	}
 
}

package com.alucn.casemanager.client.common.util;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import com.alucn.casemanager.client.constants.Constants;


public class ParamUtil {
	public static Logger logger = Logger.getLogger(ParamUtil.class);
	

	
	public static String getErrMsgStrOfOriginalException(Throwable e){
		StringBuffer errmsg = new StringBuffer();
		if(null != e){
			errmsg.append(e.getClass() + "	" + e.getMessage()+"\n");
		}
		
		if(null != e && null != e.getStackTrace() && e.getStackTrace().length > 0){
			for (StackTraceElement ste : e.getStackTrace()) {
				errmsg.append(ste.toString()+"\n");
			}
		}
		return errmsg.toString();
	}
	
	public static String getCurrentThreadId(){
		return String.valueOf(Thread.currentThread().getId());
	}
	
	public static JSONObject getJsonObject(JSONObject jsonPre,String reqType, String response, String json){
		JSONObject head = jsonPre.getJSONObject(Constants.HEAD);
		head.put(Constants.REQTYPE, reqType);
		head.put(Constants.RESPONSE, response);
		JSONObject body = jsonPre.getJSONObject(Constants.BODY);
		JSONObject jsonBody = JSONObject.fromObject(json);
		for(Object key : jsonBody.keySet()){
			body.put(key, jsonBody.getJSONObject((String)key));
		}
		return jsonPre;
	}
}

package com.alucn.casemanager.client.constants;

import net.sf.json.JSONObject;

/**
 * @author wanghaiqi
 *
 */
public class Constants {
	
	public static final String HEAD = "head";
	public static final String REQTYPE = "reqType";
	public static final String RESPONSE = "response";
	public static final String BODY = "body";
	public static final String LAB = "lab";
	public static final String IP = "serverIp";
	public static final String SERVERRELEASE = "serverRelease";
	public static final String SERVERSPA = "serverSPA";
	public static final String SERVERRTDB = "serverRTDB";
	public static final String TASKSTATUS = "taskStatus";
	public static final String STATUS = "status";
	public static final String IDLE = "Idle";
	public static final String RUNNINGCASE = "runningCase";
	public static final String TASKRESULT = "taskResult";
	public static final String SUCCESS = "success";
	public static final String NAME = "name";
	public static final String TIME = "time";
	public static final String FAIL = "fail";
	public static final String CASE_SUCCESS="SUCCESS";
	public static final String CASE_NOSERVE="NOSERVE";
	public static final String EMBEDDED_MESSAGE_RES = "PRERESPONSERESCON";
	public static final String CASEFAILANDSUCESS = "{\"head\":{\"clientip\":\"\",\"reqtype\":\"\",\"result\":\"\",\"status\":{\"serverName\":\"\",\"ip\":\"\",\"casesuccess\":\"\",\"casefail\":\"\",\"release\":\"\",\"protocol\":\"\",\"SPA\":[],\"RTDB\":[],\"runningcase\":\"\"}},\"body\":{}}";
//	public static final JSONObject CASESTATUSPRE = JSONObject.fromObject("{\"head\": {\"reqType\": \"\",\"response\": \"\"},\"body\": {\"lab\": {\"serverIp\": \"\",\"serverName\": \"\",\"serverRelease\": \"\",\"serverProtocol\": \"\",\"serverSPA\": [],\"serverRTDB\": []},\"taskStatus\": {\"status\": \"\",\"runningCase\": \"\"},\"taskResult\": {\"success\": [],\"fail\": []}}}");
	public static final JSONObject CASESTATUSPRE = JSONObject.fromObject("{\"head\": {\"reqType\": \"\",\"response\": \"\"},\"body\": {\"lab\": {\"serverIp\": \"\",\"serverName\": \"\",\"serverRelease\": \"\",\"serverProtocol\": \"\",\"serverTpye\": \"\",\"serverMate\": \"\",\"mateServer\": \"\",\"setName\": \"\",\"serverSPA\": [],\"serverRTDB\": []},\"taskStatus\": {\"status\": \"\",\"runningCase\": \"\"},\"taskResult\": {\"success\": [{\"name\": \"\",\"time\": \"\"}],\"fail\": []}}}");
	public static final String CASEREQTYPUP = "update";
	public static final String CASECOMMAND = "command";
	public static final String CASEINORUP = "insertorupdate";
	public static final String CASELISTACK = "caselistack";
	public static final String CASESTATUSFINSHED = "Finished";
	public static final String CASESTATUSRUNNINGSIGN = "Running";
	public static final String CASESTATUSFINISHED = "{\"taskResult\": {\"success\": [{\"name\": \"\",\"time\": \"\"}],\"fail\": []}}";
	public static final String CASESTATUSRUNNING = "{\"taskStatus\": {\"status\": \"Running\",\"runningCase\": \"\"}}";
	public static final String CASESTATUSIDLE = "{\"taskStatus\": {\"status\": \"Idle\",\"runningCase\": \"\"}}";
	public static final String CASESTATUSFAIL = "{\"taskStatus\": {\"status\": \"Fail\",\"runningCase\": \"\"}}";
	public static final String CASESTATUSRECON = "{\"taskStatus\": {\"status\": \"Recon\",\"runningCase\": \"\"}}";
	public static final String CASESTATUSREADY = "{\"taskStatus\": {\"status\": \"Ready\",\"runningCase\": \"\"}}";
	public static final String AVAILABLECASE = "availableCase";
	public static final String UPDATE = "UPDATESUCCESS";
	public static final String ACKUPDATE = "ACKUPDATESUCCESS";
	public static final String COMMANDSUCCESS = "COMMANDSUCCESS";
	
	
	//date format
	public static final String DATEPATTERN1="yyyy-MM-dd";
	public static final String DATEPATTERN2="yyyy-MM-dd HH:mm:ss";
	public static final String DATEPATTERN3="HH:mm:ss";
	public static final String DATEPATTERN4="yyyyMMdd";
	//errcode
	public static final String CASE_ERROR_CONNECTFAIL="CONNECTFAIL";//conn fail
	public static final String CASE_ERROR_TIMEOUT="TIMEOUT";//timeout
	public static final String CASE_ERROR_PARAMERROR_NOCONFPATH="Input parameter, missing profile path";
	public static final String CASE_ERROR_PARAMERROR_NOREQPACKET="Input parameter, missing reqString";
	public static final String CASE_ERROR_PARAMERROR_NOSERVER="No server configured in the configuration file";
	public static final String CASE_ERROR_PARAMERROR_SERVERNUMERROR="The number of servers that are not configured is not in the configuration file or the actual number of servers";
	public static final String CASE_ERROR_PARAMERROR_NOLOGPATH="No log path";
	public static final String CASE_ERROR_PARAMERROR_NOERRORLOGPATH="Error log path not configured";
	public static final String CASE_ERROR_PARAMERROR_NOLOGCONFNAME="No configuration file path";
	public static final String CASE_ERROR_PARAMERROR="PARAMERROR";
	public static final String CASE_ERROR_CONFFILEMISS="miss config file";
	
	//conf param
	public static final String CASE_CONF_SERVER="case.client.server";
	public static final String CASE_CONF_SERVERNUM="case.client.server_num";
	public static final String CASE_CONF_SOCKETERRORTIME="case.client.socket_error_time";
	public static final String CASE_CONF_SOCKETERRORTIMEOUT="case.client.socket_error_timeout";
	public static final String CASE_CONF_REFRESHTIMEBREAK="case.client.refresh_time_break";
	public static final String CASE_CONF_HEALTHYCHECKTIMEBREAK="case.client.healthycheck_time_break";
	public static final String CASE_CONF_HEALTHYCHECKERRORTIME="case.client.healthycheck_error_time";
	public static final String CASE_CONF_DAEMONTIMEBREAK="case.client.daemon_thread_error_time_break";
	public static final String CASE_CONF_PYTHONPATH="case.client.python.path";
	public static final String CASE_CONF_PYTHONNAME="case.client.python.name";
	public static final String CASE_CONF_CASELISTNAME="case.client.caselist.name";
	public static final String CASE_CONF_CASELISTPATH="case.client.caselist.path";
	public static final String CASE_CONF_CASEREADTIMEOUT="case.socket.read_period_timeout";
	public static final String CASE_CONF_CASESTATUSPATH="case.client.casestatus.path";
	public static final String CASE_CONF_CASESPARTDBPATH="case.client.casesspartdb.path";
	public static final String CASE_CONF_CASEHANDLENUM="case.client.handle.num";
	public static final String CASE_CONF_MONITORPY="case.client.monitorpy_time_break";

	//pre
	public static final String CASE_EMBEDDED_MESSAGEREQ = "PREREQUESTHEALTH"; 
    public static final String CASE_EMBEDDED_MESSAGERESP = "PRERESPONSEHEALTH";
	
    public static final String CHARACTER_SET_ENCODING_UTF8 = "UTF-8";
    public static final String EPAY = "EPAY";
	private Constants(){
	}
}

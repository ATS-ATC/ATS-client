package com.alucn.casemanager.client.model;

import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.alucn.casemanager.client.common.util.CommonsUtil;
import com.alucn.casemanager.client.constants.Constants;
import com.alucn.casemanager.client.exception.CaseParamIncompletedException;


/**
 * case info class
 * @author wanghaiqi
 *
 */
public class Configuration {
	
	public static Logger logger = Logger.getLogger(Configuration.class);
	
	private static Configuration conf;
	
	private final static ReentrantLock tlock = new ReentrantLock();
	
	private Vector<SocketInfo> serverList;
	
	private Integer serverNum;
	
	private Integer socketErrorTime;
	
	private Long socketErrorTimeout;
	
	private Long refreshTimeBreak;
	
	private Long monitorPyBreak;
	
	private Long healthycheckTimeBreak;
	
	private Integer healthycheckErrorTime;
	
	private Long daemonTimeBreak;
	
	//python
	private String pythonName;
	
	private String pythonPath;
	
	private String caseListPath;
	
	private String caseStatusPath;
	
	private String caseListName;
	
	private String casesocketreadtimeout;
	
	private String spaAndrtdb;
	
	private String caseHandleNum;
	
	
	/**
	 * Initializing profile information
	 * @param properties
	 */
	public static void refreshConfiguration(Properties properties) throws CaseParamIncompletedException{
		final ReentrantLock lock = tlock;
		lock.lock();
		try{
			Configuration config = new Configuration();
			//Get server number
			int servernum = Integer.parseInt(properties.get(Constants.CASE_CONF_SERVERNUM).toString());
			//Get server connection information
			Vector<SocketInfo> serverList = new Vector<SocketInfo>();
			for (int i = 1; i <= servernum; i++) {
				SocketInfo socketInfo = new SocketInfo();
				String ip = properties.get(Constants.CASE_CONF_SERVER+i+"_ip").toString();
				if(CommonsUtil.isEmpty(ip)){
					throw new CaseParamIncompletedException("[Socket connection server IP is not configured]");
				}else{
					socketInfo.setIp(ip);
				}
				String port = properties.get(Constants.CASE_CONF_SERVER+i+"_port").toString();
				if(CommonsUtil.isEmpty(port)){
					throw new CaseParamIncompletedException("[Socket connection server PORT is not configured]");
				}else{
					socketInfo.setPort(port);
				}
				String timeout = properties.get(Constants.CASE_CONF_SERVER+i+"_timeout").toString();
				if(CommonsUtil.isEmpty(timeout)){
					logger.info("[Socket connection timeout is not configured, the default value is used]");
					socketInfo.setTimeout(100000);
				}else{
					socketInfo.setTimeout(Integer.parseInt(timeout));
				}
				String soTimeout = properties.get(Constants.CASE_CONF_SERVER+i+"_sotimeout").toString();
				if(CommonsUtil.isEmpty(soTimeout)){
					logger.info("[Socket read timeout is not configured, the default value is used]");
					socketInfo.setSoTimeout(100000);
				}else{
					socketInfo.setSoTimeout(Integer.parseInt(soTimeout));
				}
				if(socketInfo.isEmpty()){
					logger.error("server"+i+"Not configured correctly");
					throw new CaseParamIncompletedException("[Socket connection server PORT is not configured]");
				}else{
					serverList.add(socketInfo);
				}
			}
			config.setServerList(serverList);
			config.setServerNum(servernum);
			config.setSocketErrorTime(Integer.parseInt(properties.get(Constants.CASE_CONF_SOCKETERRORTIME).toString()));
			config.setSocketErrorTimeout(Long.parseLong(properties.get(Constants.CASE_CONF_SOCKETERRORTIMEOUT).toString()));
			config.setRefreshTimeBreak(Long.parseLong(properties.get(Constants.CASE_CONF_REFRESHTIMEBREAK).toString()));
			config.setHealthycheckTimeBreak(Long.parseLong(properties.get(Constants.CASE_CONF_HEALTHYCHECKTIMEBREAK).toString()));
			config.setHealthycheckErrorTime(Integer.parseInt(properties.get(Constants.CASE_CONF_HEALTHYCHECKERRORTIME).toString()));
			config.setDaemonTimeBreak(Long.parseLong(properties.get(Constants.CASE_CONF_DAEMONTIMEBREAK).toString()));
			config.setPythonName(properties.get(Constants.CASE_CONF_PYTHONNAME).toString());
			config.setPythonPath(properties.get(Constants.CASE_CONF_PYTHONPATH).toString());
			config.setCaseListName(properties.get(Constants.CASE_CONF_CASELISTNAME).toString());
			config.setCaseListPath(properties.get(Constants.CASE_CONF_CASELISTPATH).toString());
			config.setCasesocketreadtimeout(properties.get(Constants.CASE_CONF_CASEREADTIMEOUT).toString());
			config.setCaseStatusPath(properties.get(Constants.CASE_CONF_CASESTATUSPATH).toString());
			config.setSpaAndrtdb(properties.get(Constants.CASE_CONF_CASESPARTDBPATH).toString());;
			config.setCaseHandleNum(properties.get(Constants.CASE_CONF_CASEHANDLENUM).toString());
			config.setMonitorPyBreak(Long.parseLong(properties.get(Constants.CASE_CONF_MONITORPY).toString()));
			config = checkConfiguration(config);
			conf = config;
		}finally{
			lock.unlock();
		}
	}
	
	//Print configuration information
	public static void showConfigurationInfo(){
		logger.info("[Currently configured server information：]");
		for (SocketInfo socketinfo : conf.getServerList()) {
			logger.info(socketinfo.getName());
		}
		logger.info("Number of servers currently configured"+conf.getServerNum());
		logger.info("Number of failed requests for current configuration"+conf.getSocketErrorTime());
		logger.info("The request for the current configuration failed to reconnect the time base"+conf.getSocketErrorTimeout());
		logger.info("Current configuration of case information for hot load time interval (MS)"+conf.getRefreshTimeBreak());
		logger.info("Current configuration health detection time interval (MS)"+conf.getHealthycheckTimeBreak());
		logger.info("Current configuration health detection time interval (MS)"+conf.getHealthycheckErrorTime());
		logger.info("The current configuration of the daemon thread polling interval (MS)"+conf.getDaemonTimeBreak());
	}
	
	//Verify that the case information object is complete
	public static Configuration checkConfiguration(Configuration config) throws CaseParamIncompletedException{
		//Configure server list
		if(config.getServerList().size()<0){
			logger.error(Constants.CASE_ERROR_PARAMERROR_NOSERVER);
			throw new CaseParamIncompletedException(Constants.CASE_ERROR_PARAMERROR_NOSERVER);
		}
		//The number of server lists is equal to the number of configurations
		if(config.getServerNum()==null||config.getServerList().size()!=config.getServerNum()){
			logger.error(Constants.CASE_ERROR_PARAMERROR_SERVERNUMERROR);
			throw new CaseParamIncompletedException(Constants.CASE_ERROR_PARAMERROR_SERVERNUMERROR);
		}
		//Determine whether the request failed to reset the number of times if the default value is not configured
		if(config.getSocketErrorTime()==null||config.getSocketErrorTime()==0){
			logger.info("[The request failed to reconnect and the default value has been used]");
			config.setSocketErrorTime(1);
		}
		//Determine whether the request is reset if the interval time base is configured and the default value is not configured
		if(config.getSocketErrorTimeout()==null||config.getSocketErrorTimeout()==0){
			logger.info("[Request reset interval time is not configured, the default value is used]");
			config.setSocketErrorTimeout(1000L);
		}
		//Determine whether the case information thermal load interval is configured to assign default values if not configured
		if(config.getRefreshTimeBreak()==null||config.getRefreshTimeBreak()==0){
			logger.info("[Case information load time interval is not configured, the default value has been used]");
			config.setSocketErrorTimeout(300000L);
		}
		//Determine whether the health detection interval is configured to default if no configuration is given
		if(config.getHealthycheckTimeBreak()==null||config.getHealthycheckTimeBreak()==0){
			logger.info("[The health test interval is not configured, the default values are used]");
			config.setHealthycheckTimeBreak(1000L);
		}
		//Determine whether the number of times the failure of the health test is configured to assign default values if not configured
		if(config.getHealthycheckErrorTime()==null||config.getHealthycheckErrorTime()==0){
			logger.info("[The health check failed to be reset and the default value was used]");
			config.setHealthycheckErrorTime(3);
		}
		//Judge whether the guard thread polling interval is configured to assign a default value if it is not configured
		if(config.getDaemonTimeBreak()==null||config.getDaemonTimeBreak()==0){
			logger.info("[The health test interval is not configured, the default values are used]");
			config.setDaemonTimeBreak(300000L);
		}
		return config;
	}
	
	//单例获取
	public static Configuration getConfiguration(){
		if(conf==null){
			conf=new Configuration();
		}
		return conf;
	}
	
	private Configuration(){
		
	}

	public Vector<SocketInfo> getServerList() {
		return serverList;
	}

	public void setServerList(Vector<SocketInfo> serverList) {
		this.serverList = serverList;
	}

	public Integer getServerNum() {
		return serverNum;
	}

	public void setServerNum(Integer serverNum) {
		this.serverNum = serverNum;
	}

	public Integer getSocketErrorTime() {
		return socketErrorTime;
	}

	public void setSocketErrorTime(Integer socketErrorTime) {
		this.socketErrorTime = socketErrorTime;
	}

	public Long getSocketErrorTimeout() {
		return socketErrorTimeout;
	}

	public void setSocketErrorTimeout(Long socketErrorTimeout) {
		this.socketErrorTimeout = socketErrorTimeout;
	}

	public Long getRefreshTimeBreak() {
		return refreshTimeBreak;
	}

	public void setRefreshTimeBreak(Long refreshTimeBreak) {
		this.refreshTimeBreak = refreshTimeBreak;
	}

	public Long getHealthycheckTimeBreak() {
		return healthycheckTimeBreak;
	}

	public void setHealthycheckTimeBreak(Long healthycheckTimeBreak) {
		this.healthycheckTimeBreak = healthycheckTimeBreak;
	}

	public Integer getHealthycheckErrorTime() {
		return healthycheckErrorTime;
	}

	public void setHealthycheckErrorTime(Integer healthycheckErrorTime) {
		this.healthycheckErrorTime = healthycheckErrorTime;
	}
	public Long getDaemonTimeBreak() {
		return daemonTimeBreak;
	}

	public void setDaemonTimeBreak(Long daemonTimeBreak) {
		this.daemonTimeBreak = daemonTimeBreak;
	}

	public String getPythonName() {
		return pythonName;
	}

	public void setPythonName(String pythonName) {
		this.pythonName = pythonName;
	}

	public String getPythonPath() {
		return pythonPath;
	}

	public void setPythonPath(String pythonPath) {
		this.pythonPath = pythonPath;
	}

	public String getCaseListPath() {
		return caseListPath;
	}

	public void setCaseListPath(String caseListPath) {
		this.caseListPath = caseListPath;
	}

	public String getCaseListName() {
		return caseListName;
	}

	public void setCaseListName(String caseListName) {
		this.caseListName = caseListName;
	}

	public String getCasesocketreadtimeout() {
		return casesocketreadtimeout;
	}

	public void setCasesocketreadtimeout(String casesocketreadtimeout) {
		this.casesocketreadtimeout = casesocketreadtimeout;
	}

	public String getCaseStatusPath() {
		return caseStatusPath;
	}

	public void setCaseStatusPath(String caseStatusPath) {
		this.caseStatusPath = caseStatusPath;
	}

	public String getSpaAndrtdb() {
		return spaAndrtdb;
	}

	public void setSpaAndrtdb(String spaAndrtdb) {
		this.spaAndrtdb = spaAndrtdb;
	}

	public String getCaseHandleNum() {
		return caseHandleNum;
	}

	public void setCaseHandleNum(String caseHandleNum) {
		this.caseHandleNum = caseHandleNum;
	}

	public Long getMonitorPyBreak() {
		return monitorPyBreak;
	}

	public void setMonitorPyBreak(Long monitorPyBreak) {
		this.monitorPyBreak = monitorPyBreak;
	}
	
}


package com.alucn.casemanager.client.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.alucn.casemanager.client.constants.Constants;
import com.alucn.casemanager.client.exception.CaseParamIncompletedException;
import com.alucn.casemanager.client.exception.CaseRefreshConfCacheException;
import com.alucn.casemanager.client.model.Configuration;
import com.alucn.casemanager.client.model.SocketInfo;

/**
 * cache
 * @author wanghaiqi
 *
 */
public class CaseCache {
	public static Logger logger = Logger.getLogger(CaseCache.class);

	private static  CopyOnWriteArrayList<SocketInfo> healthyServers = new CopyOnWriteArrayList<SocketInfo>();
	
	private static CopyOnWriteArrayList<SocketInfo> unhealthyServers = new CopyOnWriteArrayList<SocketInfo>();
	
	//Profile date
	private static String propertiesDateStr="";
	
	//init cache
	public static String refreshCache(String configPath) throws CaseParamIncompletedException, CaseRefreshConfCacheException{
		InputStream in = null;
		Long refreshTime = System.currentTimeMillis();
		try {
			logger.debug("[refresh cache start]");
			logger.debug("profile path"+configPath);
			File conf = new File(configPath);
			Date propertiesDate = new Date(conf.lastModified());
			String tempDateStr = CommonsUtil.date2String(propertiesDate, Constants.DATEPATTERN2);
			//To determine whether the last modification time of the file is different from the file modification time of the system cache
			if(!tempDateStr.equals(propertiesDateStr)){
				logger.debug("[load new profile]");
				//update file date
				propertiesDateStr = tempDateStr;
				if(conf.isFile()&&conf.exists()){
					in = new FileInputStream(conf);
					Properties serverListProperties = new Properties();
					serverListProperties.load(in);
					Configuration.refreshConfiguration(serverListProperties);
					Configuration.showConfigurationInfo();
					//Refresh available server list
					CopyOnWriteArrayList<SocketInfo> healthyServerTemp = new CopyOnWriteArrayList<SocketInfo>();
					for (SocketInfo socketInfo : Configuration.getConfiguration().getServerList()) {
						healthyServerTemp.add(socketInfo);
					}
					healthyServers=healthyServerTemp;
				}else{
					logger.error(Constants.CASE_ERROR_CONFFILEMISS);
					throw new CaseRefreshConfCacheException(Constants.CASE_ERROR_CONFFILEMISS);
				}
			}else{
				logger.debug("[Configuration file not modified]");
			}
		} catch (IOException e) {
			logger.error("[read profile exception]",e);
			e.printStackTrace();
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				logger.error("close profile io exception",e);
				e.printStackTrace();
			}
		}
		logger.debug("[refresh profile cache end]");
		logger.debug("[refresh profile cache time:"+(System.currentTimeMillis()-refreshTime)+"]");
		return Constants.CASE_SUCCESS;
	}
	
	private CaseCache(){
	}
	
	//Initialize available server list
	public static void setHealthyServers(CopyOnWriteArrayList<SocketInfo> healthyServers) {
		CaseCache.healthyServers = healthyServers;
	}

	//Initialize unavailable server list
	public static void setUnHealthyServers(CopyOnWriteArrayList<SocketInfo> unHealthyServers) {
		CaseCache.unhealthyServers = unHealthyServers;
	}
	
	//Add server to available server list
	public static void appendHealthyServers(SocketInfo healthyServer) {
		if(!healthyServers.contains(healthyServer)){
			healthyServers.add(healthyServer);
			deleteUnHealthyServers(healthyServer);
			logger.debug("A new server is available "+healthyServer.getName()+" add to available servers");
		}else{
			logger.debug("The server is already in the list of available servers "+healthyServer.getName());
		}
	}
	//To add a server to a unavailable server list
	public static void appendUnHealthyServers(SocketInfo unhealthyServer) {
		if(!unhealthyServers.contains(unhealthyServer)){
			unhealthyServers.add(unhealthyServer);
			deleteHealthyServers(unhealthyServer);
			logger.error("A server is down "+unhealthyServer.getName()+" add to unavailable servers");
		}else{
			logger.debug("The server is already in the unavailable server list "+unhealthyServer.getName());
		}
	}
	
	//To delete a server on the available server list
	public static void deleteHealthyServers(SocketInfo healthyServer) {
		if(healthyServers.contains(healthyServer)){
			healthyServers.remove(healthyServer);
			logger.error("A server is down "+healthyServer.getName()+" Has been removed from the list of available servers");
		}else{
			logger.debug("The server is not in the list of available servers "+healthyServer.getName());
		}
	}
	//Delete server on unavailable server list
	public static void deleteUnHealthyServers(SocketInfo unhealthyServer) {
		if(unhealthyServers.contains(unhealthyServer)){
			unhealthyServers.remove(unhealthyServer);
			logger.debug("A new server is available "+unhealthyServer.getName()+"Has been removed from the list of available servers");
		}else{
			logger.debug("The server is not available in the unavailable server list "+unhealthyServer.getName());
		}
	}

	//List of available servers
	public static CopyOnWriteArrayList<SocketInfo> getHealthyServers(){
		if(healthyServers!=null&&!healthyServers.isEmpty()){
			return healthyServers;
		}else{
			return new CopyOnWriteArrayList<SocketInfo>();
		}
	}
	
	//List of unavailable servers
	public static CopyOnWriteArrayList<SocketInfo> getUnhealthyServers(){
		if(unhealthyServers!=null&&!unhealthyServers.isEmpty()){
			return unhealthyServers;
		}else{
			return new CopyOnWriteArrayList<SocketInfo>();
		}
	}
}

package com.alucn.casemanager.client.listener;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.alucn.casemanager.client.common.util.CaseCache;
import com.alucn.casemanager.client.constants.Constants;
import com.alucn.casemanager.client.exception.CaseParamIncompletedException;
import com.alucn.casemanager.client.exception.CaseRefreshConfCacheException;
import com.alucn.casemanager.client.thread.CaseMonitorThread;


/**
 * init
 * @author wanghaiqi
 *
 */
public class InitListener {
	
	public static Logger logger = Logger.getLogger(InitListener.class);

	private static String configPath;
	
	public static Thread caseMonitorThread;
	
	public static String configFilesPath;

	/**
	 * Initialization method to start the project
	 * @param confPath
	 */
	public static void init(String confPath){
		//log4j config init
		PropertyConfigurator.configure(confPath+File.separator+"log4j.properties");
		//init system config
		String initResult = "";
		try {
			initResult = CaseCache.refreshCache(confPath+File.separator+"caseclientconf.properties");
			if(!Constants.CASE_SUCCESS.equals(initResult)){
				logger.info("[Failed to initialize configuration file]");
			}
		} catch (CaseParamIncompletedException paramIncompletedException) {
			logger.error(paramIncompletedException.getMessage(),paramIncompletedException);
			paramIncompletedException.printStackTrace();
		} catch (CaseRefreshConfCacheException refreshConfCacheException) {
			logger.error(refreshConfCacheException.getMessage(),refreshConfCacheException);
			refreshConfCacheException.printStackTrace();
		} 
		
		//start daemon thread
		configPath = confPath;
		if(caseMonitorThread==null ||!caseMonitorThread.isAlive()){
			synchronized (InitListener.class) {
				if(caseMonitorThread==null ||!caseMonitorThread.isAlive()){
					caseMonitorThread = new Thread(new CaseMonitorThread());
					logger.info("start daemon thread");
					caseMonitorThread.start();
				}
			}
		}else{
			logger.debug("[Daemon thread started]");
		}
	}

	public static String getConfigPath() {
		return configPath;
	}

	public static void setConfigPath(String configPath) {
		InitListener.configPath = configPath;
	}
	
	
	public static Thread getcaseMonitorThread() {
		return caseMonitorThread;
	}
	
	
	public static void main(String[] args) {
		if (args.length!=1) {
			System.out.println("[The number of arguments is incorrect, and there is only one parameter for the profile path]");
		}else{
            configFilesPath = args[0];
            init(configFilesPath);
		}
	}
}

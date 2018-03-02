package com.alucn.casemanager.client.model;

/**
 * socket链接信息
 * @author wanghaiqi
 *
 */
public class SocketInfo {

	//IP地址
	private String ip;
	
	//连接端口
	private String port;
	
	//连接超时时间
	private Integer timeout;
	
	//读数据超时时�?
	private Integer soTimeout;
	
	public Integer getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(Integer soTimeout) {
		this.soTimeout = soTimeout;
	}

	public SocketInfo(){
		timeout=0;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	
	public boolean isEmpty(){
		if(ip!=null&&port!=null&&timeout!=0L){
			return false;
		}else{
			return true;
		}
	}
	
	public String getName(){
		return "IP:"+ip+"  PORT:"+port;
	}
	
	public String getInfo(){
		return "IP:"+ip+" PORT:"+port+" TIMEOUT:"+timeout+" SOTIMEOUT:"+soTimeout;
	}
}

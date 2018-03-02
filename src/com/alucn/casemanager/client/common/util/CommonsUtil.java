package com.alucn.casemanager.client.common.util;


import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.alucn.casemanager.client.constants.Constants;
import com.alucn.casemanager.client.model.Configuration;
import com.alucn.casemanager.client.model.SocketInfo;


/**
 * utils
 * @author wanghaiqi
 *
 */
public class CommonsUtil {
	
	public static Logger logger = Logger.getLogger(CommonsUtil.class);
    public static Socket socket = null;
	public static DataOutputStream dos;
	public static BufferedInputStream bis;
	public static byte[] resultByteArr;
//	private static byte[] communicateHead = new byte[4];
	private static StringBuffer receiveJsonData = new StringBuffer(Constants.CHARACTER_SET_ENCODING_UTF8);
	/**
	 * 
	 * @param byteArr
	 * @return
	 */
	public static int byteArr2Int(byte[] byteArr){
		int result ;
		result = (int)(((byteArr[0]&0xFF)<<24)|((byteArr[1]&0xFF)<<16)|((byteArr[2]&0xFF)<<8)|(byteArr[3]&0xFF));
		return result;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public static  byte[] int2ByteArr(int i){
		byte[] result = new byte[4];
		result[0] = (byte)((i>>24)&0xFF);
		result[1] = (byte)((i>>16)&0xFF);
		result[2] = (byte)((i>>8)&0xFF);
		result[3] = (byte)(i&0xFF);
		return result;
	}
	
	
	/**
	 * Whether the string is empty
	 * @param str
	 * @return true 空  false 非空
	 * 
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str)){
			return true;
		}
		return false;
	}
	
	/**
	 * Date conversion string
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String date2String(Date date, String pattern){
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String result = formater.format(date);
		return result;
	}
	
	/**
	 * String conversion date
	 * @param dateStr
	 * @param pattern
	 * @return
	 * @throws ParseException 
	 */
	public static Date string2Date(String dateStr, String pattern) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		Date date = null;
		date = formater.parse(dateStr);
		return date;
	}
	
	/**
	 * Gets the profile folder path
	 * @param confPath
	 * @return
	 */
	public static String getConfDict(String confPath){
		String[] confPaths = null;
		String fileSeparator = "/";
		if('\\'==File.separatorChar){
			fileSeparator=File.separator+File.separator;
		}
		confPaths = confPath.split(fileSeparator);
		String confDict = "";
		for (int i = 0; i < confPaths.length-1; i++) {
			confDict+=confPaths[i]+fileSeparator;
			
		}
		return confDict;
	}
	
	public static void setSocket() throws IOException {
		CommonsUtil.bis = new BufferedInputStream(socket.getInputStream()); 
		CommonsUtil.dos = new DataOutputStream(socket.getOutputStream());
	}
	
	/**
	 * socket connect
	 * @param info(连接信息)
	 * @param reqPacket
	 * @return return response message
	 * @throws Exception 
	 * @throws ChdpSocketConnectionException 
	 * @throws ConnectException 
	 */
	public synchronized static void connector(SocketInfo info, String reqPacket) throws Exception{
//		socket = getInstance();
		//Connect the socket to the connection information and set the connection timeout
		if(socket==null || (socket.isClosed()||!socket.isConnected()) && reqPacket.equals(Constants.CASE_EMBEDDED_MESSAGEREQ)){
			socket = new Socket();
			socket.connect(new InetSocketAddress(info.getIp(), Integer.parseInt(info.getPort())), info.getTimeout());
			setSocket(); 
		}
		//Set read timeout
		socket.setSoTimeout(info.getSoTimeout());
		//send req message
		int jsonDataLength = reqPacket.getBytes(Constants.CHARACTER_SET_ENCODING_UTF8).length;
		dos.write(CommonsUtil.int2ByteArr(jsonDataLength));
		dos.write(reqPacket.getBytes(Constants.CHARACTER_SET_ENCODING_UTF8));
		dos.flush();
	}
	
	public static String readInfo() throws IOException, Exception{
		receiveJsonData.delete(0,receiveJsonData.length()); 
		//JSON partial message byte length
		int jsonDataLength = byteArr2Int(readByByteNumber(CommonsUtil.bis, 4));
		//Read JSON message byte array
		byte[] jsonDataArr = readByByteNumber(CommonsUtil.bis, jsonDataLength);
		//json message
		String receiveJsonDataTmp = new String(jsonDataArr, Constants.CHARACTER_SET_ENCODING_UTF8);
		receiveJsonData.append(receiveJsonDataTmp);
		receiveJsonDataTmp = null;
		jsonDataArr = null;
		return receiveJsonData.toString();
	}
	
	/**
	 * Reads an array of bytes according to the number of bytes until the total number of bytes returned
	 * @param bis
	 * @param byteNumber
	 * @return
	 * @throws IOException
	 * @throws SysException 
	 */
	public static byte[] readByByteNumber(BufferedInputStream bis , int byteNumber) throws IOException, Exception{
		
		byte [] resultByteArrTmp = new byte[byteNumber];
		// complete length of bytes read
		int completeReadByteCount = 0;
		//Read byte length
		int onceReadByteCount = 0;
		int mills = Integer.parseInt(Configuration.getConfiguration().getCasesocketreadtimeout());
		long timeDiff = mills;
		long start = System.currentTimeMillis();
		while(completeReadByteCount < byteNumber){
			if(System.currentTimeMillis() - start >= timeDiff){
				throw new Exception("Packet receive timeout");
			}
			//Single read byte array
			byte[] onceReadByteArr = new byte[byteNumber-completeReadByteCount];
			//Read byte length
			onceReadByteCount = bis.read(onceReadByteArr);
//			logger.debug("onceReadByteCount = " + onceReadByteCount);
			//Splicing to JSON message byte array
			System.arraycopy(onceReadByteArr, 0, resultByteArrTmp, completeReadByteCount, onceReadByteCount);
			completeReadByteCount+=onceReadByteCount;
//			logger.debug("completeReadByteCount = " + completeReadByteCount);
		}
		resultByteArr=resultByteArrTmp;
		resultByteArrTmp=null;
		return resultByteArr;
	}
	/**
	 * Singleton
	 * @return
	 */
	public static Socket getInstance(){
		return socket == null ? new Socket():socket;
	}
	
	public static void close(){
		try {
			if(socket!=null){
				socket.close();
			}
			if(dos!=null){
				dos.close();
			}
			if(bis!=null){
				bis.close();
			}
		} catch (IOException e) {
			logger.error("socket close is exception",e);
			e.printStackTrace();
		}finally{
			socket=null;
			dos=null;
			bis=null;
		}
	}
}


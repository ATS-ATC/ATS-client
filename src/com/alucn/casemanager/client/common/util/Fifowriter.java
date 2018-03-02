package com.alucn.casemanager.client.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import net.sf.json.JSONArray;
import com.alucn.casemanager.client.constants.Constants;

public class Fifowriter {
	
	public static void writerFile(String filePath, String fileName, String caseList, int size) throws IOException{
		JSONArray writeSize = new  JSONArray();
		File file = new File(filePath+File.separator+fileName);
		if(!(file.exists() && file.isFile())){
			file.createNewFile();
		}
		if(!caseList.equals("")){
			JSONArray tmp = JSONArray.fromObject(caseList);
			int len = tmp.size();
			if(size>len){
				size = len;
			}
			for(int i=0; i<size; i++){
				writeSize.add(tmp.getString(i));
			}
		}else{
			writeSize.add(caseList);
		}
		
		
		FileOutputStream outputStream = null;
		OutputStreamWriter osw = null;
		try {
			outputStream = new FileOutputStream(filePath+File.separator+fileName);
			osw = new OutputStreamWriter(outputStream,Constants.CHARACTER_SET_ENCODING_UTF8);
			osw.write(writeSize.toString());
			osw.flush();
		} finally{
			if(osw != null){
				osw.close();
			}
			if(outputStream != null){
				outputStream.close();
			}
		}
		
	}
	
	public static void writerFile(String filePath, String content) throws IOException{
		File file = new File(filePath);
		if(!(file.exists() && file.isFile())){
			file.createNewFile();
		}
		
		FileOutputStream outputStream = null;
		OutputStreamWriter osw = null;
		try {
			outputStream = new FileOutputStream(filePath);
			osw = new OutputStreamWriter(outputStream,Constants.CHARACTER_SET_ENCODING_UTF8);
			osw.write(content);
			osw.flush();
		} finally{
			if(osw != null){
				osw.close();
			}
			if(outputStream != null){
				outputStream.close();
			}
		}
	}
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		if(args.length != 2){
			System.out.println("参数错误");
			return;
		}
		FileOutputStream outputStream = new FileOutputStream(args[0]);
		outputStream.write( "{\"head\":{\"clientip\":\"135.252.164.178\",\"reqtype\":\"update\",\"result\":\"\",\"status\":{\"serverName\":\"CERTIFIED01\",\"ip\":\"135.252.164.178\",\"status\":\"dead\",\"release\":\"SP17.3\",\"protocol\":\"ANSI\",\"SPA\":[\"NWTGSM\",\"NWTCOM\",\"ENWTPPS\",\"EPAY\"],\"RTDB\":[\"SIMDB\",\"ACMDB\",\"GPRSSIM\"],\"RunningCase\":\"30/65  fr6572.json\"},\"runningcase\":\"58/65\"},\"body\":{}}".getBytes());
//		outputStream.write("".getBytes());
		outputStream.flush();
		Thread.sleep(Integer.parseInt(args[1]));
		outputStream.close();
	}
}

package com.alucn.casemanager.client.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;

public class Fiforeader {
	public static String readCaseInfoFromChannel(String fifoPath) throws NumberFormatException, InterruptedException, IOException{
		FileInputStream inputStream = new FileInputStream(fifoPath);
		String result=null;
		byte[] bs = new byte[1024];
		int n = 0;
		while ((n = inputStream.read(bs)) != -1) {
			result = new String(bs, 0, n);
		}
		inputStream.close();
		return result;
	}
	
	public static String readLastLine(String filePath) throws IOException {
		  File file = new File(filePath);
		  if (!file.exists() || file.isDirectory() || !file.canRead()) {  
		    return null;  
		  }  
		  RandomAccessFile raf = null;  
		  try {  
		    raf = new RandomAccessFile(file, "r");  
		    long len = raf.length();  
		    if (len == 0L) {  
		      return "";  
		    } else {  
		      long pos = len - 1;  
		      while (pos > 0) {  
		        pos--;  
		        raf.seek(pos);  
		        if (raf.readByte() == '\n') {  
		          break;  
		        }  
		      }  
		      if (pos == 0) {  
		        raf.seek(0);  
		      }  
		      byte[] bytes = new byte[(int) (len - pos)];  
		      raf.read(bytes);  
		        return new String(bytes);  
		    }  
		  } catch (FileNotFoundException e) {  
		     e.printStackTrace();
		  } finally {  
		    if (raf != null) {  
		      try {  
		        raf.close();  
		      } catch (Exception ea) { 
		          ea.printStackTrace(); 
		      }  
		    }  
		  }  
		  return null;
	}
	
	
	 public static String readFileByChars(String fileName) {
	        Reader reader = null;
	        String context = "";
	        try {
	            // 一次读多个字符
	            char[] tempchars = new char[30];
	            int charread = 0;
	            reader = new InputStreamReader(new FileInputStream(fileName));
	            while ((charread = reader.read(tempchars)) != -1) {
	                if ((charread == tempchars.length)
	                        && (tempchars[tempchars.length - 1] != '\r')) {
	                	context+=String.valueOf(tempchars);
	                } else {
	                    for (int i = 0; i < charread; i++) {
	                        if (tempchars[i] == '\r') {
	                            continue;
	                        } else {
	                        	context+=tempchars[i];
	                        }
	                    }
	                }
	            }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
			return context;
	    }
}

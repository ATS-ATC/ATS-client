package com.alucn.casemanager.client.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.python.core.PyFunction;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class PythonUtil {

	public static void execStat(String stat){
		PythonInterpreter interpreter = new PythonInterpreter();  
	    interpreter.exec(stat);  
	}
	
	public static void execFileMethod(String filePath, String mehtodName,String pyFilePath,String caseListFile){
		PythonInterpreter interpreter = new PythonInterpreter();  
        interpreter.execfile(filePath);  
        PyFunction func = (PyFunction) interpreter.get(mehtodName, PyFunction.class);  
  
        func.__call__(new PyString(pyFilePath),new PyString(caseListFile));  
	}
	
	
	public static boolean exec(String command) throws InterruptedException, IOException{
		String[] cmd = {
				"/bin/sh",
				"-c",
				command
		};
		Process process = Runtime.getRuntime().exec(cmd);
		int result = process.waitFor();
		if (process != null) {
			process.destroy();
	    }
		if(0 != result){
			return false;
		}else{
			return true;
		}
		
	}
	public static boolean getProcess(String jName) throws IOException{
		String[] cmd = {
				"/bin/sh",
				"-c",
				"ps -ef | grep "+jName
				};
		boolean flag=false;
		Process p = Runtime.getRuntime().exec(cmd);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream os = p.getInputStream();
		byte b[] = new byte[256];
		while(os.read(b)> 0)
			baos.write(b);
		String s = baos.toString().replaceAll("grep "+jName, "");
		if(s.indexOf(jName) >= 0){
			flag=true;
		}else{
			flag=false;
		}
		return flag;
	}
	public static void main(String[] args) throws InterruptedException, IOException {
		exec(args[0]+" >> test.log &");
//		exec("echo 1111 >> test.log");
	}
}

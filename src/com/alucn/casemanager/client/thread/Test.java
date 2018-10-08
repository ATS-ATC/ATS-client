package com.alucn.casemanager.client.thread;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Test {
	public static final BlockingQueue<String> sendMessageBlockingQueue = new ArrayBlockingQueue<String>(2, false);
	
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
	public static JSONObject reqUrl(String httpUrl) {
		JSONObject infos = new JSONObject();
		URL url;
		try {
			url = new URL(httpUrl);
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader reader = null;
			String tempLine, response = "";
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			System.out.println(connection.getResponseCode() == HttpURLConnection.HTTP_OK);
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = connection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);
				while ((tempLine = reader.readLine()) != null) {
					response += tempLine;
				}
			}
			infos = JSONObject.fromObject(response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return infos;
	}
	
	
	public static String reqUrl(String httpUrl, String data) {
		URL url;
		String response = "";
		try {
			url = new URL(httpUrl);
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader reader = null;
			String tempLine;
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", " application/json");
			connection.setDoOutput(true);
			OutputStream out = connection.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = connection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);
				while ((tempLine = reader.readLine()) != null) {
					response += tempLine;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		JSONArray test = new JSONArray();
		JSONObject test1 = JSONObject.fromObject("{'a':'b'}");
		JSONObject test2 = JSONObject.fromObject("{'a':'c'}");
		JSONObject test3 = JSONObject.fromObject("{'a':'s'}");
		JSONObject test4 = JSONObject.fromObject("{'a':'f'}");
		test.add(test1);
		test.add(test2);
		test.add(test3);
//		System.out.println(test.toString());
		test.set(2, test4);
//		System.out.println(test.toString());
		
//		String reqData = "{\"protocol\": \"ITU\", \"labname\": [\"CHSP05B\"], \"DB\": [\"TIDDB\", \"CDBRTDB\", \"HTSIDB\", \"HTRTDB\", \"SCRRTDB\", \"FSNDB\", \"RCNRDB\", \"HMRTDB\", \"ACMDB\", \"GPRSSIM\", \"VTXDB\", \"AIRTDB\", \"UARTDB\", \"CTRTDB\", \"UIRTDB\", \"SIMDB\", \"SGLDB\"], \"mate\": \"N\", \"release\": \"SP18.9\", \"SPA\": [\"NWTGSM\", \"DROUTER\", \"AETHOSTEST\", \"DIAMCL\", \"EPAY\", \"ENWTPPS\", \"NWTCOM\", \"EPPSM\", \"EPPSA\", \"GATEWAY\"]}";
		
		
		System.out.println(reqUrl("http://135.251.249.124:9333/spadm/default/labapi/dailylab/CHSP12C.json"));
//		System.out.println(reqUrl("http://135.251.249.124:9333/spadm/default/certapi/certtask.json?data="+reqData));
//		System.out.println(reqUrl("http://135.251.249.124:9333/spadm/default/certapi/certtask.json", "{\"a\":\"b\"}"));
		
		/*String result = "{u'protocl': u'ITU', u'lab_number': u'1', u'DB': [u'CDBRTDB', u'CTRTDB', u'UARTDB', u'HTRTDB', u'SHRTDB', u'FSNDB', u'SFFDB', u'HMRTDB', u'ACMDB', u'GPRSSIM', u'SSDDB', u'TIDDB', u'VTXDB', u'AIRTDB', u'HTSIDB', u'UIRTDB', u'DSCDB', u'SIMDB', u'SGLDB'], u'case_nums': 99, u'mate': u'N', u'release': u'SP17.3', u'SPA': [u'NWTGSM', u'DROUTER', u'DIAMCL', u'EPAY', u'ENWTPPS', u'EPPSM', u'NWTCOM', u'GATEWAY']}";
		JSONObject resultCaseDepends = JSONObject.fromObject(result.replace("u'", "'"));
		System.out.println(resultCaseDepends.getString("SPA"));
	
		Thread tt = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String[] cmd = new String[] { "/bin/sh", "-c", "python /home/huanglei/DB/QueryCaseDepends.py /home/huanglei/DB/caseinfo.db aaaaa" };
	            Process ps = null;
				try {
					ps = Runtime.getRuntime().exec(cmd);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
	            StringBuffer sb = new StringBuffer();
	            String line;
	            try {
					while ((line = br.readLine()) != null) {
					    sb.append(line);
					}
					System.out.println(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		tt.start();*/
		/*if(getProcess(args[0])){
			String[] cmd = new String[] { "/bin/sh", "-c", "sh /home/huanglei/ATC_"+args[0]+"/start.sh"};
            Runtime.getRuntime().exec(cmd);
		}
		System.out.println(!getProcess(args[0]));*/
	}
  	
}

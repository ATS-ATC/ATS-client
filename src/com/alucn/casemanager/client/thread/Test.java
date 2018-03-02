package com.alucn.casemanager.client.thread;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Test {
	public static final BlockingQueue<String> sendMessageBlockingQueue = new ArrayBlockingQueue<String>(2, false);
	
	public static void main(String[] args) throws IOException, InterruptedException {
		JSONArray test = new JSONArray();
		JSONObject test1 = JSONObject.fromObject("{'a':'b'}");
		JSONObject test2 = JSONObject.fromObject("{'a':'c'}");
		JSONObject test3 = JSONObject.fromObject("{'a':'s'}");
		JSONObject test4 = JSONObject.fromObject("{'a':'f'}");
		test.add(test1);
		test.add(test2);
		test.add(test3);
		System.out.println(test.toString());
		test.set(2, test4);
		System.out.println(test.toString());
	}
	
}

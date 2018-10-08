package com.alucn.casemanager.client.common.util;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author wanghaiqi
 */
public class AutoIncrement {
	
	private static AtomicInteger increment = new AtomicInteger(0);
	
	private AutoIncrement(){

	}

	public static AtomicInteger getAutoIncrement(){
		if(null == increment || increment.intValue() == 1000){
			increment.set(0);
		}
		increment.addAndGet(1);
		return increment;
	}
}

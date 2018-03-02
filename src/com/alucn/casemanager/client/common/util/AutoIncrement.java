package com.alucn.casemanager.client.common.util;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 1、自动增长，定值置零（1000）
 * @author wanghaiqi
 *单例
 */
public class AutoIncrement {
	
	private static AtomicInteger increment = new AtomicInteger(0);
	
	private AutoIncrement(){

	}

	public static AtomicInteger getAutoIncrement(){
		if(increment==null||increment.intValue()==1000){
			increment.set(0);
		}
		increment.addAndGet(1);
		return increment;
	}
}

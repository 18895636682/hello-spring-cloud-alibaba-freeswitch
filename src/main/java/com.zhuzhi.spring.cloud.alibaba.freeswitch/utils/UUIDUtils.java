package com.zhuzhi.spring.cloud.alibaba.freeswitch.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class UUIDUtils {

	public static String findUUID(){
		return UUID.randomUUID().toString().replaceAll("\\-", "").toUpperCase();
	}
	
	public static void bulidUUID(int size){
		for (int j = 0; j < size; j++) {
			log.error(findUUID());
		}
	}
	
	public static void main(String[] args) {
		UUIDUtils.bulidUUID(1);
	}
}

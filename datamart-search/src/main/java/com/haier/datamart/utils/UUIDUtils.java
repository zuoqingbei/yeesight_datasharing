package com.haier.datamart.utils;

import java.util.UUID;

public class UUIDUtils {
	public static String getUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String get4UUID() {
		UUID id = UUID.randomUUID();
		String[] idd = id.toString().split("-");
		return idd[1];
	}

	/**
	 * 获得8个长度的十六进制的UUID
	 * 
	 * @return UUID
	 */
	public static String get8UUID() {
		UUID id = UUID.randomUUID();
		String[] idd = id.toString().split("-");
		return idd[0];
	}

	/**
	 * 获得12个长度的十六进制的UUID
	 * 
	 * @return UUID
	 */
	public static String get12UUID() {
		UUID id = UUID.randomUUID();
		String[] idd = id.toString().split("-");
		return idd[0] + idd[1];
	}
}

package com.breeze.eapp.utils;

public class Util {
	/**
	 * 判断第一个字符串中是否含有第二个字符串中不存在的内容
	 * 用于判断多选题是否选了错误选项
	 * @param a 用户已选串
	 * @param b 答案串
	 * @return
	 */
	public static boolean aHaveBNotExist(String a, String b) {
		int length = a.length();
		for (int i = 0; i < length; i++) {
			String tmp = a.substring(i, i + 1);
			if(b.indexOf(tmp) < 0) {
				return true;
			}
		}
		return false;
	}
}

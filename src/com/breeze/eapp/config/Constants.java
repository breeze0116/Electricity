package com.breeze.eapp.config;

import java.util.Map;

import android.os.Environment;


public class Constants {
	/**
	 ******************************************* 参数设置信息开始 ******************************************
	 */
	//日志TAG
	public final static String DEBUG_TAG = "EAPP_DEBUG";
	// 应用名称
	public final static String APP_NAME = "";
	//检测更新接口
	public final static String APP_UPDATE_URL = "http://192.168.1.188/eapp/appUpdate.php";
	//版本更新服务器返回数据
	public static Map<String, String> APP_UPDATE_RES_DATA = null;

	// SDCard路径
	public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

	// 文件保存路径
	public static final String BASE_PATH = SD_PATH + "/breeze/";

	// APP自动更新下载路径
	public static final String APP_DOWN_PATH = BASE_PATH + "update/";
	
	//题目下载路径
	public static final String QUESTION_DOWN_PATH = BASE_PATH + "question/";
	
	// 手机IMEI号码
	public static String IMEI = "";

	// 手机号码
	public static String TEL = "";

	// 屏幕高度
	public static int SCREEN_HEIGHT = 800;

	// 屏幕宽度
	public static int SCREEN_WIDTH = 480;

	// 屏幕密度
	public static float SCREEN_DENSITY = 1.5f;
}

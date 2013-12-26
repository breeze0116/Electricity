package com.breeze.eapp.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {
	private DBHelper dbHelper;
	
	/**
	 * 构造方法
	 * @param context
	 */
	public DBHandler(Context context) {
		this.dbHelper = new DBHelper(context);
	}
	
	/**
	 * 获取写数据库操作对象
	 * @param @return 
	 * @return SQLiteDatabase
	 */
	public SQLiteDatabase getWritableDatabase() {
		return dbHelper.openDatabase();
	}
	
	/**
	 * 获取读数据库操作对象
	 * @param @return 
	 * @return SQLiteDatabase
	 */
	public SQLiteDatabase getReadableDatabase() {
		return dbHelper.openDatabase();
	}
	
	/**
	 * 关闭数据库资源
	 * @param @param db 
	 * @return void
	 */
	public void closeDB(SQLiteDatabase db) {
//		if(db != null) {
//			try {
//				db.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	/**
	 * 修改数据
	 * sql 中可以用点位符，objects 参数为点位符填充值
	 * @param sql
	 * @param objects
	 * @return
	 */
	public void update(String sql,Object[] bindArgs) throws Exception {
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL(sql, bindArgs);
		} catch (Exception e) {
			throw e;
		}
		closeDB(db);
	}
	
	/**
	 * 添加数据
	 * sql 中可以用点位符，objects 参数为点位符填充值
	 * @param sql
	 * @param objects
	 * @throws Exception 
	 */
	public void save(String sql,Object[] objects) throws Exception {
		update(sql, objects);
	}
	
	/**
	 * 删除数据
	 * sql 中可以用点位符，objects 参数为点位符填充值
	 * @param sql
	 * @param objects
	 * @throws Exception 
	 */
	public void delete(String sql,Object[] objects) throws Exception {
		update(sql, objects);
	}
	
	/**
	 * 修改数据
	 * @param @param db
	 * @param @param sql
	 * @param @param objects 
	 * @return void
	 */
	public void update(SQLiteDatabase db, String sql,Object[] objects) throws Exception {
		try {
			db.execSQL(sql, objects);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 添加数据
	 * @param @param db
	 * @param @param sql
	 * @param @param objects 
	 * @return void
	 * @throws Exception 
	 */
	public void save(SQLiteDatabase db, String sql,Object[] objects) throws Exception {
		update(db, sql, objects);
	}
	
	/**
	 * 删除数据
	 * @param @param db
	 * @param @param sql
	 * @param @param objects
	 * @param @param columns 
	 * @return void
	 * @throws Exception 
	 */
	public void delete(SQLiteDatabase db, String sql,Object[] objects) throws Exception {
		update(db, sql, objects);
	}
	
	/**
	 * 获取一条数据
	 * @param @param sql
	 * @param @param selectionArgs
	 * @param @param columns
	 * @param @return 
	 * @return String[]
	 */
	public String[] getOne(String sql,String[] selectionArgs,String columns[]) {
		String[] result = new String[columns.length];
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		if(cursor.moveToFirst()) {
			for (int i = 0; i < columns.length; i++) {
				result[i] = cursor.getString(cursor.getColumnIndex(columns[i]));
			}
		}
		if(cursor != null)
			cursor.close();
		closeDB(db);
		return result;
	}
	
	/**
	 * 获取多条数据
	 * @param @param sql
	 * @param @param selectionArgs
	 * @param @param columns
	 * @param @return 
	 * @return String[]
	 */
	public List<String[]> getList(String sql,String[] selectionArgs,String columns[]) {
		List<String[]> list = new ArrayList<String[]>();
		String[] result = new String[columns.length];
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		while(cursor.moveToNext()) {
			for (int i = 0; i < columns.length; i++) {
				result[i] = cursor.getString(cursor.getColumnIndex(columns[i]));
			}
			list.add(result);
		}
		if(cursor != null)
			cursor.close();
		closeDB(db);
		return list;
	}
	
	/**
	 * 查询数据，返回游标
	 * @param @param sql
	 * @param @param selectionArgs
	 * @param @return 
	 * @return Cursor
	 */
	public Cursor getData(String sql,String[] selectionArgs) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		closeDB(db);
		return cursor;
	}
	
	/**
	 * 查询数据一条记录，返回指定个数字段Map集合
	 * @param @param sql
	 * @param @param selectionArgs
	 * @param @param resultNum
	 * @param @return 
	 * @return Map
	 */
	public Map getMap(String sql,String[] selectionArgs, int resultNum) {
		Map<String, String> dataMap = new HashMap<String, String>();
		Cursor cursor = getData(sql, selectionArgs);
		if(cursor.moveToFirst()) {
			for(int i = 0; i< resultNum; i ++) {
				dataMap.put(cursor.getColumnName(i), cursor.getString(i));
			}
		}
		if(cursor != null)
			cursor.close();
		return dataMap;
	}
	
	/**
	 * 查询一条记录，返回全部字段Map集合
	 * @param @param sql
	 * @param @param selectionArgs
	 * @param @return 
	 * @return Map
	 */
	public Map getMap(String sql,String[] selectionArgs) {
		Map<String, String> dataMap = new HashMap<String, String>();
		Cursor cursor = getData(sql, selectionArgs);
		int columnCount = cursor.getColumnCount();//总字段个数
		if(cursor.moveToFirst()) {
			for(int i = 0; i< columnCount; i ++) {
				dataMap.put(cursor.getColumnName(i), cursor.getString(i));
			}
		}
		if(cursor != null)
			cursor.close();
		return dataMap;
	}
	
	/**
	 * 查询多条记录，返回指定字段个数 List<Map> 集合
	 * @param @param sql
	 * @param @param selectionArgs
	 * @param @param resultNum
	 * @param @return 
	 * @return List<Map>
	 */
	public List<Map> getListForMap(String sql,String[] selectionArgs, int resultNum) {
		List<Map> dataList = new ArrayList<Map>();
		Map<String, String> dataMap;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = getData(sql, selectionArgs);
		while(cursor.moveToNext()) {
			dataMap = new HashMap<String, String>();
			for(int i = 0; i< resultNum; i ++) {
				dataMap.put(cursor.getColumnName(i), cursor.getString(i));
			}
			dataList.add(dataMap);
		}
		if(cursor != null)
			cursor.close();
		return dataList;
	}
	
	/**
	 * 查询多条记录，返回全部字段 List<Map> 集合
	 * @param @param sql
	 * @param @param selectionArgs
	 * @param @return 
	 * @return List<Map>
	 */
	public List<Map> getListForMap(String sql,String[] selectionArgs) {
		List<Map> dataList = new ArrayList<Map>();
		Map<String, String> dataMap;
		Cursor cursor = getData(sql, selectionArgs);
		int columnCount = cursor.getColumnCount();//总字段个数
		while(cursor.moveToNext()) {
			dataMap = new HashMap<String, String>();
			for(int i = 0; i< columnCount; i ++) {
				dataMap.put(cursor.getColumnName(i), cursor.getString(i));
			}
			dataList.add(dataMap);
		}
		if(cursor != null)
			cursor.close();
		return dataList;
	}
}

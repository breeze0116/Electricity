package com.breeze.eapp.config;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.breeze.eapp.db.DBHandler;
import com.breeze.eapp.entity.AnswerEntity;
import com.breeze.eapp.entity.TopicEntity;

/**
 * 用户数据
 * @author breeze
 *
 */
public class UserInfo {
	public static enum Mode {
		RANDOM,STAGE
	}
	
	//答题模式
	public static Mode UserMode;
	
	//题库
	public static List<TopicEntity> ExamList = new ArrayList<TopicEntity>();
	
	//当前题目序号
	public static int CurrTopicNo = 0;
	
	//答错的题
	public static List<Integer> ErrorList = new ArrayList<Integer>();
	
	//答对的题
	public static List<Integer> RightList = new ArrayList<Integer>();
	
	//当前生命值
	public static int HP = 0;
	
	//积分
	public static 	int Integral = 0;
	
	public static void initInfo() {
		UserMode = null;
		ExamList.clear();
		ErrorList.clear();
		RightList.clear();
		CurrTopicNo = 0;
		HP = 0;
		Integral = 0;
	}
	
	/**
	 * 随机模式设置
	 * @param context
	 */
	public static void setModeWithRandom(Context context) {
		UserInfo.initInfo();
		UserMode = Mode.RANDOM;
		
		DBHandler dbHandler = new DBHandler(context);
		Cursor cursor = dbHandler.getData("select * from question order by random() limit 100",null);
		TopicEntity topicEntity;
		int index = 0;
		while(cursor.moveToNext()) {
			topicEntity = new TopicEntity();
			topicEntity.id = cursor.getInt(cursor.getColumnIndex("qid"));
			topicEntity.index = index;
			topicEntity.image = "demo_0.jpg";
			topicEntity.title = cursor.getString(cursor.getColumnIndex("question"));
			topicEntity.result = cursor.getString(cursor.getColumnIndex("answer")).replaceAll(",", "");
			topicEntity.tip = cursor.getString(cursor.getColumnIndex("remark"));
			topicEntity.isAnswer = false;//初始未做答状态
			//题目类型
			switch (cursor.getInt(cursor.getColumnIndex("type"))) {
			case 1:
				topicEntity.type = TopicEntity.TopicType.SINGLE_CHOICE;//单选题
				break;
			case 2:
				topicEntity.type = TopicEntity.TopicType.MULTIPLE_CHOICE;//多选题
				break;
			case 3:
				topicEntity.type = TopicEntity.TopicType.JUDGE;//判断题
				break;
			}
			//解析答案选项
			String option = cursor.getString(cursor.getColumnIndex("option"));
			//非判断题
			if(option != null && topicEntity.type != TopicEntity.TopicType.JUDGE) {
				//String[] options = option.split("[A-Z]、");
				String[] options = option.split("\\[option\\]");
				for (int i = 0; i < options.length; i++) {
					if(!options[i].trim().equals("")) {
						AnswerEntity answer = new AnswerEntity();
						answer.content = options[i].trim();
						topicEntity.answers.add(answer);
					}
				}
			}else {
				//判断题
				for(int j=0;j<2;j++){
					AnswerEntity answer = new AnswerEntity();
					answer.content = "";
					topicEntity.answers.add(answer);
				}
			}
			ExamList.add(index, topicEntity);
			index++;
		}
		if(cursor != null)
			cursor.close();
		dbHandler.closeDB();
	}
	
	/**
	 * 关卡模式设置
	 * @param context
	 */
	public static void setModeWithStage(Context context,int stage) {
		UserInfo.initInfo();
		UserMode = Mode.RANDOM;
		
		DBHandler dbHandler = new DBHandler(context);
		Cursor cursor = dbHandler.getData("select * from question where stage = " + stage + " order by random() limit 100",null);
		TopicEntity topicEntity;
		int index = 0;
		while(cursor.moveToNext()) {
			topicEntity = new TopicEntity();
			topicEntity.id = cursor.getInt(cursor.getColumnIndex("qid"));
			topicEntity.index = index;
			topicEntity.image = "demo_0.jpg";
			topicEntity.title = cursor.getString(cursor.getColumnIndex("question"));
			topicEntity.result = cursor.getString(cursor.getColumnIndex("answer")).replaceAll(",", "");
			topicEntity.tip = cursor.getString(cursor.getColumnIndex("remark"));
			topicEntity.isAnswer = false;//初始未做答状态
			//题目类型
			switch (cursor.getInt(cursor.getColumnIndex("type"))) {
			case 1:
				topicEntity.type = TopicEntity.TopicType.SINGLE_CHOICE;//单选题
				break;
			case 2:
				topicEntity.type = TopicEntity.TopicType.MULTIPLE_CHOICE;//多选题
				break;
			case 3:
				topicEntity.type = TopicEntity.TopicType.JUDGE;//判断题
				break;
			}
			//解析答案选项
			String option = cursor.getString(cursor.getColumnIndex("option"));
			//非判断题
			if(option != null && topicEntity.type != TopicEntity.TopicType.JUDGE) {
				//String[] options = option.split("[A-Z]、");
				String[] options = option.split("\\[option\\]");
				for (int i = 0; i < options.length; i++) {
					if(!options[i].trim().equals("")) {
						AnswerEntity answer = new AnswerEntity();
						answer.content = options[i].trim();
						topicEntity.answers.add(answer);
					}
				}
			}else {
				//判断题
				for(int j=0;j<2;j++){
					AnswerEntity answer = new AnswerEntity();
					answer.content = "";
					topicEntity.answers.add(answer);
				}
			}
			ExamList.add(index, topicEntity);
			index++;
		}
		if(cursor != null)
			cursor.close();
		dbHandler.closeDB();
	}
	
}

/**
 * 
 */
package com.breeze.eapp;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-2-24
 */
public final class AppSetting {
	public final static String APP_TAG = "TopicBank";
	public final static String TOPIC_INDEX = "第%d题(%s)";
	public final static String MENU_KEY = "VALUE";
	public final static String MENU_TITLE = "TITLE";
	
	public final static String TOPIC_BACK_TIP = "您当前正在考试，确定返回目录界面吗？";
	public final static long[] VIRATOR_PARAMS = new long[] { 10, 50 };
	public final static class AnswerIcons{
		
		/**
		 * 单选
		 * 
		 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
		 * @date 2012-3-6
		 */
		public final static class SingleChoice{
			public final static int[] PRESSED = new int[]{
				R.drawable.answer_a_single_normal,
				R.drawable.answer_b_single_normal,
				R.drawable.answer_c_single_normal,
				R.drawable.answer_d_single_normal,
				R.drawable.answer_e_single_normal,
				R.drawable.answer_f_single_normal,
				R.drawable.answer_g_single_normal
			};
			
			public final static int[] NORMAL_ARRAY = new int[]{
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected,
				R.drawable.answer_single_selected
			};
		}
		
		/**
		 * 多选
		 * 
		 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
		 * @date 2012-3-6
		 */
		public final static class MultipleChoice{
			public final static int[] PRESSED = new int[]{
				R.drawable.answer_a,
				R.drawable.answer_b,
				R.drawable.answer_c,
				R.drawable.answer_d,
				R.drawable.answer_e,
				R.drawable.answer_f,
				R.drawable.answer_g
			};
			
			public final static int[] NORMAL_ARRAY= new int[]{
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select,
				R.drawable.answer_multiple_select
			};
			
			//添加或删除多选项
			public static void setMultipleResult(int index,boolean isAdd) {
				if( isAdd )
					MultipleResult.add(RESULT[index]);
				else 
					MultipleResult.remove(RESULT[index]);
			}
			//返回答案字符串
			public static String getResultStr() {
				String str = "";
				for(String value : MultipleResult){  
					str += value;
		        } 
				return str;
			}
			//清除多选项容器
			public static void cleanMultipleResult() {
				MultipleResult.clear();
			}
		}
		
		/**
		 * 判断题
		 * 
		 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
		 * @date 2012-3-6
		 */
		public final static class Judge{
			public final static int[] NORMAL_ARRAY = new int[]{
				R.drawable.answer_judge_true_normal,
				R.drawable.answer_judge_false_normal
			};
			
			public final static int[] PRESSED_ARRAY = new int[]{
				R.drawable.answer_judge_true_selected,
				R.drawable.answer_judge_false_selected
			};
		}
		
	}
	//用于答案选项判断
	public final static String[] RESULT = new String[] {
		"A","B","C","D","E","F","G","H","I","J","K"
	};
	
	public static TreeSet<String> MultipleResult = new TreeSet<String>();
}

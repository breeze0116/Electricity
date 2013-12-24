/**
 * Copyright (C) 2012  TopicBankEx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.breeze.eapp.res;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.breeze.eapp.entity.AnswerEntity;
import com.breeze.eapp.entity.TopicEntity;
import com.lurencun.android.toolkit.HasContext;

/**
 *题目详细页 
 * 
 */
public class TopicLoader extends HasContext {

	/**
	 * @param context
	 */
	public TopicLoader(Context context) {
		super(context);
	}
	
	public List<TopicEntity> load(){
		int size = 100;
		List<TopicEntity> list = new ArrayList<TopicEntity>();
		for(int i=0;i<size;i++){
			TopicEntity topic = new TopicEntity();
			topic.index = i;
			
			
			int answerCount = 4;//答案个数
			String title = null,result = null,tip = null;
			if(i<3){
				 //单选题
				topic.image = "demo_0.jpg" ;
				topic.type = TopicEntity.TopicType.SINGLE_CHOICE;
				title = " 带电作业或与邻近带电设备距离小于表2－1规定的工作，应填用（    ）工作票。";
				result = "C";
				tip = "答案：C        变规3.2.4";
				
				for(int j=0;j<answerCount;j++){
					AnswerEntity answer = new AnswerEntity();
					switch (j) {
					case 0:
						answer.content = "第一种";
						break;
					case 1:
						answer.content = "第二种 ";
						break;
					case 2:
						answer.content = "带电作业";
						break;
					default:
						answer.content = "答案选项";
						break;
					}
					topic.answers.add(answer);
				}
				
			}else if(i>=3 && i<6){
				//多选题
				topic.image = "demo_1.jpg" ;
				topic.type = TopicEntity.TopicType.MULTIPLE_CHOICE;
				title = "在保护盘附近工作，为避免盘上的保护装置误动作，必要时可提出申请，经（    ）同意后将保护暂时停用。";
				result = "AD";
				tip = "答案：A，D         变规10.9";
				
				for(int j=0;j<answerCount;j++){
					AnswerEntity answer = new AnswerEntity();
					switch (j) {
					case 0:
						answer.content = "值班调度员";
						break;
					case 1:
						answer.content = "工作票签发人";
						break;
					case 2:
						answer.content = "变电站长";
						break;
					default:
						answer.content = "运行值班负责人";
						break;
					}
					topic.answers.add(answer);
				}
				
			}else{
				//判断
				answerCount = 2;
				topic.image = "demo_2.jpg" ;
				topic.type = TopicEntity.TopicType.JUDGE;
				
				title = "低压带电作业断开导线时，应先断开零线，后断开相线。搭接导线时，顺序应相反。";
				result = "0";
				tip = "答案：×         变规6.13.4";
				
				for(int j=0;j<answerCount;j++){
					AnswerEntity answer = new AnswerEntity();
					answer.content = "";
					topic.answers.add(answer);
				}
			}
			topic.title = title;
			topic.tip = tip;
			topic.result = result;
			list.add(topic);
		}
		return list;
	}

}

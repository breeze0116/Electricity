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
package com.breeze.eapp.entity;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-3-6
 */
public class TopicEntity {

	public enum TopicType{
		SINGLE_CHOICE,
		MULTIPLE_CHOICE,
		JUDGE 
	};
	
	public int index;
	public int id;
	public TopicType type;
	public String title;
	public String image;
	public String tip;
	public List<AnswerEntity> answers = new ArrayList<AnswerEntity>();
	public String result;
	public boolean isAnswer;//是否已做答
}

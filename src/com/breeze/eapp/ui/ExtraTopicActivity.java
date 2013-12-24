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
package com.breeze.eapp.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.breeze.eapp.AppSetting;
import com.breeze.eapp.R;
import com.breeze.eapp.entity.TopicEntity;
import com.breeze.eapp.res.ExtraTopicAdapter;
import com.breeze.eapp.res.TopicLoader;
import com.lurencun.android.support.ui.BackUIActivity;
import com.lurencun.android.support.v2.widget.ViewPager;
import com.lurencun.android.support.v2.widget.ViewPagerAdapter;

/**
 * 
 * @author cfuture.chenyoca [桥下一粒砂] (chenyoca@163.com)
 * @date 2012-3-6
 */
public class ExtraTopicActivity extends BackUIActivity {

	@Override
	protected void overridePendingTransition(Activity activity){
		activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@Override
	protected void onCreateEx(Bundle savedInstanceState) {
		ViewPager container = (ViewPager) findViewById(R.id.topic_container);
		List<TopicEntity> data = new TopicLoader(this).load();
		ViewPagerAdapter<TopicEntity> adapter = new ExtraTopicAdapter(this);
		adapter.setData(data);
		container.setAdapter(adapter);
	}

	@Override
	protected int getContentViewLayoutId() {
		return R.layout.topicex;
	}

	@Override
	protected int getBackButtonResId() {
		return R.id.back_button;
	}

	@Override
	protected boolean isConfirmBack() {
		return true;
	}
	
	@Override
	protected String getTipMessage(){
		return AppSetting.TOPIC_BACK_TIP;
	}
	
	
	
}

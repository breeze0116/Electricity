package com.breeze.eapp.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.breeze.eapp.R;
import com.breeze.eapp.base.BaseActivity;
import com.breeze.eapp.config.UserInfo;

public class IndexActivity extends BaseActivity{
	private Button randomButton;
	private Button stageButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_index);
		this.bindDoubleClickExit();
		findViewById();
		initView();
	}
	
	@Override
	protected void findViewById() {
		randomButton = (Button) findViewById(R.id.button_module_r);
		stageButton = (Button) findViewById(R.id.button_module_s);
	}

	@Override
	protected void initView() {
		randomButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//随机模式
				UserInfo.setModeWithRandom(IndexActivity.this);
				openActivity(ExtraTopicActivity.class);
			}
		});
		
		stageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//关卡模式
				openActivity(MenuActivity.class);
			}
		});
	}
}

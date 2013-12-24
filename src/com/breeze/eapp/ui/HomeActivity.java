package com.breeze.eapp.ui;

import com.breeze.eapp.R;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * @author liuqf
 * 
 */
public class HomeActivity extends TabActivity implements OnCheckedChangeListener {
	private RadioGroup mainTab;
	private TabHost mTabHost;
	
	private static HomeActivity homeActivity = null;

	// 内容Intent
	private Intent mCategoryIntent;
	private Intent mHomeIntent;
	private Intent mFavoIntent;
	private Intent mSearchIntent;
	private Intent mMoreIntent;
	
	private final static String TAB_TAG_HOME = "tab_tag_home";//首页
	private final static String TAB_TAG_RANKING = "tab_tag_ranking";//排行榜
	private final static String TAB_TAG_FAVO = "tab_tag_favo";//收藏页
	private final static String TAB_TAG_INFO = "tab_tag_info";//我的信息
	private final static String TAB_TAG_MORE = "tab_tag_more";//更多信息页

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		homeActivity = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		mainTab.setOnCheckedChangeListener(this);
		prepareIntent();
		setupIntent();
	}
	
	 /**
     * 获得主Activity
     * @return
     */
    public static final HomeActivity getActivity(){
    	return homeActivity;
    }

	/**
	 * 准备tab的内容Intent
	 */
	private void prepareIntent() {
		mHomeIntent = new Intent(this,MenuActivity.class);
		mCategoryIntent = new Intent(this, CategoryActivity.class);
		mFavoIntent = new Intent(this, CategoryActivity.class);
		mSearchIntent = new Intent(this, CategoryActivity.class);
		mMoreIntent = new Intent(this, CategoryActivity.class);
	}

	/**
	 * 
	 */
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(buildTabSpec(TAB_TAG_HOME, R.string.tab_home,
				R.drawable.icon_1_n, mHomeIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_RANKING, R.string.tab_ranking,
				R.drawable.icon_2_n, mCategoryIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_FAVO, R.string.tab_favorites,
				R.drawable.icon_3_n, mFavoIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_INFO, R.string.tab_info,
				R.drawable.icon_4_n, mSearchIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_MORE, R.string.tab_more,
				R.drawable.icon_5_n, mMoreIntent));

	}

	/**
	 * 构建TabHost的Tab页
	 * 
	 * @param tag
	 *            标记
	 * @param resLabel
	 *            标签
	 * @param resIcon
	 *            图标
	 * @param content
	 *            该tab展示的内容
	 * @return 一个tab
	 */
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
	}

	/**
	 * 切换
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_button0:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
			break;
		case R.id.radio_button1:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_RANKING);
			break;
		case R.id.radio_button2:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_FAVO);
			break;
		case R.id.radio_button3:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_INFO);
			break;
		case R.id.radio_button4:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_MORE);
			break;
		}
	}
	
	/**
	 * 重写TabActivity onTouchEvent 方法
	 * 否则Tab中的activity不能响应某些事件
	 */
	public boolean onTouchEvent(MotionEvent event) {
            return this.getCurrentActivity().onTouchEvent(event);
    }
	
	// 捕捉返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ConfirmExit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void ConfirmExit() {// 退出确认
		AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
		ad.setTitle("退出");
		ad.setMessage("是否退出软件?");
		ad.setPositiveButton("是", new DialogInterface.OnClickListener() {// 退出按钮
					@Override
					public void onClick(DialogInterface dialog, int i) {
						// TODO Auto-generated method stub
						HomeActivity.this.finish();// 关闭activity

					}
				});
		ad.setNegativeButton("否", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int i) {
				// 不退出不用执行任何操作
			}
		});
		ad.show();// 显示对话框
	}

}

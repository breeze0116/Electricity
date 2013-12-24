package com.breeze.eapp.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.R.bool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.breeze.eapp.R;
import com.breeze.eapp.base.BaseActivity;
import com.breeze.eapp.config.Constants;
import com.breeze.eapp.utils.CommonTools;
import com.breeze.eapp.utils.HttpUtil;

public class MainActivity extends BaseActivity {
	public static final String TAG = MainActivity.class.getSimpleName();

	private ImageView mSplashItem_iv = null;
	private boolean initFinish = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.itau.tmall.ui.base.BaseActivity#findViewById()
	 */
	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		mSplashItem_iv = (ImageView) findViewById(R.id.splash_loading_item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Constants.SCREEN_DENSITY = metrics.density;
		Constants.SCREEN_HEIGHT = metrics.heightPixels;
		Constants.SCREEN_WIDTH = metrics.widthPixels;

		mHandler = new Handler(getMainLooper());
		findViewById();
		initView();
		initWork();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.itau.jingdong.ui.base.BaseActivity#initView()
	 */
	@Override
	protected void initView() {
		Animation translate = AnimationUtils.loadAnimation(this,R.anim.app_loading);
		translate.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				if (initFinish) {
					if (Constants.APP_UPDATE_RES_DATA != null)
						openActivity(UpdateActivity.class);
					else
						openActivity(HomeActivity.class);
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					MainActivity.this.finish();
				}
			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});
		mSplashItem_iv.setAnimation(translate);
	}

	private void initWork() {
		new Thread() {
			public void run() {
				/**********************版本检测**************************/
				sendMsg(0);
				int newVerCode = 0;
				try {
					String res = HttpUtil.doGet(Constants.APP_UPDATE_URL);
					if (res != null) {
						JSONTokener jsonParser = new JSONTokener(res);
						JSONObject person = (JSONObject) jsonParser.nextValue();
						newVerCode = person.getInt("vercode");
						int currVerCode = CommonTools
								.getVersionCode(MainActivity.this);
						if (newVerCode > currVerCode) {
							Log.i(Constants.DEBUG_TAG, "有新版本.....");
							Map<String, String> newVerInfo = new HashMap<String, String>();
							newVerInfo.put("version", newVerCode + "");
							newVerInfo.put("fileurl",person.getString("dowurl"));
							newVerInfo.put("savename", "app_update.apk");
							Constants.APP_UPDATE_RES_DATA = newVerInfo;// 设置版本更新数据
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				sleep(3000);
				/**************************资源更新检测******************************/
				Log.i(Constants.DEBUG_TAG, "资源更新检测.....");
				sendMsg(1);
				sleep(3000);
				initFinish = true;
			}
			
			private void sleep(int t) {
				try {
					Thread.sleep(t);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private void sendMsg(int flag) {
		Message msg = new Message();
		msg.what = flag;
		handler.sendMessage(msg);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
			if (!Thread.currentThread().isInterrupted()) {
				TextView textView = (TextView)findViewById(R.id.loading_alt);
				switch (msg.what) {
				case 0:
					textView.setText(R.string.main_check_update_version);
					break;
				case 1:
					textView.setText(R.string.main_check_update_res);
					break;
				}
			}
			super.handleMessage(msg);
		}
	};
}

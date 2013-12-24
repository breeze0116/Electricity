package com.breeze.eapp.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.breeze.eapp.AppManager;
import com.breeze.eapp.R;
import com.breeze.eapp.config.Constants;
import com.breeze.eapp.image.ImageLoaderConfig;
import com.breeze.eapp.utils.CommonTools;
import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class BaseActivity extends com.lurencun.android.support.ui.BaseActivity {
	public static final String TAG = BaseActivity.class.getSimpleName();

	protected Handler mHandler = null;
	protected ProgressDialog progressDialog = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this);
	}
	/*
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	*/
	/**
	 * 绑定控件id
	 */
	protected abstract void findViewById();

	/**
	 * 初始化控件
	 */
	protected abstract void initView();

	/**
	 * 通过类名启动Activity
	 * 
	 * @param pClass
	 */
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 * 
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * 通过Action启动Activity
	 * 
	 * @param pAction
	 */
	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	/**
	 * 通过Action启动Activity，并且含有Bundle数据
	 * 
	 * @param pAction
	 * @param pBundle
	 */
	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}
	
	protected void DisPlay(String content){
		//Toast.makeText(this, content, 1).show();
		CommonTools.showShortToast(this, content);
	}

	/**加载进度条*/
	public void showProgressDialog() {
		showProgressDialog((String)getResources().getText(R.string.plase_wait_loading));
	}
	
	/**加载进度条*/
	@SuppressWarnings("unused")
	public void showProgressDialog(String message) {
		if(progressDialog!=null){
			progressDialog.cancel();
		}
		progressDialog=new ProgressDialog(this);
		Drawable drawable=getResources().getDrawable(R.drawable.loading_animation);
		progressDialog.setIndeterminateDrawable(drawable);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setMessage(message);
		progressDialog.show();
	}
	
	public void dismissProgressDialog(){
		if(progressDialog != null) {
			progressDialog.dismiss();
		}
	}
}

package com.breeze.eapp.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

import com.breeze.eapp.R;
import com.breeze.eapp.base.BaseActivity;
import com.breeze.eapp.config.Constants;
import com.breeze.eapp.utils.CommonTools;
import com.breeze.eapp.utils.NetworkUtils;
import com.lurencun.android.toolkit.file.FileUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class UpdateActivity extends BaseActivity {

	private ProgressDialog pBar;
	private int newVerCode = 0;
	private String newVerurl = "";
	// private Handler handler = new Handler();
	private Map<String, String> newVerInfo;

	int fileSize;
	int downLoadFileSize;
	
	private static final String TAG = "EAPP";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = this.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.update);
		this.pBar = new ProgressDialog(this);
		int verCode = CommonTools.getVersionCode(UpdateActivity.this); // 当前版本
		try {
			newVerInfo = Constants.APP_UPDATE_RES_DATA;
			if (newVerInfo != null) {
				try {
					this.newVerCode = Integer.parseInt(newVerInfo.get("version"));
					this.newVerurl = newVerInfo.get("fileurl");
					if (verCode > 0 && this.newVerCode > verCode) {
						showBar();
						downFile(newVerurl);
					} else {
						pBar.cancel();
						// 退出
						openActivity(MenuActivity.class);
						UpdateActivity.this.finish();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(this, "下载失败，请检查您的网络!", 3).show();
					System.exit(0);
				}
			} else {
				pBar.cancel();
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "下载失败，请检查您的网络!", 3).show();
			System.exit(0);
		}
	}

	/**
	 * http 方式下载
	 * 
	 * @param @param url
	 * @return void
	 */
	void downFile(final String url) {
		new Thread() {
			@Override
			public void run() {

				HttpClient client = new DefaultHttpClient();
				if ( NetworkUtils.isCMWAP(UpdateActivity.this) ) {
					Log.i(TAG,"当前下载网络类型：CMWAP");
					HttpHost proxy = new HttpHost("10.0.0.172", 80);
					client.getParams().setParameter(
							ConnRoutePNames.DEFAULT_PROXY, proxy);
				}

				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					fileSize = (int) length;
					sendMsg(0);
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {
						FileUtil fUtil = new FileUtil();
						fUtil.makeDirectory(Constants.APP_DOWN_PATH, true);
						File file = new File(Constants.APP_DOWN_PATH, newVerInfo.get("savename"));
						fileOutputStream = new FileOutputStream(file);
						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							downLoadFileSize = count;
							sendMsg(1);
							if (length > 0) {
							}
						}
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	void down() {
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				update();
			}
		});
	}

	/**
	 * 执行安装更新操作
	 * 
	 * @param
	 * @return void
	 */
	void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Constants.APP_DOWN_PATH, newVerInfo.get("savename"))),"application/vnd.android.package-archive");
		startActivity(intent);
		finish();
	}

	/**
	 * 用户取消提示处理
	 * 
	 * @param
	 * @return void
	 */
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(UpdateActivity.this);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						UpdateActivity.this.finish();
						System.exit(0);
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						showBar();
						return;
					}
				});
		builder.create().show();
	}

	/**
	 * 显示进度条
	 * 
	 * @param
	 * @return void
	 */
	private void showBar() {
		// 返回事件监听
		pBar.setCancelable(true);
		pBar.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface arg0) {
				dialog();
			}
		});
		pBar.setTitle("软件更新");
		pBar.setMessage("正在下载更新，请稍候...");
		pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pBar.show();
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
				switch (msg.what) {
				case 0:
					pBar.setMax(fileSize);
					break;
				case 1:
					pBar.setProgress(downLoadFileSize);
					break;
				}
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}
}

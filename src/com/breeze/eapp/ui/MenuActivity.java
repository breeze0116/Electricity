package com.breeze.eapp.ui;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.breeze.eapp.AppSetting;
import com.breeze.eapp.R;
import com.breeze.eapp.config.UserInfo;
import com.breeze.eapp.entity.MenuEntity;
import com.breeze.eapp.res.MenuAdapter;
import com.breeze.eapp.res.MenuLoader;
import com.lurencun.android.support.widget.CommonAdapter;
import com.lurencun.android.toolkit.util.ActivitySwitcher;

public class MenuActivity extends com.breeze.eapp.base.BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.hideTitleBar();
    	//this.bindDoubleClickExit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        initView();
    }
    
    private void bind(){
    	GridView grid = (GridView)findViewById(R.id.menu_grid);
    	CommonAdapter<MenuEntity> adapter = new MenuAdapter(this);
    	final List<MenuEntity> data = new MenuLoader(this).load(R.array.menu_config);
    	adapter.updateDataCache(data);
    	grid.setAdapter(adapter);
    	
    	grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				MenuEntity menu = data.get(position);
				if(menu.value == 0){ //搜索
					onSearchRequested();
				}else{
					String[] keys = new String[]{AppSetting.MENU_KEY,AppSetting.MENU_TITLE};
					Object[] vals = new Object[]{menu.value,menu.title};
					//初始化关卡题目
					UserInfo.setModeWithStage(MenuActivity.this, menu.value);
					ActivitySwitcher.switchTo(MenuActivity.this, menu.nextUI,keys,vals);
				}
			}
		});
    }

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		bind();
	}
}
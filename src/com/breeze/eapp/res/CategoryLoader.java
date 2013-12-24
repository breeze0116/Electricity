/**
 * 
 */
package com.breeze.eapp.res;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.breeze.eapp.entity.CategoryEntity;
import com.lurencun.android.toolkit.HasContext;

/**
 * 
 * 题库列表
 */
public class CategoryLoader extends HasContext {

	public CategoryLoader(Context context) {
		super(context);
	}
	
	public List<CategoryEntity> load(int type){
		int size = 10;
		List<CategoryEntity> list = new ArrayList<CategoryEntity>();
		for(int i=0;i<size;i++){
			CategoryEntity item = new CategoryEntity();
			item.describe = "最新电力安规题库！";
			item.title = "最新电力安规题库";
			list.add(item);
		}
		return list;
	}

}

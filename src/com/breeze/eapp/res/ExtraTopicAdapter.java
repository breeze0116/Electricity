package com.breeze.eapp.res;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.breeze.eapp.AppSetting;
import com.breeze.eapp.R;
import com.breeze.eapp.config.Constants;
import com.breeze.eapp.config.UserInfo;
import com.breeze.eapp.entity.AnswerEntity;
import com.breeze.eapp.entity.TopicEntity;
import com.breeze.eapp.utils.Util;
import com.breeze.eapp.widget.ImageDialog;
import com.breeze.eapp.widget.TipDialog;
import com.lurencun.android.support.v2.widget.ViewPager;
import com.lurencun.android.support.v2.widget.ViewPagerAdapter;
import com.lurencun.android.toolkit.res.AssetsReader;
import com.lurencun.android.toolkit.util.BitmapScaleUitl;

public class ExtraTopicAdapter extends ViewPagerAdapter<TopicEntity> {

	private Vibrator mVirator;

	public ExtraTopicAdapter(Context context) {
		super(context);
		mVirator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
	}

	

	/* (non-Javadoc)
	 * @see com.lurencun.android.support.v2.widget.ViewPagerAdapter#createView(java.lang.Object, int)
	 */
	@Override
	public View createView(TopicEntity data, int position) {
		ScrollView  topicCell = (ScrollView) mInflater.inflate(R.layout.topic_cell, null);
		TextView index = (TextView)topicCell.findViewById(R.id.topic_index);
		TextView content = (TextView)topicCell.findViewById(R.id.topic_content);
		ImageView image = (ImageView)topicCell.findViewById(R.id.topic_imgs);
		LinearLayout answerLayout = (LinearLayout)topicCell.findViewById(R.id.topic_answer_layout);
		final TextView tipContent = (TextView)topicCell.findViewById(R.id.topic_answer_tip);//题目底部提示;
		
		final TopicEntity topic = data;
		
		String toppicType = "单选";
		if(topic.type.equals(TopicEntity.TopicType.MULTIPLE_CHOICE))
			toppicType = "多选";
		else if (topic.type.equals(TopicEntity.TopicType.JUDGE)) 
			toppicType = "判断";

		index.setText(String.format(AppSetting.TOPIC_INDEX, topic.index + 1,toppicType));
		content.setText(topic.title);
		createAnswerGroup(answerLayout,topic);
		Bitmap tempImage = BitmapScaleUitl.prorateThumbnail(AssetsReader.readBitmap(mContext, topic.image), 120, 60);
		if(tempImage.getHeight() > tempImage.getWidth()){
			Matrix matrix = new Matrix();  
			matrix.postRotate(90);  
			tempImage = Bitmap.createBitmap(tempImage, 0, 0, tempImage.getWidth(), tempImage.getHeight(), matrix, true); 
		}
		image.setImageBitmap(tempImage);
		image.setVisibility(View.GONE);//隐藏图片
		Button addFavourite = (Button)topicCell.findViewById(R.id.topic_answer_add_fav);
		addFavourite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "已将题目添加到收藏夹中！", Toast.LENGTH_SHORT).show();
			}
		});
		Button viewAnswer = (Button)topicCell.findViewById(R.id.topic_answer_view);
		
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ImageDialog dialog = new ImageDialog(mContext);
				Bitmap tempImage = AssetsReader.readBitmap(mContext, topic.image);
				if(tempImage.getWidth() > tempImage.getHeight()){
					Matrix matrix = new Matrix();  
					matrix.postRotate(90);  
					tempImage = Bitmap.createBitmap(tempImage, 0, 0, tempImage.getWidth(), tempImage.getHeight(), matrix, true); 
				}
				dialog.setImage(tempImage);
				dialog.show();
			}
		});
		viewAnswer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//tip显示
				//TipDialog mTipDialog = new TipDialog(mContext);
				//mTipDialog.setTipMsg(topic.tip);
				//mTipDialog.show();
				
				CharSequence cs = topic.tip;
				tipContent.setText(cs);
				tipContent.setVisibility(View.VISIBLE);
			}
		});
		return topicCell;
	}
	
	private void createAnswerGroup(LinearLayout answerLayout,TopicEntity topic){
		if(topic.type.equals(TopicEntity.TopicType.JUDGE)){
			createJudgeView(answerLayout,topic);
		}else if(topic.type.equals(TopicEntity.TopicType.MULTIPLE_CHOICE)){
			createMultipleChoiceView(answerLayout,topic);
		}else{
			createSingleChoiceView(answerLayout,topic);
		}
	}
	
	/**
	 * 创建判断题答案组
	 * @param answerLayout
	 * @param topic
	 */
	private void createJudgeView(LinearLayout answerLayout,TopicEntity topic){
		List<AnswerEntity> answerSet = topic.answers;
		answerLayout.setOrientation(LinearLayout.HORIZONTAL);
		if(answerSet.size() == 2){
			RelativeLayout trueSelection = (RelativeLayout) mInflater.inflate(R.layout.answer_judge_cell, null);
			RelativeLayout falseSelection = (RelativeLayout) mInflater.inflate(R.layout.answer_judge_cell, null);
			LayoutParams btnParam = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			btnParam.weight = 1.0f;
			trueSelection.setLayoutParams(btnParam);
			falseSelection.setLayoutParams(btnParam);
			
			final ImageView trueIcon = (ImageView)trueSelection.findViewById(R.id.answer_icon);
			final ImageView falseIcon = (ImageView)falseSelection.findViewById(R.id.answer_icon);
			final int trueIndex = 0;
			final int falseIndex = 1;
			final AnswerEntity trueEntity = answerSet.get(trueIndex);
			final AnswerEntity falseEntity = answerSet.get(falseIndex);
			int trueIconResId = trueEntity.isChecked ? AppSetting.AnswerIcons.Judge.PRESSED_ARRAY[trueIndex] :
				AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[trueIndex];
			int falseIconResId = falseEntity.isChecked ? AppSetting.AnswerIcons.Judge.PRESSED_ARRAY[falseIndex] :
				AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[falseIndex];
			trueIcon.setImageResource(trueIconResId);
			falseIcon.setImageResource(falseIconResId);
			
			final TopicEntity tmpEntity = topic;
			
			trueSelection.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mVirator.vibrate(AppSetting.VIRATOR_PARAMS, -1);
					trueEntity.isChecked = !trueEntity.isChecked;
					int iconResId = trueEntity.isChecked ? AppSetting.AnswerIcons.Judge.PRESSED_ARRAY[trueIndex] :
						AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[trueIndex];
					trueIcon.setImageResource(iconResId);
					if(trueEntity.isChecked){
						falseEntity.isChecked = false;
						int releaseIconResId = AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[falseIndex];
						falseIcon.setImageResource(releaseIconResId);
						
						//未做过回答才算成绩
						if(!tmpEntity.isAnswer) {
							if( "√".equals(tmpEntity.result) ) {
								Toast.makeText(ExtraTopicAdapter.this.mContext, "恭喜，正确！", Toast.LENGTH_SHORT).show();
								tmpEntity.isAnswer = true;
							}else {
								Toast.makeText(ExtraTopicAdapter.this.mContext, "回答错误！", Toast.LENGTH_SHORT).show();
								tmpEntity.isAnswer = true;
							}
						}
					}
				}
			});
			
			falseSelection.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mVirator.vibrate(AppSetting.VIRATOR_PARAMS, -1);
					falseEntity.isChecked = !falseEntity.isChecked;
					int iconResId = falseEntity.isChecked ? AppSetting.AnswerIcons.Judge.PRESSED_ARRAY[falseIndex] :
						AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[falseIndex];
					falseIcon.setImageResource(iconResId);
					if(falseEntity.isChecked){
						trueEntity.isChecked = false;
						int releaseIconResId = AppSetting.AnswerIcons.Judge.NORMAL_ARRAY[trueIndex];
						trueIcon.setImageResource(releaseIconResId);
						
						//未做过回答才算成绩
						if(!tmpEntity.isAnswer) {
							if( "×".equals(tmpEntity.result) ) {
								tmpEntity.isAnswer = true;
								Toast.makeText(ExtraTopicAdapter.this.mContext, "恭喜，正确！", Toast.LENGTH_SHORT).show();
							}else {
								tmpEntity.isAnswer = true;
								Toast.makeText(ExtraTopicAdapter.this.mContext, "回答错误！", Toast.LENGTH_SHORT).show();
							}
						}
					}
				}
			});
			
			answerLayout.addView(trueSelection);
			answerLayout.addView(falseSelection);
		}else{
			Log.e(Constants.DEBUG_TAG,"判断题只能有两答案选项！");
		}
	}
	
	/**
	 * 创建多选答案组
	 * @param answerLayout
	 * @param topic
	 */
	private void createMultipleChoiceView(LinearLayout answerLayout,TopicEntity topic){
		List<AnswerEntity> answerSet = topic.answers;
		for(int i=0;i<answerSet.size();i++){
			RelativeLayout answer = (RelativeLayout) mInflater.inflate(R.layout.answer_selection_cell, null);
			final ImageView icon = (ImageView)answer.findViewById(R.id.answer_icon);
			TextView content = (TextView)answer.findViewById(R.id.answer_content);
			final AnswerEntity answerEntity = answerSet.get(i);
			int iconResId = answerEntity.isChecked ? AppSetting.AnswerIcons.MultipleChoice.PRESSED[i] :
				AppSetting.AnswerIcons.MultipleChoice.NORMAL_ARRAY[i];
			icon.setImageResource(iconResId);
			content.setText(answerEntity.content);
			answerLayout.addView(answer);
			
			//清除已存在的答案选项
			AppSetting.AnswerIcons.MultipleChoice.cleanMultipleResult();
			
			final int index = i;
			final TopicEntity tmpEntity = topic;
			answer.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mVirator.vibrate(AppSetting.VIRATOR_PARAMS, -1);
					answerEntity.isChecked = !answerEntity.isChecked;
					int iconResId = answerEntity.isChecked ? AppSetting.AnswerIcons.MultipleChoice.PRESSED[index] :
						AppSetting.AnswerIcons.MultipleChoice.NORMAL_ARRAY[index];
					icon.setImageResource(iconResId);
					
					//答案记录
					if( answerEntity.isChecked ) {
						AppSetting.AnswerIcons.MultipleChoice.setMultipleResult(index, true);
					}else {
						AppSetting.AnswerIcons.MultipleChoice.setMultipleResult(index, false);
					}
					
					//未做答才判断成绩
					if(!tmpEntity.isAnswer) {
						//答案判断
						Log.i(Constants.DEBUG_TAG,  index + " 多选 " + AppSetting.AnswerIcons.MultipleChoice.getResultStr() + "  " + tmpEntity.result);
						if( AppSetting.AnswerIcons.MultipleChoice.getResultStr().equals(tmpEntity.result) ) {
							tmpEntity.isAnswer = true;
							Log.i(Constants.DEBUG_TAG,  index + " 恭喜，正确 " + answerEntity.isChecked);
							Toast.makeText(ExtraTopicAdapter.this.mContext, "恭喜，正确！", Toast.LENGTH_SHORT).show();
						}else if(Util.aHaveBNotExist(AppSetting.AnswerIcons.MultipleChoice.getResultStr(), tmpEntity.result)) {
							tmpEntity.isAnswer = true;
							Log.i(Constants.DEBUG_TAG,  index + " 回答错误 " + answerEntity.isChecked);
							Toast.makeText(ExtraTopicAdapter.this.mContext, "回答错误！", Toast.LENGTH_SHORT).show();
						}
					}
				}
			});
		}
	}
	
	/**
	 * 创建单选答案组
	 * @param answerLayout
	 * @param topic
	 */
	private void createSingleChoiceView(LinearLayout answerLayout,TopicEntity topic){
		final List<AnswerEntity> answerSet = topic.answers;
		final int size = answerSet.size();
		final ImageView[] tempIconArray = new ImageView[size];
		for(int i=0;i<size;i++){
			RelativeLayout answer = (RelativeLayout) mInflater.inflate(R.layout.answer_selection_cell, null);
			final ImageView icon = (ImageView)answer.findViewById(R.id.answer_icon);
			tempIconArray[i] = icon;
			TextView content = (TextView)answer.findViewById(R.id.answer_content);
			final AnswerEntity answerEntity = answerSet.get(i);
			int iconResId = answerEntity.isChecked ? AppSetting.AnswerIcons.SingleChoice.PRESSED[i] :
				AppSetting.AnswerIcons.SingleChoice.NORMAL_ARRAY[i];
			icon.setImageResource(iconResId);
			content.setText(answerEntity.content);
			answerLayout.addView(answer);
			
			final int index = i;
			final TopicEntity tmpEntity = topic;
			answer.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i("MYTAG", v.toString());
					mVirator.vibrate(AppSetting.VIRATOR_PARAMS, -1);
					boolean tempState = !answerEntity.isChecked;
					
					for(int i=0;i<size;i++){
						tempIconArray[i].setImageResource(AppSetting.AnswerIcons.SingleChoice.NORMAL_ARRAY[i]);
						answerSet.get(i).isChecked = false;
					}
					answerEntity.isChecked = tempState;
					
					int iconResId = answerEntity.isChecked ? AppSetting.AnswerIcons.SingleChoice.PRESSED[index] :
						AppSetting.AnswerIcons.SingleChoice.NORMAL_ARRAY[index];
					icon.setImageResource(iconResId);
					
					//未做答才判断成绩
					if(!tmpEntity.isAnswer) {
						//答案判断
						if( answerEntity.isChecked && AppSetting.RESULT[index].equals(tmpEntity.result) ) {
							tmpEntity.isAnswer = true;
							Toast.makeText(ExtraTopicAdapter.this.mContext, "恭喜，正确！", Toast.LENGTH_SHORT).show();
						}else {
							tmpEntity.isAnswer = true;
							Toast.makeText(ExtraTopicAdapter.this.mContext, "回答错误!", Toast.LENGTH_SHORT).show();
						}
					}
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see com.lurencun.android.support.v2.widget.ViewPagerAdapter#finishedUpdate(com.lurencun.android.support.v2.widget.ViewPager)
	 */
	@Override
	protected void finishedUpdate(ViewPager container) {
		
	}
	
	/* (non-Javadoc)
	 * @see com.lurencun.android.support.v2.widget.ViewPagerAdapter#startingUpdate(com.lurencun.android.support.v2.widget.ViewPager)
	 */
	@Override
	protected void startingUpdate(ViewPager container) {
		// TODO Auto-generated method stub
		
	}

}

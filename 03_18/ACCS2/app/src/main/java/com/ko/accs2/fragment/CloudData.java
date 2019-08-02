package com.ko.accs2.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.ko.accs2.R;
import com.ko.accs2.base.BaseFragment;
import com.ko.accs2.cloud.CloudItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * 云端fragment
 */
public class CloudData extends BaseFragment {
	private static final String TAG = CloudData.class.getSimpleName();
	@BindView(R.id.cloud_cellname_et)
	EditText mCloudCellnameEt;
	@BindView(R.id.cloud_pihao_et)
	EditText mCloudPihaoEt;
	@BindView(R.id.cloud_xibaobianhao_et)
	EditText mCloudXibaobianhaoEt;
	@BindView(R.id.cloud_daici_et)
	EditText mCloudDaiciEt;
	@BindView(R.id.cloud_btn_search)
	Button mCloudBtnSearch;
	Unbinder unbinder;
	@BindView(R.id.cloud_pihao)
	TextView mCloudPihao;
	@BindView(R.id.cloud_daici)
	TextView mCloudDaici;
	@BindView(R.id.cloud_huanyeshijian)
	TextView mCloudHuanyeshijian;
	@BindView(R.id.cloud_chuandaishijian)
	TextView mCloudChuandaishijian;
	@BindView(R.id.cloud_peiyangshijian)
	TextView mCloudPeiyangshijian;
	@BindView(R.id.cloud_xibaobianhao)
	TextView mCloudXibaobianhao;
	@BindView(R.id.cloud_cellname2)
	TextView mCloudCellname2;
	@BindView(R.id.cloud_huanyeshijian_tv)
	TextView mCloudHuanyeshijianTv;
	@BindView(R.id.cloud_chuandaishijian_tv)
	TextView mCloudChuandaishijianTv;
	@BindView(R.id.cloud_peiyangshijian_tv)
	TextView mCloudPeiyangshijianTv;
	private Button mBtnClearPeiyangshijian;
	private Button mBtnClearChuandaishijian;
	private Button mBtnClearHuanyeshijian;



	private String xibaomingchengUrl = null;
	private String xibaobianhaoUrl = null;
	private String pihaoUrl = null;
	private String daiciUrl = null;
	private String peiyangshijianUrl = null;
	private String chuandaishijianUrl = null;
	private String huanyeshijianUrl = null;


	//日历控件的添加

	@OnClick({R.id.cloud_huanyeshijian_tv, R.id.cloud_chuandaishijian_tv, R.id.cloud_peiyangshijian_tv})
	public void onViewClicked(View view) {

		switch (view.getId()) {

			case R.id.cloud_huanyeshijian_tv:

				//添加日历控件
				if (mCloudHuanyeshijianTv.getText().toString().trim().equals( "" )) {
					DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder( mContext, new DatePickerPopWin.OnDatePickedListener() {
						@Override
						public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
							mCloudHuanyeshijianTv.setText( dateDesc );

						}
					} ).textConfirm( "确认" ) //text of confirm button
							.textCancel( "取消" ) //text of cancel button
							.btnTextSize( 16 ) // button text size
							.viewTextSize( 25 ) // pick view text size
							.colorCancel( Color.parseColor( "#999999" ) ) //color of cancel button
							.colorConfirm( Color.parseColor( "#303F9F" ) )//color of confirm button
							.minYear( 2000 ) //min year in loop
							.maxYear( 2550 ) // max year in loop
							.dateChose( "" ) // date chose when init popwindow
							.build();
					pickerPopWin.showPopWin( (Activity) mContext );
				} else if (!mCloudHuanyeshijianTv.getText().toString().trim().toLowerCase().equals( "" ) && mCloudHuanyeshijianTv != null) {
					mBtnClearHuanyeshijian.setVisibility( View.VISIBLE );
					mBtnClearHuanyeshijian.setOnClickListener( new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mCloudHuanyeshijianTv.setText( "" );
							mBtnClearHuanyeshijian.setVisibility( View.GONE );
						}
					} );

				}
				break;
			case R.id.cloud_chuandaishijian_tv:
				if (mCloudChuandaishijianTv.getText().toString().trim().equals( "" )) {
					DatePickerPopWin pickerPopWin1 = new DatePickerPopWin.Builder( mContext, new DatePickerPopWin.OnDatePickedListener() {
						@Override
						public void onDatePickCompleted(int year, int month, int day, String dateDesc) {

							mCloudChuandaishijianTv.setText( dateDesc );



						}
					} ).textConfirm( "确认" ) //text of confirm button
							.textCancel( "取消" ) //text of cancel button
							.btnTextSize( 16 ) // button text size
							.viewTextSize( 25 ) // pick view text size
							.colorCancel( Color.parseColor( "#999999" ) ) //color of cancel button
							.colorConfirm( Color.parseColor( "#303F9F" ) )//color of confirm button
							.minYear( 2000 ) //min year in loop
							.maxYear( 2550 ) // max year in loop
							.dateChose( "" ) // date chose when init popwindow
							.build();
					pickerPopWin1.showPopWin( (Activity) mContext );
				} else if (!mCloudChuandaishijianTv.getText().toString().trim().toLowerCase().equals( "" ) && mCloudChuandaishijianTv != null) {
					mBtnClearChuandaishijian.setVisibility( View.VISIBLE );
					mBtnClearChuandaishijian.setOnClickListener( new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mCloudChuandaishijianTv.setText( "" );
							mBtnClearChuandaishijian.setVisibility( View.GONE );
						}
					} );
				}


				break;
			case R.id.cloud_peiyangshijian_tv:

				if (mCloudPeiyangshijianTv.getText().toString().trim().equals( "" )) {
					DatePickerPopWin pickerPopWin2 = new DatePickerPopWin.Builder( mContext, new DatePickerPopWin.OnDatePickedListener() {
						@Override
						public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
							mCloudPeiyangshijianTv.setText( dateDesc );


						}
					} ).textConfirm( "确认" ) //text of confirm button
							.textCancel( "取消" ) //text of cancel button
							.btnTextSize( 16 ) // button text size
							.viewTextSize( 25 ) // pick view text size
							.colorCancel( Color.parseColor( "#999999" ) ) //color of cancel button
							.colorConfirm( Color.parseColor( "#303F9F" ) )//color of confirm button
							.minYear( 2000 ) //min year in loop
							.maxYear( 2550 ) // max year in loop
							.dateChose( "" ) // date chose when init popwindow
							.build();
					pickerPopWin2.showPopWin( (Activity) mContext );
				} else if (!mCloudPeiyangshijianTv.getText().toString().trim().toLowerCase().equals( "" ) && mCloudPeiyangshijianTv != null) {
					mBtnClearPeiyangshijian.setVisibility( View.VISIBLE );
					mBtnClearPeiyangshijian.setOnClickListener( new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mCloudPeiyangshijianTv.setText( "" );
							mBtnClearPeiyangshijian.setVisibility( View.GONE );
						}
					} );
				}


				break;
		}
	}


	@Override
	protected View initView() {
		View view = View.inflate( mContext, R.layout.fragemt_cloud, null );
		mCloudCellnameEt = view.findViewById( R.id.cloud_cellname_et );
		mCloudXibaobianhaoEt = view.findViewById( R.id.cloud_xibaobianhao_et );
		mCloudPihaoEt = view.findViewById( R.id.cloud_pihao_et );
		mCloudDaiciEt = view.findViewById( R.id.cloud_daici_et );
		mCloudHuanyeshijianTv = view.findViewById( R.id.cloud_huanyeshijian_tv );
		mCloudChuandaishijianTv = view.findViewById( R.id.cloud_chuandaishijian_tv );
		mCloudPeiyangshijianTv = view.findViewById( R.id.cloud_peiyangshijian_tv );
		mCloudBtnSearch = view.findViewById( R.id.cloud_btn_search );
		//清除日历框
		mBtnClearPeiyangshijian = view.findViewById(R.id.btn_clear_peiyangshijian);
		mBtnClearPeiyangshijian.setVisibility( View.GONE );
		mBtnClearChuandaishijian = view.findViewById(R.id.btn_clear_chuandaishijian);
		mBtnClearChuandaishijian.setVisibility( View.GONE );
		mBtnClearHuanyeshijian = view.findViewById(R.id.btn_clear_huanyeshijian);
		mBtnClearHuanyeshijian.setVisibility( View.GONE );

		pihaoUrl = pihaoUrl == null ? "" : mCloudPihaoEt.getText().toString().trim();
		daiciUrl = daiciUrl == null ? "" : mCloudDaiciEt.getText().toString().trim();
		mCloudDaici = view.findViewById( R.id.cloud_daici );
		//设置代次批号的顺序添加焦点注册和textWatcher
		mCloudDaici.setTextColor( 0xffbbbbbb );
		mCloudDaiciEt.setEnabled( false );
		mCloudPihaoEt.setOnFocusChangeListener( new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {

					mCloudPihaoEt.addTextChangedListener( new TextWatcher() {

						@Override
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {

						}

						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {

						}

						@Override
						public void afterTextChanged(Editable s) {
							if (!mCloudPihaoEt.getText().toString().trim().equals( "" ) && mCloudPihaoEt != null) {
								mCloudDaici.setTextColor( Color.BLACK );
								mCloudDaiciEt.setEnabled( true );


							} else if (mCloudPihaoEt.getText().toString().trim().equals( "" )) {
								mCloudDaici.setTextColor( 0xffbbbbbb );
								mCloudDaiciEt.setEnabled( false );


							}
						}

					} );
				} else {
					if (!mCloudPihaoEt.getText().toString().trim().equals( "" ) && mCloudPihaoEt != null) {
						mCloudDaici.setTextColor( Color.BLACK );
						mCloudDaiciEt.setEnabled( true );

					}
				}
			}
		} );

		//搜索按钮
		mCloudBtnSearch.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				xibaomingchengUrl = xibaomingchengUrl == null ? "" : mCloudCellnameEt.getText().toString().trim();
				xibaobianhaoUrl = xibaobianhaoUrl == null ? "" : mCloudXibaobianhaoEt.getText().toString().trim();
				pihaoUrl = pihaoUrl == null ? "" : mCloudPihaoEt.getText().toString().trim();
				daiciUrl = daiciUrl == null ? "" : mCloudDaiciEt.getText().toString().trim();
				peiyangshijianUrl = peiyangshijianUrl == null ? "" : mCloudPeiyangshijianTv.getText().toString().trim();
				chuandaishijianUrl = chuandaishijianUrl == null ? "" : mCloudChuandaishijianTv.getText().toString().trim();
				huanyeshijianUrl = huanyeshijianUrl == null ? "" : mCloudHuanyeshijianTv.getText().toString().trim();

				String CloudUrl = "http://keyonecn.com:8897/training/phone?" + "cellname=" + xibaomingchengUrl
						+ "&&batch_num=" + pihaoUrl + "&&submission" + daiciUrl;
				//						+"&&serial_num="+xibaobianhaoUrl
				//						+"&&culture_time="+peiyangshijianUrl
				//						+"&&passage_time="+chuandaishijianUrl
				//						+"&&change_liquid_time="+huanyeshijianUrl;
				Log.e( TAG, "请看" + CloudUrl );

				Intent intent = new Intent();
				Bundle bundle = new Bundle();

				if (mCloudCellnameEt.getText().toString().trim().equals( "" ) &&
						mCloudXibaobianhaoEt.getText().toString().trim().equals( "" ) &&
						mCloudPihaoEt.getText().toString().trim().equals( "" ) &&
						mCloudDaiciEt.getText().toString().trim().equals( "" ) &&
						mCloudPeiyangshijianTv.getText().toString().trim().equals( "" ) &&
						mCloudChuandaishijianTv.getText().toString().trim().equals( "" ) &&
						mCloudHuanyeshijianTv.getText().toString().trim().equals( "" ) ) {
					//					CloudUrl = "http://keyonecn.com:8897/training/phone?cellname=&&batch_num=&&submission=&&serial_num=&&culture_time=&&passage_time=&&change_liquid_time=";
					//					CloudUrl = "http://keyonecn.com:8897/training/phone?cellname=cell1&&batch_num&&submission=&&serial_num=";
					bundle.putString( "CloudUrl", CloudUrl );
					intent.putExtras( bundle );
					intent.setClass( mContext, CloudItem.class );
					startActivity( intent );
				} else if (mCloudCellnameEt.getText().toString().trim().equals( "" )){
					Toast.makeText( mContext, "细胞名称不能为空", Toast.LENGTH_SHORT ).show();
				}else if (!mCloudCellnameEt.getText().toString().trim().equals( "hela" )){
					Toast.makeText(mContext,"细胞名错误", Toast.LENGTH_SHORT).show();
				}else if (mCloudCellnameEt.getText().toString().trim().equals( "hela" )){
					bundle.putString( "CloudUrl", CloudUrl );
					intent.putExtras( bundle );
					intent.setClass( mContext, CloudItem.class );
					startActivity( intent );
				}
			}
		} );
		return view;
	}

	@Override
	protected void initData() {
		super.initData();
	}

	//使用黄油刀后重写的方法两个
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: inflate a fragment view
		View rootView = super.onCreateView( inflater, container, savedInstanceState );
		unbinder = ButterKnife.bind( this, rootView );

		return rootView;
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}


}

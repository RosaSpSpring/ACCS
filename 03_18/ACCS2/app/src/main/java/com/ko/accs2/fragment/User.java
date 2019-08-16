package com.ko.accs2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ko.accs2.R;
import com.ko.accs2.base.BaseFragment;
import com.ko.accs2.bean.UserBean;
import com.ko.accs2.user.BoundedDevices;
import com.ko.accs2.user.UserSetting;
import com.ko.accs2.util.CacheUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Admin
 * @version $Rev$
 * @des 用户fragment界面
 * @updateAuthor lxm
 * @updateDes ${first time}
 */
public class User extends BaseFragment implements View.OnClickListener {
	private static final String TAG = User.class.getSimpleName();

	@BindView(R.id.user_username)
	TextView mUserUsername;
	@BindView(R.id.user_device_status)
	TextView mUserDeviceStatus;
	@BindView(R.id.dcevice_show)
	TextView mDceviceShow;

	Unbinder unbinder;
	@BindView(R.id.bounddevice)
	ConstraintLayout mBounddevice;
	@BindView(R.id.user_setting)
	ConstraintLayout mUserSetting;

	@Override
	protected View initView() {
		View view = View.inflate( mContext, R.layout.fragment_user, null );
		mUserUsername = view.findViewById( R.id.user_username );
		mUserDeviceStatus = view.findViewById( R.id.user_device_status );
		mDceviceShow = view.findViewById( R.id.dcevice_show );
		mBounddevice = view.findViewById( R.id.bounddevice );
		mUserSetting = view.findViewById( R.id.user_setting );

		mBounddevice.setOnClickListener( this );
		mUserSetting.setOnClickListener( this );

		return view;
	}


	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: inflate a fragment view
		View rootView = super.onCreateView( inflater, container, savedInstanceState );
		assert rootView != null;
		unbinder = ButterKnife.bind( this, rootView );



		getResponse();
		String code = CacheUtils.getString( mContext, "LoginActivitycode" );
		String username = CacheUtils.getString( mContext,"Username" );
		mUserUsername.setText( username );
		Log.e( TAG, "code" + code );
		//		if (code == "200") {
		//			mUserDeviceStatus.setText( "当前设备在线中");
		//		} else {
		//			mUserDeviceStatus.setText( "当前设备离线" );
		//		}


		return rootView;
	}
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void getUserBean(UserBean userBean) {
		Log.e(TAG, "userbean.msg" + userBean.getMsg());

	}


	private void getResponse() {
		//几号设备
		String response = CacheUtils.getString( mContext, "BoundedDevice2" );
		Log.e(TAG, "response" + response);
		if (response != null) {
			mDceviceShow.setText( "" );
			//			mDceviceShow.setText( response);
		}else {
			Log.e(TAG, "response为空字符" );
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	@Override
	public void onStart() {
		super.onStart();
		//注册eventbus
//		EventBus.getDefault().register( this );
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//解注册
		EventBus.getDefault().unregister( this );
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.user_setting:
				Intent intent = new Intent( mContext, UserSetting.class );
				startActivity( intent );
				break;
			case R.id.bounddevice:
				Intent i = new Intent( mContext, BoundedDevices.class );
				startActivity( i );
				break;

			default:
				break;
		}
	}


}

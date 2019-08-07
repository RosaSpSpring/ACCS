package com.ko.accs2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ko.accs2.R;
import com.ko.accs2.base.BaseFragment;
import com.ko.accs2.user.BoundedDevices;
import com.ko.accs2.user.UserSetting;
import com.ko.accs2.util.CacheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ko.accs2.constant.Constant.BOUNDEDDEVICE;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class User extends BaseFragment implements View.OnClickListener {
	private static final String TAG = User.class.getSimpleName();

	@BindView(R.id.user_username)
	TextView mUserUsername;
	@BindView(R.id.user_device_status)
	TextView mUserDeviceStatus;
	@BindView(R.id.dcevice_show)
	TextView mDceviceShow;
	@BindView(R.id.bounddevice)
	LinearLayout mBounddevice;
	@BindView(R.id.user_setting)
	LinearLayout mUserSetting;
	Unbinder unbinder;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		// TODO: inflate a fragment view
		View rootView = super.onCreateView( inflater, container, savedInstanceState );
		unbinder = ButterKnife.bind( this, rootView );
		getResponse();
		String code = CacheUtils.getString( mContext, "LoginActivitycode");
		Log.e( TAG, "code" + code );
//		if (code == "200") {
//			mUserDeviceStatus.setText( "当前设备在线中");
//		} else {
//			mUserDeviceStatus.setText( "当前设备离线" );
//		}

		return rootView;
	}


	private void getResponse(){
		//几号设备
		String response = CacheUtils.getString( mContext, "BoundedDevice2");
		if (response != null) {
			mDceviceShow.setText( "" );
//			mDceviceShow.setText( response);
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
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

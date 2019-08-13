package com.ko.accs2.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ko.accs2.MainActivity;
import com.ko.accs2.R;
import com.ko.accs2.base.BaseFragment;
import com.ko.accs2.bean.RemoteParams;
import com.ko.accs2.bean.RemoteParamsRedis;
import com.ko.accs2.netyy.LiveActivity;
import com.ko.accs2.remote.RoundProgressBar;
import com.ko.accs2.util.CacheUtils;
import com.ko.accs2.util.HttpPostUtils;
import com.ko.accs2.util.PixeUtils;
import com.netease.neliveplayer.playerkit.sdk.PlayerManager;
import com.netease.neliveplayer.playerkit.sdk.model.SDKInfo;
import com.netease.neliveplayer.playerkit.sdk.model.SDKOptions;
import com.netease.neliveplayer.sdk.NELivePlayer;
import com.netease.neliveplayer.sdk.model.NEDynamicLoadingConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static android.support.constraint.Constraints.TAG;
import static com.ko.accs2.constant.Constant.PARAMSRADIUS;
import static com.ko.accs2.constant.Constant.TESTVIDEOURL;
import static com.ko.accs2.constant.Constant.VIDEOURL;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * 监控界面fragment
 */
public class Remote extends BaseFragment {
	private static final int HIDE_REMOTE_MEDIACONTROLLER = 0x01;
	//视频更新进度
	private static final int PROGRESS = 0x02;
	@BindView(R.id.tem_cur_pb)
	RoundProgressBar mTemCurPb;
	@BindView(R.id.tem_set_pb)
	RoundProgressBar mTemSetPb;
	@BindView(R.id.co2_cur_pb)
	RoundProgressBar mCo2CurPb;
	@BindView(R.id.shidu_ratio)
	RoundProgressBar mShiduRatio;
	@BindView(R.id.co2_set_pb)
	RoundProgressBar mCo2SetPb;
	Unbinder unbinder;

	@BindView(R.id.myvideo)
	VideoView mMyvideo;
	@BindView(R.id.video_back)
	ImageView mVideoBack;
	@BindView(R.id.video_name)
	TextView mVideoName;
	@BindView(R.id.ll_top)
	LinearLayout mLlTop;
	@BindView(R.id.mediacontroller_play_pause)
	ImageView mMediacontrollerPlayPause;
	@BindView(R.id.mediacontroller_time_current)
	TextView mMediacontrollerTimeCurrent;
	@BindView(R.id.mediacontroller_seekbar)
	SeekBar mMediacontrollerSeekbar;
	@BindView(R.id.mediacontroller_time_total)
	TextView mMediacontrollerTimeTotal;
	@BindView(R.id.video_player_mute)
	ImageView mVideoPlayerMute;
	@BindView(R.id.video_player_scale)
	ImageView mVideoPlayerScale;
	@BindView(R.id.bottom_player_controller)
	LinearLayout mBottomPlayerController;
	@BindView(R.id.ll_bottom)
	LinearLayout mLlBottom;
	@BindView(R.id.tem_set)
	TextView mTemSet;
	@BindView(R.id.tem_cur)
	TextView mTemCur;
	@BindView(R.id.co2_set)
	TextView mCo2Set;
	@BindView(R.id.co2_set2)
	TextView mCo2Set2;
	@BindView(R.id.co2_set4)
	TextView mCo2Set4;
	@BindView(R.id.myprogressbar)
	ProgressBar mMyprogressbar;
	@BindView(R.id.remote_media_controller)
	RelativeLayout mRemoteMediaController;

	private TextureView mTextureView;
	private MediaPlayer mMediaPlayer;
	private VideoView mVideoView;
	private boolean isShow = false;
	String url;
	int j = 0;
	private String decodeType = "software";  //解码类型，默认软件解码
	private String mediaType = "livestream"; //媒体类型，默认网络直播

	private SDKOptions config;

	/**
	 * 6.0权限处理
	 **/
	private boolean bPermission = false;
	private final int WRITE_PERMISSION_REQ_CODE = 100;
	private boolean isMute = false;

	private boolean checkPublishPermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			List<String> permissions = new ArrayList<>();
			if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission( mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE )) {
				permissions.add( Manifest.permission.WRITE_EXTERNAL_STORAGE );
			}
			if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission( mContext, Manifest.permission.CAMERA )) {
				permissions.add( Manifest.permission.CAMERA );
			}
			if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission( mContext, Manifest.permission.READ_PHONE_STATE )) {
				permissions.add( Manifest.permission.READ_PHONE_STATE );
			}
			if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission( mContext, Manifest.permission.ACCESS_COARSE_LOCATION )) {
				permissions.add( Manifest.permission.ACCESS_COARSE_LOCATION );
			}
			if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission( mContext, Manifest.permission.ACCESS_FINE_LOCATION )) {
				permissions.add( Manifest.permission.ACCESS_FINE_LOCATION );
			}
			if (permissions.size() != 0) {
				ActivityCompat.requestPermissions( (Activity) mContext, permissions.toArray( new String[0] ), WRITE_PERMISSION_REQ_CODE );
				return false;
			}
		}
		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult( requestCode, permissions, grantResults );
		if (requestCode == WRITE_PERMISSION_REQ_CODE) {
			initPlayer();
			for (int ret : grantResults) {
				if (ret != PackageManager.PERMISSION_GRANTED) {
					return;
				}
			}
			bPermission = true;
		}
	}


	@Override
	protected View initView() {
		/*   6.0权限申请     **/
		bPermission = checkPublishPermission();
		View view = View.inflate( mContext, R.layout.remote_fragment, null );
		// 尽量在请求权限后再初始化
		if (bPermission) {
			initPlayer();
		}
		/* 触摸事件的注册 */
		((MainActivity) Objects.requireNonNull( this.getActivity() )).registerMyOnTouchListener( onTouchListener );

		mTemCurPb = view.findViewById( R.id.tem_cur_pb );
		mTemSetPb = view.findViewById( R.id.tem_set_pb );
		mCo2CurPb = view.findViewById( R.id.co2_cur_pb );
		mCo2SetPb = view.findViewById( R.id.co2_set_pb );
		mShiduRatio = view.findViewById( R.id.shidu_ratio );

		mMediaPlayer = new MediaPlayer();
		//圆形加载进度条
		mMyprogressbar = view.findViewById( R.id.myprogressbar );
		//		mTextureView = view.findViewById(R.id.myvideo);
		mVideoView = view.findViewById( R.id.myvideo );
		initProgress();
		//小窗口的视频地址

		url = VIDEOURL;



		//		显示控制栏(无法快进)
		//		mVideoView.setMediaController( new MediaController( mContext ) );
		//添加监听 当底层解码准备好了开始回调
		mVideoView.setOnPreparedListener( new MyOnPreparedListener() );


		//播放出错
		mVideoView.setOnErrorListener( new MyOnErrorListener() );
		// 播放完成回调 the end of a media file has been reached during playback.
		mVideoView.setOnCompletionListener( new MyPlayerOnCompletionListener() );
		//准备好了准备地址比较好
		mVideoView.setVideoURI( Uri.parse( url ));
		//设置控制面板
		//		mVideoView.setMediaController( new MediaController( mContext ) );
		return view;
	}

	private void initPlayer() {
		config = new SDKOptions();
		//是否开启动态加载功能，默认关闭
		//        config.dynamicLoadingConfig = new NEDynamicLoadingConfig();
		//        config.dynamicLoadingConfig.isDynamicLoading = true;
		//        config.dynamicLoadingConfig.isArmeabiv7a = true;
		//        config.dynamicLoadingConfig.armeabiv7aUrl = "your_url";
		//        config.dynamicLoadingConfig.onDynamicLoadingListener = mOnDynamicLoadingListener;
		// SDK将内部的网络请求以回调的方式开给上层，如果需要上层自己进行网络请求请实现config.dataUploadListener，
		// 如果上层不需要自己进行网络请求而是让SDK进行网络请求，这里就不需要操作config.dataUploadListener
		config.dataUploadListener = mOnDataUploadListener;
		//是否支持H265解码回调
		config.supportDecodeListener = mOnSupportDecodeListener;
		//这里可以绑定客户的账号系统或device_id，方便出问题时双方联调
		//        config.thirdUserId = "your_id";
		PlayerManager.init( mContext, config );
		SDKInfo sdkInfo = PlayerManager.getSDKInfo( mContext );
		Log.i( TAG, "NESDKInfo:version" + sdkInfo.version + ",deviceId:" + sdkInfo.deviceId );
	}

	//全屏视频地址
	private void startAotic() {
		//		String url = "http://v745decb8.live.126.net/live/8ca0aea7da304bdeaa391b6c097945e1.flv?netease=v745decb8.live.126.net";
		String url =  VIDEOURL;
		Log.d( TAG, "url = " + url );


		//		Log.d(TAG, "decode_type = " + decodeType);

		//		if ((mediaType.equals("livestream") && url.isEmpty()) || (mediaType.equals("videoondemand") && url.isEmpty())) {
		//			AlertDialogBuild(0);
		//			return;
		//		}

		if (!bPermission) {
			Toast.makeText( mContext, "app所需要的权限未满足", Toast.LENGTH_LONG ).show();
		}
		if (config != null && config.dynamicLoadingConfig != null && config.dynamicLoadingConfig.isDynamicLoading && !NELivePlayer.isDynamicLoadReady()) {
			Toast.makeText( mContext, "等待so库加载", Toast.LENGTH_SHORT ).show();
			return;
		}

		//把多个参数传给Activity
		Intent intent;
		if (mediaType != null && mediaType.equals( "livestream" )) {
			intent = new Intent( mContext, LiveActivity.class );
			intent.putExtra( "media_type", "livestream" );
			intent.putExtra( "decode_type", "software" );
			intent.putExtra( "videoPath", url );
			startActivity( intent );
		} else {
			Toast.makeText( mContext, "不是直播或者不是软解码或者路路径不对", Toast.LENGTH_SHORT ).show();
		}
	}


	//    @Override
	//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	//        if (data == null || data.getExtras() == null || TextUtils.isEmpty(data.getExtras().getString("result"))) {
	//            return;
	//        }
	//        String result = data.getExtras().getString("result");
	////        if (mEditURL != null) {
	////            mEditURL.setText(result);
	////        }
	//    }


	@Override
	public void onPause() {
		Log.d( TAG, "on pause" );
		super.onPause();
	}

	@Override
	public void onDestroy() {
		Log.d( TAG, "on destroy" );
		super.onDestroy();
		/*触摸事件的注销 */
		((MainActivity) Objects.requireNonNull( this.getActivity() )).unregisterMyOnTouchListener( onTouchListener );

	}

	//		@Override
	//		public void onRestart() {
	//			Log.d(TAG, "on restart");
	//			super.onRestart();
	//		}

	@Override
	public void onResume() {
		Log.d( TAG, "on resmue" );
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.d( TAG, "on start" );
		super.onStart();
	}


	private NELivePlayer.OnDynamicLoadingListener mOnDynamicLoadingListener = (type, isCompleted) -> Log.d( TAG, "type:" + type + "，isCompleted:" + isCompleted );

	private NELivePlayer.OnSupportDecodeListener mOnSupportDecodeListener = isSupport -> {
		Log.d( TAG, "是否支持H265硬件解码 onSupportDecode isSupport:" + isSupport );
		//如果支持H265硬件解码，那么可以使用H265的视频源进行播放
	};


	private NELivePlayer.OnDataUploadListener mOnDataUploadListener = new NELivePlayer.OnDataUploadListener() {
		@Override
		public boolean onDataUpload(String url, String data) {
			Log.d( TAG, "onDataUpload url:" + url + ", data:" + data );
			sendData( url, data );
			return true;
		}

		@Override
		public boolean onDocumentUpload(String url, Map<String, String> params, Map<String, String> filepaths) {
			Log.d( TAG, "onDataUpload url:" + url + ", params:" + params + ",filepaths:" + filepaths );
			return (new HttpPostUtils( url, params, filepaths ).connPost());
		}
	};

	private boolean sendData(final String urlStr, final String content) {
		int response = 0;
		try {
			URL url = new URL( urlStr );
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout( 5000 );
			conn.setDoOutput( true );
			conn.setDoInput( true );
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "application/json;charset=utf-8" );
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write( content.getBytes() );

			response = conn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				Log.i( TAG, " sendData finished,data:" + content );

			} else {
				Log.i( TAG, " sendData, response: " + response );

			}
		} catch (IOException e) {
			Log.e( TAG, "sendData, recv code is error: " + e.getMessage() );

		} catch (Exception e) {
			Log.e( TAG, "sendData, recv code is error2: " + e.getMessage() );

		}
		return (response == HttpURLConnection.HTTP_OK);
	}


	private void initProgress() {
		//设置五个参数的单位和颜色和内环颜色
		mTemSetPb.setThirdText( "℃" );
		mTemSetPb.setCurrentProgress( 0 );
		mTemSetPb.setThirdTextColor( getResources().getColor( R.color.colorProgressBarTextColor ) );
		mTemSetPb.setSecondTextColor( getResources().getColor( R.color.colorProgressBarTextColor ) );
		mTemSetPb.setProgressCircleColor( getResources().getColor( R.color.colorSet ) );

		mTemCurPb.setThirdText( "℃" );
		mTemCurPb.setCurrentProgress(  0 );
		mTemCurPb.setThirdTextColor( getResources().getColor( R.color.colorProgressBarTextColor ) );
		mTemCurPb.setSecondTextColor( getResources().getColor( R.color.colorProgressBarTextColor ) );
		mTemCurPb.setProgressCircleColor( getResources().getColor( R.color.colorCur ) );

		mCo2SetPb.setThirdText( "%" );
		mCo2SetPb.setCurrentProgress( 0);
		mCo2SetPb.setThirdTextColor( getResources().getColor( R.color.colorProgressBarTextColor ) );
		mCo2SetPb.setSecondTextColor( getResources().getColor( R.color.colorProgressBarTextColor ) );
		mCo2SetPb.setProgressCircleColor( getResources().getColor( R.color.colorSet ) );

		mCo2CurPb.setThirdText( "%" );
		mCo2CurPb.setCurrentProgress( 0);
		mCo2CurPb.setThirdTextColor( getResources().getColor( R.color.colorProgressBarTextColor ) );
		mCo2CurPb.setSecondTextColor( getResources().getColor( R.color.colorProgressBarTextColor ) );
		mCo2CurPb.setProgressCircleColor( getResources().getColor( R.color.colorCur ) );

		mShiduRatio.setThirdText( "%" );
		mShiduRatio.setCurrentProgress( 0 );
		mShiduRatio.setThirdTextColor( getResources().getColor( R.color.colorProgressBarTextColor ) );
		mShiduRatio.setSecondTextColor( getResources().getColor( R.color.colorProgressBarTextColor ) );
		mShiduRatio.setProgressCircleColor( getResources().getColor( R.color.colorHumanity ) );



//		mShiduRatio.setProgress(downLength);

//		mShiduRatio.setProgressNumberFormat();



	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: inflate a fragment view
		View rootView = super.onCreateView( inflater, container, savedInstanceState );
		assert rootView != null;
		unbinder = ButterKnife.bind( this, rootView );


		//		url = PARAMS;
		url = PARAMSRADIUS +"CO2";


		//联网请求co2当前参数
		OkHttpUtils.post().url( url ).build().execute( new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				e.printStackTrace();
				Log.e( TAG, "请求失败" );
			}

			@Override
			public void onResponse(String response, int id) {
				Log.e( TAG, "请求结果" );
				switch (id) {
					case 100:
						//						Toast.makeText( mContext, "http", Toast.LENGTH_SHORT ).show();
						break;
					case 101:
						//						Toast.makeText( mContext, "https", Toast.LENGTH_SHORT ).show();
						break;

					default:
						break;
				}
				if (response != null) {
					CacheUtils.putString( mContext, url, response );
					RemoteParamsRedis remoteParamsRedis = new RemoteParamsRedis(  );
					try {
						JSONObject jsonObject = new JSONObject( response );
						String msg = jsonObject.optString( "msg" );
						int code = jsonObject.optInt( "code" );
						int data = jsonObject.optInt( "data" );

						remoteParamsRedis.setCode( code );
						remoteParamsRedis.setData( data );
						remoteParamsRedis.setMsg( msg );

						mCo2CurPb.setCurrentProgress( remoteParamsRedis.getData() );
					} catch (JSONException e) {
						e.printStackTrace();
					}
					//					processData( response );
					Log.e( TAG, "response" + response);

				} else {
					Log.e( TAG, "请求结果为空" );
				}
			}
		} );
		//co2设定值
		String co2settUrl  = PARAMSRADIUS +"CO2Set";
		OkHttpUtils.post().url( co2settUrl ).build().execute( new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				e.printStackTrace();
				Log.e( TAG, "请求失败" );
			}

			@Override
			public void onResponse(String response, int id) {
				Log.e( TAG, "请求结果" );
				switch (id) {
					case 100:
						//						Toast.makeText( mContext, "http", Toast.LENGTH_SHORT ).show();
						break;
					case 101:
						//						Toast.makeText( mContext, "https", Toast.LENGTH_SHORT ).show();
						break;

					default:
						break;
				}
				if (response != null) {
					CacheUtils.putString( mContext, co2settUrl, response );
					RemoteParamsRedis remoteParamsRedis = new RemoteParamsRedis(  );
					try {
						JSONObject jsonObject = new JSONObject( response );
						String msg = jsonObject.optString( "msg" );
						int code = jsonObject.optInt( "code" );
						int data = jsonObject.optInt( "data" );

						remoteParamsRedis.setCode( code );
						remoteParamsRedis.setData( data );
						remoteParamsRedis.setMsg( msg );

						mCo2SetPb.setCurrentProgress( remoteParamsRedis.getData());
//						mCo2SetPb.setCurrentProgress( 8.9f );
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					Log.e( TAG, "请求结果为空" );
				}
			}
		} );
		//温度当前值
		String temcurUrl =  PARAMSRADIUS +"temperature";

		OkHttpUtils.post().url( temcurUrl ).build().execute( new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				e.printStackTrace();
				Log.e( TAG, "请求失败" );
			}

			@Override
			public void onResponse(String response, int id) {
				Log.e( TAG, "请求结果" );
				switch (id) {
					case 100:
						//						Toast.makeText( mContext, "http", Toast.LENGTH_SHORT ).show();
						break;
					case 101:
						//						Toast.makeText( mContext, "https", Toast.LENGTH_SHORT ).show();
						break;

					default:
						break;
				}
				if (response != null) {
					CacheUtils.putString( mContext, temcurUrl, response );
					RemoteParamsRedis remoteParamsRedis = new RemoteParamsRedis(  );
					try {
						JSONObject jsonObject = new JSONObject( response );
						String msg = jsonObject.optString( "msg" );
						int code = jsonObject.optInt( "code" );
						int data = jsonObject.optInt( "data" );

						remoteParamsRedis.setCode( code );
						remoteParamsRedis.setData( data );
						remoteParamsRedis.setMsg( msg );

						mTemCurPb.setCurrentProgress( remoteParamsRedis.getData() );
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					Log.e( TAG, "请求结果为空" );
				}
			}
		} );
		//温度设定值
		String temsetUrl =  PARAMSRADIUS +"temperatureSet";
		OkHttpUtils.post().url( temsetUrl ).build().execute( new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				e.printStackTrace();
				Log.e( TAG, "请求失败" );
			}

			@Override
			public void onResponse(String response, int id) {
				Log.e( TAG, "请求结果" );
				switch (id) {
					case 100:
						//						Toast.makeText( mContext, "http", Toast.LENGTH_SHORT ).show();
						break;
					case 101:
						//						Toast.makeText( mContext, "https", Toast.LENGTH_SHORT ).show();
						break;

					default:
						break;
				}
				if (response != null) {
					CacheUtils.putString( mContext, temsetUrl, response );
					RemoteParamsRedis remoteParamsRedis = new RemoteParamsRedis(  );
					try {
						JSONObject jsonObject = new JSONObject( response );
						String msg = jsonObject.optString( "msg" );
						int code = jsonObject.optInt( "code" );
						int data = jsonObject.optInt( "data" );

						remoteParamsRedis.setCode( code );
						remoteParamsRedis.setData( data );
						remoteParamsRedis.setMsg( msg );

						mTemSetPb.setCurrentProgress( remoteParamsRedis.getData() );
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					Log.e( TAG, "请求结果为空" );
				}
			}
		} );
		String humidityUrl =  PARAMSRADIUS +"humidity";
		OkHttpUtils.post().url( humidityUrl ).build().execute( new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				e.printStackTrace();
				Log.e( TAG, "请求失败" );
			}

			@Override
			public void onResponse(String response, int id) {
				Log.e( TAG, "请求结果" );
				switch (id) {
					case 100:
						//						Toast.makeText( mContext, "http", Toast.LENGTH_SHORT ).show();
						break;
					case 101:
						//						Toast.makeText( mContext, "https", Toast.LENGTH_SHORT ).show();
						break;

					default:
						break;
				}
				if (response != null) {
					CacheUtils.putString( mContext, humidityUrl, response );
					RemoteParamsRedis remoteParamsRedis = new RemoteParamsRedis(  );
					try {
						JSONObject jsonObject = new JSONObject( response );
						String msg = jsonObject.optString( "msg" );
						int code = jsonObject.optInt( "code" );
						int data = jsonObject.optInt( "data" );

						remoteParamsRedis.setCode( code );
						remoteParamsRedis.setData( data );
						remoteParamsRedis.setMsg( msg );

						mShiduRatio.setCurrentProgress( remoteParamsRedis.getData());
//						mShiduRatio.setCurrentProgress( 0.5f);
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					Log.e( TAG, "请求结果为空" );
				}
			}
		} );



		return rootView;
	}






	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage( msg );
			switch (msg.what) {
				case HIDE_REMOTE_MEDIACONTROLLER:
					isHiddenMediaController();
					break;
				case PROGRESS:
					int currentPosition = mVideoView.getCurrentPosition();
					mMediacontrollerSeekbar.setProgress( currentPosition );
					//每一秒更新一次
					mHandler.removeMessages( PROGRESS );
					mHandler.sendEmptyMessageDelayed( PROGRESS, 1000 );


					break;

				default:
					break;
			}
		}
	};

	//手势识别器
	GestureDetector mDetector = new GestureDetector( mContext, new GestureDetector.SimpleOnGestureListener() {


		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (isShow) {
				//隐藏
				isHiddenMediaController();
				//移除隐藏消息
				mHandler.removeMessages( HIDE_REMOTE_MEDIACONTROLLER );
			} else {
				//显示
				isShowMediaController();
				mHandler.sendEmptyMessageDelayed( HIDE_REMOTE_MEDIACONTROLLER, 5000 );
			}

			return super.onSingleTapConfirmed( e );

		}
	} );

	//在Fragment中注册事件

	MainActivity.MyOnTouchListener onTouchListener = ev -> {
		mDetector.onTouchEvent( ev );
		return false;
	};


	//显示控制栏
	private void isShowMediaController() {
		mRemoteMediaController.setVisibility( View.VISIBLE );
		isShow = true;
	}

	//隐藏控制栏
	private void isHiddenMediaController() {
		mRemoteMediaController.setVisibility( View.GONE );
		isShow = false;
	}


	//
	//	private IncubatorParamBean parseJaon(String json) {
	//		IncubatorParamBean incubatorParamBean = new IncubatorParamBean();
	//		try {
	//			JSONObject jsonObject = new JSONObject( json );
	//			int temperatureSet = jsonObject.optInt( "temperatureSet" );
	//			int temperature = jsonObject.optInt( "temperature" );
	//			int CO2Set = jsonObject.optInt( "CO2Set" );
	//			int CO2 = jsonObject.optInt( "CO2" );
	//			int humidity = jsonObject.optInt( "humidity" );
	//			Log.e( TAG, "yixieshuju" + temperature + temperatureSet + CO2 + CO2Set + humidity );
	//		} catch (JSONException e) {
	//			e.printStackTrace();
	//		}
	//
	//		return incubatorParamBean;
	//	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}


	@OnClick({R.id.myvideo, R.id.video_back, R.id.video_name, R.id.ll_top, R.id.mediacontroller_play_pause, R.id.mediacontroller_time_current, R.id.mediacontroller_seekbar, R.id.mediacontroller_time_total, R.id.video_player_mute, R.id.video_player_scale, R.id.bottom_player_controller, R.id.ll_bottom, R.id.myprogressbar, R.id.remote_media_controller})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.myvideo:
				break;
			case R.id.video_back:
				mHandler.sendEmptyMessageDelayed( HIDE_REMOTE_MEDIACONTROLLER, 5000 );
				break;
			case R.id.video_name:
				break;
			case R.id.ll_top:

				break;
			case R.id.mediacontroller_play_pause:
				mHandler.sendEmptyMessageDelayed( HIDE_REMOTE_MEDIACONTROLLER, 5000 );
				if (mVideoView.isPlaying()) {
					//暂停状态
					mVideoView.pause();
					//					ImageView imageView = (ImageView)findViewById(R.id.imageView);
					//					imageView.setImageResource(R.drawable.newscar);
					//					LayoutParams params = imageView.getLayoutParams();
					//					params.height=200;
					//					params.width =100;
					//					imageView.setLayoutParams(params);
					mMediacontrollerPlayPause.setImageResource( R.drawable.nemediacontroller_pause );
					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMediacontrollerPlayPause.getLayoutParams();
					params.height = PixeUtils.dip2px( mContext, 40 );

					params.width = PixeUtils.dip2px( mContext, 10 );
					mMediacontrollerPlayPause.setLayoutParams( params );
				} else {
					//视频播放
					mVideoView.start();
					mMediacontrollerPlayPause.setImageResource( R.drawable.nemediacontroller_play );
					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMediacontrollerPlayPause.getLayoutParams();
					params.height = PixeUtils.dip2px( mContext, 40 );

					params.width = PixeUtils.dip2px( mContext, 10 );
					mMediacontrollerPlayPause.setLayoutParams( params );
					//按钮状态设置为暂停
					//					mMediacontrollerPlayPause.setPadding( 30, 30, 30, 30 );
					//					mMediacontrollerPlayPause.setBackgroundResource( R.drawable.nemediacontroller_play );
				}
				break;
			case R.id.mediacontroller_time_current:
				break;
			case R.id.mediacontroller_seekbar:
				mHandler.sendEmptyMessageDelayed( HIDE_REMOTE_MEDIACONTROLLER, 5000 );
				//视频总时长
				int duration = mMyvideo.getDuration();


				break;
			case R.id.mediacontroller_time_total:
				break;
			case R.id.video_player_mute:
				mHandler.sendEmptyMessageDelayed( HIDE_REMOTE_MEDIACONTROLLER, 5000 );

				if (!isMute) {
					mMediaPlayer.setVolume( 0f, 0f );
					mVideoPlayerMute.setImageResource( R.drawable.nemediacontroller_mute01 );
					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMediacontrollerPlayPause.getLayoutParams();
					params.height = PixeUtils.dip2px( mContext, 40 );

					params.width = PixeUtils.dip2px( mContext, 10 );
					mVideoPlayerMute.setLayoutParams( params );

				} else {
					mVideoPlayerMute.setImageResource( R.drawable.nemediacontroller_mute02 );
					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMediacontrollerPlayPause.getLayoutParams();
					params.height = PixeUtils.dip2px( mContext, 40 );

					params.width = PixeUtils.dip2px( mContext, 10 );
					mVideoPlayerMute.setLayoutParams( params );
				}
				break;
			case R.id.video_player_scale:
				mHandler.sendEmptyMessageDelayed( HIDE_REMOTE_MEDIACONTROLLER, 5000 );
				startAotic();
				break;
			case R.id.bottom_player_controller:

				break;
			case R.id.ll_bottom:

				break;
			case R.id.myprogressbar:
				break;
			case R.id.remote_media_controller:

				break;


		}
		mHandler.removeMessages( HIDE_REMOTE_MEDIACONTROLLER );
		mHandler.sendEmptyMessageDelayed( HIDE_REMOTE_MEDIACONTROLLER, 5000 );
	}


	/**
	 * 播放完成回调
	 * a callback to be invoked when playback of
	 * a media source has completed.
	 */
	private class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
		@Override
		public void onCompletion(MediaPlayer mp) {
			//			Toast.makeText( mContext, "播放完成", Toast.LENGTH_SHORT ).show();
		}
	}


	// the media file is loaded and ready to go.
	private class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {
		@Override
		public void onPrepared(MediaPlayer mp) {
			//准备好后回调开始播放
			mVideoView.start();
			mVideoPlayerMute.setOnClickListener( v -> {
				if (!isMute) {
					mp.setVolume( 0f, 0f );
					mVideoPlayerMute.setImageResource( R.drawable.nemediacontroller_mute01 );
					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMediacontrollerPlayPause.getLayoutParams();
					params.height = PixeUtils.dip2px( mContext, 40 );
					params.width = PixeUtils.dip2px( mContext, 10 );
					mVideoPlayerMute.setLayoutParams( params );
					isMute = true;

				} else {
					mp.setVolume( 1, 1 );
					mVideoPlayerMute.setImageResource( R.drawable.nemediacontroller_mute02 );
					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMediacontrollerPlayPause.getLayoutParams();
					params.height = PixeUtils.dip2px( mContext, 40 );
					params.width = PixeUtils.dip2px( mContext, 10 );
					mVideoPlayerMute.setLayoutParams( params );
					isMute = false;
				}
			} );

			if (mVideoView.isPlaying()) {
				mMyprogressbar.setVisibility( View.GONE );
			} else {
				mMyprogressbar.setVisibility( View.VISIBLE );
			}
			//默认隐藏控制面板
			isHiddenMediaController();
		}
	}

	/**
	 * Interface definition of a callback to be invoked when there
	 * has been an error during an asynchronous operation (other errors
	 * will throw exceptions at method call time).
	 * 播放出错的时候回调
	 */
	private class MyOnErrorListener implements MediaPlayer.OnErrorListener {
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			Toast.makeText( mContext, "播放出错了", Toast.LENGTH_SHORT ).show();
			return false;
		}
	}
}

package com.ko.accs2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.ko.accs2.Screen.ScreenAdapterUtils;
import com.ko.accs2.bean.MyEventBus;
import com.ko.accs2.bean.UserBean;
import com.ko.accs2.bean.Userbeans;
import com.ko.accs2.util.CacheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;


/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * 登录
 */
public class LoginActivity extends AppCompatActivity {


	private static final String TAG = LoginActivity.class.getSimpleName();
	@BindView(R.id.is_remember)
	CheckBox mIsRemember;
	@BindView(R.id.is_auto)
	CheckBox mIsAuto;
	private EditText mEtUser;
	private EditText mEtPassword;
	@BindView(R.id.main_login)
	Button mMainLogin;
	private String url;
	private String tvusername;
	private String tvpassword;

	//
	private SharedPreferences sp;
	private String mUsername;
	private String mPassword;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		ScreenAdapterUtils.setCusomDensity( this, getApplication() );
		setContentView( R.layout.login_activity );
		ButterKnife.bind( this );

		initView();
		//拿到SharedPreference对象
		sp = PreferenceManager.getDefaultSharedPreferences( this );
		boolean isRemember = sp.getBoolean( "isremember",false );
		boolean isAuto = sp.getBoolean( "isauto",false );
		//如果要记住密码需要把checkbox设置成true
		if (isRemember){
			mIsRemember.setChecked( true );


			String username = sp.getString( "username", mUsername );
			mEtUser.setText( username );

			String password = sp.getString( "password", mPassword );
			mEtPassword.setText( password );

			//如果想要自动登录开启必须要checkbox为true
			if (isAuto){
				mIsAuto.setChecked( true );
				//直接登录 此时此刻账户
				doLogin( username,password );
			}

		}
		//记住密码的复选框设置监听判断状态
		mIsRemember.setOnCheckedChangeListener( (buttonView, isChecked) -> {
			if (mIsRemember.isChecked()) {
				Log.e(TAG, "记住密码框未选中状态"  );
				sp.edit().putBoolean( "isremember",true ).apply();
			} else {
				Log.e(TAG, "记住密码框未选中"  );
				sp.edit().putBoolean( "isremember",false ).apply();
			}
		} );
		//自动登录的复选框框设置监听判断
		mIsAuto.setOnCheckedChangeListener( (buttonView, isChecked) -> {
			if (mIsAuto.isChecked()) {
				Log.e(TAG, "自动登录未选中状态"  );
				sp.edit().putBoolean( "isauto",true ).apply();
			} else {
				Log.e(TAG, "自动登录未选中" );
				sp.edit().putBoolean( "isauto",false ).apply();
			}
		} );


		//		Log.e(TAG, "tvp2" + tvpassword+tvusername);
		EventBus.getDefault().post( new MyEventBus( "hello" ) );
		mMainLogin.setOnClickListener( v -> {
			//得到文本框的输入内容
			//				Log.e(TAG, "tvp1" + tvpassword+tvusername);
			tvusername = mEtUser.getText().toString().trim();
			tvpassword = mEtPassword.getText().toString().trim();
			//				Log.e(TAG, "tvp0" + tvpassword+tvusername);
			doLogin(tvusername,tvpassword);
		} );
	}

	private void initView() {
		mEtUser = findViewById( R.id.et_user );
		mEtPassword = findViewById( R.id.et_password );
		mMainLogin = findViewById( R.id.main_login );
		mIsRemember = findViewById(R.id.is_remember);
		mIsAuto  = findViewById(R.id.is_auto);

	}


	//利用两次点击时间差实现双击退出应用
	private long mExitTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		} else {
			return super.onKeyDown( keyCode, event );
		}

	}

	public void exit() {
		if ((System.currentTimeMillis() - mExitTime) > 2000) {
			Toast.makeText( this, "再按一次退出程序", Toast.LENGTH_SHORT ).show();
			mExitTime = System.currentTimeMillis();
		} else {
			Intent intent = new Intent( Intent.ACTION_MAIN );
			intent.addCategory( Intent.CATEGORY_HOME );
			startActivity( intent );
			System.exit( 0 );
		}
	}


	private void doLogin(String tvusername,String tvpassword) {

		if (tvusername.equals( "" ) && tvpassword.equals( "" )) {
			Toast.makeText( this, "用户名或密码不能为空", Toast.LENGTH_SHORT ).show();
		} else {
			Log.e( TAG, "username" + tvusername + "password" + tvpassword );

		}
		//Log.e( TAG, "tvusername" + tvusername + "tvpassword" + tvpassword );

		if (mIsRemember.isChecked()){
			SharedPreferences.Editor editor = sp.edit();
			editor.putString( "username",tvusername );

			editor.putString( "password",tvpassword );
			editor.apply();
		}
		loginCheck(tvusername,tvpassword);



	}

	private void loginCheck(String tvusername, String tvpassword) {
		//post联网请求
		url = "http://keyonecn.com:8897/user/login?name=" + tvusername + "&&pass=" + tvpassword;
		Log.e( TAG, "tvpp" + tvpassword + tvusername );
		if (!tvusername.equals( "" ) && !tvpassword.equals( "" )) {
			Log.e( TAG, "tvppp" + tvpassword + tvusername );
			OkHttpUtils.post().url( url ).id( 100 ).build().execute( new StringCallback() {
				@Override
				public void onError(Call call, Exception e, int id) {
					e.printStackTrace();
					Log.e( TAG, "请求失败" );
				}

				@Override
				public void onResponse(String response, int id) {
					CacheUtils.putString( LoginActivity.this, "cacheusername", mEtUser.getText().toString().trim() );
					CacheUtils.putString( LoginActivity.this, "cachepassword", mEtPassword.getText().toString().trim() );

					Log.e( TAG, "onResponse:complete" );

					if (response != null) {
						//添加登录数据（用户名和密码）缓存
						CacheUtils.putString( LoginActivity.this, url, response );
						//解析登录数据和显示数据
						processData( response );
						Log.e( TAG, "response" + response );

					} else {
                             Log.e(TAG, "请求失败" );
					}
				}
			} );
		}

	}

	private void processData(String response) {
		UserBean userBean = new UserBean();

			  try {
				  JSONObject jsonObject = new JSONObject( response );
				  String msg = jsonObject.optString( "msg" );
				  int code = jsonObject.optInt( "code" );
				  if (code == 200) {



					  JSONObject data = jsonObject.optJSONObject( "data" );

					  userBean.setMsg( msg );
					  userBean.setCode( code );
					  UserBean.DataBean dataBean = new UserBean.DataBean();
					  userBean.setData( dataBean );
					  //第二层解析

					  int id = data.optInt( "id" );
					  String username = data.optString( "name" );
					  String password = data.optString( "pass" );
					  String content = data.optString( "content" );
					  int comID = data.optInt( "comID" );


					  dataBean.setId( id );
					  dataBean.setName( username );
					  dataBean.setPass( password );
					  dataBean.setContent( content );
					  dataBean.setComID( comID );

					  EventBus.getDefault().post( userBean  );

					  CacheUtils.putString( LoginActivity.this, "LoginActivitycode", String.valueOf( code ) );
					  CacheUtils.putString( LoginActivity.this,"Username",username );
					  startActivity( new Intent( LoginActivity.this, MainActivity.class ) );
					  finish();
				  }else if (code == 400) {
					  Toast.makeText( this, "用户名或密码输入错误", Toast.LENGTH_SHORT ).show();
				  }

			  } catch (JSONException e) {
				  e.printStackTrace();
			  }
		  }







}

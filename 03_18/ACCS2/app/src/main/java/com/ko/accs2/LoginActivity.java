package com.ko.accs2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.ko.accs2.bean.MyEventBus;
import com.ko.accs2.bean.UserBean;
import com.ko.accs2.bean.Userbeans;
import com.ko.accs2.fragment.User;
import com.ko.accs2.util.CacheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.ArrayList;
import java.util.List;


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
	private EditText mEtUser;
	private EditText mEtPassword;
	@BindView(R.id.main_login)
	Button mMainLogin;
	private String url;
	private String tvusername;
	private String tvpassword;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.login_activity );
		ButterKnife.bind( this );

		mEtUser = findViewById( R.id.et_user );
		mEtPassword = findViewById( R.id.et_password );
		mMainLogin = findViewById(R.id.main_login);

//		Log.e(TAG, "tvp2" + tvpassword+tvusername);
		EventBus.getDefault().post( new MyEventBus( "hello" ) );
		mMainLogin.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//得到文本框的输入内容
//				Log.e(TAG, "tvp1" + tvpassword+tvusername);
				tvusername =  mEtUser.getText().toString().trim();
				tvpassword =  mEtPassword.getText().toString().trim();
//				Log.e(TAG, "tvp0" + tvpassword+tvusername);

				doLogin();

			}
		} );
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


	private void doLogin() {


		//Log.e( TAG, "tvusername" + tvusername + "tvpassword" + tvpassword );

		if (tvusername.equals( "" ) && tvpassword.equals( "" )) {
			Toast.makeText( this, "用户名或密码不能为空", Toast.LENGTH_SHORT ).show();
		}else {
			Log.e(TAG, "username" + tvusername + "password" + tvpassword);
			//post联网请求
			url = "http://keyonecn.com:8897/user/login?name=" + tvusername + "&&pass=" + tvpassword;
			Log.e(TAG, "tvpp" + tvpassword+tvusername);
			if (!tvusername.equals( "" ) && !tvpassword.equals( "" )) {
				Log.e(TAG, "tvppp" + tvpassword+tvusername);
				OkHttpUtils.post().url( url ).id( 100 ).build().execute( new StringCallback() {
					@Override
					public void onError(okhttp3.Call call, Exception e, int id) {
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

						}
					}
				} );
			}
		}

	}

	private void processData(String response) {
		Userbeans userbeans = null;
		String msg = null;
		int code = 0;
		try {
			JSONObject jsonObject = new JSONObject( response );
			msg = jsonObject.optString( "msg" );
			code = jsonObject.optInt( "code" );
			int id = jsonObject.optInt( "id" );
			String username = jsonObject.optString( "name" );
			String password = jsonObject.optString( "pass" );
			String content = jsonObject.optString( "content" );
			int comID = jsonObject.optInt( "comID" );
			userbeans = new Userbeans( id, username, password );
		} catch (JSONException e) {
			e.printStackTrace();
		}
		CacheUtils.putString( LoginActivity.this, "LoginActivitycode", msg );
		if (code == 200) {
			startActivity( new Intent( LoginActivity.this, MainActivity.class ) );
			finish();
		} else if (code == 400) {
			Toast.makeText( this, "用户名或密码输入错误", Toast.LENGTH_SHORT ).show();
		}


	}


}

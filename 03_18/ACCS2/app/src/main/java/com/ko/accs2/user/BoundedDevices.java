package com.ko.accs2.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.ko.accs2.R;
import com.ko.accs2.adapter.BoundedDeviceAdapter;
import com.ko.accs2.bean.BoundedDeviceBean;
import com.ko.accs2.fragment.User;
import com.ko.accs2.util.CacheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.ko.accs2.constant.Constant.BOUNDEDDEVICE;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class BoundedDevices extends AppCompatActivity implements View.OnClickListener {
	private static final String TAG = BoundedDevices.class.getSimpleName();
	@BindView(R.id.boundeddevice_back_arrow)
	ImageView mBoundeddeviceBackArrow;
	@BindView(R.id.dangqianshebei)
	TextView mDangqianshebei;
	@BindView(R.id.dangqianshebeima)
	TextView mDangqianshebeima;
	@BindView(R.id.device_list)
	ListView mDeviceList;
	private String url;
	private ListView mBoundedDeviceListview;
	private ConstraintLayout mDangqianshebiekuang;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.bounded_device );
		ButterKnife.bind( this );
		StatusBarCompat.setStatusBarColor( this, getResources().getColor( R.color.colorRB ), false );
		mBoundeddeviceBackArrow.setOnClickListener( this );
		initView();
		initData();

	}

	private void initView() {
		mBoundedDeviceListview = findViewById( R.id.device_list );
		mDangqianshebei = findViewById(R.id.dangqianshebei);
		mDangqianshebeima = findViewById(R.id.dangqianshebeima);
		mDangqianshebiekuang = findViewById(R.id.dangqianshebeikuang);

	}



	private void initData() {
		url = BOUNDEDDEVICE;
		OkHttpUtils.post().url( url ).id( 100 ).build().execute( new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				e.printStackTrace();
				Log.e( TAG, "请求失败" );
			}

			@Override
			public void onResponse(String response, int id) {
				Log.e( TAG, "onResponse:complete" );
				switch (id) {
					case 100:
						Toast.makeText( BoundedDevices.this, "http", Toast.LENGTH_SHORT ).show();
						break;
					case 101:
						Toast.makeText( BoundedDevices.this, "https", Toast.LENGTH_SHORT ).show();
						break;

					default:
						break;


				}
				if (response != null) {
					//添加缓存
					CacheUtils.putString( BoundedDevices.this, url, response );
					//解析数据和显示数据
					processData( response );
					mDangqianshebiekuang.setVisibility( View.VISIBLE );
				} else {
					mDangqianshebiekuang.setVisibility( View.GONE );
					Log.e( TAG, "请求结果为空");
				}
			}
		} );


//		BoundedDeviceAdapter.OnItemBoundedDeviceListener onItemBoundedDeviceListener = new BoundedDeviceAdapter.OnItemBoundedDeviceListener() {
//			@Override
//			public void onItemClick(int position) {
//				BoundedDeviceBean.ItemBoundedDevice itemBoundedDevice =  data.get( position );
//				Log.e(TAG, "datasssss" + data.toString());
//				mDangqianshebei.setText( itemBoundedDevice.getContent() );
//				mDangqianshebeima.setText( itemBoundedDevice.getUuid() );
//				Toast.makeText(BoundedDevices.this,"woshibeidianjide"+position, Toast.LENGTH_SHORT).show();
//				Log.e(TAG, "woooooshibeidianjideweizhi" + position);
//			}
//		};

	}


	private void processData(String response) {
		BoundedDeviceBean deviceBean = parseJson( response );
		//获取数据
		List<BoundedDeviceBean.ItemBoundedDevice> data = deviceBean.getData();


		//新建一个适配器添加数据
		BoundedDeviceAdapter boundedDeviceAdapter = new BoundedDeviceAdapter( BoundedDevices.this, data ,mDangqianshebei,mDangqianshebeima);
		mBoundedDeviceListview.setAdapter( boundedDeviceAdapter );
		Log.e( TAG, "data" + data );



	}

	private BoundedDeviceBean parseJson(String response) {
		//解析json数据
		BoundedDeviceBean deviceBean = new BoundedDeviceBean();


		try {
			JSONObject jsonObject = new JSONObject( response );
			JSONArray jsonArray = jsonObject.optJSONArray( "data" );
			if (jsonArray != null && jsonArray.length() > 0) {
				List<BoundedDeviceBean.ItemBoundedDevice> mydata = new ArrayList<>();
				deviceBean.setData( mydata );
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObjectItem = (JSONObject) jsonArray.get( i );

					if (jsonObjectItem != null) {

						BoundedDeviceBean.ItemBoundedDevice itemBoundedDevice = new BoundedDeviceBean.ItemBoundedDevice();

						//id
						int id = jsonObjectItem.optInt( "id" );
						itemBoundedDevice.setId( id );
						//uuid
						String uuid = jsonObjectItem.optString( "uuid" );
						itemBoundedDevice.setUuid( uuid );
						//content
						String content = jsonObjectItem.optString( "content" );
						itemBoundedDevice.setContent( content );
						//typeID
						int typeID = jsonObjectItem.optInt( "typeID" );
						itemBoundedDevice.setTypeID( typeID );

						//comID
						int comID = jsonObjectItem.optInt( "comID" );
						itemBoundedDevice.setComID( comID );


						//把数据添加到集合
						mydata.add( itemBoundedDevice );
					}
				}
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}



		return deviceBean;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.boundeddevice_back_arrow:
				BoundedDevices.this.finish();
				break;

			default:

				break;
		}
	}

	@OnClick({R.id.dangqianshebei, R.id.dangqianshebeima})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.dangqianshebei:
				break;
			case R.id.dangqianshebeima:
				break;
		}
	}


}

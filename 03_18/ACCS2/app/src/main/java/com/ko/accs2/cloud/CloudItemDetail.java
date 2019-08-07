package com.ko.accs2.cloud;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.hw.ycshareelement.transition.TextViewStateSaver;
import com.ko.accs2.R;
import com.ko.accs2.adapter.CellImageGlideAdapter;
import com.ko.accs2.bean.CellImageBean;
import com.ko.accs2.bean.CloudDataBean;
import com.ko.accs2.util.CacheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.ko.accs2.cloud.CloudItem.KEY_CONTACTS;
import static com.ko.accs2.constant.Constant.IMAGEURL;


/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * 云端三级界面
 */
public class CloudItemDetail extends AppCompatActivity implements View.OnClickListener, IShareElements {
	private static final String TAG = CloudItemDetail.class.getSimpleName();
	@BindView(R.id.clouditem_iv_detail_arrow)
	ImageView mClouditemIvDetailArrow;
	@BindView(R.id.clouditem_chuandaishijian)
	TextView mClouditemChuandaishijian;
	@BindView(R.id.clouditem_peiyangshijian)
	TextView mClouditemPeiyangshijian;
	@BindView(R.id.clouditem_xibaoshuliang)
	TextView mClouditemXibaoshuliang;
	@BindView(R.id.clouditem_chuandaibili)
	TextView mClouditemChuandaibili;
	@BindView(R.id.clouditem_fugalv)
	TextView mClouditemFugalv;
	@BindView(R.id.clouditem_beizhu)
	TextView mClouditemBeizhu;
	@BindView(R.id.clouditem_huanyeshijian)
	TextView mClouditemHuanyeshijian;
	@BindView(R.id.clouditem_daici)
	TextView mClouditemDaici;
	@BindView(R.id.clouditem_pihao)
	TextView mClouditemPihao;
	@BindView(R.id.rv)
	RecyclerView mRv;
	@BindView(R.id.cloueitem_xibaomingcheng3)
	TextView mCloueitemXibaomingcheng3;
	@BindView(R.id.textView3)
	TextView mTextView3;
	@BindView(R.id.detail_xibaobianhao)
	TextView mDetailXibaobianhao;
	private String url;
	private ImageView mImageView;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		//        //打开FEATURE_CONTENT_TRANSITIONS开关，可选择默认是打开的
		//        requestWindowFeature(getWindow().FEATURE_CONTENT_TRANSITIONS);
		//        //设置其他view的退出方式
		//        getWindow().setExitTransition(new Slide(Gravity.CENTER));
		YcShareElement.enableContentTransition( getApplication());
		YcShareElement.setExitTransition( this, new Slide( Gravity.LEFT ));
		super.onCreate( savedInstanceState );
		setContentView( R.layout.cloud_item_detail );
		/**
		 * 状态栏颜色
		 */
		StatusBarCompat.setStatusBarColor( CloudItemDetail.this, CloudItemDetail.this.getResources().getColor( R.color.colorRB ), false );
		ButterKnife.bind( this );

		//获得数据

		CloudDataBean.ItemData itemData = (CloudDataBean.ItemData) getIntent().getSerializableExtra( KEY_CONTACTS );

		initView();
		initListener();
		initData( itemData );

	}

	private void initData(CloudDataBean.ItemData itemData) {
		mDetailXibaobianhao.setText( itemData.getSerial_num() );
		mCloueitemXibaomingcheng3.setText( itemData.getCellname() );
		mClouditemChuandaishijian.setText( itemData.getPassage_time() );
		mClouditemPeiyangshijian.setText( itemData.getCultur_time() );
		mClouditemXibaoshuliang.setText( itemData.getCellnum() );
		mClouditemChuandaibili.setText( itemData.getStatus() );
		mClouditemFugalv.setText( itemData.getCell_coverage() );
		mClouditemBeizhu.setText( itemData.getRemark() );
		mClouditemHuanyeshijian.setText( itemData.getChange_liquid_time() );
		mClouditemDaici.setText( itemData.getSubmission() );
		mClouditemPihao.setText( itemData.getBatch_num() );

		url = IMAGEURL + itemData.getSerial_num().trim().toString();
		Log.e(TAG, "url" +IMAGEURL + itemData.getSerial_num().trim().toString() );
		OkHttpUtils.post().url( url ).id( 100 ).build().execute( new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				e.printStackTrace();
				Log.e( TAG, "请求失败");
			}

			@Override
			public void onResponse(String response, int id) {
				Log.e( TAG, "onResponse:complete");
				switch (id) {
					case 100:
						Toast.makeText( CloudItemDetail.this, "http", Toast.LENGTH_SHORT ).show();
						break;
					case 101:
						Toast.makeText( CloudItemDetail.this, "https", Toast.LENGTH_SHORT ).show();
						break;

					default:
						break;


				}
				if (response != null) {
					//添加缓存
					CacheUtils.putString( CloudItemDetail.this, url, response );

					//解析数据和显示数据
					processData( response );


				} else {
					Log.e( TAG, "请求结果为空" );
				}
			}
		} );


	}

	private void processData(String response) {
		CellImageBean imageBean = parsedJson( response );
		//得到数据
		List<CellImageBean.ItemImage> data = imageBean.getData();
		CellImageGlideAdapter glideAdapter = new CellImageGlideAdapter( CloudItemDetail.this, data );
		mRv.setAdapter( glideAdapter );
		mRv.setLayoutManager( new GridLayoutManager( CloudItemDetail.this, 3, GridLayoutManager.VERTICAL, false ) );

		glideAdapter.setOnRecyclerViewItemClickListener( new CellImageGlideAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, CellImageBean.ItemImage datas, ImageView imageView, TextView textView) {

				Intent i = new Intent( CloudItemDetail.this, ImageActivity.class );
				CellImageBean.ItemImage itemImage = datas;
				Bundle bundle = new Bundle();
				bundle.putSerializable("itemData", datas );
				i.putExtras(bundle);
				android.support.v4.util.Pair<View, String> image = new android.support.v4.util.Pair( imageView, "image");
				android.support.v4.util.Pair<View, String> text = new android.support.v4.util.Pair( textView, "text");

				ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation( CloudItemDetail.this, image, text );
				startActivity( i, optionsCompat.toBundle() );
			}
		} );

	}

	private CellImageBean parsedJson(String response) {
		/**
		 * 解析json数据
		 */
		CellImageBean imageBean = new CellImageBean();


		try {
			JSONObject jsonObject = new JSONObject( response );
			JSONArray jsonArray = jsonObject.optJSONArray( "data" );
			if (jsonArray != null && jsonArray.length() > 0) {
				List<CellImageBean.ItemImage> mydata = new ArrayList<>();
				imageBean.setData( mydata );
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObjectItem = (JSONObject) jsonArray.get( i );

					if (jsonObjectItem != null) {

						CellImageBean.ItemImage itemImage = new CellImageBean.ItemImage();
						//图片名字
						String name = jsonObjectItem.optString( "name" );
						itemImage.setName( name );

						//主键id
						int id = jsonObjectItem.optInt( "id" );
						itemImage.setId( id );
						//机器id
						int mid = jsonObjectItem.optInt( "mid" );
						itemImage.setMacID( mid );
						//生成图片时间
						String time = jsonObjectItem.optString( "time" );
						itemImage.setTime( time );
						//图片地址
						String imageurl = jsonObjectItem.optString( "imageurl" );
						itemImage.setImageurl( imageurl );
						//把数据添加到集合
						mydata.add( itemImage );
					}
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return imageBean;
	}


	private void initListener() {
		mClouditemIvDetailArrow.setOnClickListener( this );
	}

	private void initView() {
		mTextView3 = findViewById( R.id.textView3 );
		mDetailXibaobianhao = findViewById( R.id.detail_xibaobianhao );
		mCloueitemXibaomingcheng3 = findViewById( R.id.cloueitem_xibaomingcheng3 );
		mClouditemIvDetailArrow = findViewById( R.id.clouditem_iv_detail_arrow );
		mClouditemChuandaishijian = findViewById( R.id.clouditem_chuandaishijian );
		mClouditemPeiyangshijian = findViewById( R.id.clouditem_peiyangshijian );
		mClouditemXibaoshuliang = findViewById( R.id.clouditem_xibaoshuliang );
		mClouditemChuandaibili = findViewById( R.id.clouditem_chuandaibili );
		mClouditemFugalv = findViewById( R.id.clouditem_fugalv );
		mClouditemBeizhu = findViewById( R.id.clouditem_beizhu );
		mClouditemHuanyeshijian = findViewById( R.id.clouditem_huanyeshijian );
		mClouditemDaici = findViewById( R.id.clouditem_daici );
		mClouditemPihao = findViewById( R.id.clouditem_pihao );
		mRv = findViewById( R.id.rv );

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.clouditem_iv_detail_arrow:
				CloudItemDetail.this.finish();
				break;

			default:
				break;
		}
	}

	@Override
	public ShareElementInfo[] getShareElements() {
		return new ShareElementInfo[]{};
	}
}

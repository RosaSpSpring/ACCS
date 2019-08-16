package com.ko.accs2.cloud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.hw.ycshareelement.transition.IShareElements;
import com.ko.accs2.MainActivity;
import com.ko.accs2.R;
import com.ko.accs2.adapter.CloudItemAdapter;
import com.ko.accs2.bean.CloudDataBean;
import com.ko.accs2.util.CacheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import java.util.ArrayList;
import java.util.List;

import static com.ko.accs2.constant.Constant.CLOUDMOHUSEARCH;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * 云端的二级界面
 */
public class CloudItem extends Activity implements View.OnClickListener{
    private static final String TAG = CloudItem.class.getSimpleName();
    public static final String KEY_CONTACTS = "itemData";
    @BindView(R.id.clouditem_iv_arrow)
    ImageView mClouditemIvArrow;
    @BindView(R.id.clouditem_lv)
    ListView mClouditemLv;
    private String url;




	@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_item_show);
        /**
         * 状态栏颜色
         */
        StatusBarCompat.setStatusBarColor(CloudItem.this, CloudItem.this.getResources().getColor(R.color.colorRB), false);
        ButterKnife.bind(this);

        //获取从云端fragment的url
        String cloudUrl = getIntent().getExtras().getString( "CloudUrl" );
        Log.e(TAG, "cloudUrl" + cloudUrl);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mClouditemIvArrow.setOnClickListener(this);
    }

    private void initData() {
        /**
         * 子线程联网请求
         */
        new  Thread(new Runnable() {
            @Override
            public void run() {

                    Log.e(TAG, "联网请求当前信息");
                    //post联网请求云端数据
                    url = CLOUDMOHUSEARCH;
                    OkHttpUtils
                            .post()
                            .url(url)
                            .id(100)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    e.printStackTrace();
                                    Toast.makeText(CloudItem.this,"服务器返回数据失败", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "请求失败");
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e(TAG, "onResponse:complete");
                                    switch (id) {
                                        case 100:
                                            break;
                                        case 101:
                                            break;

                                        default:
                                            break;
                                    }
                                    if (response != null) {
                                        //添加缓存
                                        CacheUtils.putString(CloudItem.this, url, response);

                                        //解析数据和显示数据
                                        processData(response);
                                    } else {
                                        Log.e(TAG, "请求结果为空");
                                    }
                                }
                            });
                }

        }).start();
    }

    private void processData(String json) {

        //解析数据
        CloudDataBean cloudDataBean = parsedJson(json);
        final List<CloudDataBean.ItemData> data = cloudDataBean.getData();


        //设置适配器显示数据
        CloudItemAdapter adapter = new CloudItemAdapter(CloudItem.this, data);
        mClouditemLv.setAdapter(adapter);
        adapter.setOnCloudItemClickListener(new CloudItemAdapter.OnCloudItemClickListener() {
            @Override
            public void onItemClick(int posion) {
                CloudDataBean.ItemData itemData = data.get(posion);

                Log.e(TAG, "itemData!!!!" + itemData.toString());
                Intent intent = new Intent(CloudItem.this, CloudItemDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemData", itemData );
                intent.putExtras(bundle);
                /*Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("serinfo", serInfo);
                intent.setClass(MainActivity.this, ResultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //获得Serializable方式传过来的值
                */
                startActivity(intent);
               //假如不要这个finish（）点击item会不会只回到上一级
//                finish();
            }
        });

    }

    private CloudDataBean parsedJson(String response) {
        /**
         * 解析json数据
         */
        CloudDataBean dataBean = new CloudDataBean();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("data");

            if (jsonArray != null && jsonArray.length() > 0) {
                List<CloudDataBean.ItemData> mydata = new ArrayList<>();
                dataBean.setData(mydata);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);

                    if (jsonObjectItem != null) {
						CloudDataBean.ItemData cloudItem = new CloudDataBean.ItemData();
                        //细胞名字
                        String cellname = jsonObjectItem.optString("cellname");//cellname
                        cloudItem.setCellname(cellname);
                        //细胞位置
                        int toid = jsonObjectItem.optInt("toid");//toid
                        cloudItem.setToid(toid);
                        //细胞编号
                        String serial_num = jsonObjectItem.optString("serial_num");//name
                        cloudItem.setSerial_num(serial_num);
                        //0：待观察 1：待换液
                        String status = jsonObjectItem.optString("status");
                        cloudItem.setStatus(status);
                        //代次
                        String submission = jsonObjectItem.optString("submission");
                        cloudItem.setSubmission(submission);
                        //批号
                        String batch_num = jsonObjectItem.optString("batch_num");
                        cloudItem.setBatch_num(batch_num);
                        //培养时间
                        String culture_time = jsonObjectItem.optString("culture_time");
                        cloudItem.setCultur_time(culture_time);
                        //传代时间
                        String passage_time = jsonObjectItem.optString("passage_time");
                        cloudItem.setPassage_time(passage_time);
                        //换液时间
                        String change_liquid_time = jsonObjectItem.optString("change_liquid_time");
                        cloudItem.setChange_liquid_time(change_liquid_time);
                        //细胞数量
                        String cellnum = jsonObjectItem.optString("cellnum");
                        cloudItem.setCellnum(cellnum);
                        //铺满率
                        String cell_coverage = jsonObjectItem.optString("cell_coverage");
                        cloudItem.setCell_coverage(cell_coverage);
                        //传代比例
                        String passage_ratio = jsonObjectItem.optString("passage_ratio");
                        cloudItem.setPassage_ratio(passage_ratio);
                        //备注
                        String remark = jsonObjectItem.optString("remark");
                        cloudItem.setRemark(remark);
                        //主键id
                        int id = jsonObjectItem.optInt("id");
                        cloudItem.setId(id);
                        //机器id
                        int mid = jsonObjectItem.optInt("mid");
                        cloudItem.setMid(mid);
                        //把数据添加到集合
                        mydata.add(cloudItem);

                    }
                }
            }else {
                Toast.makeText(this,"请求数据为0条", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataBean;


    }

    private void initView() {
        mClouditemIvArrow = findViewById(R.id.clouditem_iv_arrow);
        mClouditemLv = findViewById(R.id.clouditem_lv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clouditem_iv_arrow:
                CloudItem.this.finish();
                break;

            default:
                break;
        }
    }


}

package com.ko.testtrees;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.ko.testtrees.bean.CurData;
import com.ko.testtrees.bean.MyString;
import com.ko.testtrees.widget.FirstItemViewFactory;
import com.ko.testtrees.widget.FirstNameView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;

public class MainActivity extends AppCompatActivity {

	public static final String TAG = MainActivity.class.getSimpleName();

	private LinearLayout myfirstViwe;
	private List<String> list1 = new ArrayList<>();
	private List<String> list2 = new ArrayList<>();
	private FirstNameView fistNameView;
	private TreeNode rootNode;
	private List<TreeNode> mTreeNodes;
	private TreeView treeView;

	private List<CurData.DataBean> listByName = new ArrayList<>(  );
	private List cengji = new ArrayList(  );
	private List cengji2 = new ArrayList(  );
	public  Map mMap = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String json = MyString.CURDATA;
		CurData curData1 = paresJson( json );
		List<CurData.DataBean> data = curData1.getData();
		Log.e(TAG, "看似" + curData1.getData().size());
		for (int i = 0; i < data.size(); i++) {
			int node = data.get( i ).getNode();
			Log.e(TAG, "看似*************"  + node);

			   CurData.DataBean dataBean = data.get( i );
			   if (node == 0 ){
				   listByName.add( dataBean );
				   list1.add( dataBean.getName() );
			   }else if (node == 1){
			   	   list2.add( dataBean.getName());
			   }else {
			   	Log.e(TAG, "數據不存在" );
			   }
		}

//		for (int i = 0; i < listByName.size(); i++) {
//			if (cengji.contains( list1.get( i ) )){
//
//			};
//		}

		for (int i = 0; i < data.size(); i++){
			if (data.get( i ).getNode() == 1) {
				cengji.add( data.get( i ) );
				Log.e(TAG, "cengji.Size" + cengji.size());

			}
		}
		for (int i = 0; i < listByName.size(); i++) {
			for (int j = 0; j < cengji.size(); j++) {
				if (cengji.contains( listByName.get( i ))){
					cengji2.add( cengji.get( j ) );
					Log.e(TAG, "cengji _____" + cengji2.size() );
				}else {
					Log.e(TAG, "bubaohan" );
				}
			}
		}


		init();
	}

	private CurData paresJson(String json) {
		CurData curData = new CurData();
		try {
			JSONObject jsonObject = new JSONObject( json );
			//第一层解析
			JSONArray data = jsonObject.optJSONArray( "data" );
			String msg = jsonObject.optString( "msg" );
			curData.setMsg( msg );
			List<CurData.DataBean> dataBean = new ArrayList<>(  );
			curData.setData( dataBean );
          //第二层解析
			for (int i = 0; i < data.length(); i++) {
				JSONObject jsonObject1 = data.optJSONObject( i );
				if (jsonObject1 != null){
					String id = jsonObject1.optString( "id" );
					String name = jsonObject1.optString( "name" );
					String daici = jsonObject1.optString( "daici" );
					String caozuo = jsonObject1.optString( "caozuo" );
					String pingshu = jsonObject1.optString( "pingshu" );
					String date = jsonObject1.optString( "date" );
					String operator = jsonObject1.optString( "operator" );
					int node = jsonObject1.optInt( "node" );
					//第二層封裝
					CurData.DataBean dataBean1 = new CurData.DataBean();
					dataBean1.setId( id );
					dataBean1.setName( name );
					dataBean1.setDaici(daici  );
					dataBean1.setCaozuo(caozuo  );
					dataBean1.setPingshu(pingshu  );
					dataBean1.setDate( date);
					dataBean1.setOperator(operator  );
					dataBean1.setNode( node );
					dataBean.add( dataBean1 );
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return curData;
	}



	private void init() {
		myfirstViwe = (LinearLayout) findViewById(R.id.myView_first);
		fistNameView = new FirstNameView();
		loadData();
		treeView = new TreeView(rootNode,this,new FirstItemViewFactory());

		View view = treeView.getView();
		view.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		myfirstViwe.addView(view);

	}

	private void loadData(){

		//跟节点
		rootNode = TreeNode.root();

		TreeNode treeNode1 = new TreeNode(listByName);
		treeNode1.setLevel(0);
		treeNode1.setExpanded(true);
		treeNode1.setItemClickEnable(true);

		for(int i = 0; i < listByName.size(); i++){
			TreeNode treeNode2 = new TreeNode(listByName.get( i ));
			treeNode2.setLevel(1);//设置级数
			treeNode2.setExpanded(false);//设置是否展开
			treeNode2.setItemClickEnable(true);//是否可点击
			treeNode1.addChild(treeNode2);

			for(int k = 0; k < cengji2.size(); k++){
				TreeNode treeNode3 = new TreeNode(cengji2.get( k ));
				treeNode3.setLevel(2);
				treeNode3.setItemClickEnable(false);
				treeNode2.addChild(treeNode3);
			}

		}
		rootNode.addChild(treeNode1);

	}

	public void btnClick(View view){
		//得到选择的类目
		mTreeNodes = treeView.getSelectedNodes();
		Log.d("wls", "btnClick: " + "************" + mTreeNodes.size());
		for(int i = 0; i < mTreeNodes.size(); i++){
			Log.d("wls", "init: "  + mTreeNodes.get(i).getValue());
		}
	}

}

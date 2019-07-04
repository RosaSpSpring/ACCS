package com.ko.testtrees.widget;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ko.testtrees.MainActivity;
import com.ko.testtrees.R;
import com.ko.testtrees.bean.CurData;

import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import me.texy.treeview.base.BaseNodeViewBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class AllList extends BaseNodeViewBinder {
public static final String TAG = AllList.class.getSimpleName();

    private TextView mName;
    private TextView mDaici;
    private TextView mPinhshu;
    private TextView mOperator;
    private TextView mDate;
    private TextView mCaozuo;
    public AllList(View itemView) {
        super(itemView);
//        mName = itemView.findViewById(R.id.tv_name);
//        mDaici = itemView.findViewById(R.id.tv_daici);
//        mPinhshu = itemView.findViewById(R.id.tv_pingshu);
//        mOperator = itemView.findViewById(R.id.tv_operator);
//        mCaozuo = itemView.findViewById(R.id.tv_caozuo);
//        mDate = itemView.findViewById(R.id.tv_date);
    }


    @Override
    public int getLayoutId() {
        return R.layout.all_item;
    }

    @Override
    public void bindView(TreeNode treeNode) {

        ArrayList<CurData.DataBean> value = (ArrayList<CurData.DataBean>) treeNode.getValue();

        if(value != null){
            for (int i = 0; i < value.size(); i++) {
               Log.e(TAG, "name---------------------------------" + value.get( i ).getName());
               Log.e(TAG, "Value.Size()" + value.size());
//                mName.setText( value.get( i ).getName() );
//                mDaici.setText( value.get( i ).getDaici() );
//                mCaozuo.setText( value.get( i ).getCaozuo() );
//                mDate.setText( value.get( i ).getDate() );
//                mPinhshu.setText( value.get( i ).getPingshu() );
//                mOperator.setText( value.get( i ).getOperator() );
            }
        }else {
            Log.e(TAG, "请求数据错误" );
        }

    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
//        if(expand){
//            image_arrow.setImageResource(R.mipmap.img_catergory_spinner_icon);
//        }else {
//            image_arrow.setImageResource(R.mipmap.img_catergory_icon);
//        }
    }
}

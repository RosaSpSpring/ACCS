package com.ko.testtrees.widget;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ko.testtrees.R;
import com.ko.testtrees.bean.CurData;

import java.util.ArrayList;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.CheckableNodeViewBinder;

/**
 * Created by Administrator on 2017/7/4.
 */

public class FirstAllList extends BaseNodeViewBinder {
    public static final String TAG = FirstAllList.class.getSimpleName();

    private TextView mName;
    private TextView mDaici;
    private TextView mPinhshu;
    private TextView mOperator;
    private TextView mDate;
    private TextView mCaozuo;
    public FirstAllList(View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.tv_name);
        mDaici = itemView.findViewById(R.id.tv_daici);
        mPinhshu = itemView.findViewById(R.id.tv_pingshu);
        mOperator = itemView.findViewById(R.id.tv_operator);
        mCaozuo = itemView.findViewById(R.id.tv_caozuo);
        mDate = itemView.findViewById(R.id.tv_date);
    }



    @Override
    public int getLayoutId() {
        return R.layout.first_all;
    }

    @Override
    public void bindView(TreeNode treeNode) {
        /** 设置TextView数据，作为第一级类目 */
//        text_all_Select.setText(treeNode.getValue() + "");
        CurData.DataBean value = (CurData.DataBean) treeNode.getValue();

        //        Log.e(TAG, "AllList**********************************" + value.toString());
        if(value != null){
                mName.setText( value.getName() );
                mDaici.setText( value.getDaici() );
                mCaozuo.setText( value.getCaozuo() );
                mDate.setText( value.getDate() );
                mPinhshu.setText( value.getPingshu() );
                mOperator.setText( value.getOperator() );
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

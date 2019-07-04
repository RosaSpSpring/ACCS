package com.ko.testtrees.widget;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;


import com.ko.testtrees.R;
import com.ko.testtrees.bean.CurData;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.CheckableNodeViewBinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/4.
 */

public class FirstItemList extends BaseNodeViewBinder {
    public static final String TAG = FirstAllList.class.getSimpleName();

    private TextView mItemDaici;
    private TextView mItemPinhshu;
    private TextView mItemOperator;
    private TextView mItemDate;
    private TextView mItemCaozuo;
    private Map<String,List<CurData.DataBean>> mMap = new HashMap<>(  );

    public FirstItemList(View itemView) {
        super(itemView);

        mItemDaici = itemView.findViewById(R.id.tv_item_daici);
        mItemPinhshu = itemView.findViewById(R.id.tv_item_pingshu);
        mItemOperator = itemView.findViewById(R.id.tv_item_operator);
        mItemDate = itemView.findViewById(R.id.tv_item_date);
        mItemCaozuo = itemView.findViewById(R.id.tv_item_caozuo);
    }



    @Override
    public int getLayoutId() {
        return R.layout.item_list;
    }

    @Override
    public void bindView(TreeNode treeNode) {
        CurData.DataBean value = (CurData.DataBean) treeNode.getValue();
//        ArrayList<CurData.DataBean> valueParent = (ArrayList<CurData.DataBean>) treeNode.getParent().getValue();
//        for (int i = 0; i < valueParent.size(); i++) {
////          if (value.get( i ))
//            String nameParent = valueParent.get( i ).getName();
//            Log.e(TAG, "nameParent***********************" + nameParent );

            mItemDate.setText( value.getDate());
            mItemCaozuo.setText( value.getCaozuo());
            mItemDaici.setText( value.getDaici() );
            mItemOperator.setText( value.getOperator() );
            mItemPinhshu.setText( value.getPingshu() );

//        }




        //        mMap = (Map<String, List<CurData.DataBean>>) treeNode.getValue();
        //
        //        Set<Map.Entry<String, List<CurData.DataBean>>> entries = mMap.entrySet();
        //        Iterator<Map.Entry<String, List<CurData.DataBean>>> iterator = entries.iterator();
        //        while (iterator.hasNext()){
        //            String key = iterator.next().getKey();
        //            List<CurData.DataBean> value = iterator.next().getValue();
        //
        //        }



    }
}

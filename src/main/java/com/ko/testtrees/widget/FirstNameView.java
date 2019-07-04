package com.ko.testtrees.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;


/**
 * Created by Administrator on 2017/7/4.
 */

public class FirstNameView {

    //控件根节点
    private TreeNode root;
    //tree控件
    private TreeView treeView;
    View rootView;


    public View onCreateView(Context context, ViewGroup viewGroup) {

        viewGroup.removeAllViews();
        treeView = new TreeView(TreeNode.root(),context,
                new FirstItemViewFactory());
        View view = treeView.getView();
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.addView(view);

        return rootView;
    }




}

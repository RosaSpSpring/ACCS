package com.ko.testtrees.widget;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;

/**
 * Created by Administrator on 2017/7/4.
 */

public class FirstItemViewFactory extends BaseNodeViewFactory {
    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level){
            case 0:
                return new AllList(view);
            case 1:
                return new FirstAllList(view);

            case 2:
                return new FirstItemList(view);
        }
        return null;
    }
}

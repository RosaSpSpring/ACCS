package com.ko.accs2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.ko.accs2.Screen.ScreenAdapterUtils;
import com.ko.accs2.base.BaseFragment;
import com.ko.accs2.fragment.CloudData;
import com.ko.accs2.fragment.Remote;
import com.ko.accs2.fragment.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends FragmentActivity {


    @BindView(R.id.title_fragment)
    TextView mTitleFragment;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.rb_1)
    RadioButton mRb1;
    @BindView(R.id.rb_2)
    RadioButton mRb2;
    @BindView(R.id.rb_3)
    RadioButton mRb3;
    @BindView(R.id.rg_main)
    RadioGroup mRgMain;

    private List<BaseFragment> mBaseFragment;
    private static String[] titles = {"云端","监控","用户"};


    /*
  上次切换的fragment
   */
    private Fragment mContent;

    /**
     * 选中的fragment默认位置
     */
    private int position;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenAdapterUtils.setCusomDensity( this, getApplication());
        setContentView(R.layout.activity_main);
        //设置状态栏的颜色
        StatusBarCompat.setStatusBarColor(MainActivity.this,MainActivity.this.getResources().getColor(R.color.colorRB),false);

        ButterKnife.bind(this);
        initView();
        /**
         * 初始化Fragment
         */
        initFragment();
        //设置监听切换fragment
        setListener();



    }



    private void setListener() {
        mRgMain.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //选中默认框架
        mRgMain.check(R.id.rb_1);
    }

    private void initFragment() {
     mBaseFragment = new ArrayList<>();
     mBaseFragment.add(new CloudData());
     mBaseFragment.add(new Remote());
     mBaseFragment.add(new User());
    }

    private void initView() {
        mTitleFragment = findViewById(R.id.title_fragment);
        mFlContent = findViewById(R.id.fl_content);
        mRb1 = findViewById(R.id.rb_1);
        mRb2 = findViewById(R.id.rb_2);
        mRb3 = findViewById(R.id.rb_3);
        mRgMain = findViewById(R.id.rg_main);

        setRadioButtonImg();

    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_1:
                    position = 0;
//                    mRb1.setTextColor(MainActivity.this.getResources().getColorStateList(R.color.bottom_textcolor_drawable_selector));
                    mTitleFragment.setText(titles[0]);
                    break;
                case R.id.rb_2:
                    position = 1;
//                    mRb2.setTextColor(MainActivity.this.getResources().getColorStateList(R.color.bottom_textcolor_drawable_selector));
                    mTitleFragment.setText(titles[1]);
                    break;
                case R.id.rb_3:
                    position = 2;
//                    mRb3.setTextColor(MainActivity.this.getResources().getColorStateList(R.color.bottom_textcolor_drawable_selector));
                    mTitleFragment.setText(titles[2]);
                    break;
                default:
                    position = 1;
                    break;
            }
            //根据位置得到Fragment
            BaseFragment to = getFragment();
            //替换
            switchFragment(mContent, to);


        }
    }

    private void switchFragment(Fragment from, BaseFragment to) {
        if (from != to) {
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有添加
            if (!to.isAdded()) {
                //没有被添加

                //from隐藏
                if (from != null) {
                    ft.hide(from);

                }
                //添加to
                if (to != null) {
                    ft.add(R.id.fl_content, to).commit();
                }
            } else {
                //to已被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //显示to
                if (to != null) {
                    ft.show(to).commit();
                }
            }

        }
    }

    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    //改变radiobutton图片的大小
    private void setRadioButtonImg() {
        Drawable First = getResources().getDrawable(R.drawable.main_cloud_selector);
        First.setBounds(0, 20,80 ,102);
        //参数从左到右依次是距左右边距离，距上下边距离，图片长度,图片宽度
        mRb1.setCompoundDrawables(null, First, null, null);
        Drawable Second = getResources().getDrawable(R.drawable.main_remote_selector);
        Second.setBounds(0, 20,80 ,102);
        mRb2.setCompoundDrawables(null, Second, null, null);
        Drawable Three = getResources().getDrawable(R.drawable.main_user_selector);
        Three.setBounds(0, 20,80 ,102);
        mRb3.setCompoundDrawables(null, Three, null, null);
    }


    //利用两次点击时间差实现双击退出应用
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
//让fragment也可以实现触摸事件需要在主activity中定义接口

    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(
            10);
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }
    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener) ;
    }
    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }


    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

}

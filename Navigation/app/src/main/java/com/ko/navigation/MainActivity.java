package com.ko.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ko.navigation.fragment.TabFragment;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

/**
 * 使用ViewPager + Fragments 实现主界面结构   防微信
 * 1 : ViewPager + FragmentPagerAdapter(不会执行onDestroy和onCreate) 实现
 * 2 : ViewPager + FragmentStatePagerAdapter(会执行onDestroy和onCreate) 实现
 */

public class MainActivity extends AppCompatActivity {
    private ViewPager mVpMain;
    private List<String> mTitles = new ArrayList( Arrays.asList( "微信","通讯录","发现","我" ) );
    private Button mBtnWeChat;
    private Button mBtnFriend;
    private Button mBtnFind;
    private Button mBtnMine;

    //
    private SparseArray<TabFragment> mFragments = new SparseArray<>(  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initView();
        mVpMain.setOffscreenPageLimit( mTitles.size() );//限制页面个数
        mVpMain.setAdapter( new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                TabFragment tabFragment =  TabFragment.newinstance( mTitles.get( position ) );
                if (position == 0  ){
                    tabFragment.setOnTitleClickListener( new TabFragment.OnTitleClickListener() {
                        @Override
                        public void onClick(String title) {
                            changeWeChatTab( title );
                        }
                    } );
                }
                return tabFragment;
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment tabFragment = (TabFragment) super.instantiateItem( container, position );
                mFragments.put( position,tabFragment );
                return tabFragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mFragments.remove( position );
                super.destroyItem( container, position, object );
            }
        } );

    }

    private void initView() {
        mVpMain = findViewById(R.id.vp_main);
        mBtnFind = findViewById(R.id.btn_find);
        mBtnFriend = findViewById(R.id.btn_friend);
        mBtnWeChat = findViewById(R.id.btn_wechat);
        mBtnMine = findViewById(R.id.btn_mine);

        mBtnWeChat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabFragment tabFragment = mFragments.get( 0 );
                if (tabFragment != null) {
                    tabFragment.chageTitile( "微信,changed" );
                }
            }
        } );
    }

    //fragment调用activity里面的方法
    public void changeWeChatTab(String title){
        mBtnWeChat.setText( title );
    }
}

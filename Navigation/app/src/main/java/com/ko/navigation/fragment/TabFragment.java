package com.ko.navigation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ko.navigation.MainActivity;
import com.ko.navigation.R;
import com.ko.navigation.utils.L;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * @author lxm
 * @version 2019/8/19-15:22
 * @des ${TODO}
 * @updateDes ${TODO}
 * @updateAuthor $Author$
 */
public class TabFragment extends Fragment {
	public static final String TAG = TabFragment.class.getSimpleName();
	public static final String BUNDLE_KEY_TITLE = "key_title";

	private TextView mTvTitle;
	private String mTitle;

	//说道回调要有interface
	public static interface OnTitleClickListener{
		void onClick(String title);
	}
	private OnTitleClickListener mOnTitleClickListener;

	public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener){
		mOnTitleClickListener = onTitleClickListener;

	}



	public static TabFragment newinstance(String title){
		Bundle bundle = new Bundle();
		bundle.putString( BUNDLE_KEY_TITLE,title );

		TabFragment tabFragment = new TabFragment();
		tabFragment.setArguments( bundle );

        return tabFragment;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		L.d(TAG, "onCreate" ,"title = " + mTitle);
		return inflater.inflate( R.layout.fragment_tab,container,false );

	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		Bundle arguments = getArguments();
		if (arguments != null) {
			mTitle = arguments.getString( BUNDLE_KEY_TITLE, "" );

		}

	}

	//view创建完成了
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated( view, savedInstanceState );
		mTvTitle = view.findViewById(R.id.tv_title);
		mTvTitle.setText( mTitle );
		mTvTitle.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//获取activity对象
				//使用activity对象调用其中的方法 较好的方法是一种在fragment的特有的方法里面暴露接口,写回调让activity去调用
				if (mOnTitleClickListener != null){
					mOnTitleClickListener.onClick( "wechatchanged" );
				}


			}
		} );
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		L.d(TAG, "onDestroyView" ,"title = " + mTitle);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		L.d(TAG, "onDestroy" ,"title = " + mTitle);
	}
	//activity调用fragment中的方法
	public void chageTitile(String title){
		//判断是否添加到activity中如果没有则fragment生命周期是不会开始的
		if (!isAdded()){
			return;
		}
		mTvTitle.setText( title );
	}
}

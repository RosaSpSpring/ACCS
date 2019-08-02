package com.ko.accs2.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.hw.ycshareelement.transition.TextViewStateSaver;
import com.ko.accs2.R;
import com.ko.accs2.bean.CellImageBean;
import com.ko.accs2.bean.CloudDataBean;
import com.ko.accs2.cloud.CloudItem;
import com.ko.accs2.cloud.CloudItemDetail;
import com.ko.accs2.cloud.ImageActivity;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;
import static com.ko.accs2.cloud.CloudItem.KEY_CONTACTS;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * recyclerView的适配器用于放置细胞图片
 */
public class CellImageGlideAdapter extends RecyclerView.Adapter<CellImageGlideAdapter.MyViewHolder> {
	private static final String TAG = CellImageGlideAdapter.class.getSimpleName();
	public  Context mContext;
	private List<CellImageBean.ItemImage> mData;



	public CellImageGlideAdapter(CloudItemDetail context, List<CellImageBean.ItemImage> data) {
		mContext = context;
		mData = data;
	}


	@Override
	public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

		View itemView = LayoutInflater.from(viewGroup.getContext() ).inflate( R.layout.cell_image_item,viewGroup,false );

		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {

		//设置共享元素相同的transitionName
		//       ViewCompat.setTransitionName(myViewHolder.mCellImageItem,"imageUrl");
		//       ViewCompat.setTransitionName(myViewHolder.mCellImageItemName,"tupianname");
		//共享元素，此处本该在holder里面，但是里面没法调用activity
		YcShareElement.setEnterTransitions( (Activity) mContext, new IShareElements() {
			@Override
			public ShareElementInfo[] getShareElements() {
				return new ShareElementInfo[]{new ShareElementInfo( myViewHolder.mCellImageItem ), new ShareElementInfo( myViewHolder.mCellImageItemName, new TextViewStateSaver() )};
			}
		},false);
		CellImageBean.ItemImage itemImage = mData.get( i );
		//联网获取数据
		myViewHolder.mCellImageItemName.setText(itemImage.getName());

		//		Glide.with(mContext)
		//                .load(itemImage.getImageurl())
		//				.apply( RequestOptions.centerCropTransform())
		//                .into(myViewHolder.mCellImageItem);
		Glide.with(mContext)
				.load("http://keyonecn.com:8897/images/1.png")
				.apply( RequestOptions.centerCropTransform())
				.into(myViewHolder.mCellImageItem);




		//		ViewCompat.setTransitionName(myViewHolder.mCellImageItem,"avatar:"+itemImage.getName());
		//		ViewCompat.setTransitionName(myViewHolder.mCellImageItemName,"name:"+itemImage.getName());



	}


	@Override
	public int getItemCount() {
		return mData == null ? 0 : mData.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder {
		private ImageView mCellImageItem;
		private TextView mCellImageItemName;

		public MyViewHolder(final View itemView) {
			super(itemView);
			mCellImageItem = itemView.findViewById(R.id.cell_image_item);
			mCellImageItemName = itemView.findViewById(R.id.cell_image_item_name);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mOnRecyclerViewItemClickListener != null){
						mOnRecyclerViewItemClickListener.onItemClick( v,mData.get( getLayoutPosition() ),mCellImageItem,mCellImageItemName );
					}


					//在这里实现点击后共享元素的activity但是没有上下文  把item点击接口写出去
					//                	Intent intent = new Intent();
					//                    if (mOnRecyclerViewItemClickListener)
					//通过此处进行回传点击
					/*if (mOnRecyclerViewItemClickListener != null){



						CellImageBean.ItemImage datas = mData.get( getLayoutPosition() );
						mOnRecyclerViewItemClickListener.onItemClick( v, datas );


						Intent intent = new Intent( mContext,ImageActivity.class );
						Bundle bundle = YcShareElement.buildOptionsBundle( (Activity) mContext, new IShareElements() {
							@Override
							public ShareElementInfo[] getShareElements() {
								return new ShareElementInfo[]{new ShareElementInfo(mCellImageItem),
										new ShareElementInfo(mCellImageItemName, new TextViewStateSaver())};
							}
						} );
						bundle.putSerializable( KEY_CONTACTS, (Serializable) datas );
						ActivityCompat.startActivity( mContext,intent,bundle );
					}*/
				}
			});

		}


	}
	//写item点击回调接口 点击recyclerview某条的监听
	public interface OnRecyclerViewItemClickListener{
		/**
		 * 点击item回调
		 * @param view 得到的视图
		 * @param datas 得到数据
		 */
		public void onItemClick(View view,CellImageBean.ItemImage datas,ImageView imageView,TextView textView);


	}
	public OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

	/**
	 * 设置recyclerView某条的监听
	 * @param onRecyclerViewItemClickListener
	 */
	public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
		mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
	}
}

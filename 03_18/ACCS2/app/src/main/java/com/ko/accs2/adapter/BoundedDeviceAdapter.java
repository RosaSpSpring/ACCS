package com.ko.accs2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ko.accs2.R;
import com.ko.accs2.bean.BoundedDeviceBean;
import com.ko.accs2.user.BoundedDevices;
import com.ko.accs2.util.CacheUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class BoundedDeviceAdapter extends BaseAdapter {
	private static final String TAG = BoundedDeviceAdapter.class.getSimpleName();

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<BoundedDeviceBean.ItemBoundedDevice> mItemBoundedDevices;
	private TextView mDangqsb;
	private TextView mDangqsbm;
	private static boolean ONLINE = false;

	public BoundedDeviceAdapter(Context context, List<BoundedDeviceBean.ItemBoundedDevice> data, TextView dangqianshebei, TextView dangqianshebeima) {
		this.mContext = context;
		this.mItemBoundedDevices = data;
		mLayoutInflater = LayoutInflater.from( context );
		this.mDangqsb = dangqianshebei;
		this.mDangqsbm = dangqianshebeima;
	}

	@Override
	public int getCount() {
		return mItemBoundedDevices == null ? 0 : mItemBoundedDevices.size();
	}

	@Override
	public Object getItem(int position) {
		return mItemBoundedDevices.get( position );
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (viewHolder == null) {
			convertView = mLayoutInflater.inflate( R.layout.bounded_device_list, parent, false );
			viewHolder = new ViewHolder();
			convertView.setTag( viewHolder );

			viewHolder.mDeviceLine = convertView.findViewById( R.id.device_line );
			viewHolder.mYibangshebeima = convertView.findViewById( R.id.yibangshebeima );
			viewHolder.mYibangshebei = convertView.findViewById( R.id.yibangshebei );
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		BoundedDeviceBean.ItemBoundedDevice data = mItemBoundedDevices.get( position );
		Log.e( TAG, "data.getContent" + data.getContent() + "getUuid" + data.getUuid() );
		viewHolder.mYibangshebei.setText( data.getContent() );
		CacheUtils.putString( mContext, "BoundedDevice", data.getContent() );
		viewHolder.mYibangshebeima.setText( data.getUuid() );
		//		mDangqsb.setText( data.getContent() );
		//		mDangqsbm.setText( data.getUuid() );


		//		if (mDangqsbm.getText().toString().equals( viewHolder.mYibangshebeima.getText().toString() )) {
		//			viewHolder.mDeviceLine.setImageResource( R.drawable.online );
		//		} else {
		//			viewHolder.mDeviceLine.setImageResource( R.drawable.offline );
		//		}
		ViewHolder finalViewHolder = viewHolder;
		finalViewHolder.mDeviceLine.setImageResource( R.drawable.offline );
		convertView.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnItemBoundedDeviceListener != null) {
					mOnItemBoundedDeviceListener.onItemClick( position );
				}
				finalViewHolder.mDeviceLine.setImageResource( R.drawable.offline );

				//设置对话框标题 
				new AlertDialog.Builder( mContext ).setTitle( "系统提示" ).setMessage( "确定要切换当前设备" ).setPositiveButton( "确定", new DialogInterface.OnClickListener() {
					//确定按钮响应事件
					@Override
					public void onClick(DialogInterface dialog, int which) {

						mDangqsb.setText( finalViewHolder.mYibangshebei.getText().toString() );
						mDangqsbm.setText( finalViewHolder.mYibangshebeima.getText().toString() );
						String ress = mDangqsb.getText().toString();
						CacheUtils.putString( mContext, "BoundedDevice2", ress );
						finalViewHolder.mDeviceLine.setImageResource( R.drawable.online );

					}

				} ).setNegativeButton( "返回", new DialogInterface.OnClickListener() {
					//返回按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				} ).show();
			}
		} );


		return convertView;
	}

	static class ViewHolder {
		@BindView(R.id.yibangshebei)
		TextView mYibangshebei;
		@BindView(R.id.device_line)
		ImageView mDeviceLine;
		@BindView(R.id.yibangshebeima)
		TextView mYibangshebeima;

		ViewHolder(View view) {
			ButterKnife.bind( this, view );
		}

		public ViewHolder() {

		}
	}

	/**
	 * 设置一个外部监听接口
	 */
	public interface OnItemBoundedDeviceListener {
		/**
		 * 某条点击时回调
		 *
		 * @param position
		 */
		public void onItemClick(int position);
	}

	private OnItemBoundedDeviceListener mOnItemBoundedDeviceListener;

	/**
	 * 设置item的监听
	 *
	 * @param onItemBoundedDeviceListener
	 */
	public void setOnItemBoundedDeviceListener(OnItemBoundedDeviceListener onItemBoundedDeviceListener) {
		mOnItemBoundedDeviceListener = onItemBoundedDeviceListener;
	}
}

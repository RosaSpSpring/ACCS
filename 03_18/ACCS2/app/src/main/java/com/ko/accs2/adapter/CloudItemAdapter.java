package com.ko.accs2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ko.accs2.R;
import com.ko.accs2.bean.CloudDataBean;

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
public class CloudItemAdapter extends BaseAdapter {
	private static final String TAG = CloudItemAdapter.class.getSimpleName();

	private Context context;
	private LayoutInflater mLayoutInflater;
	private List<CloudDataBean.ItemData> cloudItemData;


	public CloudItemAdapter(Context context, List<CloudDataBean.ItemData> data) {
		this.context = context;
		this.cloudItemData = data;
		mLayoutInflater = LayoutInflater.from( context );
	}

	@Override
	public int getCount() {
		return cloudItemData == null ? 0 : cloudItemData.size();
	}

	@Override
	public Object getItem(int position) {
		return cloudItemData.get( position );
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent){
		ViewHolder holder = null;
		if (holder == null) {
			convertView = mLayoutInflater.inflate( R.layout.cloud_item_lv, parent, false );
			holder = new ViewHolder();
			holder.mCloueitemXibaomingcheng = convertView.findViewById( R.id.cloueitem_xibaomingcheng );
			holder.mCloueitemChuandaishijian = convertView.findViewById( R.id.cloueitem_chuandaishijian );
			holder.mCloueitemPeiyangshijian = convertView.findViewById( R.id.cloueitem_peiyangshijian );
			holder.mCloueitemXibaohuanyeshijian = convertView.findViewById( R.id.cloueitem_xibaohuanyeshijian );
			holder.mCloueitemTupian = convertView.findViewById( R.id.cloueitem_tupian);
			convertView.setTag( holder );

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CloudDataBean.ItemData data = cloudItemData.get( position );
		holder.mCloueitemXibaomingcheng.setText( data.getCellname() );
		holder.mCloueitemChuandaishijian.setText( data.getPassage_time() );
		holder.mCloueitemPeiyangshijian.setText( data.getCultur_time() );
		holder.mCloueitemXibaohuanyeshijian.setText( data.getChange_liquid_time() );
		convertView.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onCloudItemClickListener != null) {
					onCloudItemClickListener.onItemClick( position );
				}
			}
		} );


		return convertView;
	}


	static class ViewHolder {
		@BindView(R.id.cloueitem_xibaomingcheng)
		TextView mCloueitemXibaomingcheng;
		@BindView(R.id.cloueitem_chuandaishijian)
		TextView mCloueitemChuandaishijian;
		@BindView(R.id.cloueitem_peiyangshijian)
		TextView mCloueitemPeiyangshijian;
		@BindView(R.id.cloueitem_xibaohuanyeshijian)
		TextView mCloueitemXibaohuanyeshijian;
		@BindView(R.id.cloueitem_tupian)
		ImageView mCloueitemTupian;

		ViewHolder(View view) {
			ButterKnife.bind( this, view );

		}


		public ViewHolder() {

		}
	}

	/**
	 * 设置一个外部监听接口
	 */
	public interface OnCloudItemClickListener {
		/**
		 * 某条点击时回调
		 *
		 * @param posion
		 */
		public void onItemClick(int posion);
	}

	private OnCloudItemClickListener onCloudItemClickListener;

	/**
	 * 设置item的监听
	 *
	 * @param onCloudItemClickListener
	 */
	public void setOnCloudItemClickListener(OnCloudItemClickListener onCloudItemClickListener) {
		this.onCloudItemClickListener = onCloudItemClickListener;
	}


}

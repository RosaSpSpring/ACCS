package com.ko.accs2.cloud;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ko.accs2.R;
import com.ko.accs2.bean.CellImageBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ImageActivity extends AppCompatActivity {
    @BindView(R.id.cell_image_item_activity)
    ImageView mCellImageItemActivity;
    @BindView(R.id.image_activity)
    TextView mImageActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //getWindow().requestFeature(getWindow().FEATURE_CONTENT_TRANSITIONS);
        super.onCreate( savedInstanceState );

        setContentView( R.layout.image_activity );
        ButterKnife.bind( this );
        CellImageBean.ItemImage mydata = (CellImageBean.ItemImage) getIntent().getSerializableExtra( "itemData" );

        mCellImageItemActivity = findViewById( R.id.cell_image_item_activity );
        mImageActivity = findViewById( R.id.image_activity );
        Glide.with( this ).load( "http://keyonecn.com:8897/images/1.png").into( mCellImageItemActivity );
        mImageActivity.setText( mydata.getName() );
        mCellImageItemActivity.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition( ImageActivity.this );
            }
        } );
    }
}

package com.ko.accs2.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.githang.statusbar.StatusBarCompat;
import com.ko.accs2.LoginActivity;
import com.ko.accs2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class UserSetting extends AppCompatActivity implements View.OnClickListener {

	@BindView(R.id.setting_arrow_back)
	ImageView mSettingArrowBack;
	@BindView(R.id.linearLayout)
	RelativeLayout mLinearLayout;
	@BindView(R.id.qiehuanzhanghao)
	RelativeLayout mQiehuanzhanghao;
	@BindView(R.id.tuichudenglu)
	RelativeLayout mTuichudenglu;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.user_setting );
		ButterKnife.bind( this );
		StatusBarCompat.setStatusBarColor( this, getResources().getColor( R.color.colorRB ), false );
		mQiehuanzhanghao = findViewById( R.id.qiehuanzhanghao );
		mTuichudenglu = findViewById( R.id.tuichudenglu );
		mSettingArrowBack = findViewById( R.id.setting_arrow_back );
		initLisetener();


	}

	private void initLisetener() {
		mQiehuanzhanghao.setOnClickListener( this );
		mTuichudenglu.setOnClickListener( this );
		mSettingArrowBack.setOnClickListener( this );
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.qiehuanzhanghao:
				startActivity( new Intent( UserSetting.this, ChangeAccount.class ) );
				break;
			case R.id.tuichudenglu:
				Intent intent = new Intent( UserSetting.this, LoginActivity.class );
				startActivity( intent );
				finish();
				break;
			case R.id.setting_arrow_back:
				UserSetting.this.finish();
				break;

			default:
				break;
		}
	}
}

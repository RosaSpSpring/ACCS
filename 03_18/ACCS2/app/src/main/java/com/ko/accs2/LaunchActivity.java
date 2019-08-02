package com.ko.accs2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.weeboos.permissionlib.PermissionRequest;
import com.ko.accs2.guide.CustomVideoView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.ko.accs2.util.PermisionUtils.isGrantExternalRW;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * 启动动画
 */
public class LaunchActivity extends AppCompatActivity {
	private static final String TAG = LaunchActivity.class.getSimpleName();
	private CustomVideoView videoView;
	private ImageView imageView;
	private String videoPath;
	private int playProgress;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );

		//隐藏状态栏和标题栏
		getSupportActionBar().hide();  //隐藏标题栏
		getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );//隐藏状态栏


		setContentView( R.layout.activity_lanuch );
		getWindow().setFormat( PixelFormat.TRANSLUCENT );
		videoView = findViewById( R.id.video_view );
		imageView = findViewById( R.id.image_view );

		hideBottomUIMenu();
		readDataFromAssets();
		File file = new File( LaunchActivity.this.getExternalFilesDirs( Environment.DIRECTORY_DOWNLOADS )[0], "media.mp4" );

		Log.e( TAG, "file的绝对路径" + file.getAbsolutePath() );
		videoPath = file.getAbsolutePath();
		PermissionRequest request = new PermissionRequest( this );//这个this需要一个activiy对象或者fragment对象
		request.requestPermission( new PermissionRequest.PermissionListener() {
			@Override
			public void permissionGranted() {
				playVideo();
			}

			@Override
			public void permissionDenied(ArrayList<String> permissions) {

			}

			@Override
			public void permissionNeverAsk(ArrayList<String> permissions) {

			}
		}, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE} );

	}

	private boolean readDataFromAssets() {
		InputStream inputStream = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		File dir = getExternalFilesDir( Environment.DIRECTORY_DOWNLOADS );
		Log.e( TAG, "readDataFromAssets: dir = " + dir.getAbsolutePath() );

		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {

			inputStream = LaunchActivity.this.getResources().getAssets().open( "media.mp4" );
			Log.e( TAG, "dudaole" );
			File file = new File( dir, "media.mp4" );
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();

			fos = new FileOutputStream( file );
			bos = new BufferedOutputStream( fos );

			byte[] bytes = new byte[1024];
			while (inputStream.read( bytes ) > 0) {
				bos.write( bytes, 0, bytes.length );
			}
			Log.e( TAG, "dule" );
			inputStream.close();
			bos.close();
			fos.close();

		} catch (IOException e) {
			Log.e( TAG, "meidu" );
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (bos != null) {
					bos.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	//    隐藏底部导航栏
	private void hideBottomUIMenu() {
		//隐藏虚拟按键，并且全屏
		if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility( View.GONE );
		} else if (Build.VERSION.SDK_INT >= 19) {
			//for new api versions.
			getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_FULLSCREEN );
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility( uiOptions );
		}
	}

	private void playVideo() {
		Log.e( TAG, "^^^^^^^^^^^^^^^^" );
		//        videoPath = Environment.getExternalStorageDirectory().getPath()+ "/media.mp4";
		//        videoPath = "android.resource://" + getPackageName() + "/" + R.raw.media;
		MediaMetadataRetriever media = new MediaMetadataRetriever();
		media.setDataSource( videoPath );
		//获取第一帧
		Bitmap bitmap = media.getFrameAtTime( 1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC );
		imageView.setImageBitmap( bitmap );
		media.release();
		videoView.setVideoPath( videoPath );
		//        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.media));
		videoView.start();
		videoView.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				// imageView.setVisibility(View.GONE);
				mediaPlayer.setLooping( false );
				mediaPlayer.setOnInfoListener( new MediaPlayer.OnInfoListener() {
					@Override
					public boolean onInfo(MediaPlayer mediaPlayer, int what, int i1) {

						//开始播放时，就把显示第一帧的ImageView gone 掉
						if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
							// video started; hide the placeholder.
							imageView.setVisibility( View.GONE );
							//videoView.seekTo(0);
							return true;
						}
						return false;
					}
				} );
			}
		} );
	}
	//请求权限结果处理
	//    @Override
	//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
	//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	//            playVideo();
	//        }
	//    }

	@Override
	protected void onPause() {
		super.onPause();
		//记录播放的progress,避免黑屏
		videoView.pause();
		playProgress = videoView.getCurrentPosition();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (playProgress != 0) {
			videoView.seekTo( playProgress );
			videoView.start();
		}
		// playProgress = videoView.getCurrentPosition();
	}
	//手势监听

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Intent i = new Intent( LaunchActivity.this, LoginActivity.class );
				startActivity( i );
				finish();
				break;
			case MotionEvent.ACTION_MOVE:

				break;
			case MotionEvent.ACTION_UP:

				break;

			default:
				break;
		}
		return super.onTouchEvent( event );
	}
}

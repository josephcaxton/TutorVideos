package com.learnerscloud.gcsemaths.videos;

import com.learnerscloud.gcsemaths.videos.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;



public class Splash extends Activity{
	
	private ProgressBar mProgress;
    private int mProgressStatus = 0;
     
     
    
  

    private Handler mHandler = new Handler();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		View LLView = findViewById(R.id.LLMain);
		View root = LLView.getRootView();
		root.setBackgroundColor(Color.argb(0, 238, 244, 228));
		
		mProgress = (ProgressBar) findViewById(R.id.SplashprogressBar);
		
		 LocalCache cache = ((LocalCache)getApplicationContext());
		cache.PlaySound(this, R.raw.arrowwoodimpact);
		cache.setServerURL("http://www.learnerscloud.com/AndroidStream/maths");
		cache.setServer("http://learnerscloud.com");
		cache.setVideoURL("/AndroidStream/maths");
		cache.setAppid(2); // 2 means this is maths, 1 is English , 3 is physics
		cache.setAllowAccess(0);
		
		
		
		Thread timer = new Thread(){
			public void run(){
				while (mProgressStatus < 100) {
				     mProgressStatus = createXmlFile();
				  // Update the progress bar
				     mHandler.post(new Runnable() {
				         public void run() {
				             mProgress.setProgress(mProgressStatus);
				         }
				     });
				}
				
			   // Download Configuration Files:
				LocalCache cache = ((LocalCache)getApplicationContext()); 
				Configurator C = new Configurator();
			    Boolean Success1 = C.DownloadFile(cache.getServerURL() + "/mathsConfig.xml", "mathsConfig.xml", getApplicationContext());
			  //Boolean Success2 = C.DownloadFile("http://learnerscloud.com/iosStreamv2/maths/mathsConfig.xml", "mathsConfig.xml", getApplicationContext());
			    if(Success1 != true){
			    	runOnUiThread(new Runnable() {
					     public void run() {
			    	alert("Your device is not ready. You need access to the internet to stream our videos: Try again");
			    	
					     	} 
					    });
			    	
			    }
			    else{
					
			    Intent OpenStartPage = new Intent("com.learnerscloud.gcsemaths.videos.TABS");
				startActivity(OpenStartPage);
			    }
				
			}
		};
		
			
		
		timer.start();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		//SplashSound.stop();
		finish();
	}
	
	private int createXmlFile() {
		
		mProgressStatus ++;
		return mProgressStatus;
	}


	 void alert(String message) {
	        AlertDialog.Builder bld = new AlertDialog.Builder(this);
	        bld.setMessage(message);
	        bld.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,
                        int which) {
                    dialog.dismiss();
                    System.exit(0);
                }
            });
	        Log.d("LearnersCloud", "Showing alert dialog: " + message);
	        bld.create().show();
	    }
	
	

}

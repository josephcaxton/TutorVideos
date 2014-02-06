package com.learnersCloud.iEvaluator.Maths250;

import com.learnersCloud.iEvaluator.Maths250.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends Activity {
	
	
	VideoView videoView;
	SurfaceHolder holder;
	MediaController mediaController;
	private String Path;
	MediaPlayer mediaplayer;
  // This is not working i think due to a bug in android. 
  // Error is MediaPlayer is null.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.videoplayer);
	    videoView = (VideoView) findViewById(R.id.videoView1);
	    
	    holder = videoView.getHolder();
        holder.setFixedSize(400,300);
	    
	    /*videoView.setOnErrorListener(this);
	    videoView.setOnCompletionListener(this);
	    videoView.setOnPreparedListener(this);
	    videoView.setKeepScreenOn(true);*/
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        
        Bundle getBasket = getIntent().getExtras();
        Path = getBasket.getString("Location");
        
        Uri videoUri = Uri.parse( Path);
        
        //Log.d("Joseph",  videoUri.toString());
        
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videoUri);    
        
        
        videoView.start();
        
	}
	
	@Override
	protected void onStop() {
		
		super.onStop();
		mediaplayer.release();
		mediaplayer = null;
	}

	/*public void onPrepared(MediaPlayer arg0) {
		
		
	}

	public void onCompletion(MediaPlayer mp) {
		
		this.finish();
		
	}

	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		
		Log.e("Joseph","Buffering ......");
		
	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.e("Joseph", "onError--->   what:" + what + "    extra:" + extra);
		       if (mp != null) {
		    	   mp.stop();
		    	   mp.release();
		        }
		return false;
	}
	
	*/

}

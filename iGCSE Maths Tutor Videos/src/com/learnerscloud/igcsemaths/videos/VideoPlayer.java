package com.learnerscloud.igcsemaths.videos;

import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import com.learnerscloud.igcsemaths.videos.R;

public class VideoPlayer extends Activity {
	
	VideoView V;
	 @Override
	    public void onCreate(Bundle savedInstanceState)  {
	        super.onCreate(savedInstanceState);
	        
	        Bundle getBasket = getIntent().getExtras();
			String url = getBasket.getString("_FullURL");
	       
	        //Settings.Secure.putString(getContentResolver(), Settings.Secure.HTTP_PROXY, "stage.learnerscloud.com:8080");
	        //String url = "http://iosuser:letmein2@learnerscloud.com:80/iosstreamv2/maths/M-ALGH-044-01/all.m3u8";
	        //String url = "http://learnerscloud.com/iosstream/maths/MB-2DSH-041-01-temp/371/prog_index.m3u8";
	        //String url = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
	       //String url = "http://akamedia2.lsops.net/live/smil:cnbc_en.smil/playlist.m3u8";
	      //String url ="http://iphoned5.akamai.com.edgesuite.net/mhbarron/nasatv/nasatv_700.m3u8";
	      //String url = "http://192.168.1.73/~joseph/M-ALGH-044-01/all.m3u8";
	      //String url = "http://173.120.13.57/iosstream/maths/M-ALGH-044-01/all.m3u8";
	        
	      if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
				return;

	      	setContentView(R.layout.videoplayer);
			V = (VideoView) findViewById(R.id.videoView1);
			V.setVideoPath(url);
			V.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
			MediaController mc = new MediaController(this);
			mc.setFileName("LearnersCloud.com");
			
			V.setMediaController(mc);
			
			
			V.setOnPreparedListener(new OnPreparedListener()
			{
			    @Override
			    public void onPrepared(MediaPlayer mediaPlayer)
			    {
			   // Toast.makeText(getApplicationContext(), "onPrepared", Toast.LENGTH_LONG).show();
			    V.start();
			    }
			});
			
			V.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

				@Override
				public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
					
				//Toast.makeText(getApplicationContext(), "Buffering.....", Toast.LENGTH_LONG).show();
					
				}
				
				
			});
	       
	        
	    }

	    @Override
		public void onConfigurationChanged(Configuration newConfig) {
			if (V != null)
				V.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
			super.onConfigurationChanged(newConfig);
		}

}

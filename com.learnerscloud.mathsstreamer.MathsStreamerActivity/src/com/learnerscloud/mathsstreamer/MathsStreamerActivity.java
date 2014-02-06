package com.learnerscloud.mathsstreamer;

import android.app.Activity;
import android.os.Bundle;
//import android.widget.MediaController;
//import android.widget.VideoView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.content.res.Configuration;


public class MathsStreamerActivity extends Activity  {
    /** Called when the activity is first created. */
	
	VideoView V;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        //Settings.Secure.putString(getContentResolver(), Settings.Secure.HTTP_PROXY, "stage.learnerscloud.com:8080");
        //String url = "http://iosuser:letmein2@learnerscloud.com:80/iosstreamv2/maths/M-ALGH-044-01/all.m3u8";
        String url = "http://stage.learnerscloud.com/iosstream/maths/M-ALGH-044-01/371/prog_index.m3u8";
        //String url = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
       //String url = "http://akamedia2.lsops.net/live/smil:cnbc_en.smil/playlist.m3u8";
      //String url ="http://iphoned5.akamai.com.edgesuite.net/mhbarron/nasatv/nasatv_700.m3u8";
      //String url = "http://192.168.1.73/~joseph/M-ALGH-044-01/all.m3u8";
        
      if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;

      	setContentView(R.layout.main);
		V = (VideoView) findViewById(R.id.videoView1);
		V.setVideoPath(url);
		V.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
		V.setMediaController(new MediaController(this));
       
        
    }

    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (V != null)
			V.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
		super.onConfigurationChanged(newConfig);
	}

    
    
}
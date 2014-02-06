package com.learnersCloud.iEvaluator.Maths250;


import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import com.learnersCloud.iEvaluator.Maths250.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Video  extends Activity implements  OnPreparedListener, OnCompletionListener,OnClickListener 
{
   
	//MediaPlayer mediaplayer;
	MediaController mediaController;
	VideoView videoView;
	Context c;
	SurfaceHolder holder;
	LocalCache cache;
	TextView Ad;
	long DownloadedSize;   // used to check patial download
	private ProgressBar mProgress;
    private static String Video_PATH =  "http://www.learnersCloud.com/Android"; //"/data/data/com.learnersCloud.iEvaluatorForAndroid.Chemistry/Videos";
    long FileSizeOnServer;
    int DownloadProgress;
    TextView Videobottomtxt;
    GoogleAnalyticsTracker tracker;
    Button VisitLearnersCloud;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videopage);
        
        this.loadWidgets();
        
    }
        private void loadWidgets(){
        c = this.getBaseContext();
		
        Gallery gallery = (Gallery) findViewById(R.id.VideoGallery);
       videoView = (VideoView) findViewById(R.id.videoView2);
       
       Ad = (TextView)findViewById(R.id.txtVideoAd);
       Ad.setText("For more videos visit");
       Linkify.addLinks( Ad, Linkify.WEB_URLS ); 
       
       Videobottomtxt = (TextView)findViewById(R.id.txtVideoBottomText);
       String myHtml = //"<p>LearnersCloud combines cutting edge technology with top quality content to offer you the best in e-learning.</p>" + " " +
       	"<p>Regardless your age, location or past GCSE experience, LearnersCloud helps you revise efficiently for your exams. ";
      
       Videobottomtxt.setText(Html.fromHtml(myHtml));
       
       mProgress = (ProgressBar)findViewById(R.id.VideoprogressBar);
       mProgress.setVisibility( View.INVISIBLE);

       VisitLearnersCloud = (Button)findViewById(R.id.btnLearnersCloud);
       VisitLearnersCloud.setOnClickListener(this);
       
       tracker = GoogleAnalyticsTracker.getInstance();
       // Start the tracker in manual dispatch mode...
          tracker.startNewSession("UA-32911832-1", this); 
       
       
        holder = videoView.getHolder();
        holder.setFixedSize(300,300);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

       
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        
        videoView.setOnPreparedListener(this);
        videoView.setOnCompletionListener(this);  
        
        
        cache = ((LocalCache)getApplicationContext());
        
        
        gallery.setAdapter(new ImageAdapter(this));
        
        gallery.setOnItemClickListener(new OnItemClickListener() {
           
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                
        		
        		
        		switch(position){
        		
        		case 0:
        			
        			if(cache.GetConnectionType() == 3){ 
        				
        				 Ad.setText("Loading video...... Please wait");
        				 mProgress.setVisibility(View.VISIBLE);
        				String url= Video_PATH + "/" + "student_Apps.mp4";
        				
        				videoView.setVideoURI(Uri.parse(url));
        				
        				videoView.setMediaController(mediaController);
        				videoView.requestFocus();
        			
        				videoView.start();
        			 videoView.setBackgroundDrawable(null);
        			//downloadAndPlayVideo();  Lets forget about this method
        				}
        				else{
        					
        					// No Wifi
        			 		Toast.makeText(getBaseContext(),
        			 	               "You need to connect to a Wifi to play our videos, please connect to Wifi",
        			 	                Toast.LENGTH_LONG).show();
        				}
        			
	
        			break;
        		case 1:
        			
        			
        			if(cache.GetConnectionType() == 3){ 
        				
       				 Ad.setText("Loading video...... Please wait");
       				 mProgress.setVisibility(View.VISIBLE);
       				String url= Video_PATH + "/" + "English_Apps.mp4";
       				
       				videoView.setVideoURI(Uri.parse(url));
       				
       				videoView.setMediaController(mediaController);
       				videoView.requestFocus();
       			
       				videoView.start();
       			 videoView.setBackgroundDrawable(null);
       			//downloadAndPlayVideo();  Lets forget about this method
       				}
       				else{
       					
       					// No Wifi
       			 		Toast.makeText(getBaseContext(),
       			 	               "You need to connect to a Wifi to play our videos, please connect to Wifi",
       			 	                Toast.LENGTH_LONG).show();
       				}
       			
	
        			
        			break;
        		case 2:
        			
        			
        			if(cache.GetConnectionType() == 3){ 
        				
          				 Ad.setText("Loading video...... Please wait");
          				 mProgress.setVisibility(View.VISIBLE);
          				String url= Video_PATH + "/" + "Physics_Apps.mp4";
          				
          				videoView.setVideoURI(Uri.parse(url));
          				
          				videoView.setMediaController(mediaController);
          				videoView.requestFocus();
          			
          				videoView.start();
          			 videoView.setBackgroundDrawable(null);
          			//downloadAndPlayVideo();  Lets forget about this method
          				}
          				else{
          					
          					// No Wifi
          			 		Toast.makeText(getBaseContext(),
          			 	               "You need to connect to a Wifi to play our videos, please connect to Wifi",
          			 	                Toast.LENGTH_LONG).show();
          				}
          			
   	
        			
        			break;
        		case 3:
        			
        			if(cache.GetConnectionType() == 3){ 
        				
         				 Ad.setText("Loading video...... Please wait");
         				 mProgress.setVisibility(View.VISIBLE);
         				String url= Video_PATH + "/" + "Chemistry_Apps.mp4";
         				
         				videoView.setVideoURI(Uri.parse(url));
         				
         				videoView.setMediaController(mediaController);
         				videoView.requestFocus();
         			
         				videoView.start();
         			 videoView.setBackgroundDrawable(null);
         			//downloadAndPlayVideo();  Lets forget about this method
         				}
         				else{
         					
         					// No Wifi
         			 		Toast.makeText(getBaseContext(),
         			 	               "You need to connect to a Wifi to play our videos, please connect to Wifi",
         			 	                Toast.LENGTH_LONG).show();
         				}
         			
  	
	
        			break;
        			
        		}
            }
        });
        
        
       


    }
    
    
 
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
    	
    	videoView.stopPlayback();
		TabGroup5 parentActivity = (TabGroup5)getParent();
		parentActivity.onBackPressed();
	}  
    



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//return super.onTouchEvent(event);
		
		
		if(videoView.isPlaying()){
		 videoView.pause();
		}
		else
		{
			videoView.start();
		}
		
		return false;
	}



	public class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        private Context mContext;

        private Integer[] mImageIds = {
                R.drawable.maths,
                R.drawable.english,
                R.drawable.physics,
                R.drawable.chemistry
        };

        public ImageAdapter(Context c) {
            mContext = c;
            TypedArray attr = mContext.obtainStyledAttributes(R.styleable.VideoGalleryStyle);
            mGalleryItemBackground = attr.getResourceId(
                    R.styleable.VideoGalleryStyle_android_galleryItemBackground, 0);
            attr.recycle();
        }

        public int getCount() {
            return mImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);

            imageView.setImageResource(mImageIds[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(mGalleryItemBackground);

            return imageView;
        }

		
    }


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		tracker.stopSession();

	}

	public void onCompletion(MediaPlayer arg0) {
		
		videoView.setBackgroundDrawable(getResources().getDrawable(R.drawable.start_up_logo));
		
	}

	public void onPrepared(MediaPlayer mp) {
		
		mProgress.setVisibility( View.INVISIBLE );
		 Ad.setText("");
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		videoView.stopPlayback();
		
		
	}
	public void onClick(View v) {
		
		if(v.getId()== R.id.btnLearnersCloud){
			 
			Uri uriUrl = Uri.parse("http://www.learnersCloud.com");
		        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		        startActivity(launchBrowser);
		}
		
	}

}

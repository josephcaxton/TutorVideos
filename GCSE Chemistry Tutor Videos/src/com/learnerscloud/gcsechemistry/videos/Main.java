package com.learnerscloud.gcsechemistry.videos;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.learnerscloud.gcsechemistry.videos.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Main extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
	
	Button Start;
	Intent intent;
	ArrayList<VideoItem> Videolist;
	ArrayList<String> Entries;
	int FreeAccess = 0;
	int Refresh = 0;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.main);
        View LLView = findViewById(R.id.LLMainPage);
		View root = LLView.getRootView();
		root.setBackgroundColor(Color.WHITE);
        
        Start =(Button) findViewById(R.id.btnStart);
       
        Start.setOnClickListener(this);
        
        //Login user if they have subscription
		 Thread SecondThread = new Thread(){
				public void run(){
					
					GetSubScription();
					
				}
			};
			SecondThread.start();
    }
    
 
	

	
	public void onClick(View v) {
		
		switch (v.getId()){
		 
		case(R.id.btnStart):
			
			// Check if Internet connection available
			LocalCache cache = ((LocalCache)getApplicationContext());
			int retVal = cache.HaveNetworkConnection();
			 
			 
			if(retVal == 0 ){
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
				builder.setCancelable(true);
				builder.setTitle("No internet connection");
				builder.setMessage("Your device is not connected to the internet. You need access to the internet to stream our videos ");
				builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                dialog.dismiss();
                                System.exit(0);
                            }
                        });
				AlertDialog alert = builder.create();
                alert.show();
                //break;
			}
			
			else
			{
			
				
				
				String Fileurl = getApplicationContext().getExternalFilesDir(null) +  "/chemistryConfig.xml";
				
				
				try {
					
				 Videolist = new ArrayList<VideoItem>();
				  // Check if this user has logged in successfully so give free access
				  FreeAccess = cache.getAllowAccess();
				  
				 // The Login Button first
				 Entries = new ArrayList<String>();
				  Entries.add("Login");
				  Entries.add("If you have an existing \n subscription purchased \n at LearnersCloud.com");
				  Entries.add("2");
				  Entries.add("search");
				  Entries.add("Login");
				  createVideoItem(Entries);
				  
				
					// I have used the regular expression to pick text between <VideoItem and <VideoItem>
					// The i paused ever row for the values i need; My God help you, if you need to work on this
					String Retvalues = cache.ScanFile(Fileurl);
					
						Pattern regex = Pattern.compile("<VideoItem(.*)<VideoItem>");
						Matcher regexMatcher = regex.matcher(Retvalues);
						while (regexMatcher.find()){
							
							
							//Log.d("Joseph", regexMatcher.group(1));
							
							String Row =  regexMatcher.group(1);
							int Start = Row.indexOf("|");
							int Finish = Row.indexOf("|", Start + 1);
				Entries = new ArrayList<String>();
						// Loop 7 time as we are looking for not more than 7 values;
							for (int i = 1; i <= 7; i++ ){
							
							//Log.d("Joseph", Row.substring(++ Start, Finish));
							String val = Row.substring(++ Start, Finish);
						    Entries.add(val);
							Start = Finish + 2;
							Start = Row.indexOf("|",Start);
							Finish = Start + 2;
							Finish = Row.indexOf("|", Finish);
							
							
							}
							createVideoItem(Entries);
					}
					
						 
							cache.setVideoItems(Videolist);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				 ArrayList<VideoItem> values = cache.getVideoItems();
				 if(values == null || values.isEmpty()){
					 
					 AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
						builder.setCancelable(true);
						builder.setTitle("No internet connection");
						builder.setMessage("Your device is not connected to the internet. You need access to the internet to stream our videos ");
						builder.setPositiveButton("OK",
		                        new DialogInterface.OnClickListener() {
		                            @Override
		                            public void onClick(DialogInterface dialog,
		                                    int which) {
		                                dialog.dismiss();
		                                System.exit(0);
		                            }
		                        });
						AlertDialog alert = builder.create();
		                alert.show();
				 }
				 else
				 {
			
				if( Refresh == 0){
					// reset times we have opened search box to 1
					cache.setDialogBoxClickCount(1);
					Intent ListVideosIntent = new Intent(getParent(),VideoListActivity.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent(); 
					parentActivity.startChildActivity("Video List",ListVideosIntent);
				}
				 }
			
			
			
			break;
			}
		
		}
		
		 
	}
	
	private void createVideoItem(ArrayList<String> values) {
		
		  VideoItem vi = new VideoItem();
		    vi.setTitle(values.get(0).toString());
		    vi.setDescription(values.get(1).toString());
		    
		    //Log.e("Joseph", values.get(2).toString());
		    if(Integer.parseInt(values.get(2).toString()) == 1 ){
				
			vi.setSubscribeimage(R.drawable.free);
			vi.setSearchimage(0);		
			}
		    
		    else if(Integer.parseInt(values.get(2).toString()) == 2 && FreeAccess == 0){
		    	
		    	vi.setSubscribeimage(R.drawable.login);
		    	vi.setSearchimage(R.drawable.search);
		    }
		    else if (Integer.parseInt(values.get(2).toString()) == 2 && FreeAccess == 1){
		    	
		    	vi.setSubscribeimage(R.drawable.logout);
		    	vi.setSearchimage(R.drawable.search);
			 }
		    else if(FreeAccess == 1){
		    	
		    	vi.setSubscribeimage(0);
		    	vi.setSearchimage(0);
		    }
		    else
		    {
		    	vi.setSubscribeimage(R.drawable.subscribe);
		    	vi.setSearchimage(0);
		    }
		    
		    
		    vi.setFree(values.get(2).toString());
		    vi.setM3u8(values.get(4).toString());
		    
		    Videolist.add(vi);
		    
			
		}

	private void GetSubScription(){
	
	 String WifiAddress = GetWifiAddress();
	 LocalCache cache = ((LocalCache)getApplicationContext());
	 int AppId = cache.getAppid();
	 
	 HttpClient httpclient = new DefaultHttpClient();
	 HttpPost httppost = new HttpPost(cache.getServer() + "/Services/android/VideoSubscription.asmx/HasCurrentSubscription");
	 try {
		
		
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("DeviceID", WifiAddress));
		 params.add(new BasicNameValuePair("AppID", Integer.toString(AppId)));
		
         
		
		httppost.setEntity(new UrlEncodedFormEntity(params));
		HttpResponse response = httpclient.execute(httppost);
		String str = inputStreamToString(response.getEntity().getContent()).toString();
			
		/*Return codes:
             0 OK
          */
			
			
			Pattern regex = Pattern.compile("http://learnerscloud.com/\">(.*)</int>");
			Matcher regexMatcher = regex.matcher(str);
			
			while (regexMatcher.find()){
				
				final String RetVal =  regexMatcher.group(1);
				if(Integer.parseInt(RetVal) == 0){
					
					cache.setAllowAccess(1);
					
					
				
			}
				
			
			}
	 }
		catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
 
 

	}
	 private StringBuilder inputStreamToString(InputStream is) {
	     String line = "";
	     StringBuilder total = new StringBuilder();
	     // Wrap a BufferedReader around the InputStream
	     BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	     // Read response until the end
	     try {
	      while ((line = rd.readLine()) != null) { 
	        total.append(line); 
	      }
	     } catch (IOException e) {
	      e.printStackTrace();
	     }
	     // Return full string
	     return total;
	    }
	

	@Override
	protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		TabGroup1 parentActivity = (TabGroup1)getParent();
		parentActivity.onBackPressed();
	}


 private String GetWifiAddress(){
		 
		 WifiManager wm = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		 return wm.getConnectionInfo().getMacAddress();
	 }


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		LocalCache cache = ((LocalCache)getApplicationContext());
		// log user out if he ever goes to the main screen. This to fix a problem
		//cache.setAllowAccess(0);
		
	}


	
}
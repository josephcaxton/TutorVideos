package com.learnerscloud.igcsemaths.videosamazon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class LocalCache extends Application{
	
	
	// If Activity State = 0 then all is normal, that means the activity will not be cleaned off the stack
	// If it is 1 then we are in a template, Activity will be remove off the stack and destroyed
	
	private int LocalActivityState;

	public int getLocalActivityState() {
		return LocalActivityState;
	}

	public void setLocalActivityState(int activityState) {
		LocalActivityState = activityState;
	}
	
	
	public void PlaySound(Context c, int ResourceId){
		
		
		
		MediaPlayer SplashSound = MediaPlayer.create(c, ResourceId);
		SplashSound.start();
		
		SplashSound.setOnCompletionListener(new OnCompletionListener() {

           
            public void onCompletion(MediaPlayer mp) {
                
            
        		mp.release();
        		mp = null;
            }

        });

		//SplashSound = null;
	}
	// this is to check the internet connection available
	// 0 = no connection
	// 1 = Mobile connection
	// 2 Wifi connection
	public int HaveNetworkConnection()
	{
		
	    int connectionType = 0;
	    

	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo)
	    {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	            	connectionType = 3;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	            	connectionType = 1;
	    }
	    return connectionType;
	}
	
	public int GetConnectionType()
	{
		return HaveNetworkConnection();
	}
	
	private ArrayList<VideoItem> VideoItems;

	public ArrayList<VideoItem> getVideoItems() {
		
		return VideoItems;
	}

	public void setVideoItems(ArrayList<VideoItem> videoItems) {
		VideoItems = videoItems;
	}
	
private ArrayList<VideoItem> AllVideoItems;
	
	public ArrayList<VideoItem> getAllVideoItems() {
		return AllVideoItems;
	}

	public void setAllVideoItems(ArrayList<VideoItem> allVideoItems) {
		AllVideoItems = allVideoItems;
	}
	
	private  int DialogBoxClickCount;
	
	public int getDialogBoxClickCount() {
		return DialogBoxClickCount;
	}

	public void setDialogBoxClickCount(int dialogBoxClickCount) {
		DialogBoxClickCount = dialogBoxClickCount;
	}
	
	public String ScanFile(String url) throws IOException{
		
		File file = new File(url);
		StringBuilder fileContents = new StringBuilder((int)file.length());
		Scanner scanner = new Scanner(file,"UTF-8");
		String lineSeparator = System.getProperty("line.separator");
		try {
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
		
	}
	
	private String ServerURL;

	public String getServerURL() {
		return ServerURL;
	}

	public void setServerURL(String serverURL) {
		ServerURL = serverURL;
	}
	
	private int Appid;

	public int getAppid() {
		return Appid;
	}

	public void setAppid(int appid) {
		Appid = appid;
	}
	
	private String VideoURL;
	
	
	public String getVideoURL() {
		return VideoURL;
	}

	public void setVideoURL(String videoURL) {
		VideoURL = videoURL;
	}

	private int AllowAccess;

	public int getAllowAccess() {
		return AllowAccess;
	}

	public void setAllowAccess(int allowAccess) {
		AllowAccess = allowAccess;
	}
	
	
	
	
	
	 private final String A = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA12LfKNTvEZCs1h694bIWLDuLU7hxCnupF8czEqsEXXlFA9NTHE";
	 private final String AA = "/eiurnwodmromna820840sjomas8103740jsdm193740sjnzs107492302gihgi975yigtryr453dhiye34yfdse4678ygyiuiy97689420jgfd5d3f8i9865giu9";
	 private final String B = "Ogc8j6gZNiQ+eaR47aNghalUWQe/SF9SjxmI6prjgfiGOUwuvT447s4lMVQkBtIjYA7uGvr8ozz5S3WwrFxg3Y4rzkNubY2/1n2BGc27n";
	 private final String BA = "/Ytien79mh907dyeink565230384mso3jeim&YTGim9m3HUY";
	 private final String BB = "/84037503eirmdome3m4kdwm3h4_dkw0384msorw awm3kmsim9m3HUY";
	 private final String C = "dPdp7xAPz6PIb7xZNTGdO/AIOQHwEi45hxSFdnmzA6JvBIOJ/9grHwUry1+pCMzQay88M1aSNIlvzxmpHGXxrbSqYEKITGBFkPtfDAmatzirUMsK";
	 private final String D = "cXYu1TLXqfp96Vl10RwoD1cd3BAs2/3NcOVO9W2m7f5WGMJjz8tOM/WenBkKPA3LEvrk4x7Ds6QIDAQAB";
	
	private String LicenseKey;

	public String getLicenseKey() {
		setLicenseKey();
		return LicenseKey;
	}

	private void setLicenseKey(){ 
		
		StringBuilder s = new StringBuilder();
		s.append(A);
		s.append(B);
		s.append(C);
		s.append(D);
		
		LicenseKey = s.toString();
	}
	
	private String server;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	public VideoListAdapter CacheListAdapter;

	public VideoListAdapter getCacheListAdapter() {
		return CacheListAdapter;
	}

	public void setCacheListAdapter(VideoListAdapter cacheListAdapter) {
		CacheListAdapter = cacheListAdapter;
	}
	
	public Context ctx;

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}
	
	
	
	
}

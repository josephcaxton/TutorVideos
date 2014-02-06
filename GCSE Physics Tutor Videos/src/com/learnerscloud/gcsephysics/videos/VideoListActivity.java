package com.learnerscloud.gcsephysics.videos;

import java.util.ArrayList;




import android.app.ListActivity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class VideoListActivity extends ListActivity{
	
	

	 public void onCreate(Bundle icicle) {
		    super.onCreate(icicle);
		   
		   // Refresh Main Again in case we have Access
		    reloadObjects();
		    loadAdapter();
		   
		  
	 }

	private void  loadAdapter() {
		LocalCache cache = ((LocalCache)getApplicationContext());
		
		ArrayList<VideoItem> values = cache.getVideoItems();
		
		if(values == null){
			
			Toast.makeText(this, "Could not connect to server please try again", Toast.LENGTH_SHORT).show();
		}
		else
		{
		
		VideoListAdapter adapter = new VideoListAdapter(this,R.layout.listlayout, values);
		setListAdapter(adapter);
		// Store the adapter to cache
		cache.setCacheListAdapter(adapter);
		cache.setCtx(getParent());
		}
	}
	 

	 
	 @Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
	 
		 LocalCache cache = ((LocalCache)getApplicationContext());
			//get selected items
		 VideoItem selectedValue = (VideoItem)getListAdapter().getItem(position);
			//Toast.makeText(this, selectedValue.getM3u8(), Toast.LENGTH_SHORT).show();
		 if (Integer.parseInt(selectedValue.getFree()) == 1 || (cache.getAllowAccess() == 1 && Integer.parseInt(selectedValue.getFree()) != 2) ){
		 
		
		String fullUrl = cache.getServer() + cache.getVideoURL() + "/" + selectedValue.getM3u8() + "/all.m3u8" ;
			
		Bundle basket = new Bundle();
		basket.putString("_FullURL", fullUrl);
		Intent VideoPlayer = new Intent(getParent(),VideoPlayer.class);
		VideoPlayer.putExtras(basket);
		
		startActivity(VideoPlayer);
		 }
		 // we are logging in
		 else if (Integer.parseInt(selectedValue.getFree()) == 2 && cache.getAllowAccess() == 0 ){
			 
			 Intent login = new Intent(getParent(),Login.class);
			 startActivity(login);
		 }
		 // here we are logging out
		 else if (Integer.parseInt(selectedValue.getFree()) == 2 && cache.getAllowAccess() == 1){
			 
			 cache.setAllowAccess(0);
			// Relaod objects from main
				reloadObjects();
				// Restart this activity
				this.onCreate(null);
		 }
		 
		 else
			 
		 {
			 // To price list
			 Intent buy = new Intent(getParent(),Buy.class);
				//TabGroupActivity parentActivity = (TabGroupActivity)getParent(); 
				//parentActivity.startChildActivity("Buy List",buy);
			 startActivity(buy);
			 
		
		 }
		
		
	}
	 
	 private void reloadObjects(){
		 // The refreshed the VideoItems objects
		
		 TabGroupActivity tabGroup = (TabGroupActivity)getParent();
		 LocalActivityManager LocalActivity =  tabGroup.getLocalActivityManager();
		 Main mainActivity = (Main)LocalActivity.getActivity("Main");
		 mainActivity.Refresh = 1;
		 mainActivity.onClick(mainActivity.Start);
		
		 
		 
	 }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LocalCache cache = ((LocalCache)getApplicationContext());
		
		if(cache.getAllowAccess() == 1){
			// Relaod objects from main
			reloadObjects();
			// Restart this activity
			this.onCreate(null);
		}
		
	}

	
	


	 
	 
}

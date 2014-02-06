package com.learnerscloud.gcseenglish.videos;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

public class TabGroup4Activity extends ActivityGroup {
	private ArrayList<String> mIdList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        if (mIdList == null) {
        	
        	mIdList = new ArrayList<String>();
        }
    }
    // Finish from child Activity
   	@Override
   	public void finishFromChild(Activity child) {
   		// Gets called where a child is finshed();
   		LocalActivityManager manager = getLocalActivityManager();
   		int index = mIdList.size()-1;
   		
   		if (index < 1) {
   			finish();
   			return;
   			}
   		
   		
   		manager.destroyActivity(mIdList.get(index), true);
   		mIdList.remove(index);
   		index--;
   		
   		//LocalCache cache = ((LocalCache)getApplicationContext());
   		//int State = cache.getLocalActivityState();
   		
   		//if(State != 1){
   			
   		String lastId = mIdList.get(index);
   		Intent lastIntent = manager.getActivity(lastId).getIntent();
   		Window newWindow = manager.startActivity(lastId, lastIntent);
   		setContentView(newWindow.getDecorView());
   		
   		//}
   		//cache.setLocalActivityState(0);
   		
   	}
   // Start Child Activity
   	public void startChildActivity(String Id, Intent intent) {
   		Window window = getLocalActivityManager().startActivity(Id,intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
   		if (window != null) {
   		mIdList.add(Id);
   		setContentView(window.getDecorView());
   		}
   	}
   	
   	// to prevent systems before android.os.Build.VERSION_CODES.ECLAIR
   	// from calling their default KeyEvent.KEYCODE_BACK during onKeyDown.
   	@Override
   	public boolean onKeyDown(int keyCode, KeyEvent event) {
   		// TODO Auto-generated method stub
   		if (keyCode == KeyEvent.KEYCODE_BACK) {
   			//preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
   			return true;
   			}
   			return super.onKeyDown(keyCode, event);
   		}
   	

   	//Overrides the default implementation for KeyEvent.KEYCODE_BACK
   	// so that all systems call onBackPressed().
   	@Override
   	public boolean onKeyUp(int keyCode, KeyEvent event) {
   		
   		if (keyCode == KeyEvent.KEYCODE_BACK) {
   			onBackPressed();
   			return true;
   			}
   			return super.onKeyUp(keyCode, event);
   			}
   	
   	//If a Child Activity handles KeyEvent.KEYCODE_BACK.
   	// Simply override and add this method.
   	@Override
   	public void onBackPressed() {
   		int length = mIdList.size();
   		if ( length > 1) {
   		Activity current = getLocalActivityManager().getActivity(mIdList.get(length-1));
   		current.finish();
   		}
   		else{
   			finish();
   		}
   	}
   	



}

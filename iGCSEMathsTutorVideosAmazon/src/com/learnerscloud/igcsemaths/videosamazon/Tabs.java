package com.learnerscloud.igcsemaths.videosamazon;

import com.learnerscloud.igcsemaths.videosamazon.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class Tabs extends TabActivity implements OnTabChangeListener{
	
	/*Notes By Joseph Caxton-Idowu on 9th sept 2011
	Now in Android develpment there is no simple way to use TabActivity with multiple tabs 
	without a big fuss
	So listen up , If not you will not understand what i have done.
	
	First I created the tabs.xml and Tabs class
	The Tabs calls >>>  TabGroup1, TabGroup1 etc
	The TabGroups extends a special class i have created called TabGroupActivity---- This is where all the weard things happens
	This TabGroupActivity tracks the tabs opened and help manage the activities using the LocalActivityManager of the ActivityGroup.
	
	If you don't understand go and read up on ActivityGroups. And if you still don't understand get excited.
	
	 */
	
	private Resources res;
    private TabHost tabHost;
     
    private int group1Id = 10000;
    private int group2Id = 20000;
    
    
    //private int group3Id = 30000; 
    //private int group4Id = 40000;
    
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		
		res = getResources(); // Resource object to get Drawables
	    tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;
	    Intent intent;  // Reusable Intent for each tab

	    // This is the Video Tab
	    intent = new Intent().setClass(this, TabGroup1.class);
	    spec = tabHost.newTabSpec("TabGroup1").setIndicator("Videos",
	                      res.getDrawable(R.drawable.nav_btn_videos_deselected))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    // This is the Coverage Tab
	    intent = new Intent().setClass(this, TabGroup2.class);
	    spec = tabHost.newTabSpec("TabGroup2").setIndicator("Coverage",
	                      res.getDrawable(R.drawable.nav_btn_coverage_deselected))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    //This is the Share Tab
	    //intent = new Intent().setClass(this, TabGroup3.class);
	    //spec = tabHost.newTabSpec("TabGroup3").setIndicator("",
	    //                  res.getDrawable(R.drawable.nav_btn_share_deselected))
	    //              .setContent(intent);
	    //tabHost.addTab(spec);
	    
	  //This is the Help Tab
	    intent = new Intent().setClass(this, TabGroup4.class);
	    spec = tabHost.newTabSpec("TabGroup4").setIndicator("Help",
	                      res.getDrawable(R.drawable.nav_btn_help_deselected))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	  //This is the Video Tab
	   // intent = new Intent().setClass(this, TabGroup5.class);
	   // spec = tabHost.newTabSpec("TabGroup5").setIndicator("Video",
	   //                   res.getDrawable(R.drawable.nav_btn_videos_deselected))
	   //               .setContent(intent);
	   // tabHost.addTab(spec);
		
	    tabHost.setOnTabChangedListener(this);
	    
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//return super.onCreateOptionsMenu(menu);
		
		//MenuInflater inflater = getMenuInflater();
	    //inflater.inflate(R.menu.settings, menu);
		
		
		/*MenuItem SoundOn = menu.add(group1Id, 0, 0, "Sound On");
		SoundOn.setIcon(R.drawable.soundon);
		
		MenuItem Soundoff = menu.add(group2Id, 0, 1, "Sound Off");
		Soundoff.setIcon(R.drawable.soundoff);*/
		
		
		//MenuItem ShowAnswerOn = menu.add(group3Id, 0, 0, "View Answer On");
		//ShowAnswerOn.setIcon(R.drawable.btncheckon);
		
		//MenuItem ShowAnswerOff = menu.add(group4Id, 0, 1, "View Answer Off");
		//ShowAnswerOff.setIcon(R.drawable.btncheckoff);
		
	    return true;

		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		OtherPreferences sharedPrefs = new OtherPreferences(this.getBaseContext());
		Integer ValueSelected = item.getGroupId();
		
		
	    if(ValueSelected == 10000){ // Sound ON
	    	//Log.e("Joseph",sharedPrefs.getPreference("sound"));
	    	//if(sharedPrefs.getPreference("sound").equalsIgnoreCase("0")){
	    		
	    		sharedPrefs.saveToPref("sound", "on");
	    		
				
	        // }
	        
	        
	         return true;
	    }
	    if(ValueSelected == 20000){ //Sound Off
	    	// Bug the if statement for string does not work for the strange reason -- Will revisit
	    	//Log.e("Joseph",sharedPrefs.getPreference("sound"));
	    	//String val = sharedPrefs.getPreference("sound");
	    	
	    	//if(val.equals("on") ){
	    		
	    		sharedPrefs.saveToPref("sound", "off");
	    		
	    	//}
	    	
	        
	    	return true;
	    }
	        
	   // if(ValueSelected == 30000){ // ShowAnswer ON
	    	
	    	//if(sharedPrefs.getPreference("ShowAnswer").equalsIgnoreCase("0")){
	    		
	    		sharedPrefs.saveToPref("ShowAnswer", "on");
	    		
	    	//}
	        
	     //   return true;
	    //}
	        
	   // if(ValueSelected == 40000){ // ShowAnswer OFF
	    	
	    		//if(sharedPrefs.getPreference("ShowAnswer").equalsIgnoreCase("1")){
	    		
	    			sharedPrefs.saveToPref("ShowAnswer", "off");
	    			
	    		//}
	    	//return true;
	  //  }
		
	    return true;
	}
	
	

	public void onTabChanged(String tabId) {
	
		
	
		
		}
	
	

}

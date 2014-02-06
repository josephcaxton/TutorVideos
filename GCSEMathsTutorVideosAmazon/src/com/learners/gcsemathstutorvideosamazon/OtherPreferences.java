package com.learners.gcsemathstutorvideosamazon;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.preference.PreferenceActivity;





public class OtherPreferences extends  PreferenceActivity {
	
	private static final String APP_SHARED_PREFS = "otherpreferences"; //  Name of the file -.xml
    private SharedPreferences appSharedPrefs;
    private Editor prefsEditor;
    
    
    public OtherPreferences(Context context)
    {
    	
    	
    	appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, MODE_WORLD_READABLE); 
        this.prefsEditor = appSharedPrefs.edit();
    }
    
    public void saveToPref(String PrefName, String PrefValue ) {
        prefsEditor.putString(PrefName, PrefValue);
        prefsEditor.commit();
    }
    public String getPreference(String PreferenceName) {
        
    	
    	return appSharedPrefs.getString(PreferenceName, "N/A");
    }




	

}

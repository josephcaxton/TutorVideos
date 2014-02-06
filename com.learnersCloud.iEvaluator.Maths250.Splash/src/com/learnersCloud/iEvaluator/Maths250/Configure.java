package com.learnersCloud.iEvaluator.Maths250;



import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Configure  extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.configure);
		 

}

	 /*public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		
		case(2):
			
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
			String value = sharedPrefs.getString("difficultyPref","");
		Toast.makeText(getBaseContext(),
				value,
                Toast.LENGTH_LONG).show();
   
			SharedPreferences customSharedPreference = getSharedPreferences(
                    "Difficulty", Activity.MODE_PRIVATE);
		
			SharedPreferences.Editor editor = customSharedPreference
                .edit();
			
			editor.putString("difficultyPref",
                    "1");
			editor.commit();
			
			Toast.makeText(getBaseContext(),
                    "Difficulty updated",
                    Toast.LENGTH_LONG).show();
			break;
		
		case(1):
			
			
			break;
		
		
		
		}
		
		return true;
	}*/
	
}

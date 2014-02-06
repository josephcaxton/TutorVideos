package com.learnersCloud.iEvaluator.Maths250;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class LocalCache extends Application{
	
	private List<QuestionLookupItem> QuestionsIds;

	public List<QuestionLookupItem> getQuestionsIds() {
		return QuestionsIds;
	}

	public void setQuestionsIds(List<QuestionLookupItem> questionsIds) {
		QuestionsIds = questionsIds;
	}
	
	// If Activity State = 0 then all is normal, that means the activity will not be cleaned off the stack
	// If it is 1 then we are in a template, Activity will be remove off the stack and destroyed
	
	private int LocalActivityState;

	public int getLocalActivityState() {
		return LocalActivityState;
	}

	public void setLocalActivityState(int activityState) {
		LocalActivityState = activityState;
	}
	
	private int  YourCorrectAnswerResult;

	public int getYourCorrectAnswerResult() {
		return YourCorrectAnswerResult;
	}

	public void setYourCorrectAnswerResult(int val) {
		YourCorrectAnswerResult+= val; // Add one to the result
	}
	public void setYourCorrectAnswerResultToZero(){
		YourCorrectAnswerResult = 0;
	}

	private int ScorableQuestions;

	public int getScorableQuestions() {
		return ScorableQuestions;
	}

	public void setScorableQuestions(int scorableQuestions) {
		ScorableQuestions+= scorableQuestions;
	}
	public void setScorableQuestionsToZero(){
		ScorableQuestions = 0;
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
	private int HaveNetworkConnection()
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
	
	
	
	
}

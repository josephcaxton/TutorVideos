package com.learnersCloud.iEvaluator.Maths250;



import java.util.ArrayList;
import java.util.List;

import com.learnersCloud.iEvaluator.Maths250.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;



public class Customise extends ActivityGroup implements OnClickListener,OnSeekBarChangeListener {
	
	SeekBar seekbar;
	TextView NumberOfQuestions;
	Button btnCustomise;
	TextView txtDifficulty;
	TextView txtTopic;
	TextView txtTypeofQuestion;
	Button btnStartTest;
	TextView FinalResult;
	private static final int MAXIMUMQUESTIONS = 250;
	
	//private static SQLiteDatabase db;
	private static DataBaseHelper myDbHelper;
	
	LocalCache cache; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.customisepage);
		 
		 View LLView = findViewById(R.id.LLCustomise);
			View root = LLView.getRootView();
			root.setBackgroundColor(Color.WHITE);
		
		 seekbar = (SeekBar)findViewById(R.id.seekBarQuestions);
		 NumberOfQuestions =(TextView)findViewById(R.id.TextViewNumberOfQuestions);
		 btnCustomise = (Button)findViewById(R.id.btnCustomise);
		 txtDifficulty = (TextView)findViewById(R.id.txtDifficulty);
		 txtTopic = (TextView)findViewById(R.id.txtTopic);
		 txtTypeofQuestion = (TextView)findViewById(R.id.txtTypeofQuestion);
		 btnStartTest =(Button)findViewById(R.id.btnStartTest);
		 FinalResult =(TextView)findViewById(R.id.textViewFinalAnswer);
		 cache = ((LocalCache)getApplicationContext());
		 
		 seekbar.setMax(MAXIMUMQUESTIONS);
		 seekbar.setProgress(10);
		 NumberOfQuestions.setText("You selected" +  " " + "10" + " " + "Questions" + ", your search criteria may not return records and does not necessarily have all types of questions");
		 
		 seekbar.setOnSeekBarChangeListener(this);
		 NumberOfQuestions.setOnClickListener(this);
		 btnCustomise.setOnClickListener(this);
		 btnStartTest.setOnClickListener(this);
		 FinalResult.setVisibility(View.INVISIBLE);

		ConfigureDifficulty();
		ConfigureTopics();
		ConfigureTypeOfQuestion();
		
			}


	
		 
	private void ConfigureTypeOfQuestion() {
		
		openDatabaseConnection();
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
			if(sharedPrefs.getString("typeofquestionPref","").contentEquals("") || sharedPrefs.getString("typeofquestionPref","").contentEquals("0"))
			{
				// If the is the first time app is started then add pref for Type of Question = 0 meaning ALL or if the Pref is already 0 fill the txtbox with ALL;
				
				/*Toast.makeText(getBaseContext(),
	                    "Type of Q is empty",
	                    Toast.LENGTH_LONG).show();*/
				myDbHelper.close();
				
				SharedPreferences customSharedPreference = getSharedPreferences(
	                    "typeofquestionPref", MODE_PRIVATE);
			
				SharedPreferences.Editor editor = customSharedPreference
	                .edit();
				
				editor.putString("typeofquestionPref",
	                    "0");
				editor.commit();
				
				txtTypeofQuestion.setText("ALL");
				
			}
			else
			{
		
			 String WhereStatement = "_id " + "= " + sharedPrefs.getString("typeofquestionPref","");
			 
			
				String[] Columns = {"DESCRIPTION"};
				
				Cursor c = myDbHelper.query("QUESTIONTEMPLATE", Columns,
						WhereStatement, null, null, null, "DESCRIPTION");
				
				 if (c.getCount() > 0){
				
				c.moveToPosition(0);
				txtTypeofQuestion.setText(c.getString(0));
				
				 }
				 else
				 {
					 Toast.makeText(getBaseContext(),
			                    "Type of Question --Data missing from database: "+ sharedPrefs.getString("typeofquestionPref",""),
			                    Toast.LENGTH_LONG).show();
					 txtTypeofQuestion.setText("ALL");
				 }
				
				c.close();
				myDbHelper.close();
			}

		
	}


	private void ConfigureDifficulty() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		int value = Integer.parseInt(sharedPrefs.getString("difficultyPref","0"));
		
		switch(value){
		
		case 1:
			txtDifficulty.setText("Foundation");
			
			break;
			
		case 3:
			txtDifficulty.setText("Higher");
			
			break;
			
		case 0:
			txtDifficulty.setText("Foundation / Higher");
			break;
		
		}
		
		
		
	}




	public void onClick(View v) {
		
		switch (v.getId()){
		 
		case(R.id.btnCustomise):
			
			//Intent ChangeDifficultyIntent = new Intent("com.learnersCloud.iEvaluator.Maths250.CHANGEDIFFICULTYACTIVITY");
			//TabGroupActivity parentActivity = (TabGroupActivity)getParent(); 
			//startActivity(ChangeDifficultyIntent);
			
			Intent CustomiseIntent = new Intent("com.learnersCloud.iEvaluator.Maths250.CONFIGURE");
			startActivity(CustomiseIntent);
			
			
			break;
			
		case(R.id.btnStartTest):
			
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		    cache.setScorableQuestionsToZero();
			String Difficulty = sharedPrefs.getString("difficultyPref","0");
			String Topic = sharedPrefs.getString("topicPref","0");
			String TypeOfQuestion = sharedPrefs.getString("typeofquestionPref","0");
			int AccessLevel = sharedPrefs.getInt("versionPref", 2);		
			StringBuilder sb = new StringBuilder();
			
			if(!Difficulty.equals("0")){
				
				sb.append("SELECT  a._id, a.Difficulty, b.QuestionHeader_Topic,b.QuestionTemplate FROM QuestionItems a " +
			    		"inner join QuestionHeader b on a.QuestionHeader1 = b._id  where a.Difficulty = " + Difficulty +  " ");
			}
			else
			{
		    
			sb.append("SELECT  a._id, a.Difficulty, b.QuestionHeader_Topic,b.QuestionTemplate FROM QuestionItems a " +
		    		"inner join QuestionHeader b on a.QuestionHeader1 = b._id  where 1 = 1 " +  " ");
			
			}
		    if(!Topic.equals("0") ){
		    	
		    	sb.append(" and b.QuestionHeader_Topic = " + Topic + " ");
		    }
		    
		    if(!TypeOfQuestion.equals("0")){
		    	
		    	sb.append(" and b.QuestionTemplate = " + TypeOfQuestion + " ");
		    }
		    
		    // Access Level
		    int Access = AccessLevel  ;
		     sb.append(" and a.AccessLevel <= " + Access + " ");
		     
		    sb.append(" order by RANDOM() LIMIT ");
		    sb.append(Integer.toString(seekbar.getProgress()));
		    
		    
		    /*Toast.makeText(getBaseContext(),
            sb.toString(),
            Toast.LENGTH_LONG).show(); */
		    openDatabaseConnection();
		    
		    Cursor c = myDbHelper.Rawquery(sb.toString(), null);
		    
		    if (c.getCount() > 0){
		    	
		    	/*Toast.makeText(getBaseContext(),
		                "Your query returned " + String.valueOf(c.getCount()) + " records.",
		                Toast.LENGTH_LONG).show(); */
		    	// now add this records to out global array in cache.
		    	List<QuestionLookupItem> mylist = new ArrayList<QuestionLookupItem>();
		    	
		    	if (c.moveToFirst()) {
		    		
		    		do {
		    			
		    			QuestionLookupItem item = new QuestionLookupItem();
		    			item.set_id(c.getInt(c.getColumnIndex("_id")));
		    			item.set_Difficulty(c.getInt(c.getColumnIndex("DIFFICULTY")));
		    			item.set_QuestionHeader_Topic(c.getInt(c.getColumnIndex("QUESTIONHEADER_TOPIC")));
		    			item.set_QuestionTemplate(c.getInt(c.getColumnIndex("QUESTIONTEMPLATE")));
		    			// Descriptive type questions = 1 and cannot be scored so add only scorable questions
		    			if (c.getInt(c.getColumnIndex("QUESTIONTEMPLATE")) != 1){
		    				cache.setScorableQuestions(1);
		    			}
		    			mylist.add(item);
		    			
		    			

		            } while (c.moveToNext());
		    		
		    		
		    		c.close();
					myDbHelper.close();

		    		// this is a global class i have created to store global stuff
		    		
		    		
		    		cache.setQuestionsIds(mylist);
		    		cache.setYourCorrectAnswerResultToZero();
		    		
		    		Toast.makeText(getBaseContext(),"Your query selected " +
				              String.valueOf(mylist.size()) + " question(s), descriptive type questions are not added to marks",
				                Toast.LENGTH_LONG).show(); 
		    		
		    		// Now get the list back and loop each question;
		    		// This design does not seem to work as the dialog does not pause the code
		    		//List<QuestionLookupItem> list = cache.getQuestionsIds();
		    		
		    		/*for(Iterator<QuestionLookupItem> i = list.iterator(); i.hasNext();){
		    			
		    			QuestionLookupItem item = i.next();

		    			// Load Question via template 
		    			LoadQuestionViaTemplate(item);
		    			
		    			
		    			Toast.makeText(getBaseContext(),
				               String.valueOf(item.get_id()),
				                Toast.LENGTH_LONG).show(); 
		    			i.next();
		    		}*/
		    		
		    		
		    		
		    		
		    		
		    		
		    		// get the first item.
		    		List<QuestionLookupItem> list = cache.getQuestionsIds();
		    		
		    		LoadQuestionViaTemplate(list.get(0));
		    		
		    		

		    	}
		    	
		    	

		    	
		    }
		    else
		    {
		    	Toast.makeText(getBaseContext(),
		                "Your query did not return any records.",
		                Toast.LENGTH_LONG).show(); 
		    }
		    
			
			break;
		
		}
		
		
	}
	public void LoadQuestionViaTemplate(QuestionLookupItem item) {
		
		Integer TemplateType = item.get_QuestionTemplate();
		//This is to create a unique number 
		Integer UniqueID = item.get_id();
		switch(TemplateType){
		
		case 1: // Descriptive Question.
			
			Bundle basket2 = new Bundle();
			basket2.putInt("_id", item.get_id());
			
			Intent DescriptiveType = new Intent(getParent(),DescriptiveTypeQuestion.class);
			DescriptiveType.putExtras(basket2);
			
			TabGroupActivity parentActivity2 = (TabGroupActivity)getParent(); 
			parentActivity2.startChildActivity(UniqueID.toString(),DescriptiveType);
			
			break;
			
		case 2: // Multiple Choice Single Answer
			
			//Dialog MultipleChoiceSingleAnswerdialog = new Dialog(Customise.this);
			//MultipleChoiceSingleAnswerdialog.setContentView(R.layout.multiplechoicesingleanswer);
			//MultipleChoiceSingleAnswerdialog.setTitle("This is a test");
			
			
			//MultipleChoiceSingleAnswerdialog.show();
			
			// Get the _id of the first Question.Push it to the next Activity
			Bundle basket = new Bundle();
			basket.putInt("_id", item.get_id());
			
			Intent MultipleChoiceSingleAnswer = new Intent(getParent(),MultipleChoiceSingleAnswer.class);
			MultipleChoiceSingleAnswer.putExtras(basket);
			
			TabGroupActivity parentActivity = (TabGroupActivity)getParent(); 
			parentActivity.startChildActivity(UniqueID.toString(),MultipleChoiceSingleAnswer);
			
			
			
			
		
		break;
		
		/*case 0: // Multiple Choice Multiple Answer   Missing in Maths
			
			Bundle basket1 = new Bundle();
			basket1.putInt("_id", item.get_id());
			
			Intent MultipleChoiceMultipleAnswer = new Intent(getParent(),MultipleChoiceMultipleAnswer.class);
			MultipleChoiceMultipleAnswer.putExtras(basket1);
			
			TabGroupActivity parentActivity1 = (TabGroupActivity)getParent(); 
			parentActivity1.startChildActivity(UniqueID.toString(),MultipleChoiceMultipleAnswer);
			
		break; */
		
		
		case 3: // True Or False
			Bundle basket5 = new Bundle();
			basket5.putInt("_id", item.get_id());
			
			Intent Truefalse = new Intent(getParent(),TrueFalse.class);
			Truefalse.putExtras(basket5);
			
			TabGroupActivity parentActivity5 = (TabGroupActivity)getParent(); 
			parentActivity5.startChildActivity(UniqueID.toString(),Truefalse);
			
		break;
		
		
		case 4: // Yes Or NO
			
			Bundle basket4 = new Bundle();
			basket4.putInt("_id", item.get_id());
			
			Intent Yesno = new Intent(getParent(),YesNo.class);
			Yesno.putExtras(basket4);
			
			TabGroupActivity parentActivity4 = (TabGroupActivity)getParent(); 
			parentActivity4.startChildActivity(UniqueID.toString(),Yesno);
				
		break;
		
		
		case 5: // Fill the Blanks
			
			Bundle basket3 = new Bundle();
			basket3.putInt("_id", item.get_id());
			
			Intent Filltheblanks = new Intent(getParent(),FillTheBlanks.class);
			Filltheblanks.putExtras(basket3);
			
			TabGroupActivity parentActivity3 = (TabGroupActivity)getParent(); 
			parentActivity3.startChildActivity(UniqueID.toString(),Filltheblanks);
			
		break;
		
		
		
		}
		
	}




	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Main parentActivity = (Main)getParent();
		parentActivity.onBackPressed();
	}




	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		NumberOfQuestions.setText("You selected" +  " " + Integer.toString(progress) + " " + "Questions" );

		
	}




	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}




	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}




	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		ConfigureDifficulty();
		ConfigureTopics();
		ConfigureTypeOfQuestion();
		
	try { 
			
			if(cache.getScorableQuestions() > 0){
			// if there is a result let the user know about it
			FinalResult.setText("Your last result is " + Integer.toString(cache.getYourCorrectAnswerResult()) + " out of " + Integer.toString(cache.getScorableQuestions()));
			FinalResult.setVisibility(View.VISIBLE);
			 }
			
		}
		
		catch (NumberFormatException e) {
		    
		}

		
	}
	
private void ConfigureTopics(){
		
		 openDatabaseConnection();
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		if(sharedPrefs.getString("topicPref","").contentEquals("") || sharedPrefs.getString("topicPref","").contentEquals("0")){
			
			 // If the is the first time app is started then add pref for Type of Question = 0 meaning ALL or if the Pref is already 0 fill the txtbox with ALL;
			myDbHelper.close();
			
			SharedPreferences customSharedPreference = getSharedPreferences(
                    "topicPref", MODE_PRIVATE);
		
			SharedPreferences.Editor editor = customSharedPreference
                .edit();
			
			editor.putString("topicPref",
                    "0");
			editor.commit();
			 txtTopic.setText("ALL");
		}
		else
		{
			
			 String WhereStatement = "_id " + "= " + sharedPrefs.getString("topicPref","");
			 
			
				String[] Columns = {"TOPICNAME"};
				
				Cursor c = myDbHelper.query("TOPICS", Columns,
						WhereStatement, null, null, null, "TOPICNAME");
				
				 if (c.getCount() > 0){
				
				c.moveToPosition(0);
				txtTopic.setText(c.getString(0));
				
				 }
				 else
				 {
					 Toast.makeText(getBaseContext(),
			                    "Topic ----Data missing from database: "+ sharedPrefs.getString("topicPref",""),
			                    Toast.LENGTH_LONG).show();
					 txtTopic.setText("ALL");
				 }
				
				c.close();
				myDbHelper.close();
				
		}

			/*
			 List<String> list = new ArrayList<String>();
			  if (c.getCount() > 0){

			c.moveToFirst();

			do{
				list.add(c.getString(c.getColumnIndex("TOPICNAME")));
			}

			while (c.moveToNext());
			}

			txtTopic.setText(list.get(0));
			}

			finally{

				
			if (myDataBase != null)
			myDataBase.close();
			}*/
		
}




private void openDatabaseConnection() {
	myDbHelper = new DataBaseHelper(getBaseContext());
	
	try {
		
		myDbHelper.openDataBase();
		
 
	}catch(SQLException sqle){
 
		throw sqle;
 
	}
}



	


	


}

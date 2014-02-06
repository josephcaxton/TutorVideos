package com.learnersCloud.iEvaluator.Maths250;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class TrueFalse extends ActivityGroup implements OnClickListener{
	
private static DataBaseHelper myDbHelper;
	
	WebView QuestionHeaderBox;
	TextView Dia_ReasonHeaderBox;
	String CorrectAnswer;
	String CorrectAnswerReasonInHTML;
	//RadioGroup TRadioGroup;
	RadioButton rdoTrue;
	RadioButton rdoFalse;
	Button BtnContinue;
	String Question;
	LocalCache cache;
	
	ImageView FirstImage;
	ImageView SecondImage;
	Button BtnReason;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.truefalse);
		
		QuestionHeaderBox =(WebView)findViewById(R.id.TQuestionHeaderBox);
		//TRadioGroup = (RadioGroup)findViewById(R.id.TradioGroup1);
		rdoTrue = (RadioButton)findViewById(R.id.TTrue);
		rdoFalse = (RadioButton)findViewById(R.id.TFalse);
		FirstImage = (ImageView)findViewById(R.id.TImage1);
		SecondImage =(ImageView)findViewById(R.id.TImage2); 
		BtnContinue =(Button)findViewById(R.id.TContinue);
		BtnReason = (Button)findViewById(R.id.TrueFalseShowReason);
		
		
		rdoTrue.setOnClickListener(this);
		rdoFalse.setOnClickListener(this);
		BtnContinue.setOnClickListener(this);
		
		BtnReason.setOnClickListener(this);
		BtnReason.setTag(4); // I am using this to identify the Reason Button
		BtnReason.setVisibility(View.INVISIBLE);
		
		rdoTrue.setTag(1);
		rdoFalse.setTag(2);
		BtnContinue.setTag(3); // I am using this to identify the continue button
		BtnContinue.setVisibility(View.INVISIBLE);
		View LLView = findViewById(R.id.LLTrueFalse);
		View root = LLView.getRootView();
		root.setBackgroundColor(Color.BLACK);
		
		cache = ((LocalCache)getApplicationContext());
		
		WebSettings websettings = QuestionHeaderBox.getSettings();
		websettings.setJavaScriptEnabled(true);
		websettings.setSavePassword(false);
        websettings.setSaveFormData(false);
        websettings.setSupportZoom(true);
        websettings.setBuiltInZoomControls(true);
		
		Bundle getBasket = getIntent().getExtras();
		Integer _id = getBasket.getInt("_id");
		
		GetQuestion(_id);
		GetAnswers(_id);
		
		
		
		
		
		
	}
	
private void GetQuestion(Integer _id) {
		

		openDatabaseConnection();
		
		String WhereStatement = "_id " + "= " + String.valueOf(_id);
		String[] Columns = {"QUESTION"};
		
		Cursor c = myDbHelper.query("QUESTIONITEMS", Columns,
				WhereStatement, null, null, null, null);
		
		
		if (c.getCount() > 0){
			
			c.moveToPosition(0);
			
			Question =  c.getString(0);
			
			 }
		//file remove extension
		String TrimmedQuestion = Question.subSequence(0, Question.lastIndexOf('.')).toString();
		
		Uri path = Uri.parse("file:///android_asset/QImages/" + TrimmedQuestion + ".jpeg");
	    String HTML = "<img src=\"" + path.toString() + "\"" + " width=\"100%\"" + " />";
	    
	    final String mimeType = "text/html";
	    final String encoding = "utf-8";

		
		QuestionHeaderBox.loadDataWithBaseURL("fakeit://not required", HTML, mimeType, encoding, null);
		
		//QuestionHeaderBox.getSettings().setInitialScale(75);
		c.close();
		myDbHelper.close();
		
	}

private void GetAnswers(Integer _id) {
	
	openDatabaseConnection();
	
	String WhereStatement = "QUESTIONITEM " + "= " + String.valueOf(_id);
	String[] Columns = {"CORRECT","ANSWERTEXT","REASON"};
	
	Cursor c = myDbHelper.query("ANSWERS", Columns,
			WhereStatement, null, null, null, null);
	
		if (c.getCount() > 0){
		
			c.moveToFirst();
		
			List<Answer> list = new ArrayList<Answer>();
			do{
				Answer ans = new Answer();
				ans.set_Correct(c.getInt(c.getColumnIndex("CORRECT")));
				ans.set_AnswerText(c.getString(c.getColumnIndex("ANSWERTEXT")));
				ans.set_Reason(c.getString(c.getColumnIndex("REASON")));
				list.add(ans);
				// if the answer is correct keep the string in HTML 
				 if(ans.get_Correct() == 1){
					 CorrectAnswer = ans.get_AnswerText();
					 CorrectAnswerReasonInHTML = ans.get_Reason();
				 }
			}

			while (c.moveToNext());
			
			c.close();
			myDbHelper.close();
			
			
		           
			
			}
		
			
	}

	
	
	public void onClick(View v) {
		
		rdoTrue.setEnabled(false);
		rdoFalse.setEnabled(false);
		
		if(Integer.parseInt(v.getTag().toString()) == 1){
			
			BtnContinue.setVisibility(View.VISIBLE);
			if(CorrectAnswerReasonInHTML != null){
			BtnReason.setVisibility(View.VISIBLE);
			}
			if (CorrectAnswer.equalsIgnoreCase("1") ) {
				
				Toast.makeText(getBaseContext(),
	               String.valueOf("Correct !"),
	                Toast.LENGTH_SHORT).show();
				// Clap for correct answer
				OtherPreferences sharedPrefs = new OtherPreferences(this.getBaseContext());
				
				if(sharedPrefs.getPreference("sound").equalsIgnoreCase("on")){
				LocalCache cache = ((LocalCache)getApplicationContext());
				cache.PlaySound(this, R.raw.clapping);
				}
				
			FirstImage.setImageResource(R.drawable.correct);
			SecondImage.setImageResource(R.drawable.wrong);
			cache.setYourCorrectAnswerResult(1); // Add one to result
			
			}
			else
			{
				Toast.makeText(getBaseContext(),
	               String.valueOf("Wrong !"),
	                Toast.LENGTH_SHORT).show();
				
				// Buzz for wrong answer
				OtherPreferences sharedPrefs = new OtherPreferences(this.getBaseContext());
				
				if(sharedPrefs.getPreference("sound").equalsIgnoreCase("on")){
				LocalCache cache = ((LocalCache)getApplicationContext());
				cache.PlaySound(this, R.raw.cough);
				}
				
				FirstImage.setImageResource(R.drawable.wrong);
				SecondImage.setImageResource(R.drawable.correct);
			}
			
		}
		else if(Integer.parseInt(v.getTag().toString()) == 2)
			
		{
			BtnContinue.setVisibility(View.VISIBLE);
			if(CorrectAnswerReasonInHTML != null){
			BtnReason.setVisibility(View.VISIBLE);
			}
			
			if (CorrectAnswer.equalsIgnoreCase("0") ) {
				
				Toast.makeText(getBaseContext(),
	               String.valueOf("Correct !"),
	                Toast.LENGTH_SHORT).show();
				// Clap for correct answer
				OtherPreferences sharedPrefs = new OtherPreferences(this.getBaseContext());
				
				if(sharedPrefs.getPreference("sound").equalsIgnoreCase("on")){
				LocalCache cache = ((LocalCache)getApplicationContext());
				cache.PlaySound(this, R.raw.clapping);
				}
				
				FirstImage.setImageResource(R.drawable.wrong);
				SecondImage.setImageResource(R.drawable.correct);			
			cache.setYourCorrectAnswerResult(1); // Add one to result
			
			}
			else
			{
				Toast.makeText(getBaseContext(),
	               String.valueOf("Wrong !"),
	                Toast.LENGTH_SHORT).show();
				
				// Buzz for wrong answer
				OtherPreferences sharedPrefs = new OtherPreferences(this.getBaseContext());
				
				if(sharedPrefs.getPreference("sound").equalsIgnoreCase("on")){
				LocalCache cache = ((LocalCache)getApplicationContext());
				cache.PlaySound(this, R.raw.cough);
				}
				FirstImage.setImageResource(R.drawable.correct);
				SecondImage.setImageResource(R.drawable.wrong);
			}
				
			
		}
		// this is going to show the reason 
				else if (Integer.parseInt(v.getTag().toString()) == 4)
				{
					showDialog(1);
				}
		else
		{
			NextQuestion();  
		}
		
	}
	
		private void NextQuestion() {
		
		
		List<QuestionLookupItem> list = cache.getQuestionsIds();
		// remove the question we have just answered
		
		//QuestionLookupItem  item = list.get(0);
		//Log.e("Joseph",item.get_id().toString());
		
		list.remove(0);
		//cache.setQuestionsIds(list);
		
		if(list.size() < 1){
		// Finish here
		//QuestionLookupItem  item2 = list.get(0);
			Toast.makeText(getBaseContext(),
		               String.valueOf("Test Finished ! " + "Result is " + Integer.toString(cache.getYourCorrectAnswerResult())
		            		   + " out of " + Integer.toString(cache.getScorableQuestions())),
		                Toast.LENGTH_LONG).show(); 
			
			//Record the Results to Database;
			String Query = "INSERT INTO RESULTS " + " (CorrectAnswers,TotalQuestions)" + " Values (" 
			+ cache.getYourCorrectAnswerResult() + ", " + cache.getScorableQuestions() + " ); ";
			DataBaseHelper myDbHelper = new DataBaseHelper(getBaseContext());
			myDbHelper.InsertToDatabase(Query);
			
			// Go back to Customise Screen
			onBackPressed();
			 
		}
		else
		{
			// Create a new list with the remainer of questions and add it to the cache
			List<QuestionLookupItem> mylist = new ArrayList<QuestionLookupItem>();
			
			for(int i = 0; i< list.size(); i++){
				 if(list.get(i).equals(null)){
				
				 }
				 	else
				 	{
				 		mylist.add(list.get(i));
				 	}
		}
			cache.setQuestionsIds(mylist);
			
			List<QuestionLookupItem> list1 = cache.getQuestionsIds();
			LoadQuestionViaTemplate(list1.get(0));
		}
		
		//Log.e("Joseph",item2.get_id().toString());
			
		
	}
		private void LoadQuestionViaTemplate(QuestionLookupItem item) {
			
			// Get back to the customise and run another question using the QuestionLookupItem
			
			TabGroupActivity tabGroup = (TabGroupActivity)getParent();
			 LocalActivityManager LocalActivity =  tabGroup.getLocalActivityManager();
			 Customise cus = (Customise)LocalActivity.getActivity("Customise");
			 cus.LoadQuestionViaTemplate(item);
			
			 CustomonBackPressed();
			
		}





		@Override
		public void onBackPressed() {
			
			// Flag that we are coming from the BackButton
			cache.setLocalActivityState(0);
			TabGroupActivity parentActivity = (TabGroupActivity)getParent();
			parentActivity.onBackPressed();
		}
		
		
		
	   public void CustomonBackPressed() {
			
			// Flag that we are coming from the CustomBackButton
		   
		    cache = ((LocalCache)getApplicationContext());
			cache.setLocalActivityState(1);
			TabGroupActivity parentActivity = (TabGroupActivity)getParent();
			parentActivity.onBackPressed();
		}
		
		
		@Override
		protected void onPause() {
			super.onPause();
			
		}





		@Override
		protected void onStop() {
			super.onStop();
			
		}



		@Override
		protected Dialog onCreateDialog(int id) {
			
			//Reason Dialog.
	        Context mContext = getParent(); 
	        final Dialog dialog = new Dialog(mContext);
	        dialog.setContentView(R.layout.reasondialog);
	        dialog.setTitle("Answer Reason");
	        Dia_ReasonHeaderBox =(TextView)dialog.findViewById(R.id.ReasonHeaderBox);
	        Button ReasonCancel = (Button)dialog.findViewById(R.id.ReasonCancel);
	        
	        ReasonCancel.setOnClickListener(new OnClickListener()
	        		{
	        	public void onClick(View v){
	        		dialog.dismiss();
	        	}
	        	
	        });
	        
	        if(CorrectAnswerReasonInHTML != null){
	        	
	        	Dia_ReasonHeaderBox.setText(Html.fromHtml(CorrectAnswerReasonInHTML)); 
	        }
	        else
	        {
	        	Dia_ReasonHeaderBox.setText(Html.fromHtml("No reason entered on system")); 
	        }
	        return dialog;
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

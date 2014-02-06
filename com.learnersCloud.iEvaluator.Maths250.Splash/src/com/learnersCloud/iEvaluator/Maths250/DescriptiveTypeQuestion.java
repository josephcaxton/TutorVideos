package com.learnersCloud.iEvaluator.Maths250;



import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
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
import android.widget.Toast;


import android.widget.TextView;

public class DescriptiveTypeQuestion extends ActivityGroup implements OnClickListener {
	private static DataBaseHelper myDbHelper;
	WebView QuestionHeaderBox;
	Button BtnContinue;
	Button BtnShowAnswer;
	TextView txtAnswer;
	String Question;
	String CorrectAnswerInHTML;
	String CorrectAnswerReasonInHTML;
	LocalCache cache;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.descriptivetypequestions);
		
		QuestionHeaderBox =(WebView)findViewById(R.id.DQuestionHeaderBox);
		txtAnswer =(TextView)findViewById(R.id.DtxtAnswer);
		BtnShowAnswer =(Button)findViewById(R.id.DbtnShowAnswer);
		BtnContinue= (Button)findViewById(R.id.DBtnContinue);
		
		BtnShowAnswer.setOnClickListener(this);
		BtnContinue.setOnClickListener(this);
		
		View LLView = findViewById(R.id.LLDescriptiveType);
		View root = LLView.getRootView();
		root.setBackgroundColor(Color.BLACK);
		
		cache = ((LocalCache)getApplicationContext());
		
		WebSettings websettings = QuestionHeaderBox.getSettings();
		websettings.setJavaScriptEnabled(true);
		websettings.setSavePassword(false);
        websettings.setSaveFormData(false);
        websettings.setSupportZoom(true);
        websettings.setBuiltInZoomControls(true);
        txtAnswer.setText("You cannot type into this box. Work out your answer and press Show Answer");
        
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
				// As this is descriptive type there will be only one answer
				 if(ans.get_Correct() == 1){
					 CorrectAnswerInHTML = ans.get_AnswerText();
					 
					 if (CorrectAnswerReasonInHTML != null){
						 
					 CorrectAnswerReasonInHTML = ans.get_Reason();
					 }
					 else
					 {
						 CorrectAnswerReasonInHTML = ""; 
					 }
					 
				 }
			}

			while (c.moveToNext());
			
			c.close();
			myDbHelper.close();
			
			
			
		}
		
			
	}

	
	
	public void onClick(View v) {
		
		switch (v.getId()){
		case R.id.DbtnShowAnswer:
			if(txtAnswer.getText().length() == 73){
				
				StringBuilder sb = new StringBuilder();
				sb.append(CorrectAnswerInHTML);
				sb.append("<br/>");
				sb.append(CorrectAnswerReasonInHTML);
				
				txtAnswer.setText(Html.fromHtml(sb.toString()));
				BtnShowAnswer.setEnabled(false);
			}
		
		break;
		
		case  R.id.DBtnContinue:
			
			NextQuestion();
			
		break;
		
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
		
	

	
	private void openDatabaseConnection() {
		myDbHelper = new DataBaseHelper(getBaseContext());
		
		try {
			
			myDbHelper.openDataBase();
			
	 
		}catch(SQLException sqle){
	 
			throw sqle;
	 
		}
	}

}

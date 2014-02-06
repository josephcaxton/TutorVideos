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
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class FillTheBlanks extends ActivityGroup implements OnClickListener{

	private static DataBaseHelper myDbHelper;
	private List<EditText> EditTextArray;
	private boolean FlipSwitch = false;
	WebView QuestionHeaderBox;
	TextView Dia_ReasonHeaderBox;
	TableLayout Table;
	String Question;
	String CorrectAnswer;
	String CorrectAnswerReasonInHTML;
	Button BtnContinue;
	List<String> CorrectAnswerList;
	LocalCache cache;
	Button BtnReason;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filltheblanks);
		
		QuestionHeaderBox =(WebView)findViewById(R.id.FQuestionHeaderBox);
		Table =(TableLayout)findViewById(R.id.Ftable);
		BtnContinue= (Button)findViewById(R.id.FContinue);
		BtnReason = (Button)findViewById(R.id.FilltheBlanksShowReason);
		
		BtnContinue.setOnClickListener(this);
		BtnContinue.setTag(2); // I am using this to identify the continue button
		
		BtnReason.setOnClickListener(this);
		BtnReason.setTag(3); // I am using this to identify the Reason Button
		BtnReason.setVisibility(View.INVISIBLE);
		
		//BtnContinue.setVisibility(View.INVISIBLE);
		View LLView = findViewById(R.id.LLFillTheBlanks);
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
			CorrectAnswerList= new ArrayList<String>();
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
					 CorrectAnswerList.add(CorrectAnswer); // Collection of correct answers
				 }
			}

			while (c.moveToNext());
			
			c.close();
			myDbHelper.close();
			
			// Initialize EditText array 
			EditTextArray = new ArrayList<EditText>();
			
			 for (int current = 0; current < list.size(); current++)
		        {
				 
				 // Create a table row 
				 TableRow tr = new TableRow(this);
				 tr.setId(1000+current);
				 tr.setGravity(Gravity.CENTER);
				 
				 tr.setLayoutParams(new LayoutParams(
		                    LayoutParams.FILL_PARENT,
		                    LayoutParams.FILL_PARENT));
				
				 
				 
				 // Create each EditText and add to row and then add row to table
				 EditText et = new EditText(this);
				 et.setId(1000+current);
				 et.setHint("<enter answer>");
				 et.setSingleLine();
				 et.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
				 et.setTextColor(Color.BLACK);
				
				 et.setWidth(420);
				 et.setHeight(LayoutParams.FILL_PARENT);
				 
				 // Add button the button array
				 EditTextArray.add(et);
				 
				 tr.addView(et);
		            
		            Table.addView(tr);
				 
		        }
			
			}
		
			
	}


	
	
	
	public void onClick(View v) {
		
		if(FlipSwitch== false){
			// What this does is. It take each correct answer in string
			// Compare them to what the user has entered. If a list of correct answers
			// is created called i, for every correct answer reduce i by 1
			// then check if 1 = 0, if it is 0 user is correct.
			Integer i = CorrectAnswerList.size();
			for(int current = 0; current <  CorrectAnswerList.size(); current++){
				
					String Answer = CorrectAnswerList.get(current);
					for(int count = 0; count <  EditTextArray.size(); count++){
						EditText tx = EditTextArray.get(count);
						String AnswerInEditText = tx.getText().toString();
						if(AnswerInEditText.trim().equalsIgnoreCase(Answer.trim())){
							i--;
						}
					}
					
			}
			FlipSwitch = true;
			if(i == 0){
				// User is Correct
				cache.setYourCorrectAnswerResult(1); // Add one to result
				Toast.makeText(getBaseContext(),
			               String.valueOf("Correct !"),
			                Toast.LENGTH_SHORT).show(); 
				if(CorrectAnswerReasonInHTML != null){
				BtnReason.setVisibility(View.VISIBLE);
				}
				// Clap for correct answer
				OtherPreferences sharedPrefs = new OtherPreferences(this.getBaseContext());
				
				if(sharedPrefs.getPreference("sound").equalsIgnoreCase("on")){
				LocalCache cache = ((LocalCache)getApplicationContext());
				cache.PlaySound(this, R.raw.clapping);
				}
				
				NextQuestion(); 
			}
			else{
				// user is wrong
				
				Toast.makeText(getBaseContext(),
			               String.valueOf("Wrong Answer !"),
			                Toast.LENGTH_SHORT).show();
				if(CorrectAnswerReasonInHTML != null){
				BtnReason.setVisibility(View.VISIBLE);
				}
				// Buzz for wrong answer
				OtherPreferences sharedPrefs = new OtherPreferences(this.getBaseContext());
				
				if(sharedPrefs.getPreference("sound").equalsIgnoreCase("on")){
				LocalCache cache = ((LocalCache)getApplicationContext());
				cache.PlaySound(this, R.raw.cough);
				}
				
				// Show the Answer
				for(int current = 0; current <  CorrectAnswerList.size(); current++){
					
					String Answer = CorrectAnswerList.get(current);
					EditText tx = EditTextArray.get(current);
					tx.setText(Answer);
					FlipSwitch = true;
					
				}
				
			}
			
		}
		// this is going to show the reason 
				else if (Integer.parseInt(v.getTag().toString()) == 3)
				{
					showDialog(1);
				}
		
		else{
			// user has clicked on this button before so go to the next question
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

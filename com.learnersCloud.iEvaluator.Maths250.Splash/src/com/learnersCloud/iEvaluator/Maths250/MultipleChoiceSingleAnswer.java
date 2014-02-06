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

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MultipleChoiceSingleAnswer extends ActivityGroup implements OnClickListener{

	
	private static DataBaseHelper myDbHelper;
	private List<Button> ButtonArray;
	WebView QuestionHeaderBox;
	TextView Dia_ReasonHeaderBox;
	TableLayout Table;
	String Question;
	String CorrectAnswerInHTML;
	String CorrectAnswerReasonInHTML;
	Button BtnContinue;
	Button BtnReason;
	//boolean Cleaner = true; // Used to cleanup this object by making it not to show the child on the TabGroupActivity
	LocalCache cache;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multiplechoicesingleanswer);
		
		QuestionHeaderBox =(WebView)findViewById(R.id.QuestionHeaderBox);
		Table =(TableLayout)findViewById(R.id.Qtable);
		BtnContinue= (Button)findViewById(R.id.Continue);
		BtnReason = (Button)findViewById(R.id.MultipleChoiceSingleShowReason);
		
		
		BtnContinue.setOnClickListener(this);
		BtnContinue.setTag(2); // I am using this to identify the continue button
		BtnContinue.setVisibility(View.INVISIBLE);
		
		BtnReason.setOnClickListener(this);
		BtnReason.setTag(3); // I am using this to identify the Reason Button
		BtnReason.setVisibility(View.INVISIBLE);
		
		View LLView = findViewById(R.id.LLMultipleSingle);
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
		
		/*Toast.makeText(getBaseContext(),
	               String.valueOf(String.valueOf(_id)),
	                Toast.LENGTH_LONG).show(); */
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
						 CorrectAnswerInHTML = ans.get_AnswerText();
						 CorrectAnswerReasonInHTML = ans.get_Reason();
					 }
				}

				while (c.moveToNext());
				
				c.close();
				myDbHelper.close();
				
				// Initialize button array 
				ButtonArray = new ArrayList<Button>();
				
				 for (int current = 0; current < list.size(); current++)
			        {
					 Answer ThisAns = list.get(current);
					 // Create a table row 
					 TableRow tr = new TableRow(this);
					 tr.setId(1000+current);
					 tr.setGravity(Gravity.CENTER);
					 
					 tr.setLayoutParams(new LayoutParams(
			                    LayoutParams.FILL_PARENT,
			                    LayoutParams.FILL_PARENT));
					
					 
					
			        
			         /*TextView Tv = new TextView(this);
			         Tv.setId(1000+current);
			         Tv.setText(Html.fromHtml(ThisAns.get_AnswerText()));
			         Tv.setTextColor(Color.WHITE);
			         
			            tr.addView(Tv);
			            
			            Table.addView(tr);*/
					 
					 
					 // Configure each button and add to row and then add row to table
					 Button bt = new Button(this);
					 bt.setId(1000+current);
					 bt.setText(Html.fromHtml(ThisAns.get_AnswerText()));
					 bt.setTextColor(Color.BLACK);
					 bt.setGravity(Gravity.CENTER);
					 bt.setWidth(420);
					 bt.setHeight(LayoutParams.FILL_PARENT);
					 bt.setTextSize(12);
					 // Add the correct answer to the button
					 bt.setTag(ThisAns.get_Correct());
					 
					 bt.setOnClickListener(this);
					 // Add button the button array
					 ButtonArray.add(bt);
					 
					 tr.addView(bt);
			            
			            Table.addView(tr);
					 
			        }
				
				}
			
				
		}
	







	public void onClick(View v) {
		
		// Check if the answer is correct or not
		if(Integer.parseInt(v.getTag().toString()) == 1){
			
			Toast.makeText(getBaseContext(),
		               String.valueOf("Correct !"),
		                Toast.LENGTH_SHORT).show(); 
		
			
			
			Button btn = (Button)v;  // cast the view to a button
			btn.setClickable(false);
			btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.correct, 0);
			BtnContinue.setVisibility(View.VISIBLE);
			if(CorrectAnswerReasonInHTML != null){
			BtnReason.setVisibility(View.VISIBLE);
			}
			// disable all the buttons
						for(int i = 0; i < ButtonArray.size(); i++){
						
						Button btn_all = ButtonArray.get(i);
						btn_all.setEnabled(false);
						}
						
			cache.setYourCorrectAnswerResult(1); // Add one to result
			
			// Clap for correct answer
			OtherPreferences sharedPrefs = new OtherPreferences(this.getBaseContext());
			
			if(sharedPrefs.getPreference("sound").equalsIgnoreCase("on")){
			LocalCache cache = ((LocalCache)getApplicationContext());
			cache.PlaySound(this, R.raw.clapping);
			}
			
			
		}
		else if(Integer.parseInt(v.getTag().toString()) == 0)
			
		{
			/*Toast.makeText(getBaseContext(),
		               String.valueOf("Wrong !"),
		                Toast.LENGTH_SHORT).show(); */
			
			for(int i = 0; i < ButtonArray.size(); i++){
				
				Button btn = ButtonArray.get(i);
				Object tag = btn.getTag();
				if(Integer.parseInt(tag.toString()) == 1){
					
					String LastText = CorrectAnswerInHTML;  //+ " " + "&#10003";
					btn.setText(Html.fromHtml(LastText));
					btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.correct, 0);
					btn.setEnabled(false);
				}
				else
				{
					btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.wrong, 0);
					btn.setEnabled(false);
				}
			}
			
			BtnContinue.setVisibility(View.VISIBLE);
			if(CorrectAnswerReasonInHTML != null){
			BtnReason.setVisibility(View.VISIBLE);
			}
			// Buzz for Wrong Answer
			OtherPreferences sharedPrefs = new OtherPreferences(this.getBaseContext());
			
			if(sharedPrefs.getPreference("sound").equalsIgnoreCase("on")){
			LocalCache cache = ((LocalCache)getApplicationContext());
			cache.PlaySound(this, R.raw.cough);
			}
			
		}
		// this is going to show the reason 
		else if (Integer.parseInt(v.getTag().toString()) == 3)
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

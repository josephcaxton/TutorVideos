package com.learnerscloud.gcsephysics.videos;



import com.learnerscloud.gcsephysics.videos.LocalCache;
import com.learnerscloud.gcsephysics.videos.R;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Help extends Activity implements OnClickListener{
   
	LocalCache cache;
	TextView Ad;
	Button email;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.loadWidgets();

       
        
    }
    
    private void loadWidgets(){
    	
    	 setContentView(R.layout.helppage);
       
 		Ad = (TextView)findViewById(R.id.txtHelpAd);
 		email = (Button)findViewById(R.id.btnReportProblem); 
 		email.setOnClickListener(this);
 
         
         setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
         String StringHTML = "<p>App purchase problem?</p>" +
         		"<p>If you have a query about a payment to the App Store, please contact the store directly." +
         		" We don’t process these payments ourselves, so unfortunately we can’t help you with them.</p>" +
         		"<p>Still need help?</p>" +
         		"<p>If your problem has still not been solved then feel free to contact us and we’ll be happy to help. " +
         		"GCSE students, please remember: we can’t answer any questions about your course, or help you with your homework!</p>";
         Ad.setText(Html.fromHtml(StringHTML));
      
    }




	public void onClick(View v) {
		
		if(v.getId() == R.id.btnReportProblem){
			
			// Send Email:
			SendEmail();
			
		}
		
		
	}



private void SendEmail() {
	

	Intent emailIntent = new Intent(Intent.ACTION_SEND);
	 emailIntent.setType("text/plain");
	 emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ "support@learnerscloud.com"});
	 emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Query from Android app");
	 emailIntent.putExtra(Intent.EXTRA_TEXT, "Add your message here");
	 
	 try{
		 startActivity(Intent.createChooser(emailIntent, "Send mail..."));
 
	 }
	 catch (Exception e){
		 
		 Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();

		 }
	 
	}






}

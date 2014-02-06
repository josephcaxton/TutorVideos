package com.learnerscloud.gcsebiology.videos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SingupLogin extends Activity implements OnClickListener{

	TextView Message;
	Button signuphere;
	Button loginhere;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.signuplogin);

	        Message =(TextView)findViewById(R.id.txttitlemessagehere);
	        signuphere = (Button)findViewById(R.id.btnsignuphere);
	        loginhere = (Button)findViewById(R.id.btnloginhere);
	        
	        String StringHTML ="<p>Important</p>" + 
	        "<p>To make a purchase you’ll need to create an account or login to an existing learnerscloud account.</p>";
	        Message.setText(Html.fromHtml(StringHTML));
	        

	        signuphere.setOnClickListener(this);
	        loginhere.setOnClickListener(this);
	        
	 }
	 
	 @Override
		public void onClick(View v) {
			
			if(v.getId() == R.id.btnsignuphere){
				
				 Intent signup = new Intent(this.getApplicationContext(),Signup.class);
				 startActivity(signup);
				 finish();
				 	
				}
				 else if (v.getId() == R.id.btnloginhere){
					
					 Intent login = new Intent(this.getApplicationContext(),Login.class);
					 startActivity(login);
					 finish();
				 }
			
				 else
				 {
					 
				 }
			
	 }
			
		
}


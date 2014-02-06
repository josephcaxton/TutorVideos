package com.learners.gcsemathstutorvideosamazon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Signup extends Activity implements OnClickListener{
	
	TextView Message;
	EditText EAddress;
	EditText Pwd;
	Button signup;
	
	String ProductIdentifier;
	String Deviceid;
	String UserName;
	String Password;
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        Bundle getBasket = getIntent().getExtras();
	        ProductIdentifier  = getBasket.getString("_ProductIdentifier");
	        Deviceid  = getBasket.getString("_Deviceid");
	        
	        setContentView(R.layout.signup);

	        Message =(TextView)findViewById(R.id.txtsignupmessage);
	        EAddress =(EditText)findViewById(R.id.txtsignupemailaddress);
	        Pwd =(EditText)findViewById(R.id.txtsignuppassword);
	        signup = (Button)findViewById(R.id.btnsignuplogin);
	       
	        String StringHTML ="<p>Why do i have to supply my email? </p>" + 
	        "<p>Your LearnersCloud subscription is accessible via your computer, iOS and now android devices. " +
	        "<br>To access your account on a device you’ll need to create an account login using your email address and password.</p>";
	        Message.setText(Html.fromHtml(StringHTML));
	        

	        signup.setOnClickListener(this);
	 }
	 
	 @Override
		public void onClick(View v) {
			
			if(v.getId() == R.id.btnsignuplogin ){
				
				 v.setEnabled(false);
				 UserName = EAddress.getText().toString();
				 Password =Pwd.getText().toString();
				 if(isValidEmail(UserName) && Password.length() > 4){
					//alert("Username:" + UserName + "Password:" + Password + "Productid:" + ProductIdentifier + "Deviceid:" + Deviceid);
					 Thread SecondThread = new Thread(){
							public void run(){
								
								SendInfoToLearnersCloud();
								
							}
						};
						SecondThread.start();
				 	}
				 else
				 {
					 v.setEnabled(true);
					 Message.setText("Email address or password entered is not valid. Try again"); 
				 }
			}
			
		}
	 
	 public final static boolean isValidEmail(CharSequence target) {
		    if (target == null) {
		        return false;
		    } else {
		        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		    }
		}
	 
	 private void SendInfoToLearnersCloud(){
		 LocalCache cache = ((LocalCache)getApplicationContext()); 
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost = new HttpPost(cache.getServer() +"/Services/android/VideoSubscription.asmx/RecordSubscription");
			
			try {
				
				 //Give user access now
				
				 List<NameValuePair> params = new ArrayList<NameValuePair>();
				 params.add(new BasicNameValuePair("productIdentifier", ProductIdentifier));
				 params.add(new BasicNameValuePair("DeviceID", Deviceid));
				 params.add(new BasicNameValuePair("email", UserName));
				 params.add(new BasicNameValuePair("password", Password));
		         
				
				httppost.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse response = httpclient.execute(httppost);
				String str = inputStreamToString(response.getEntity().getContent()).toString(); //"http://learnerscloud.com/\">0</int>" ; 
					
				/*Return codes:
	                 0 OK
	                -1 Error in C#
	                -2 ProductIdentifier not found
	                -3 Error in stored proc */
					
					
					Pattern regex = Pattern.compile("http://learnerscloud.com/\">(.*)</int>");
					Matcher regexMatcher = regex.matcher(str);
					
					while (regexMatcher.find()){
						
						final String RetVal =  regexMatcher.group(1);
						if(Integer.parseInt(RetVal) == 0){
							
							cache.setAllowAccess(1);
							
							finish();
							
							
						}
						else if (Integer.parseInt(RetVal) < 0){
							
							    								    	
							 			
							    	runOnUiThread(new Runnable() {
							    		  public void run() {
							    			  int returnValue = Integer.parseInt(RetVal);
										    	 String MessageDetail = "";
										    	if (returnValue == -1){
										    		
										    		MessageDetail = "There was a problem opening your account at LearnersCloud email support@learnersCloud.com";
										    	}
										    	else if (returnValue == -2){
										    		
										    		MessageDetail = "No product Identifier, contact LearnersCloud by emailing support@learnersCloud.com ";
										    	}
										    	else if(returnValue == -3){
										    		MessageDetail = "Error in stored proc, contact LearnersCloud by emailing support@learnersCloud.com";
										    	}

							    			  alert(MessageDetail);
							    		  }
							    	
							
							 
							    	});
							
						}
					}
						
					
					} 
				catch (UnsupportedEncodingException e) {
			        e.printStackTrace();
			    } catch (ClientProtocolException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
		 
		 
	 }
	 private StringBuilder inputStreamToString(InputStream is) {
	     String line = "";
	     StringBuilder total = new StringBuilder();
	     // Wrap a BufferedReader around the InputStream
	     BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	     // Read response until the end
	     try {
	      while ((line = rd.readLine()) != null) { 
	        total.append(line); 
	      }
	     } catch (IOException e) {
	      e.printStackTrace();
	     }
	     // Return full string
	     return total;
	    }
	        
	 void alert(String message) {
	        AlertDialog.Builder bld = new AlertDialog.Builder(this);
	        bld.setMessage(message);
	        bld.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,
                        int which) {
                    //dialog.dismiss();
                    
                    finish();
                }
            });
	        Log.d("LearnersCloud", "Showing alert dialog: " + message);
	        bld.create().show();
	       
	    }
}

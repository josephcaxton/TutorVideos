package com.learnerscloud.gcseenglishtutorvideosamazon;

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
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login  extends Activity implements OnClickListener{
	
	TextView TitleMessage;
	EditText EmailAddress;
	EditText Password;
	Button Login;
	Button Cancel;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.logindiag);

	        TitleMessage =(TextView)findViewById(R.id.txttitlemessage);
	        EmailAddress =(EditText)findViewById(R.id.txtEmailAddress);
	        Password =(EditText)findViewById(R.id.txtPassword);
	        Login = (Button)findViewById(R.id.btnlogin);
	        Cancel = (Button)findViewById(R.id.btncancel);
	        
	        TitleMessage.setText("If you have an existing subscription that you purchased on the LearnersCloud website, login below.");
	        

	        Login.setOnClickListener(this);
	        Cancel.setOnClickListener(this);
	        
	       
	        
	    }

	@Override
	public void onClick(View v) {
		
		switch (v.getId()){
		 
		case(R.id.btncancel):
			
			finish();
			
		break;
		
		case (R.id.btnlogin):
			
			Thread SecondThread = new Thread(){
			public void run(){
				
				CheckSubscription();
				
			}
		};
		
			
		
		SecondThread.start();
		break;
		
	}
			
			
			
		
		
		}
	
	private void CheckSubscription() {
		
		LocalCache cache = ((LocalCache)getApplicationContext());
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(cache.getServer() + "/Services/iOS/VideoSubscription.asmx/FindSLSubscription");
		
		 try {
			
			 String Email = EmailAddress.getText().toString();
			 String Pass = Password.getText().toString();
			 String Appid = String.valueOf(cache.getAppid());
			 List<NameValuePair> params = new ArrayList<NameValuePair>();
			 params.add(new BasicNameValuePair("AppID", Appid));
			 params.add(new BasicNameValuePair("SLemail", Email));
			 params.add(new BasicNameValuePair("SLpassword", Pass));
	         
			
				httppost.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse response = httpclient.execute(httppost);
				String str = inputStreamToString(response.getEntity().getContent()).toString();
				
				/* Response from Silverlight Authentication
			      0 = OK
			     -1 = No current subscription
			     -2 = Authentication failed
			     -3 = AppID not recognised
			     -4 = Any other exception*/
				
				
				Pattern regex = Pattern.compile("http://learnerscloud.com/\">(.*)</int>");
				Matcher regexMatcher = regex.matcher(str);
				
				while (regexMatcher.find()){
					
					String RetVal =  regexMatcher.group(1);
					if(Integer.parseInt(RetVal) == 0){
						
						cache.setAllowAccess(1);
						
						finish();
						
						
					}
					else if (Integer.parseInt(RetVal) < 0){
						runOnUiThread(new Runnable() {
						     public void run() {
						
						 TitleMessage.setText("You don't have any running subscription");
						 TitleMessage.setTextColor(Color.BLUE);
						     }
						});
					}
					
				}
			} catch (UnsupportedEncodingException e) {
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
}

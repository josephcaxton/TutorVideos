package com.learnersCloud.iEvaluator.Maths250;

import java.util.ArrayList;
import java.util.List;

import com.learnersCloud.iEvaluator.Maths250.R;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Result extends Activity implements OnClickListener{
	
	String PlusChartSize;
	WebView webView;
	Bitmap bmImg;
	Button Refresh;
	ProgressBar Progress;
	private static DataBaseHelper myDbHelper;
	private List<Integer> TotalArrays;
	StringBuilder sbCorrect;
	StringBuilder sbWrong;
	StringBuilder sbGrand;
	String Last;
	final Activity activity = this;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.resultpage);
        
        webView  = (WebView)findViewById(R.id.webViewResults);
        Refresh  = (Button)findViewById(R.id.ResultRefresh);
        Progress = (ProgressBar)findViewById(R.id.ResultsprogressBar);
        Refresh.setEnabled(false);
        Progress.setVisibility( View.VISIBLE);
        Refresh.setOnClickListener(this);
        WebSettings websettings = webView.getSettings();
		websettings.setJavaScriptEnabled(true);
		websettings.setSavePassword(false);
        websettings.setSaveFormData(false);
        websettings.setSupportZoom(true);
        websettings.setDefaultZoom(ZoomDensity.MEDIUM);
        websettings.setAppCacheEnabled(false);
        
        
        webView.setWebViewClient(new WebViewClient() {

        	   public void onPageFinished(WebView view, String url) {
        		   if (Progress.getVisibility() == View.VISIBLE) {
                   	Progress.setVisibility(View.INVISIBLE);
        	    }
        	   }

			/*@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				
				view.loadUrl(url);
				
				return true;
			}*/
        	   
        	});

        /*webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
            	activity.setTitle("Loading...");
                activity.setProgress(progress * 100);
 
                if(progress == 100)
                    activity.setTitle(R.string.app_name);
            }
            
            public void onPageFinished(WebView view, String url) {
                
                if (Progress.getVisibility() == View.VISIBLE) {
                	Progress.setVisibility(View.INVISIBLE);
                	
                }
            }
        });
        
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                // Handle the error
            }
 
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
            
                view.loadUrl(url);
                
                Progress.setVisibility( View.INVISIBLE);
                return true;
            }
        });*/
        
        
        LoadWebView();
        
      
        
    }



	private void LoadWebView() {
		
		 if(CheckConnection()){
	    	   
	    	   Refresh.setEnabled(true);
	    	   GetDataFromDatabase();
	    	    
	    	 // this is just ot remove the last comma on the strings
	    	   if(sbCorrect != null ){
	    	     int lastCommaCorrect = sbCorrect.length();
	    	     int lastCommaWrong = sbWrong.length();
	    	     
	    	     sbCorrect.deleteCharAt(lastCommaCorrect -1);
	    	     sbWrong.deleteCharAt(lastCommaWrong - 1);
	    	     
	    	    
	    	    String SiteAddress =  "http://chart.apis.google.com/chart?";
	    	    // We are testing for TAB Screen size here
	    	    
	    	    DisplayMetrics dm = new DisplayMetrics();
	    	     getWindowManager().getDefaultDisplay().getMetrics(dm);
	    	     
	    	    if(dm.heightPixels == 1024 && dm.widthPixels == 600){
	    	    	
	    	    	 PlusChartSize = "chs=600x400&";
	    	    }
	    	    else if(dm.heightPixels == 976 && dm.widthPixels == 600){
	    	    		
	    	    	PlusChartSize = "chs=600x400&";
	    	    }
	    	    else
	    	    {
	    	    	 PlusChartSize = "chs=400x400&";
	    	    }
	    	    
	    	    String PlusChartType = "cht=bvs&";
	    	    String PlusChartColor = "chco=0000FF,FF0000&";
	    	    String PlusVisibleAxis = "chxt=y&";
	    	    
	    	    //Get Max Value of Total
	    	    Integer max = 0;
	    	    max = TotalArrays.get(0);
	    	    for (Integer i = 1; i < TotalArrays.size(); i++)
	    	      {
	    	      if (TotalArrays.get(i) > max)
	    	        {
	    	        max = TotalArrays.get(i);
	    	        }
	    	      }
	    	    
	    	    String PlusAxisRange = "chxr=0,0," + Integer.toString(max)  + "&";
	    	    
	    	    String PlusLegend = "chdl=Correct Answers|Wrong Answers&";
	    	    String PlusSeriesColors = "chco=0000FF,FF0000&";
	    	    String PlusLegendPosition = "chdlp=b&chd=t:";
	    	    String PlusCorrect = sbCorrect.toString();
	    	    String PlusDivider = "|";
	    	    String PlusWrong = sbWrong.toString();
	    	    String PlusScaling = "&chds=0," + Integer.toString(max);
	    	    
	    	    sbGrand = new StringBuilder();
	    	    sbGrand.append(SiteAddress);
	    	    sbGrand.append(PlusChartSize);
	    	    sbGrand.append(PlusChartType);
	    	    sbGrand.append(PlusChartColor);
	    	    sbGrand.append(PlusVisibleAxis);
	    	    sbGrand.append(PlusAxisRange);
	    	    sbGrand.append(PlusLegend);
	    	    sbGrand.append(PlusSeriesColors);
	    	    sbGrand.append(PlusLegendPosition);
	    	    sbGrand.append(PlusCorrect);
	    	    sbGrand.append(PlusDivider);
	    	    sbGrand.append(PlusWrong);
	    	    sbGrand.append(PlusScaling);
	    	    
	    	    Last = sbGrand.toString();
	    	    
	    	    // Now Download Image
	    	    
	    	   LoadImageFromWebOperations(Last); 
	    	    
	    	    
	    	   // image.setImageDrawable(drawable);
	    	    
	    	   }
	    	   else
	    	   {
	    		   Toast.makeText(getBaseContext(),
			               "You do not have any test result, click on Questions to start a test ",
			                Toast.LENGTH_LONG).show(); 
	    		   if (Progress.getVisibility() == View.VISIBLE) {
	                   	Progress.setVisibility(View.INVISIBLE);
	        	    }
	    	   }
	    	   
	       }
	       else
	    	   
	       {
	    	   Toast.makeText(getBaseContext(),
		               "Internet connection is not available ! ",
		                Toast.LENGTH_LONG).show();
	    	   if (Progress.getVisibility() == View.VISIBLE) {
                  	Progress.setVisibility(View.INVISIBLE);
       	    }
	    	   
	       }
	        
		
	}



	private void LoadImageFromWebOperations(String last) {
		
		try{
			 

			webView.loadUrl(last);
			
			
			/*URL FileUrl = null;
			FileUrl= new URL(last);
			HttpURLConnection conn= (HttpURLConnection)FileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			
			int length = conn.getContentLength();
			int[] bitmapData =new int[length];
			byte[] bitmapData2 =new byte[length];
			InputStream is = conn.getInputStream();

			bmImg = BitmapFactory.decodeStream(is);
			image.setImageBitmap(bmImg);*/

			
		
		}
		
		catch (Exception e) {
		System.out.println("Exc="+e);
		
		
		}
			
	}



	private void GetDataFromDatabase() {
		
		openDatabaseConnection();
		
		
		String[] Columns = {"CorrectAnswers","TotalQuestions"};
		
		Cursor c = myDbHelper.query1("RESULTS", Columns,
				null, null, null, null, "_id DESC", "10");
		
		 if (c.getCount() > 0){
				
			 sbCorrect = new StringBuilder();
			 sbWrong = new StringBuilder();
			 TotalArrays = new ArrayList<Integer>();
			 
			 if (c.moveToFirst()) {
		    		
		    		do {
		    			
		    			int CorrectVal = c.getInt(c.getColumnIndex("CorrectAnswers"));
		    			sbCorrect.append( Integer.toString(CorrectVal) + ",");
		    			
		    			int TotalVal = c.getInt(c.getColumnIndex("TotalQuestions"));
		    			int WrongVal = TotalVal - CorrectVal;
		    			sbWrong.append(Integer.toString(WrongVal) + ",");
		    			
		    			TotalArrays.add(TotalVal);
		    			

		            } while (c.moveToNext());
		    		
				
				 }
		
		
		 }
		 c.close();
		myDbHelper.close();
		 
	}



	private Boolean CheckConnection() {
		LocalCache cache = ((LocalCache)getApplicationContext());
		if(cache.GetConnectionType() > 0){
			
			return true;
			
		}
		else
			
		{
			return false;
		}
		 
		 
	}
	
	private void openDatabaseConnection() {
		myDbHelper = new DataBaseHelper(getBaseContext());
		
		try {
			
			myDbHelper.openDataBase();
			
	 
		}catch(SQLException sqle){
	 
			throw sqle;
	 
		}
	}



	public void onClick(View v) {
		
		if(v.getId() == R.id.ResultRefresh){
			
			 Progress.setVisibility( View.VISIBLE);
			 LoadWebView();
			
		}
				
		
	}



	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
		TabGroup2Activity parentActivity = (TabGroup2Activity)getParent();
		parentActivity.onBackPressed();
	}
	

}

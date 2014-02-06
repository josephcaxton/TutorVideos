package com.learnerscloud.gcsechemistrytutorvideosamazon;

import java.util.ArrayList;




import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Coverage extends ListActivity {

	
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        this.loadAdapter();

       
        
    }
    private void  loadAdapter(){
    	
    	ArrayList<String> values = new ArrayList<String>();
    	values.add("Chemistry AQA Examination Coverage");
    	values.add("Chemistry CCEA Examination Coverage");
    	values.add("Chemistry Edexcel Examination Coverage");
    	values.add("Chemistry OCR 21st Century Examination Coverage");
    	values.add("Chemistry OCR Gateway B Examination Coverage");
    	values.add("Chemistry WJEC Examination Coverage");
    	values.add("");
    	values.add("");
    	values.add("");
    	values.add("");
    	values.add("");
    	values.add("");
    	values.add("");
    	values.add("");
    	values.add("");
    	values.add("");
    	values.add("");
    	
    	
    	CoverageAdapter adapter = new CoverageAdapter(this,R.layout.coverage, values);
    	
    	setListAdapter(adapter);
    	
    
     
   }
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	
    	// Check if Internet connection available
    				LocalCache cache = ((LocalCache)getApplicationContext());
    				int retVal = cache.HaveNetworkConnection();
    				 
    				 
    				if(retVal == 0 ){
    					
    					AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
    					builder.setCancelable(true);
    					builder.setTitle("No internet connection");
    					builder.setMessage("Your device is not connected to the internet. You need access to the internet to view coverage ");
    					builder.setPositiveButton("OK",
    	                        new DialogInterface.OnClickListener() {
    	                            @Override
    	                            public void onClick(DialogInterface dialog,
    	                                    int which) {
    	                                dialog.dismiss();
    	                                System.exit(0);
    	                            }
    	                        });
    					AlertDialog alert = builder.create();
    	                alert.show();
    	               
    				}
    				else
    				{
    					
    				
    	
    	switch(position){
		
    	case 0:
			String pdf = "http://www.learnerscloud.com/doc/chem/GCSE%20Chemistry%20AQA%20Examination%20Coverage.pdf"; 
			Bundle basket = new Bundle();
			basket.putString("_FullURL", pdf);
			Intent CoverageBrowser = new Intent(getParent(),CoverageBrowser.class);
			CoverageBrowser.putExtras(basket);
			
			startActivity(CoverageBrowser);
		
		break;
		
	case 1:
		String pdf1 = "http://www.learnerscloud.com/doc/chem/GCSE%20Chemistry%20CCEA%20Examination%20Coverage.pdf"; 
		Bundle basket1 = new Bundle();
		basket1.putString("_FullURL", pdf1);
		Intent CoverageBrowser1 = new Intent(getParent(),CoverageBrowser.class);
		CoverageBrowser1.putExtras(basket1);
		
		startActivity(CoverageBrowser1);
		
		
		break;
		
	case 2:
		String pdf2 = "http://www.learnerscloud.com/doc/chem/GCSE%20Chemistry%20Edexcel%20Examination%20Coverage.pdf"; 
		Bundle basket2 = new Bundle();
		basket2.putString("_FullURL", pdf2);
		Intent CoverageBrowser2 = new Intent(getParent(),CoverageBrowser.class);
		CoverageBrowser2.putExtras(basket2);
		
		startActivity(CoverageBrowser2);
		break;
	case 3:
		String pdf3 = "http://www.learnerscloud.com/doc/chem/GCSE%20Chemistry%20OCR%2021st%20Century%20A%20Examination%20Coverage.pdf"; 
		Bundle basket3 = new Bundle();
		basket3.putString("_FullURL", pdf3);
		Intent CoverageBrowser3 = new Intent(getParent(),CoverageBrowser.class);
		CoverageBrowser3.putExtras(basket3);
		
		startActivity(CoverageBrowser3);
		break;
	case 4:
		String pdf4 = "http://www.learnerscloud.com/doc/chem/GCSE%20Chemistry%20OCR%20Gateway%20B%20Examination%20Coverage.pdf"; 
		Bundle basket4 = new Bundle();
		basket4.putString("_FullURL", pdf4);
		Intent CoverageBrowser4 = new Intent(getParent(),CoverageBrowser.class);
		CoverageBrowser4.putExtras(basket4);
		
		startActivity(CoverageBrowser4);
		break;
	case 5:
		String pdf5 = "http://www.learnerscloud.com/doc/chem/GCSE%20Chemistry%20WJEC%20Examination%20Coverage.pdf"; 
		Bundle basket5 = new Bundle();
		basket5.putString("_FullURL", pdf5);
		Intent CoverageBrowser5 = new Intent(getParent(),CoverageBrowser.class);
		CoverageBrowser5.putExtras(basket5);
		
		startActivity(CoverageBrowser5);
		break;
			
		
    	}
		}
    }


}
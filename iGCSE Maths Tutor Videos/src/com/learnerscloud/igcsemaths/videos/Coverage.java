package com.learnerscloud.igcsemaths.videos;

import java.util.ArrayList;

import com.learnerscloud.igcsemaths.videos.R;

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
    	values.add("Edexcel iGCSE Coverage");
    	values.add("Cambridge iGCSE");
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
				String pdf = "http://www.learnerscloud.com/doc/maths/MATHS-EDEXCEL%20IGCSE.pdf"; 
				Bundle basket = new Bundle();
				basket.putString("_FullURL", pdf);
				Intent CoverageBrowser = new Intent(getParent(),CoverageBrowser.class);
				CoverageBrowser.putExtras(basket);
				
				startActivity(CoverageBrowser);
			
			break;
			
		case 1:
			String pdf1 = "http://www.learnerscloud.com/doc/maths/MATHS-CAMBRIDGE%20IGCSE.pdf"; 
			Bundle basket1 = new Bundle();
			basket1.putString("_FullURL", pdf1);
			Intent CoverageBrowser1 = new Intent(getParent(),CoverageBrowser.class);
			CoverageBrowser1.putExtras(basket1);
			
			startActivity(CoverageBrowser1);
			
			
			break;
			
		
			
    		}
		}
    }


}
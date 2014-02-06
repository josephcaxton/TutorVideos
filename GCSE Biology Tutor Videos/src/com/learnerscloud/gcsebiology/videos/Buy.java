package com.learnerscloud.gcsebiology.videos;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.learnerscloud.gcsebiology.videos.R;
import com.learnerscloud.gcsebiology.videos.util.IabHelper;
import com.learnerscloud.gcsebiology.videos.util.IabResult;
import com.learnerscloud.gcsebiology.videos.util.Inventory;
import com.learnerscloud.gcsebiology.videos.util.Purchase;
import com.learnerscloud.gcsebiology.videos.util.SkuDetails;


public class Buy extends ListActivity{
	
	private static final String TAG = "LearnersCloud";
	IabHelper mHelper;
	ArrayList<SkuDetails> ShoppingItems;
	String WiFiAddress;
	String ProductToBuy;
	
	
	// Products
	static final String Onemonth = "androidbiology1month"; //"";"android.test.purchased"
	static final String Threemonths = "androidbiology3months";
	static final String Sixmonths = "androidbiology6months";
	static final String Ninemonths = "androidbiology9months";
	static final String Twelvemonths = "androidbiology12months";

	 public void onCreate(Bundle icicle) {
		    super.onCreate(icicle);
		  
		    LocalCache cache = ((LocalCache)getApplicationContext());
		    String base64EncodedPublicKey = cache.getLicenseKey();
		    
		   
		    // 1, Setup the connection via IabHelper
		    mHelper = new IabHelper(this, base64EncodedPublicKey);
		    //mHelper.enableDebugLogging(true, "Joseph");
		    mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
	            public void onIabSetupFinished(IabResult result) {

	                if (!result.isSuccess()) {
	                    // Something is wrong.
	                    complain("Problem setting up in-app billing: " + result);
	                    return;
	                }

	                // IAB is fully set up. Now, let's get an inventory of stuff we own.
	                Log.d(TAG, "Setup successful. Querying inventory.");
	                ArrayList<String> SkuList = new  ArrayList<String>();
	                SkuList.add(Onemonth);
	                SkuList.add(Threemonths);
	                SkuList.add(Sixmonths);
	                SkuList.add(Ninemonths);
	                SkuList.add(Twelvemonths);
	                mHelper.queryInventoryAsync(true,SkuList,mGotInventoryListener);
	               
	            }
	        });
		    
		   

		    
		  
		   
		  
	 }
	 private void  loadAdapter() {
			
		// 3, Load item to show on screen	
		 ArrayList<SkuDetails> values =  ShoppingItems;
			
			if(values == null){
				
				 complain("Could not get items from in-app billing: try again");
			}
			else
			{
			
			BuyAdapter adapter = new BuyAdapter(this,R.layout.buy, values);
			setListAdapter(adapter);
			}
		}
	 
	 // 2, Listener that's called when we finish querying the items we own
	    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
	        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
	            Log.d(TAG, "Query inventory finished.");
	            
	            if (result.isFailure()) {
	                complain("Failed to query inventory: " + result);
	                return;
	            }

	            Log.d(TAG, "Query inventory was successful.");
	            // query the locally cached inventory Clear purchases
	            mHelper.queryInventoryAsync(localInventoryListener);
	           
	            
	            
	            ShoppingItems = new ArrayList<SkuDetails>();
	            
	            SkuDetails onemonth = inventory.getSkuDetails(Onemonth);
	            ShoppingItems.add(onemonth);
	            
	            SkuDetails threemonths = inventory.getSkuDetails(Threemonths);
	            ShoppingItems.add(threemonths);
	            
	            SkuDetails sixmonths = inventory.getSkuDetails(Sixmonths);
	            ShoppingItems.add(sixmonths);
	            
	            SkuDetails ninemonths = inventory.getSkuDetails(Ninemonths);
	            ShoppingItems.add(ninemonths);
	            
	            SkuDetails twelvemonths = inventory.getSkuDetails(Twelvemonths);
	            ShoppingItems.add(twelvemonths);
	            
	            loadAdapter();
	        }
	    };

	 
	 void complain(String message) {
	        Log.e(TAG, "**** App Error: " + message);
	        alert("Error: " + message);
	    }
	 void alert(String message) {
	        AlertDialog.Builder bld = new AlertDialog.Builder(this);
	        bld.setMessage(message);
	        bld.setNeutralButton("OK", null);
	        Log.d(TAG, "Showing alert dialog: " + message);
	        bld.create().show();
	    }
	 
	 private String GetWifiAddress(){
		 
		 WifiManager wm = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		 return wm.getConnectionInfo().getMacAddress();
	 }

	 @Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
		 // 4, User selects item to buy and is passed to the payment system
		 SkuDetails selectedSku = (SkuDetails)getListAdapter().getItem(position);
		 ProductToBuy = selectedSku.getSku();
		 
		 WiFiAddress = GetWifiAddress();
		 
		 // Lets signup or sign-in before lunching the purchase flow
		 //*************************************************
		 Intent Signuplogin = new Intent(Buy.this.getApplicationContext(),SingupLogin.class);
		 startActivity(Signuplogin);
		//mHelper.launchPurchaseFlow(Buy.this, ProductToBuy, 19699,     mPurchaseFinishedListener, WiFiAddress);
		 
		/*Bundle basket = new Bundle();
  		 basket.putString("_ProductIdentifier", "androidmaths3months");
  		 basket.putString("_Deviceid", WiFiAddress);
    	 Intent signup = new Intent(Buy.this.getApplicationContext(),Signup.class);
    	 signup.putExtras(basket);
    	  
    	 startActivityForResult(signup, 11);*/
		 
	 }
	 
	 // 5, Return from the store telling us purchase is concluded: I will only work onActivityResult is overide;
	 IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
	   public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
	      if (result.isFailure()) {
	         Log.d(TAG, "Error purchasing: " + result);
	         complain("Error purchasing: " + result);
	         return;
	      }      
	      if (purchase.getSku().equals(ProductToBuy) && purchase.getDeveloperPayload().equals(WiFiAddress)) {
	         
	    	 // complain("Success!!!"); 
	    	  // Now that the items are brought send all details to signup screen
	    	 Bundle basket = new Bundle();
	  		 basket.putString("_ProductIdentifier", ProductToBuy);
	  		 basket.putString("_Deviceid", WiFiAddress);
	    	 Intent signup = new Intent(Buy.this.getApplicationContext(),Signup.class);
	    	 signup.putExtras(basket);
	    	  
	    	 startActivityForResult(signup, 11);
	    	  
	      }
	   }
	};
	 
	
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
			   new IabHelper.OnConsumeFinishedListener() {
			   public void onConsumeFinished(Purchase purchase, IabResult result) {
			      if (result.isSuccess()) {
			         // provision the in-app purchase to the user
			         // (for example, credit 50 gold coins to player's character)
			      }
			      else {
			         // handle error
			      }
			   }
			};
			
			// Queries the local inventory
			IabHelper.QueryInventoryFinishedListener localInventoryListener  = new IabHelper.QueryInventoryFinishedListener() {
			   public void onQueryInventoryFinished(IabResult result,
			      Inventory inventory) {

			      if (result.isFailure()) {
			        // handle error here
			      }
			      else {
			        // does the user have the premium upgrade?
			    	 
			        boolean IhavePurchase = inventory.hasPurchase(Onemonth);        
			         if(IhavePurchase){
			        	 
			        	 mHelper.consumeAsync(inventory.getPurchase(Onemonth), 
			        			   mConsumeFinishedListener);
			         }
			        
			      }
			   }
			};
			// 5, Without this onIabPurchaseFinished will not working
			@Override
			protected void onActivityResult(int requestCode, int resultCode,Intent data) {
				// Pass on the activity result to the helper for handling
				
				if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
					//handle of activity results not related to in-app
				super.onActivityResult(requestCode, resultCode, data);
				 if(requestCode == 11){
					 // We are returning from signup so close this screen
					 finish();
				 }
				}
				else
				{
				 
				}
				  
			}
			
			
			
			@Override
		    public void onDestroy() {
		        super.onDestroy();
		        Log.d(TAG, "Destroying helper.");
		        if (mHelper != null) mHelper.dispose();
		        mHelper = null;
		    }

	 
}

package com.learnerscloud.gcseenglishtutorvideosamazon;

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
import com.amazon.inapp.purchasing.Item;
import com.amazon.inapp.purchasing.PurchasingManager;



public class Buy extends ListActivity{
	   
	private static final String TAG = "LearnersCloud";
	
	String WiFiAddress;
	String ProductToBuy;
	public ArrayList<Item> values;
	AmazonPurchaseObserver Observer;
	

	 public void onCreate(Bundle icicle) {
		    super.onCreate(icicle);
		    
	 }
	 
	 @Override
		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			
			values = new  ArrayList<Item>();
			Observer = new AmazonPurchaseObserver(this);
			PurchasingManager.registerObserver(Observer);
			
		}
	 
	   protected void loadAdapter() {
			
		// 3, Load item to show on screen	
			
			if(values == null){
				
				 complain("Could not get items from in-app billing: try again");
			}
			else
			{
			
			BuyAdapter adapter = new BuyAdapter(this,R.layout.buy, values);
			setListAdapter(adapter);
			}
		} 
	 
	   
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
		 Item selectedItem = (Item)getListAdapter().getItem(position);
		 ProductToBuy = selectedItem.getSku();
		 
		 WiFiAddress = GetWifiAddress();
		 
		 Observer.setWifi(WiFiAddress);
		 Observer.setProductTobuy(ProductToBuy);
		 
		 Observer.PurchaseNow(ProductToBuy);
		 
		
		 
	 } 
	 
			
			@Override
			protected void onActivityResult(int requestCode, int resultCode,Intent data) {
				super.onActivityResult(requestCode, resultCode, data);
				 if(requestCode == 11){
					 // We are returning from signup so close this screen
					 finish();
				 }
				
				  
			} 
			

	 
}

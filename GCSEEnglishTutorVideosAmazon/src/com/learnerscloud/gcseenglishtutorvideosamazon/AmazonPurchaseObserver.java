package com.learnerscloud.gcseenglishtutorvideosamazon;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amazon.inapp.purchasing.BasePurchasingObserver;
import com.amazon.inapp.purchasing.Item;
import com.amazon.inapp.purchasing.ItemDataResponse;
import com.amazon.inapp.purchasing.ItemDataResponse.ItemDataRequestStatus;
import com.amazon.inapp.purchasing.PurchaseResponse;
import com.amazon.inapp.purchasing.PurchasingManager;

public class AmazonPurchaseObserver extends BasePurchasingObserver{

	
	 private static final String TAG = "Amazon-IAP";
	
	 private final Buy baseActivity;
	 private String Wifi;
	 private String ProductTobuy;
	
	
	public AmazonPurchaseObserver(final Buy buyactivity) {
        super(buyactivity);
        this.baseActivity =  buyactivity;
    }
	
	 @Override
	 // Load Items to for Price List
	    public void onSdkAvailable(final boolean isSandboxMode) {
	       // Log.v(TAG, "onSdkAvailable recieved: Response -" + isSandboxMode);
	       // PurchasingManager.initiateGetUserIdRequest();
	        
	       Set<String> s = new TreeSet<String>();
	        s.add("androidenglish1monthamazon");
	        s.add("androidenglish3monthsamazon");
	        s.add("androidenglish6monthsamazon");
	        s.add("androidenglish9monthsamazon");
	        s.add("androidenglish12monthsamazon");
	        
	        PurchasingManager.initiateItemDataRequest(s);
	    }
	 
	 // Now Buy 
	  public void PurchaseNow(String sku){
		  
		   PurchasingManager.initiatePurchaseRequest(sku);
		  
	  }
	  
	  public String getWifi() {
			return Wifi;
		}

		public void setWifi(String wifi) {
			Wifi = wifi;
		}
	 
		public String getProductTobuy() {
			return ProductTobuy;
		}

		public void setProductTobuy(String productTobuy) {
			ProductTobuy = productTobuy;
		}
	
	 @Override
	 // Response from load Price list
	    public void onItemDataResponse(final ItemDataResponse itemDataResponse) {
	       
		 if (itemDataResponse.getItemDataRequestStatus()  == ItemDataRequestStatus.SUCCESSFUL){
			 
			 final Map<String, Item> unsortedMap = itemDataResponse.getItemData();
			 Map<String, Item> items = new TreeMap<String, Item>(unsortedMap);
			 
             for (final String key : items.keySet()) {
                 Item i = items.get(key);
                 baseActivity.values.add(i);
                // Log.v(TAG, String.format("Item: %s\n Type: %s\n SKU: %s\n Price: %s\n Description: %s\n", i.getTitle(), i.getItemType(), i.getSku(), i.getPrice(), i.getDescription()));
               
             }
             baseActivity.loadAdapter();
		 }
	    }
	 
	 @Override
	 // Response from Buy
	    public void onPurchaseResponse(final PurchaseResponse purchaseResponse) {
	        Log.v(TAG, "onPurchaseResponse recieved");
	        Log.v(TAG, "PurchaseRequestStatus:" + purchaseResponse.getPurchaseRequestStatus());
	       
	        switch (purchaseResponse.getPurchaseRequestStatus()) {
            case SUCCESSFUL:
               
            	 Bundle basket = new Bundle();
         		 basket.putString("_ProductIdentifier", getProductTobuy());
         		 basket.putString("_Deviceid", getWifi());
           	     Intent signup = new Intent(baseActivity.getApplicationContext(),Signup.class);
           	     signup.putExtras(basket);
           	  
           	 baseActivity.startActivityForResult(signup, 11);
               
                break;
            case FAILED:
            	
            	baseActivity.complain("Error processing transaction. Please try again");
            	 break;
            case INVALID_SKU:
            	baseActivity.complain("Item you selected is not available. Please try another item");
            	 break;
            }
           
        }
	        
}

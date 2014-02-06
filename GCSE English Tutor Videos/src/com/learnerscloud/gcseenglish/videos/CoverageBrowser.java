package com.learnerscloud.gcseenglish.videos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


@SuppressLint("SetJavaScriptEnabled")
public class CoverageBrowser extends Activity{
	
	private WebView webView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		webView = new WebView(this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginsEnabled(true);
		
		
		 Bundle getBasket = getIntent().getExtras();
		String url = getBasket.getString("_FullURL");
		
		webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
		
		
	}

}

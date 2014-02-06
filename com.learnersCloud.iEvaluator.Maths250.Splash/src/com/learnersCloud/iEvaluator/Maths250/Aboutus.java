package com.learnersCloud.iEvaluator.Maths250;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.TextView;

public class Aboutus extends Activity{
	
	TextView tv;
	TextView link;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.aboutus);
	        
	        tv= (TextView)findViewById(R.id.txtAboutus);
	        link= (TextView)findViewById(R.id.txtAboutusSitelink);
	        
	        String myHtml = "<p>LearnersCloud GCSE Online platform offers hundreds of HD videos featuring expert tutors and " +
	        		"thousands of test questions to help you achieve your learning goals.</p>" + " <p>We are convinced that LearnersCloud is <b>the best e-learning resource</b> " +
	        	"for GCSE students. Here are just a few reasons why:</p>" + "<p>Unique features help you organise the material and tailor it to suit your individual needs."
	        		+ "<br/>Quality assured content that covers main UK examining boards is constantly reviewed and updated."
	        		+ "<br/>Flexible and affordable pricing with the latest technological innovation guarantee value for money."
	        		+ "<br/>Revise and test your knowledge when it suits you.";
	        		//+ "<p>Visit:&nbsp;";
	        
	        tv.setText(Html.fromHtml(myHtml));
	        
	        //link.setMovementMethod(LinkMovementMethod.getInstance());
	        
	        String Website = "Visit: www.learnersCloud.com";
	        
	        link.setText(Website);
	        Linkify.addLinks( link, Linkify.WEB_URLS ); 
	        //Pattern pattern = Pattern.compile("www.LearnersCloud.com");
	        //Linkify.addLinks(link, pattern, "http://");
	        
	        
	    }

}

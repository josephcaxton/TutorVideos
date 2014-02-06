package com.learners.gcsemathstutorvideosamazon;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Share extends ListActivity{

	 @Override
	    public void onCreate(Bundle icicle) {
	        super.onCreate(icicle);
	        
	        this.loadAdapter();

	       
	        
	    }
   
	 private void  loadAdapter(){
	    	
	    	ArrayList<ShareItem> values = new ArrayList<ShareItem>();
	    	// Email Friend
	    	ShareItem sf = new ShareItem();
	    	sf.setShareimage(R.drawable.icon_email_to_friend);
	    	sf.setDescription("Email to a friend");
	    	values.add(sf);
	    	// Share on facebook
	    	ShareItem sface = new ShareItem();
	    	sface.setShareimage(R.drawable.icon_share_on_facebook);
	    	sface.setDescription("Share on Facebook");
	    	values.add(sface);
	    	// Share on twitter
	    	ShareItem st = new ShareItem();
	    	st.setShareimage(R.drawable.icon_share_on_twitter);
	    	st.setDescription("Share on Twitter");
	    	values.add(st);
	    	//Get notification
	    	//ShareItem not = new ShareItem();
	    	//not.setDescription("GET NOTIFICATIONS");
	    	//values.add(not);
	    	//Follow us on Twitter
	    	ShareItem stwitter = new ShareItem();
	    	stwitter.setShareimage(R.drawable.icon_follow_on_twitter);
	    	stwitter.setDescription("Follow us on Twitter");
	    	values.add(stwitter);
	    	// Like us on Facebook
	    	ShareItem sFacebooklike = new ShareItem();
	    	sFacebooklike.setShareimage(R.drawable.icon_like_on_facebook);
	    	sFacebooklike.setDescription("Like us on Facebook");
	    	values.add(sFacebooklike);
	    	
	    	
	    	
	    	ShareAdapter adapter = new ShareAdapter(this,R.layout.share, values);
	    	
	    	setListAdapter(adapter);
	    	
	    
	     
	   }
	 @Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
		 
		 Intent emailIntent = new Intent(Intent.ACTION_SEND);
		 switch(position){
			
		case 0:
			
			
			emailIntent.setType("text/html");
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Learn and revise Maths on the go - Maths app");
			emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder()
		    .append("<p>Checkout the FREE LearnersCloud Video App loaded with quality revision videos.</p>")
		    .append("To download this App <p><a href = https://play.google.com/store/apps/developer?id=LearnersCloud.com#?t=W251bGwsbnVsbCxudWxsLDEsImNvbS5sZWFybmVyc0Nsb3VkLmlFdmFsdWF0b3JGb3JBbmRyb2lkLkNoZW1pc3RyeSJd>  for Android click here or search for \"LearnersCloud\" in your app store</a></p>")
		    .append("<p> <a href= \"http://itunes.apple.com/us/app/maths-videos/id522347113?ls=1&mt=8\">  for iPad click here or search for \"LearnersCloud\" in your app store</a></p>")
		    .append("<p> <a href= \"http://itunes.apple.com/us/app/maths-videos./id531691732?ls=1&mt=8\">  for iPhone and iPod touch click here or search for \"LearnersCloud\" in your app store</a></p>")
		    .toString()));
		 
		 try{
			 startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	 
		 }
		 catch (Exception e){
			 
			 Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();

			 }
		 
		 break;
			
			case 1:
				
				
				emailIntent.setType("text/plain");
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Learn and revise Maths on the go - Maths app");
				emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder()
			    .append("<p>Checkout the FREE LearnersCloud Video App loaded with quality revision videos.</p>")
			    .append("To download this App <p><a href = https://play.google.com/store/apps/developer?id=LearnersCloud.com#?t=W251bGwsbnVsbCxudWxsLDEsImNvbS5sZWFybmVyc0Nsb3VkLmlFdmFsdWF0b3JGb3JBbmRyb2lkLkNoZW1pc3RyeSJd>  for Android click here or search for \"LearnersCloud\" in your app store</a></p>")
			    .append("<p> <a href= \"http://itunes.apple.com/us/app/maths-videos/id522347113?ls=1&mt=8\">  for iPad click here or search for \"LearnersCloud\" in your app store</a></p>")
			    .append("<p> <a href= \"http://itunes.apple.com/us/app/maths-videos./id531691732?ls=1&mt=8\">  for iPhone and iPod touch click here or search for \"LearnersCloud\" in your app store</a></p>")
			    .toString()));
			 
			 try{
				 startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		 
			 }
			 catch (Exception e){
				 
				 Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();

				 }
				
			
			break;
			
			case 2:
				
				
				break;
				
			case 3:
				
				
				break;
				
			case 4:
				
				
				break;
		 }
	 }

}

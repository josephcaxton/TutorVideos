package com.learnerscloud.gcsephysics.videos;

import java.io.IOException;
import java.util.ArrayList;



import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VideoListAdapter extends ArrayAdapter<VideoItem>{
	private final Context context;
	private ArrayList<VideoItem> values;
	int resource;
	
	
	public VideoListAdapter(Context context, int resource, ArrayList<VideoItem> values) {
		super(context, resource, values);
		this.context = context;
		this.values = values;
		this.resource=resource;
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		 if (view == null) {
			 LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 view = inflater.inflate(resource, parent, false);
		 }
		 VideoItem vi = values.get(position);
		 if (vi!= null) {
			 
		ImageView imageView = (ImageView) view.findViewById(R.id.listlogo);
		TextView txtLabel= (TextView) view.findViewById(R.id.listlabel);
		
		ImageView imageButton = (ImageView) view.findViewById(R.id.listButton);
		
		

		//Log.d("Joseph", Integer.toString(vi.getSubscribeimage()));
		imageButton.setImageResource(vi.getSubscribeimage());
		 
		String message = vi.getTitle() + " - " + vi.getDescription();
		  
		txtLabel.setText(message);
		
		// icon based on bundle icons
		String ImageNumber = Integer.toString(position + 1);
		String FullImageName = ImageNumber + ".png";
		try {
			Drawable d = Drawable.createFromStream(context.getAssets().open(FullImageName), null);
			imageView.setImageDrawable(d); 
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		 }	 
		 Button search = (Button)view.findViewById(R.id.btnSearch);
		 if(vi.getSearchimage() == R.drawable.search){
				
				search.setVisibility(View.VISIBLE);
				search.setOnClickListener(new OnClickListener(){
					
					@Override
					  public void onClick(View v) 
					  {
						LocalCache cache = ((LocalCache)context.getApplicationContext());
						
						showDialog(cache.getDialogBoxClickCount());
					  }
						
					
				});
				}
		 else
		 {
			 search.setVisibility(View.INVISIBLE);
		 }
 
		return view;
	}
	protected void showDialog(int i) {
		LocalCache cache = ((LocalCache)context.getApplicationContext());
		
		// Save a copy of the VideoItems the time we open dialog. And Reset to full
		if(i == 1){
			ArrayList<VideoItem> values = cache.getVideoItems();
			cache.setAllVideoItems(new ArrayList<VideoItem>(values));
		}
		else
		{
			// Refresh the Adapter with all videos
			ArrayList<VideoItem> values = cache.getVideoItems();
			values.clear();
			ArrayList<VideoItem> allvalues = cache.getAllVideoItems();
			
			VideoListAdapter lstadpater = cache.getCacheListAdapter();
			
			for (int v = 0; v < allvalues.size(); v++ ){
				VideoItem vi = allvalues.get(v);
				values.add(vi);
			}
			lstadpater.notifyDataSetChanged();
			
		}
		// We have now open dialog for set the counter
		cache.setDialogBoxClickCount(i += 1);
		
		Context mContext = cache.getCtx();
		 final Dialog dialog = new Dialog(mContext);
		 dialog.setContentView(R.layout.searchdialog);
		 dialog.setTitle("Search");
		 final EditText textbox = (EditText)dialog.findViewById(R.id.txtsearch);
		 Button btnCancel = (Button)dialog.findViewById(R.id.txtSearchCancel);
		 
		 btnCancel.setOnClickListener(new OnClickListener()
    		{
    	public void onClick(View v){
    		if (textbox.getText().length() == 0){
    			
    		}
    		dialog.dismiss();
    	}
    	
    });
		 
		 textbox.addTextChangedListener(new TextWatcher(){
			 
			 @Override
			    public void afterTextChanged(Editable s) {
			        // TODO Auto-generated method stub
			    }

			    @Override
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			        // TODO Auto-generated method stub
			    }

			    @Override
			    public void onTextChanged(CharSequence s, int start, int before, int count) {
			        
			    	LocalCache cache = ((LocalCache)context.getApplicationContext());
			    	VideoListAdapter lstadpater = cache.getCacheListAdapter();
			    	
			    	ArrayList<VideoItem> values = cache.getVideoItems();
			    	
			    	
			    	ArrayList<VideoItem> lst = new ArrayList<VideoItem>(values);
			    	values.clear();
			    	 // add the first column by default once
		    		values.add(lst.get(0));
		    		
			    	for (int i = 1; i < lst.size(); i++ ){
	
			    	VideoItem vi = lst.get(i);
			    	String Title = vi.getTitle();
			    	String Description = vi.getDescription();
			    	
			    	// Do the Search here
			    	if (Title.toLowerCase().contains(s.toString().toLowerCase()) || Description.toLowerCase().contains(s.toString().toLowerCase())){
			    		 
			    		values.add(vi);
			    		
			    		
			    		}
			    	
			    	}
			    	 lstadpater.notifyDataSetChanged();
		 }
			   
			    });
		 dialog.show(); 
	}
}


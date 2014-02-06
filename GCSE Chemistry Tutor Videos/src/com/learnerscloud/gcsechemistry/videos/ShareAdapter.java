package com.learnerscloud.gcsechemistry.videos;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareAdapter extends ArrayAdapter<ShareItem>{
	private final Context context;
	private ArrayList<ShareItem> values;
	int resource;
	
	public ShareAdapter(Context context, int resource, ArrayList<ShareItem> values) {
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
		 ShareItem si = values.get(position);
		 if (si!= null) {
			 
		ImageView imageView = (ImageView) view.findViewById(R.id.shareimage);
		TextView txtDetails = (TextView) view.findViewById(R.id.sharedescription);

		
		txtDetails.setText(si.getDescription());
		// icon based on bundle icons
		imageView.setImageResource(si.getShareimage()); 
		
		 }
		 return view;
	}
}

package com.learnerscloud.gcsechemistry.videos;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CoverageAdapter extends ArrayAdapter<String>{
	private final Context context;
	private ArrayList<String> values;
	int resource;
	
	public CoverageAdapter(Context context, int resource, ArrayList<String> values) {
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
		 
		 String val = values.get(position);
		 TextView CoverageText = (TextView) view.findViewById(R.id.coverageitem);
		 CoverageText.setText(val);
		 return view;
	}
	
	

}

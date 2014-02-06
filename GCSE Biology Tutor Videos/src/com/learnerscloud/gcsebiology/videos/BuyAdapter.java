package com.learnerscloud.gcsebiology.videos;

import java.util.ArrayList;
import com.learnerscloud.gcsebiology.videos.util.SkuDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class BuyAdapter extends ArrayAdapter<SkuDetails>{

	private final Context context;
	private ArrayList<SkuDetails> values;
	int resource;
	
	public BuyAdapter(Context context, int resource, ArrayList<SkuDetails> values) {
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
		
		 SkuDetails si = values.get(position);
		 if (si!= null) {
			 
		
		TextView txtDetails = (TextView) view.findViewById(R.id.buydescription);
		ImageView imageButton = (ImageView) view.findViewById(R.id.buyButton);

		String DesAndPrice = si.getDescription() + " " + si.getPrice();
		txtDetails.setText(DesAndPrice);
		
		imageButton.setImageResource(R.drawable.buy_now);	
		
		 }	 
		
 
		return view;
	}
}

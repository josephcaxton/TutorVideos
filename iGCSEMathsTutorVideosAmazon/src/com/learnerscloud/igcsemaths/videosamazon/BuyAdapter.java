package com.learnerscloud.igcsemaths.videosamazon;

import java.util.ArrayList;
import com.amazon.inapp.purchasing.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class BuyAdapter extends ArrayAdapter<Item>{

	private final Context context;
	private ArrayList<Item> values;
	int resource;
	
	public BuyAdapter(Context context, int resource, ArrayList<Item> values) {
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
		
		 Item si = values.get(position);
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

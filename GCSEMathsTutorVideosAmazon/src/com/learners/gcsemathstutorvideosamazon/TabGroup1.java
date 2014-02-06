package com.learners.gcsemathstutorvideosamazon;

import android.content.Intent;
import android.os.Bundle;

public class TabGroup1 extends TabGroupActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startChildActivity("Main",new Intent(this,Main.class));
		
	}
	

}

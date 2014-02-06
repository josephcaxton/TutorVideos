package com.learnerscloud.igcsemaths.videos;

import android.content.Intent;
import android.os.Bundle;

public class TabGroup2 extends TabGroup2Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startChildActivity("Coverage",new Intent(this,Coverage.class));
		
	}

}

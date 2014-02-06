package com.learnerscloud.gcseenglishtutorvideosamazon;

import android.content.Intent;
import android.os.Bundle;

public class TabGroup4 extends TabGroup4Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startChildActivity("Help",new Intent(this,Help.class));
	}

}
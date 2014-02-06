package com.learnerscloud.igcsemaths.videosamazon;

import android.content.Intent;
import android.os.Bundle;

public class TabGroup3 extends TabGroup3Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startChildActivity("Share",new Intent(this,Share.class));
	}
}

package com.example.wzbc.znote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class SingleFragmentActivity extends AppCompatActivity {
	
	
	@Override
	protected void onCreate(Bundle bundle) {
		
		super.onCreate(bundle);
		setContentView(R.layout.activity_for_fragment);
		FragmentManager fm=getSupportFragmentManager();
		Fragment fragment=fm.findFragmentById(R.id.fragmentContainer);
		if (fragment==null) {
			fragment=getFragment();
			fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
		}
	}

	protected abstract Fragment getFragment();
}

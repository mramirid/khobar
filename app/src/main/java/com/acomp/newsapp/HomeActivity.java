package com.acomp.newsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = findViewById(R.id.toolbar);
		toolbar.setTitle(getString(R.string.app_name));
	}
}

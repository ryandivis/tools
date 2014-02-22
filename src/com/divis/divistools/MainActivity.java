package com.divis.divistools;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	
	public final static String EXTRA_USERNAME = "com.divis.divistools.USERNAME";
	public final static String EXTRA_PASSWORD = "com.divis.divistools.PASSWORD";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button loginButton = (Button) findViewById(R.id.button_login);
		loginButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
			case R.id.button_login:
				//show loading indicator small gif
				
				//async login procedures
				
				Intent intent = new Intent(this, DisplayMessageActivity.class);
				
				EditText editText = (EditText) findViewById(R.id.edit_username);
				String username = editText.getText().toString();
				
				editText = (EditText) findViewById(R.id.edit_password);
				String password = editText.getText().toString();
				
				intent.putExtra(EXTRA_USERNAME, username);
				intent.putExtra(EXTRA_PASSWORD, password);
				
				startActivity(intent);
				
				break;
		
		}
		
	}

}

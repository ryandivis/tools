package com.divis.divistools;

import java.util.Calendar;

import com.divis.divistools.fragments.CalendarDayFragment;
import com.divis.divistools.fragments.CalendarFragment;
import com.divis.divistools.fragments.DisplayMessageFragment;
import com.divis.divistools.fragments.SearchFragment;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class DisplayMessageActivity extends FragmentActivity implements CalendarFragment.OnNavigationListener{
	
	private int currMonth;
	private int currYear;
	private int currDay;
	private Calendar mCalendar = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mCalendar = Calendar.getInstance();
		currMonth = mCalendar.get(Calendar.MONTH);
		currYear = mCalendar.get(Calendar.YEAR);
		currDay = mCalendar.get(Calendar.DAY_OF_MONTH);
		
		//retrieve the username and password
		Intent intent = getIntent();
		String username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
		String password = intent.getStringExtra(MainActivity.EXTRA_PASSWORD);
		
		setContentView(R.layout.activity_display_message);
		
		if(findViewById(R.id.fragment_container) != null) {
			if(savedInstanceState != null) {
				return;
			}
			
			DisplayMessageFragment fragment = new DisplayMessageFragment();
			fragment.setArguments(getIntent().getExtras());
			
			getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();			
		}
		
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			openSearch();
			return true;
		case R.id.action_calendar:
			openCalendar(currMonth,currYear);
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();
		if(fm.getBackStackEntryCount() > 0)
		{
			fm.popBackStack();
		} else {
			super.onBackPressed();
		}
	}
	
	public void openSearch() {
		SearchFragment searchFragment = new SearchFragment();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		transaction.replace(R.id.fragment_container, searchFragment);
		
		transaction.addToBackStack(null);
		
		transaction.commit();
	}
	
	public void openCalendar(int month, int year) {
		CalendarFragment calFragment = new CalendarFragment();
		
		Bundle bundle = new Bundle();
		
		bundle.putInt("currMonth",month);
		bundle.putInt("currYear",year);
		
		calFragment.setArguments(bundle);
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		transaction.replace(R.id.fragment_container, calFragment);
		
		transaction.addToBackStack(null);
		
		transaction.commit();
	}
	
	public void openCalendarDay(int month, int year, int day) {
		CalendarDayFragment calFragment = new CalendarDayFragment();
		
		Bundle bundle = new Bundle();
		bundle.putInt("currMonth", month);
		bundle.putInt("currYear", year);
		bundle.putInt("currDay",day);
		
		calFragment.setArguments(bundle);
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		transaction.replace(R.id.fragment_container, calFragment);
		
		transaction.addToBackStack(null);
		
		transaction.commit();
	}

	@Override
	public void onMonthNavigation(int month, int year) {
		openCalendar(month, year);
	}

	@Override
	public void onDayNavigation(int month, int year, int day) {
		openCalendarDay(month, year, day);
		
	}
	
}

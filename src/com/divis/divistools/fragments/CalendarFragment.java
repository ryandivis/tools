package com.divis.divistools.fragments;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.divis.divistools.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CalendarFragment extends Fragment implements OnClickListener {
	
	private Calendar mCalendar = null;
	private MonthDisplayHelper mHelper = null;
	private int currMonth;
	private int currYear;
	private int firstDayOfMonth;
	private int daysOfMonth;
	private int monthDay = 1;
	private String headerText;
	List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
	OnNavigationListener mCallback;
	
	public interface OnNavigationListener {
		public void onMonthNavigation(int month, int year);
		public void onDayNavigation(int month, int year, int day);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//init the calendar with the current date
		mCalendar = Calendar.getInstance();
		currMonth = getArguments().getInt("currMonth",mCalendar.get(Calendar.MONTH));
		currYear = getArguments().getInt("currYear", mCalendar.get(Calendar.YEAR));
		
		mCalendar.set(currYear, currMonth, 1);
		
		return inflater.inflate(R.layout.calendar_view, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//setup next and prev clicks
		TextView next = (TextView) getActivity().findViewById(R.id.navigate_month_next);
		next.setOnClickListener(this);
		
		TextView prev = (TextView) getActivity().findViewById(R.id.navigate_month_prev);
		prev.setOnClickListener(this);
		
		init();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			mCallback = (OnNavigationListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + "must implement OnNavigationListener");
		}
	}
	
	public void init() {
		
		mHelper = new MonthDisplayHelper(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH));
		
		firstDayOfMonth = mHelper.getFirstDayOfMonth();
		daysOfMonth = mHelper.getNumberOfDaysInMonth();
		
		//for each week in month, create a table layout and add cells to layout
		TableLayout parentLayout = (TableLayout) getActivity().findViewById(R.id.calendar_layout);
		
		//update the calendar heading to show month and year
		TextView calHeader = (TextView) getActivity().findViewById(R.id.month_label);
		headerText = months.get(mHelper.getMonth()) + " " + mHelper.getYear();
		calHeader.setText(headerText);
		
		LayoutParams weekParams = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
		
		for(int week = 0; week <= mHelper.getRowOf(daysOfMonth); week++)
		{
			TableRow weekLayout = (TableRow) getActivity().getLayoutInflater().inflate(R.layout.calendar_row, null);
			
			for(int day = 1; day <= 7; day++)
			{
				TextView dayView = new TextView(getActivity());
				dayView.setLayoutParams(new TableRow.LayoutParams(0,LayoutParams.FILL_PARENT,1f));
				dayView.setGravity(Gravity.CENTER);
				if((week == 0 && day < firstDayOfMonth) || monthDay > daysOfMonth) {
					dayView.setBackgroundResource(R.drawable.other_month);
				} else {
					dayView.setText(Integer.toString(monthDay));
					dayView.setBackgroundResource(R.drawable.current_month);
					monthDay++;
				}
				
				dayView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int day = Integer.parseInt((String) ((TextView) v).getText());
						mCallback.onDayNavigation(currMonth, currYear, day);
					}
				});
				
				weekLayout.addView(dayView);
			}
			
			parentLayout.addView(weekLayout,weekParams);
			
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.navigate_month_next:
			currMonth++;
			if(currMonth > 11)
			{
				currMonth = 0;
				currYear++;
			}
			mCallback.onMonthNavigation(currMonth, currYear);
			return;
		case R.id.navigate_month_prev:
			currMonth--;
			if(currMonth < 0)
			{
				currMonth = 11;
				currYear--;
			}
			mCallback.onMonthNavigation(currMonth, currYear);
			return;
		}
	}
	
}

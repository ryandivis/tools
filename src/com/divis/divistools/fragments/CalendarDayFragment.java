package com.divis.divistools.fragments;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.divis.divistools.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CalendarDayFragment extends Fragment {
	
	private Calendar mCalendar;
	private int currDay;
	private int currMonth;
	private int currYear;
	List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//init the calendar with the current date
		mCalendar = Calendar.getInstance();
		currMonth = getArguments().getInt("currMonth",mCalendar.get(Calendar.MONTH));
		currYear = getArguments().getInt("currYear", mCalendar.get(Calendar.YEAR));
		currDay = getArguments().getInt("currDay",mCalendar.get(Calendar.DAY_OF_MONTH));
		
		mCalendar.set(currYear, currMonth, currDay);
		
		return inflater.inflate(R.layout.calendar_day, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//set header
		TextView headerText = (TextView) getActivity().findViewById(R.id.calendar_day_heading_text);
		
		String headerString = months.get(currMonth)+" "+currDay+" "+currYear;
		
		headerText.setText(headerString);
		
		
	}
	
}

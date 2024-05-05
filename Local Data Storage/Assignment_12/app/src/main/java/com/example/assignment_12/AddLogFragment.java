package com.example.assignment_12;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.assignment_12.databinding.FragmentAddLogBinding;

import java.util.Calendar;
import java.util.HashMap;


public class AddLogFragment extends Fragment {

    public Entry entry;
    public double selectedSleepHours = 0.0;
    public double selectedExerciseHours = 0.0;
    public double weight_double;
    public static int selectedYear = 0, selectedMonth = 0, selectedDay = 0;
    public static int hour = 0, minutes = 0;
    public static String amPms;
    public int selectedSleepQuality = 0;
    public double sleepHours, exerciseHours;
    public int sleepQuality, year, month, day;
    public String sleepHoursStr, exerciseHoursStr, sleepQualityStr, dateStr, timeStr;
    public AddLogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_cancel){
            mListener.onSelectionCanceled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onDateSelected(int year, int month, int day) {

        binding.textViewDate.setText(month + "/" + day + "/" + year);
    }
    public void onTimeSelected(int hour, int minute, String amPm) {

        //binding.textViewTime.setText(hour + ":" + minute);
        binding.textViewTime.setText(String.format("%02d:%02d %s", hour, minute, amPm));

    }

    FragmentAddLogBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddLogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Log");

        if(selectedSleepHours != 0.0){
            binding.textViewSleepHours.setText(selectedSleepHours+"");
        }
        if (selectedSleepQuality != 0){
            binding.textViewSleepQuality.setText(selectedSleepQuality+"");
        }
        if(selectedExerciseHours != 0.0){
            binding.textViewExerciseHours.setText(selectedExerciseHours+"");
        }


        binding.buttonSelectSleepHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectSleepHours();
            }
        });

        binding.buttonSelectSleepQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectSleepQuality();
            }
        });

        binding.buttonSelectExerciseHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectExerciseHours();
            }
        });
        binding.pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mListener.gotoSelectDate();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");


            }
        });
        binding.pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(requireActivity().getSupportFragmentManager(), "timePicker"); // if error try getContext or getActivity or requireContext

            }
        });


        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (selectedYear == 0 || selectedMonth == 0 || selectedDay == 0){
//                    Toast.makeText(getActivity(), "Select Date !!", Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    onDateSelected(selectedYear, selectedMonth, selectedDay);
//                }
//
//                if (hour == 0 || minutes == 0 || amPms.isEmpty()){
//                    Toast.makeText(getActivity(), "Select Time !!", Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    onTimeSelected(hour, minutes, amPms);
//                }
                // checks if year, month, and day is null if it is it gives a toast message, else it calls the method


                String weight = binding.editTextWeight.getText().toString();
                sleepHoursStr = binding.textViewSleepHours.getText().toString();
                sleepQualityStr = binding.textViewSleepQuality.getText().toString();
                exerciseHoursStr = binding.textViewExerciseHours.getText().toString();
                dateStr = binding.textViewDate.getText().toString();
                timeStr = binding.textViewTime.getText().toString();

                if (weight.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Current Weight !!", Toast.LENGTH_SHORT).show();
                } else if(sleepHoursStr.isEmpty() || sleepHoursStr.equals("N/A")){
                    Toast.makeText(getActivity(), "Select Sleep Hours !!", Toast.LENGTH_SHORT).show();
                } else if (sleepQualityStr.isEmpty() || sleepQualityStr.equals("N/A")){
                    Toast.makeText(getActivity(), "Select Sleep Quality !!", Toast.LENGTH_SHORT).show();
                } else if (exerciseHoursStr.isEmpty() || exerciseHoursStr.equals("N/A")){
                    Toast.makeText(getActivity(), "Select Exercise Hours !!", Toast.LENGTH_SHORT).show();
                } else if (dateStr.isEmpty() || dateStr.equals("N/A")){
                    Toast.makeText(getActivity(), "Select Date !!", Toast.LENGTH_SHORT).show();
                } else if (timeStr.isEmpty() || timeStr.equals("N/A")){
                    Toast.makeText(getActivity(), "Select Time !!", Toast.LENGTH_SHORT).show();
                }
                else {
                    weight_double = Double.parseDouble(weight);
                    sleepHours = Double.parseDouble(sleepHoursStr);
                    sleepQuality = Integer.parseInt(sleepQualityStr);
                    exerciseHours = Double.parseDouble(exerciseHoursStr);
                    String[] date = dateStr.split("/");
                    year = Integer.parseInt(date[2]);
                    month = Integer.parseInt(date[0]);
                    day = Integer.parseInt(date[1]);
//                    String[] time = timeStr.split(":");
//                    hour = Integer.parseInt(time[0]);
//                    minutes = Integer.parseInt(time[1]);

                    AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "entries-db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();


                    // check ordering of parameters
                    entry = new Entry(weight_double, sleepHours, exerciseHours, sleepQuality, year, month, day, hour, minutes, amPms);
                    db.entryDao().insertAll(entry);
                    mListener.doneAddLog();
                }


            }
        });

    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker.
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it.
            return new DatePickerDialog(requireContext(), this, year, month, day); // if error try getContext
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {

            // Do something with the date the user picks.
            // to fix issue you could set the variables equal to it then set the textview using the variables else where or put it in a method
//            binding.textViewDate.setText((month+1)+"/"+day+"/"+year); // could redo it as timepicker
            selectedYear = year;
            selectedMonth = month+1;
            selectedDay = day;

            // you make these variables non static and then call the method from the fragment like below using parameters

            //onDateSelected(year, month, day);
            ((AddLogFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.rootView)).onDateSelected(selectedYear, selectedMonth, selectedDay);

        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker.
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it.
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    false); //set last one to false
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time the user picks.
            //binding.textViewTime.setText(hourOfDay+":"+minute);
            String amPm;
            if (hourOfDay < 12) {
                amPm = "AM";
            } else {
                amPm = "PM";
                if (hourOfDay != 12) {
                    hourOfDay -= 12; // convert 24-hour time to 12-hour time
                }
            }
            if (hourOfDay == 0) {
                hourOfDay = 12;
            }
            //binding.textViewTime.setText(String.format("%02d:%02d %s", hourOfDay, minute, amPm));
            hour = hourOfDay;
            minutes = minute;
            amPms = amPm;
            ((AddLogFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.rootView)).onTimeSelected(hour, minutes, amPms);


        }
    }

    AddLogListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (AddLogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement AddLogListener");
        }
        getActivity().invalidateOptionsMenu();
    }
    public interface AddLogListener {
        void doneAddLog();
        void onSelectionCanceled();
        void gotoSelectSleepHours();
        void gotoSelectSleepQuality();
        void gotoSelectExerciseHours();

    }

}
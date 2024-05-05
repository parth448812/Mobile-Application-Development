// Assignment 12
// MainActivity.java
// Parth Patel and Janvi Nandwani

package com.example.assignment_12;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements LoggedEntriesFragment.LoggedEntriesListener,
        AddLogFragment.AddLogListener, SelectSleepHoursFragment.SelectSleepHoursListener,
    SelectSleepQualityFragment.SelectSleepQualityListener, SelectExerciseHoursFragment.SelectExerciseHoursListener, VisualizeProgressFragment.VisualizeProgressListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new LoggedEntriesFragment())
                .commit();
    }

    @Override
    public void gotoAddLog() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddLogFragment(), "add-log-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoVisualize() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new VisualizeProgressFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doneAddLog() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSelectionCanceled() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectSleepHours() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectSleepHoursFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectSleepQuality() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectSleepQualityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectExerciseHours() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectExerciseHoursFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSleepHoursSelected(double sleepHours) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.selectedSleepHours = sleepHours;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSleepQualitySelected(int sleepQuality) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.selectedSleepQuality = sleepQuality;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onExerciseHoursSelected(double exerciseHours) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        if(fragment != null){
            fragment.selectedExerciseHours = exerciseHours;
        }
        getSupportFragmentManager().popBackStack();
    }

}
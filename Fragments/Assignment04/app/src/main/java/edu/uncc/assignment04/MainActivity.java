// Assignment 4
// Assignment04
// Parth Patel and Janvi Manchanda

package edu.uncc.assignment04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.uncc.assignment04.fragments.DemographicFragment;
import edu.uncc.assignment04.fragments.IdentificationFragment;
import edu.uncc.assignment04.fragments.MainFragment;
import edu.uncc.assignment04.fragments.ProfileFragment;
import edu.uncc.assignment04.fragments.SelectEducationFragment;
import edu.uncc.assignment04.fragments.SelectIncomeFragment;
import edu.uncc.assignment04.fragments.SelectLivingStatusFragment;
import edu.uncc.assignment04.fragments.SelectMaritalStatusFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener,
        IdentificationFragment.IdentificationFragmentListener, DemographicFragment.DemographicFragmentListener,
        SelectEducationFragment.SelectEducationFragmentListener, SelectMaritalStatusFragment.SelectMaritalStatusFragmentListener,
        SelectLivingStatusFragment.SelectLivingStatusFragmentListener, SelectIncomeFragment.SelectIncomeStatusFragmentListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new MainFragment())
                .commit();
    }

    @Override
    public void gotoIdentification() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new IdentificationFragment())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void gotoDemographic(Response response) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DemographicFragment.newInstance(response), "demographic-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoEducation() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectEducationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoMarital() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectMaritalStatusFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoLiving() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectLivingStatusFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoIncome() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectIncomeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoProfile(Response response) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(response))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendEducation(String education) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic-fragment");
        if(fragment != null){
            fragment.setSelectedEducation(education);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelEducation() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendIncome(String income) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic-fragment");
        if(fragment != null){
            fragment.setSelectedIncome(income);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelIncome() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendLiving(String living) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic-fragment");
        if(fragment != null){
            fragment.setSelectedLiving(living);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelLiving() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendMarital(String marital) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic-fragment");
        if(fragment != null){
            fragment.setSelectedMarital(marital);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelMarital() {
        getSupportFragmentManager().popBackStack();
    }
}
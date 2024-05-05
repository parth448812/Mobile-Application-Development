package com.example.assignment_12;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.assignment_12.databinding.FragmentAddLogBinding;
import com.example.assignment_12.databinding.FragmentSelectSleepHoursBinding;

import java.util.ArrayList;


public class SelectSleepHoursFragment extends Fragment {


    ArrayList<String> sleepHours = new ArrayList<>();
    ArrayAdapter<String> adapter;


    public SelectSleepHoursFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        sleepHours.clear();
        for (double i = 0.5; i <= 15; i += 0.5) {
            //sleepHours.add(String.valueOf(i));
            sleepHours.add(i+"");
        }
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

    FragmentSelectSleepHoursBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectSleepHoursBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Sleep Hours");

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, sleepHours);
        binding.listViewSleepHours.setAdapter(adapter);
        binding.listViewSleepHours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sleepHoursStr = sleepHours.get(position);
                double sleepHour = Double.parseDouble(sleepHoursStr);
                mListener.onSleepHoursSelected(sleepHour);
            }
        });
    }

    SelectSleepHoursListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (SelectSleepHoursListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement SelectSleepHoursListener");
        }
        getActivity().invalidateOptionsMenu();
    }
    public interface SelectSleepHoursListener {
        void onSleepHoursSelected(double sleepHours);
        void onSelectionCanceled();

    }
}
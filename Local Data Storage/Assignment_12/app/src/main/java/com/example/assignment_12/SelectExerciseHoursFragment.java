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

import com.example.assignment_12.databinding.FragmentSelectExerciseHoursBinding;

import java.util.ArrayList;


public class SelectExerciseHoursFragment extends Fragment {

    ArrayList<String> exerciseHours = new ArrayList<>();
    ArrayAdapter<String> adapter;



    public SelectExerciseHoursFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        exerciseHours.clear();
        for (double i = 0.5; i <= 15; i += 0.5) {
            //exerciseHours.add(String.valueOf(i));
            exerciseHours.add(i+"");
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

    FragmentSelectExerciseHoursBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectExerciseHoursBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Exercise Hours");

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, exerciseHours);
        binding.listViewExerciseHours.setAdapter(adapter);
        binding.listViewExerciseHours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String exerciseHoursStr = exerciseHours.get(position);
                double exerciseHour = Double.parseDouble(exerciseHoursStr);
                mListener.onExerciseHoursSelected(exerciseHour);
            }
        });
    }

    SelectExerciseHoursListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (SelectExerciseHoursListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement SelectExerciseHoursListener");
        }
        getActivity().invalidateOptionsMenu();
    }
    public interface SelectExerciseHoursListener {
        void onExerciseHoursSelected(double exerciseHours);
        void onSelectionCanceled();

    }
}
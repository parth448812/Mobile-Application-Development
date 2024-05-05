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

import com.example.assignment_12.databinding.FragmentSelectSleepQualityBinding;

import java.util.ArrayList;


public class SelectSleepQualityFragment extends Fragment {

    ArrayList<String> sleepQualities = new ArrayList<>();
    ArrayAdapter<String> adapter;


    public SelectSleepQualityFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sleepQualities.clear();
        sleepQualities.add("Poor (1)");
        sleepQualities.add("Fair (2)");
        sleepQualities.add("Good (3)");
        sleepQualities.add("Very Good (4)");
        sleepQualities.add("Excellent (5)");
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

    FragmentSelectSleepQualityBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectSleepQualityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Sleep Quality");

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, sleepQualities);
        binding.listViewSleepQuality.setAdapter(adapter);
        binding.listViewSleepQuality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String sleepQuality = sleepQualities.get(position);
                int sleepQualityInt = position + 1;
                mListener.onSleepQualitySelected(sleepQualityInt);
            }
        });
    }

    SelectSleepQualityListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (SelectSleepQualityListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement SelectSleepQualityListener");
        }
        getActivity().invalidateOptionsMenu();
    }
    public interface SelectSleepQualityListener {
        void onSleepQualitySelected(int sleepQuality);
        void onSelectionCanceled();

    }

}
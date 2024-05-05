package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentSelectAgeBinding;
import edu.uncc.assignment05.databinding.FragmentSelectGenderBinding;
import edu.uncc.assignment05.models.Data;


public class SelectAgeFragment extends Fragment {


    public SelectAgeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectAgeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectAgeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    ArrayAdapter<String> adapter;
    Integer[] ages = new Integer[83];


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < 83; i++) {
            ages[i] = i+18;
        }
        ArrayAdapter<Integer> adapter;
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ages);
        binding.listView.setAdapter(adapter);
        getActivity().setTitle("Select Age");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int age = ages[position];
                mListener.sendAge(age);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelAge();
            }
        });

    }
    SelectAgeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectAgeListener) context;
    }
    public interface SelectAgeListener {
        void sendAge(int age);
        void cancelAge();
    }
}
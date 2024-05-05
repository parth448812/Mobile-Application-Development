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

import java.util.ArrayList;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentSelectGenderBinding;
import edu.uncc.assignment05.models.Data;
import edu.uncc.assignment05.models.User;


public class SelectGenderFragment extends Fragment {


    public SelectGenderFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectGenderBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectGenderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    ArrayAdapter<String> adapter;
    String[] genders = Data.genders;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, genders);
        binding.listView.setAdapter(adapter);
        getActivity().setTitle("Select Gender");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String gender = genders[position];
                mListener.sendGender(gender);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelGender();
            }
        });

    }
    SelectGenderListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectGenderListener) context;
    }
    public interface SelectGenderListener {
        void sendGender(String gender);
        void cancelGender();
    }
}
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

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentSelectGenderBinding;
import edu.uncc.assignment05.databinding.FragmentSelectStateBinding;
import edu.uncc.assignment05.models.Data;


public class SelectStateFragment extends Fragment {


    public SelectStateFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectStateBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectStateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    ArrayAdapter<String> adapter;
    String[] states = Data.states;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, states);
        binding.listView.setAdapter(adapter);
        getActivity().setTitle("Select State");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String state = states[position];
                mListener.sendState(state);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelState();
            }
        });

    }
    SelectStateListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectStateListener) context;
    }
    public interface SelectStateListener {
        void sendState(String state);
        void cancelState();
    }
}
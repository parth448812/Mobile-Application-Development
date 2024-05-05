package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectMaritalStatusBinding;


public class SelectMaritalStatusFragment extends Fragment {


    public SelectMaritalStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectMaritalStatusBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectMaritalStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Marital Status");

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = binding.radioGroup.getCheckedRadioButtonId();
                String marital = getString(R.string.not_married);
                if(selectedId == R.id.radioButtonNotMarried){
                    marital = getString(R.string.not_married);
                } else if(selectedId == R.id.radioButtonMarried){
                    marital = getString(R.string.married);
                } else if(selectedId == R.id.radioButtonPreferNotToSay){
                    marital = getString(R.string.prefer_not);
                }


                mListener.sendMarital(marital);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelMarital();
            }
        });
    }

    SelectMaritalStatusFragmentListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectMaritalStatusFragmentListener) context;
    }

    public interface SelectMaritalStatusFragmentListener{
        void sendMarital(String education);
        void cancelMarital();
    }
}
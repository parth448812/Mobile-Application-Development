package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectEducationBinding;


public class SelectEducationFragment extends Fragment {

    public SelectEducationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectEducationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectEducationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Education");

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = binding.radioGroup.getCheckedRadioButtonId();
                String education = getString(R.string.high_school);
                if(selectedId == R.id.radioButtonBHS){
                    education = getString(R.string.below);
                } else if(selectedId == R.id.radioButtonHS){
                    education = getString(R.string.high_school);
                } else if(selectedId == R.id.radioButtonBS){
                    education = getString(R.string.bachelors);
                } else if(selectedId == R.id.radioButtonMS){
                    education = getString(R.string.masters);
                } else if(selectedId == R.id.radioButtonPHD){
                    education = getString(R.string.phd);
                } else if(selectedId == R.id.radioButtonTS){
                    education = getString(R.string.trade);
                } else if(selectedId == R.id.radioButtonPreferNotToSay){
                    education = getString(R.string.prefer_not);
                }



                mListener.sendEducation(education);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelEducation();
            }
        });
    }

    SelectEducationFragmentListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectEducationFragmentListener) context;
    }

    public interface SelectEducationFragmentListener{
        void sendEducation(String education);
        void cancelEducation();
    }
}
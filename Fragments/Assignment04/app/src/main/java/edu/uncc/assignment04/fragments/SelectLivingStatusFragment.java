package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectLivingStatusBinding;


public class SelectLivingStatusFragment extends Fragment {



    public SelectLivingStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectLivingStatusBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectLivingStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Living Status");

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = binding.radioGroup.getCheckedRadioButtonId();
                String living = getString(R.string.homeowner);
                if(selectedId == R.id.radioButtonHomeOwner){
                    living = getString(R.string.homeowner);
                } else if(selectedId == R.id.radioButtonRenter){
                    living = getString(R.string.renter);
                } else if(selectedId == R.id.radioButtonLessee){
                    living = getString(R.string.lessee);
                } else if(selectedId == R.id.radioButtonOther){
                    living = getString(R.string.other);
                } else if(selectedId == R.id.radioButtonPreferNotToSay){
                    living = getString(R.string.prefer_not);
                }



                mListener.sendLiving(living);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelLiving();
            }
        });
    }


    SelectLivingStatusFragmentListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectLivingStatusFragmentListener) context;
    }

    public interface SelectLivingStatusFragmentListener{
        void sendLiving(String education);
        void cancelLiving();
    }
}
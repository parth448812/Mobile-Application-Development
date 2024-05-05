package edu.uncc.assignment04.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uncc.assignment04.Response;
import edu.uncc.assignment04.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    private Response mProfile;

    private static final String ARG_PARAM_PROFILE = "ARG_PARAM_PROFILE";

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance(Response response) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_PROFILE, response);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProfile = (Response) getArguments().getSerializable(ARG_PARAM_PROFILE);
        }
    }
    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");

        binding.textViewName.setText(mProfile.getName());
        binding.textViewEmail.setText(mProfile.getEmail());
        binding.textViewEdu.setText(mProfile.getEducation());
        binding.textViewMaritalStatus.setText(mProfile.getMartial_status());
        binding.textViewLivingStatus.setText(mProfile.getLiving());
        binding.textViewIncomeValue.setText(mProfile.getIncome());


    }
}
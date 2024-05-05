package edu.uncc.assignment04.fragments;

import static edu.uncc.assignment04.fragments.IdentificationFragment.response;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.uncc.assignment04.Response;
import edu.uncc.assignment04.databinding.FragmentDemographicBinding;


public class DemographicFragment extends Fragment {

    private static final String ARG_PARAM_PROFILE = "ARG_PARAM_PROFILE";
    private String selectedEducation = null;
    private String selectedMarital = null;
    private String selectedLiving = null;
    private String selectedIncome = null;

    // private Response response;

    public void setSelectedEducation(String education){
        this.selectedEducation = education;
        binding.textViewEducation.setText(education);

    }
    public void setSelectedMarital(String marital){
        this.selectedMarital = marital;
        binding.textViewMaritalStatus.setText(marital);

    }
    public void setSelectedLiving(String living){
        this.selectedLiving = living;
        binding.textViewLivingStatus.setText(living);

    }
    public void setSelectedIncome(String income){
        this.selectedIncome = income;
        binding.textViewIncomeStatus.setText(income);

    }

    public DemographicFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance(Response response) {
        DemographicFragment fragment = new DemographicFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_PROFILE, response);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentDemographicBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDemographicBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Demographic");

        binding.buttonSelectEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoEducation();
            }
        });

        binding.buttonSelectMarital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoMarital();
            }
        });

        binding.buttonSelectLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoLiving();
            }
        });

        binding.buttonSelectIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoIncome();;
            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedEducation == null){
                    Toast.makeText(getActivity(), "Select Education Level!!", Toast.LENGTH_SHORT).show();
                }
                else if(selectedMarital == null){
                    Toast.makeText(getActivity(), "Select Marital Status!!", Toast.LENGTH_SHORT).show();
                } else if(selectedLiving == null){
                    Toast.makeText(getActivity(), "Select Living Status!!", Toast.LENGTH_SHORT).show();
                } else if(selectedIncome == null){
                    Toast.makeText(getActivity(), "Select Income !!", Toast.LENGTH_SHORT).show();
                }
                else {
                    response.setEducation(selectedEducation);
                    response.setMaritial_status(selectedMarital);
                    response.setLiving_status(selectedLiving);
                    response.setIncome(selectedIncome);
                    mListener.gotoProfile(response);
                }

            }
        });

        if(selectedEducation == null){
            binding.textViewEducation.setText("N/A");
        } else {
            binding.textViewEducation.setText(selectedEducation);
        }

        if(selectedMarital == null){
            binding.textViewMaritalStatus.setText("N/A");
        } else {
            binding.textViewMaritalStatus.setText(selectedMarital);
        }

        if(selectedLiving == null){
            binding.textViewLivingStatus.setText("N/A");
        } else {
            binding.textViewLivingStatus.setText(selectedLiving);
        }

        if(selectedIncome == null){
            binding.textViewIncomeStatus.setText("N/A");
        } else {
            binding.textViewIncomeStatus.setText(selectedIncome);
        }
    }

    DemographicFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (DemographicFragmentListener) context;
    }

    public interface DemographicFragmentListener{
        void gotoEducation();
        void gotoMarital();
        void gotoLiving();
        void gotoIncome();
        void gotoProfile(Response response);
    }
}
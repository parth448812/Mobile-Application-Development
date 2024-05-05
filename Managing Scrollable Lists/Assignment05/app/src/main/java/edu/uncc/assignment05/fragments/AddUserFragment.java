package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentAddUserBinding;
import edu.uncc.assignment05.models.User;


public class AddUserFragment extends Fragment {
//    private static final String ARG_PARAM_PROFILE = "ARG_PARAM_PROFILE";
    private String selectedGender = null;
    private int selectedAge = 0;
    private String selectedState = null;
    private String selectedGroup = null;



    public void setSelectedGender(String gender){
        this.selectedGender = gender;
        binding.textViewGender.setText(gender);

    }
    public void setSelectedAge(int age){
        this.selectedAge = age;
        binding.textViewAge.setText(age + "");

    }
    public void setSelectedState(String state){
        this.selectedState = state;
        binding.textViewState.setText(state);

    }
    public void setSelectedGroup(String group){
        this.selectedGroup = group;
        binding.textViewGroup.setText(group);

    }

    public AddUserFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAddUserBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add New User");



        binding.buttonSelectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoGender();
            }
        });

        binding.buttonSelectAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAge();
            }
        });

        binding.buttonSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoState();
            }
        });

        binding.buttonSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoGroup();;
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String gender = binding.textViewGender.getText().toString();
                String age = binding.textViewAge.getText().toString();
                String state = binding.textViewState.getText().toString();
                String group = binding.textViewGroup.getText().toString();


                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Name !!", Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Email !!", Toast.LENGTH_SHORT).show();
                }
                else if(gender.isEmpty() || gender.equals("N/A")){
                    Toast.makeText(getActivity(), "Select Gender !!", Toast.LENGTH_SHORT).show();
                }
                else if(age.isEmpty() || age.equals("N/A")){
                    Toast.makeText(getActivity(), "Select Age !!", Toast.LENGTH_SHORT).show();
                } else if(state.isEmpty() || state.equals("N/A")){
                    Toast.makeText(getActivity(), "Select State !!", Toast.LENGTH_SHORT).show();
                } else if(group.isEmpty() || group.equals("N/A")){
                    Toast.makeText(getActivity(), "Select Group !!", Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = new User(name, email, gender, selectedAge, state, group);
                    mListener.sendCreatedUser(user);
                }

            }
        });

        if(selectedGender == null){
            binding.textViewGender.setText("N/A");
        } else {
            binding.textViewGender.setText(selectedGender);
        }

        if(selectedAge == 0){
            binding.textViewAge.setText("N/A");
        } else {
            binding.textViewAge.setText(selectedAge + ""); // convert to string
        }

        if(selectedState == null){
            binding.textViewState.setText("N/A");
        } else {
            binding.textViewState.setText(selectedState);
        }

        if(selectedGroup == null){
            binding.textViewGroup.setText("N/A");
        } else {
            binding.textViewGroup.setText(selectedGroup);
        }
    }


    AddUserListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddUserListener) context;
    }

    public interface AddUserListener{
        void sendCreatedUser(User user);

//        void goToUsers();
        void gotoGender();
        void gotoAge();
        void gotoState();
        void gotoGroup();
    }
}
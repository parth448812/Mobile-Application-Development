package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentUserDetailsBinding;
import edu.uncc.assignment05.databinding.FragmentUsersBinding;
import edu.uncc.assignment05.models.User;


public class UserDetailsFragment extends Fragment {

    private static final String ARG_PARAM_APP = "ARG_PARAM_APP";
    private User user;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    public static UserDetailsFragment newInstance(User user) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, user);
//        args.putString(ARG_PARAM2, param2);
        args.putSerializable(ARG_PARAM_APP, user);
        fragment.setArguments(args);
        return fragment;



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_PARAM_APP);
        }
    }

    FragmentUserDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    ArrayList<String> genres;
//    ArrayAdapter<String> adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("User Details");


        binding.textViewName.setText(user.getName());
        binding.textViewEmail.setText(user.getEmail());
        binding.textViewGender.setText(user.getGender());
        binding.textViewAge.setText(user.getAge() + "");
        binding.textViewState.setText(user.getState());
        binding.textViewGroup.setText(user.getGroup());


        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBackToUsers();
            }
        });

        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.deleteUser(user);
            }
        });

    }

    UsersDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UsersDetailsListener) context;
    }

    public interface UsersDetailsListener{
        void deleteUser(User user);

        void goBackToUsers();

    }
}
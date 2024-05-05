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
import edu.uncc.assignment05.databinding.FragmentAddUserBinding;
import edu.uncc.assignment05.databinding.FragmentSelectSortBinding;
import edu.uncc.assignment05.models.User;


public class SelectSortFragment extends Fragment {


    public SelectSortFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectSortBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Sort Selection");



        binding.imageViewNameAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("name-ascending");
            }
        });
        binding.imageViewNameDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("name-descending");
            }
        });
        binding.imageViewEmailAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("email-ascending");
            }
        });
        binding.imageViewEmailDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("email-descending");
            }
        });
        binding.imageViewGenderAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("gender-ascending");
            }
        });
        binding.imageViewGenderDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("gender-descending");
            }
        });
        binding.imageViewAgeAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("age-ascending");
            }
        });
        binding.imageViewAgeDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("age-descending");
            }
        });
        binding.imageViewStateAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("state-ascending");
            }
        });
        binding.imageViewStateDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("state-descending");
            }
        });
        binding.imageViewGroupAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("group-ascending");
            }
        });
        binding.imageViewGroupDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendSortSelection("group-descending");
            }
        });



        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelSort();
            }
        });
    }
SelectSortListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectSortListener) context;
    }

    public interface SelectSortListener{
        void cancelSort();
        void sendSortSelection(String sort);
//        void sendCreatedUser(User user);

    }
}
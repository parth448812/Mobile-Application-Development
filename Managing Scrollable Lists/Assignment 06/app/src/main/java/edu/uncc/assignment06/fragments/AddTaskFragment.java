package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentAddTaskBinding;
import edu.uncc.assignment06.models.Task;

public class AddTaskFragment extends Fragment {


    private String selectedCategory;
    private String selectedPriority;

    public void setSelectedCategory(String category){
        this.selectedCategory = category;
        binding.textViewCategory.setText(category);

    }
    public void setSelectedPriority(String priority){
        this.selectedPriority = priority;
        binding.textViewPriority.setText(priority);

    }
    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String category) {
        return null;
    }


//    public static AddTaskFragment newInstance(String param1) {
//        AddTaskFragment fragment = new AddTaskFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    FragmentAddTaskBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add New Task");



        binding.buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCategory();
            }
        });
        binding.buttonSelectPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoPriority();
            }
        });



        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString();
                String category = binding.textViewCategory.getText().toString();
                String priority = binding.textViewPriority.getText().toString();
                
                //int priorityInt = Integer.parseInt(priority);
                int priorityInt = 0;


                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Name !!", Toast.LENGTH_SHORT).show();
                }
                else if(category.isEmpty() || category.equals("N/A")){
                    Toast.makeText(getActivity(), "Select Category !!", Toast.LENGTH_SHORT).show();
                }
                else if(priority.isEmpty() || priority.equals("N/A")){
                    Toast.makeText(getActivity(), "Select Priority !!", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (priority.equals("Very High")) {
                        priorityInt = 5;
                    } else if (priority.equals("High")) {
                        priorityInt = 4;
                    } else if (priority.equals("Medium")) {
                        priorityInt = 3;
                    } else if (priority.equals("Low")) {
                        priorityInt = 2;
                    } else if (priority.equals("Very Low")) {
                        priorityInt = 1;
                    }


                    Task task = new Task(name, category, priority, priorityInt);
                    mListener.sendCreatedTask(task);
                }

            }
        });

        if(selectedCategory == null){
            binding.textViewCategory.setText("N/A");
        } else {
            binding.textViewCategory.setText(selectedCategory);
        }

        if(selectedPriority == null){
            binding.textViewPriority.setText("N/A");
        } else {
            binding.textViewPriority.setText(selectedPriority);
        }
    }


    AddTaskListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddTaskListener) context;
    }

    public interface AddTaskListener{
        void sendCreatedTask(Task task);
        void gotoCategory();

        void gotoPriority();
    }
}



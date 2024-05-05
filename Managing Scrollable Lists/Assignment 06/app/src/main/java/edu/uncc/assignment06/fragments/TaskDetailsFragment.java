package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment06.databinding.FragmentTaskDetailsBinding;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.models.Task;

public class TaskDetailsFragment extends Fragment {

    private static final String ARG_PARAM_APP = "ARG_PARAM_APP";
    private Task task;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }


    public static TaskDetailsFragment newInstance(Task task) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        args.putSerializable(ARG_PARAM_APP, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = (Task) getArguments().getSerializable(ARG_PARAM_APP);
        }
    }

    FragmentTaskDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("User Details");


        binding.textViewName.setText(task.getName());
        binding.textViewCategory.setText(task.getCategory());
        binding.textViewPriority.setText(task.getPriorityStr());


        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBackToTasks();
            }
        });

        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.deleteTask(task);
            }
        });

    }


    TasksDetailsListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TasksDetailsListener) context;
    }
    public interface TasksDetailsListener{
        void deleteTask(Task task);

        void goBackToTasks();
    }
}
// Assignment 06
// TasksFragment.java
// Parth Patel and Janvi Nandwani

package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentTasksBinding;
import edu.uncc.assignment06.models.Task;

public class TasksFragment extends Fragment {


    private ArrayList<Task> mTasks = new ArrayList<>();
    private String selectedSort = null;
    public TasksFragment(ArrayList<Task> mTasks) {
        this.mTasks = mTasks;
    }


//    public static TasksFragment newInstance(String param1, String param2) {
//        TasksFragment fragment = new TasksFragment();
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

    FragmentTasksBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ///ArrayAdapter<Task> adapter;
    TaskAdapter adapter;
    // String[] categories = Data.categories;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //if (mTasks.size() != 0) {
            mTasks = mListener.getAllTasks();
//        adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, mTasks);

            //adapter = new TaskAdapter(getActivity(), mTasks);
            //binding.listView.setAdapter(adapter);

            adapter = new TaskAdapter();
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerView.setAdapter(adapter);
        //}


        getActivity().setTitle("Tasks");

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSort = "priority-ascending";
                binding.textViewSortIndicator.setText("Sort by Priority (ASC)");
                //Log.d(TAG, selectedSort);
                //log(selectedSort);
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task task1, Task task2) {
                        // Compare based on priority
                        return Integer.compare(task1.getPriority(), task2.getPriority());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSort = "priority-descending";
                binding.textViewSortIndicator.setText("Sort by Priority (DESC)");
                //Console.log(selectedSort);
                //Log.d(TAG, selectedSort);
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task task1, Task task2) {
                        // Compare based on priority
                        return Integer.compare(task2.getPriority(), task1.getPriority());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });


//        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Task task = mTasks.get(position);
//                mListener.gotoTaskDetails(task);
//            }
//        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.clearAll();
                adapter.notifyDataSetChanged();
            }
        });
        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoAddTask();
            }
        });

//        if(selectedSort == null){
//            binding.textViewSortIndicator.setText("Sort by Priority (ASC)");
//        }
//        else if (selectedSort.equals("priority-ascending")) {
//            binding.textViewSortIndicator.setText("Sort by Priority (ASC)");
//        } else if (selectedSort.equals("priority-descending")) {
//            binding.textViewSortIndicator.setText("Sort by Priority (DESC)");
//        }
    }


//    public void updateTasks(ArrayList<Task> mTasks) {
//        this.mTasks = mTasks;
//    }

//    class TaskAdapter extends ArrayAdapter<Task>{
//        public TaskAdapter(@NonNull Context context, @NonNull List<Task> objects) {
//            super(context, R.layout.task_list_item, objects);
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            if(convertView == null){
//                convertView = getLayoutInflater().inflate(R.layout.task_list_item, parent, false);
//            }
//
//            TextView textViewName = convertView.findViewById(R.id.textViewName);
//            TextView textViewCategory = convertView.findViewById(R.id.textViewCategory);
//            TextView textViewPriority = convertView.findViewById(R.id.textViewPriority);
//
//            Task task = getItem(position);
//
//            textViewName.setText(task.getName());
//            textViewCategory.setText(task.getCategory());
//            textViewPriority.setText(task.getPriorityStr());
//
//
//            return convertView;
//        }
//    }

    class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.task_list_item, parent, false);
            return new TaskViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.setupUI(task);
        }

//        @Override
//        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
//            Task task = mTasks.get(position);
//            holder.setupUI(task);
//        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }



        class TaskViewHolder extends RecyclerView.ViewHolder{
            TextView textViewName, textViewCategory, textViewPriority;
            Task mTask;
            ImageView imageView;
            public TaskViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewCategory = itemView.findViewById(R.id.textViewCategory);
                textViewPriority = itemView.findViewById(R.id.textViewPriority);

                imageView = itemView.findViewById(R.id.imageView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoTaskDetails(mTask);
                    }
                });
            }

            public void setupUI(Task task){
                //textView.setText(task.getName());
                textViewName.setText(task.getName());
                textViewCategory.setText(task.getCategory());
                textViewPriority.setText(task.getPriorityStr());
                mTask = task;

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTasks.remove(mTask);
                        notifyDataSetChanged();
                    }
                });
            }

        }
    }

    TasksListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TasksListener) context;
    }
    public interface TasksListener{
        ArrayList<Task> getAllTasks();
        void gotoAddTask();
        void gotoTaskDetails(Task task);

        void clearAll();
        ArrayList<Task> updateTasks();
    }
}
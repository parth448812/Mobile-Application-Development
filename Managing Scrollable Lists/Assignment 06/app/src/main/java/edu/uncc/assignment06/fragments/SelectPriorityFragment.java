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
import android.widget.TextView;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectCategoryBinding;
import edu.uncc.assignment06.databinding.FragmentSelectPriorityBinding;
import edu.uncc.assignment06.models.Data;


public class SelectPriorityFragment extends Fragment {



    public SelectPriorityFragment() {
        // Required empty public constructor
    }


    /*public static SelectPriorityFragment newInstance(String param1, String param2) {
        SelectPriorityFragment fragment = new SelectPriorityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectPriorityBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectPriorityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    String[] priorities = Data.priorities;

    //ArrayList<String> categories = Data.categories;
    PrioritiesAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories);
        adapter = new PrioritiesAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        //binding.listView.setAdapter(adapter);
        getActivity().setTitle("Select Priority");



//        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String category = categories[position];
//                mListener.sendSelectedCategory(category);
//            }
//        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelPriority();
            }
        });
    }

    class PrioritiesAdapter extends RecyclerView.Adapter<SelectPriorityFragment.PrioritiesAdapter.PriorityViewHolder>{
        @NonNull
        @Override
        public SelectPriorityFragment.PrioritiesAdapter.PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.selection_list_item, parent, false);
            return new SelectPriorityFragment.PrioritiesAdapter.PriorityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectPriorityFragment.PrioritiesAdapter.PriorityViewHolder holder, int position) {
            String priority = priorities[position];
            holder.setupUI(priority);
        }

        @Override
        public int getItemCount() {
            return priorities.length;
        }

        class PriorityViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            String mPriority;
            public PriorityViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.sendSelectedPriority(mPriority);
                    }
                });
            }

            public void setupUI(String priority){
                textView.setText(priority);
                mPriority = priority;
            }
        }
    }
    SelectPriorityListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectPriorityListener) context;
    }

    public interface SelectPriorityListener{


        void sendSelectedPriority(String category);

        void cancelPriority();


    }

}
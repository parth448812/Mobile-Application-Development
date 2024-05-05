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
import android.widget.TextView;

import java.util.ArrayList;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectCategoryBinding;
import edu.uncc.assignment06.models.Data;

public class SelectCategoryFragment extends Fragment {


    public SelectCategoryFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectCategoryBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    ArrayAdapter<String> adapter;
    String[] categories = Data.categories;

    //ArrayList<String> categories = Data.categories;
    CategoriesAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories);
        adapter = new CategoriesAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        //binding.listView.setAdapter(adapter);
        getActivity().setTitle("Select Category");



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
                mListener.cancelCategory();
            }
        });
    }

    class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>{
        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.selection_list_item, parent, false);
            return new CategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            String category = categories[position];
            holder.setupUI(category);
        }

        @Override
        public int getItemCount() {
            return categories.length;
        }

        class CategoryViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            String mCategory;
            public CategoryViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.sendSelectedCategory(mCategory);
                    }
                });
            }

            public void setupUI(String category){
                textView.setText(category);
                mCategory = category;
            }
        }
    }
    SelectCategoryListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectCategoryListener) context;
    }

    public interface SelectCategoryListener{


        void sendSelectedCategory(String category);

        void cancelCategory();


    }

}
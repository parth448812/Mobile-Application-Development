package edu.uncc.assignment05.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentUsersBinding;
import edu.uncc.assignment05.models.User;


public class UsersFragment extends Fragment {
    ArrayList<User> mUsers;
    String selectedSort;



    private static final String ARG_PARAM_PROFILE = "ARG_PARAM_PROFILE";
    public void setSelectedSort(){
        //this.selectedSort = sort;
        binding.textViewSortIndicator.setText("Sort by Name (ASC)");


    }
    public UsersFragment(ArrayList<User> mUsers) {
        // Required empty public constructor
        this.mUsers = mUsers;
    }
    public void updateUsers(ArrayList<User> mUsers){
        this.mUsers = mUsers;
    }
//    public static Fragment newInstance(ArrayList<User> mUsers) {
//
//        UsersFragment fragment = new UsersFragment(mUsers);
//        return fragment;
//    }
//    public static Fragment newInstance(User user, ArrayList<User> mUsers) {
//        UsersFragment fragment = new UsersFragment(mUsers);
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_PARAM_PROFILE, user);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentUsersBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    ArrayAdapter<String> adapter;
    ArrayAdapter<User> adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        users = MainActivity.mUsers;
        adapter = new ArrayAdapter<User>(getActivity(), android.R.layout.simple_list_item_1, mUsers);
        adapter = new UsersAdapter(getActivity(), mUsers);

        binding.listView.setAdapter(adapter);
        getActivity().setTitle("Users");

//        if (sort == "name-ascending"){
//            mUsers.sort(); // unsure what to put
//        }
//        else if (sort.isEmpty()){
//            ;
//        }
        // sortUsers();


        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = mUsers.get(position);
                mListener.sendSelectedUser(user);
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendtoSort();
            }
        });

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToAddUser();

            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.mUsers.clear();
                mListener.clearAll();
//                mUsers.clear(); // might not work see if you need to make it static
            }
        });

        if(selectedSort == null){
            binding.textViewSortIndicator.setText("Sort by Name (ASC)");
        } else if (selectedSort.equals("name-ascending")) {
            binding.textViewSortIndicator.setText("Sort by Name (ASC)");
        } else if (selectedSort.equals("name-descending")) {
            binding.textViewSortIndicator.setText("Sort by Name (DESC)");
        } else if (selectedSort.equals("email-ascending")) {
            binding.textViewSortIndicator.setText("Sort by Email (ASC)");
        } else if (selectedSort.equals("email-descending")) {
            binding.textViewSortIndicator.setText("Sort by Email (DESC)");
        } else if (selectedSort.equals("gender-ascending")) {
            binding.textViewSortIndicator.setText("Sort by Gender (ASC)");
        } else if (selectedSort.equals("gender-descending")) {
            binding.textViewSortIndicator.setText("Sort by Gender (DESC)");
        } else if (selectedSort.equals("age-ascending")) {
            binding.textViewSortIndicator.setText("Sort by Age (ASC)");
        } else if (selectedSort.equals("age-descending")) {
            binding.textViewSortIndicator.setText("Sort by Age (DESC)");
        } else if (selectedSort.equals("state-ascending")) {
            binding.textViewSortIndicator.setText("Sort by State (ASC)");
        } else if (selectedSort.equals("state-descending")) {
            binding.textViewSortIndicator.setText("Sort by State (DESC)");
        } else if (selectedSort.equals("group-ascending")) {
            binding.textViewSortIndicator.setText("Sort by Group (ASC)");
        } else if (selectedSort.equals("group-descending")) {
            binding.textViewSortIndicator.setText("Sort by Group (DESC)");
        }
    }




//    @SuppressLint("SetTextI18n")
    public void setSelectedSelection(String sort) {
        this.selectedSort = sort;
        if (sort.equals("name-ascending")) {
//            binding.textViewSortIndicator.setText("Sort by Name (ASC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    return user1.getName().compareTo(user2.getName());
                }
            });

        } else if (sort.equals("name-descending")) {
//            binding.textViewSortIndicator.setText("Sort by Name (DESC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    return user2.getName().compareTo(user1.getName());
                }
            });

        } else if (sort.equals("email-ascending")) {
            //binding.textViewSortIndicator.setText("Sort by Email (ASC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    return user1.getEmail().compareTo(user2.getEmail());
                }
            });

        } else if (sort.equals("email-descending")) {
            //binding.textViewSortIndicator.setText("Sort by Email (DESC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    return user2.getEmail().compareTo(user1.getEmail());
                }
            });
        } else if (sort.equals("gender-ascending")) {
            //binding.textViewSortIndicator.setText("Sort by Gender (ASC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    // Compare based on gender
                    return user1.getGender().compareTo(user2.getGender());
                }
            });

        } else if (sort.equals("gender-descending")) {
            //binding.textViewSortIndicator.setText("Sort by Gender (DESC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    // Compare based on gender in reverse order
                    return user2.getGender().compareTo(user1.getGender());
                }
            });

        } else if (sort.equals("age-ascending")) {
            //binding.textViewSortIndicator.setText("Sort by Age (ASC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    // Compare based on age
                    return Integer.compare(user1.getAge(), user2.getAge());
                }
            });

        } else if (sort.equals("age-descending")) {
            //binding.textViewSortIndicator.setText("Sort by Age (DESC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    // Compare based on age in reverse order
                    return Integer.compare(user2.getAge(), user1.getAge());
                }
            });

        } else if (sort.equals("state-ascending")) {
            //binding.textViewSortIndicator.setText("Sort by State (ASC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    // Compare based on gender
                    return user1.getState().compareTo(user2.getState());
                }
            });
        } else if (sort.equals("state-descending")) {
            //binding.textViewSortIndicator.setText("Sort by State (DESC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    // Compare based on gender in reverse order
                    return user2.getState().compareTo(user1.getState());
                }
            });
        } else if (sort.equals("group-ascending")) {
            //binding.textViewSortIndicator.setText("Sort by Group (ASC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    // Compare based on gender
                    return user1.getGroup().compareTo(user2.getGroup());
                }
            });

        } else if (sort.equals("group-descending")) {
            //binding.textViewSortIndicator.setText("Sort by Group (DESC)");
            Collections.sort(mUsers, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    // Compare based on gender in reverse order
                    return user2.getGroup().compareTo(user1.getGroup());
                }
            });

        }

    }

    class UsersAdapter extends ArrayAdapter<User>{
        public UsersAdapter(@NonNull Context context, @NonNull List<User> objects) {
            super(context, R.layout.user_list_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.user_list_item, parent, false);
            }

            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewEmail = convertView.findViewById(R.id.textViewEmail);
            TextView textViewGender = convertView.findViewById(R.id.textViewGender);
            TextView textViewAge = convertView.findViewById(R.id.textViewAge);
            TextView textViewState = convertView.findViewById(R.id.textViewState);
            TextView textViewGroup = convertView.findViewById(R.id.textViewGroup);
            User user = getItem(position);

            textViewName.setText(user.getName());
            textViewEmail.setText(user.getEmail());
            textViewGender.setText(user.getGender());
            textViewAge.setText(user.getAge() + "");
            textViewState.setText(user.getState());
            textViewGroup.setText(user.getGroup());

            return convertView;
        }
    }

    UsersListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UsersListener) context;
    }

    public interface UsersListener{
        void sendSelectedUser(User user);

        void goToAddUser();

        void sendtoSort();

        void clearAll();
    }
}
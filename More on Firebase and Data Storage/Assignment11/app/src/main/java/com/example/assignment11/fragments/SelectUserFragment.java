package com.example.assignment11.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.assignment11.R;
import com.example.assignment11.databinding.FragmentSelectUserBinding;
import com.example.assignment11.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;


public class SelectUserFragment extends Fragment {


    ArrayList<User> users = new ArrayList<>();
    ArrayList<String> blockedUsers = new ArrayList<>();
    ArrayList<String> blockedBy = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    ArrayAdapter<User> adapter;

    public SelectUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        listenerRegistration = db.collection("users")
                //.whereNotEqualTo("userId", auth.getCurrentUser().getUid())
                //Right now any user that the current has blocked does not appear in the list.
                // However, the user that is blocked can still see the current user in their list.
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            users.clear();
                            for (QueryDocumentSnapshot doc : value) {
                                User user = doc.toObject(User.class);

                                if (!(user.getUserId().equals(auth.getCurrentUser().getUid()))){
                                    // Don't show the current user in the list
                                    // Also, don't show blocked users
                                    // not current user
//                                    blockedBy = user.getBlockedBy();
//                                    if (!(blockedBy.contains(user.getUserId()))){
//                                        users.add(user);
//                                    } else {
//                                        ;
//                                    }
//                                    blockedBy.clear();

                                    users.add(user);



                                }
                                else {
                                    //current user
                                    //blockedUsers = user.getBlocked(); // remove if you want to see blocked users
                                    blockedBy = user.getBlockedBy();
                                    Log.d("SelectUserFragment", "Blocked users: " + blockedUsers);
                                }
                            }

                            // remove if you want to see blocked users
//                            if (blockedUsers.size() > 0){
//                                users.removeIf(user -> blockedUsers.contains(user.getUserId()));
//                                Log.d("SelectUserFragment", "Number of users fetched: " + users);
//                            }

                            if (blockedBy.size() > 0){
                                users.removeIf(user -> blockedBy.contains(user.getUserId()));
                                Log.d("SelectUserFragment", "Number of users fetched: " + users);
                            }

                            adapter.notifyDataSetChanged();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_cancel){
            mListener.onSelectionCanceled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    FragmentSelectUserBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select User");

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, users);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = users.get(position);
                mListener.onUserSelected(user);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration != null){
            listenerRegistration.remove();
        }
    }

    UserListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (UserListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement UserListener");
        }
    }

    public interface UserListener{
        void onUserSelected(User user);
        void onSelectionCanceled();
    }
}
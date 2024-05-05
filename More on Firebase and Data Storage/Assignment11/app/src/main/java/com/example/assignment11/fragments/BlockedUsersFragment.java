package com.example.assignment11.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.example.assignment11.databinding.FragmentBlockedUsersBinding;
import com.example.assignment11.databinding.FragmentSelectUserBinding;
import com.example.assignment11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class BlockedUsersFragment extends Fragment {


    ArrayList<User> blockedUsers = new ArrayList<>();
    ArrayList<String> blockedUserIds = new ArrayList<>();
    ArrayAdapter<User> adapter;
    ListenerRegistration listenerRegistration;
    User mUser;
    ArrayList<User> mUsers = new ArrayList<>();



    public BlockedUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        listenerRegistration = db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            blockedUsers.clear();
                            for (QueryDocumentSnapshot doc : value) {
                                User user = doc.toObject(User.class);
                                //mUsers.add(user);
                                if (user.getUserId().equals(auth.getCurrentUser().getUid())) {
                                    blockedUserIds = user.getBlocked();
                                    for (String userId : blockedUserIds) {
                                        db.collection("users")
                                                .document(userId)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                User blockedUser = document.toObject(User.class);
                                                                blockedUsers.add(blockedUser);

                                                            }
                                                            adapter.notifyDataSetChanged();
                                                        }

                                                    }
                                                });
                                    }
                                }



                            }

                        }
                    }
                });

        //DocumentReference docRef = db.collection("messages").document(auth.getCurrentUser().getUid());
        // The idea is that I want to get the arraylist of blocked userId from the current user and then get the user details from the userId

//        db.collection("users")
//                .document(auth.getCurrentUser().getUid())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                // Get the list of blocked user IDs from the document
//                                blockedUserIds = (ArrayList<String>) document.get("blocked");
//                                if (blockedUserIds != null && !blockedUserIds.isEmpty()) {
//                                    // Now fetch the users who are blocked
//                                    db.collection("users")
//                                            .whereIn("userId", blockedUserIds)
//                                            .get()
//                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                    if (task.isSuccessful()) {
//                                                        blockedUsers.clear();
//                                                        for (QueryDocumentSnapshot doc : task.getResult()) {
//                                                            User blockedUser = doc.toObject(User.class);
//                                                            blockedUsers.add(blockedUser);
//                                                        }
//                                                        adapter.notifyDataSetChanged();
//                                                    } else {
//                                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
//                                } else {
//                                    // No blocked users found
//                                    blockedUsers.clear();
//                                    adapter.notifyDataSetChanged();
//                                }
//                            } else {
//                                // Document does not exist
//                                Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        setHasOptionsMenu(true);
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

    FragmentBlockedUsersBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentBlockedUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Blocked Users");

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, blockedUsers);
        binding.listViewBlocked.setAdapter(adapter);

        //not clickable for now, maybe to unblock
//        binding.listViewBlocked.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                User user = users.get(position);
//                mListener.onUserSelected(user);
//            }
//        });
    }

    BlockedUsersListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BlockedUsersListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement BlockedUsers");
        }
    }

    public interface BlockedUsersListener{
        void onUserSelected(User user);
        void onSelectionCanceled();
    }
}
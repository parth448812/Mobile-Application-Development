package com.example.assignment11.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.assignment11.databinding.FragmentUsersBinding;
import com.example.assignment11.databinding.MessageRowItemBinding;
import com.example.assignment11.databinding.UsersRowItemBinding;
import com.example.assignment11.models.Message;
import com.example.assignment11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class UsersFragment extends Fragment {

    ArrayList<User> mUsers = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    UsersAdapter userAdapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<String> mBlockedUsers = new ArrayList<>();
    User mUser;


    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    FragmentUsersBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Users");

        binding.buttonBlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoBlocked();
            }
        });
        binding.textViewUsersTitle.setText("Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        binding.recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UsersAdapter();
        binding.recyclerViewUsers.setAdapter(userAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        listenerRegistration = db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            mUsers.clear();
                            for (QueryDocumentSnapshot doc : value) {
                                User user = doc.toObject(User.class);
                                //mUsers.add(user);
                                if (user.getUserId().equals(auth.getCurrentUser().getUid())) {
                                    mUser = user;
                                    mBlockedUsers = user.getBlocked();
                                }
                                else {
                                    mUsers.add(user);
                                }


                            }
                            userAdapter.notifyDataSetChanged();
                        }
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

    class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
        @NonNull
        @Override
        public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            UsersRowItemBinding binding = UsersRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new UsersViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
            User user = mUsers.get(position);
            holder.setupUI(user);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        class UsersViewHolder extends RecyclerView.ViewHolder {
            UsersRowItemBinding mBinding;
            User mUser;
            String mCurrentUser;
            public UsersViewHolder(UsersRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(User user){
                mUser = user;
                mBinding.textViewConversationText.setText(mUser.getName());
                mCurrentUser = mAuth.getCurrentUser().getUid();
                if (mBlockedUsers.contains(mUser.getUserId())) {
                    mBinding.textViewBlocked.setText("Blocked");
                    mBinding.imageViewBlock.setImageResource(R.drawable.hide);
                } else {
                    mBinding.textViewBlocked.setText("Not Blocked");//replace with "" if not blocked
                    mBinding.imageViewBlock.setImageResource(R.drawable.visible);
                }

                mBinding.imageViewBlock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> updateData = new HashMap<>();
                        HashMap<String, Object> updateOtherData = new HashMap<>();
                        if(mBlockedUsers != null){
                            if(mBlockedUsers.contains(mUser.getUserId())){
                                //unblock the current user .. remove uid from the array ..
                                updateData.put("blocked", FieldValue.arrayRemove(mUser.getUserId()));
                                updateOtherData.put("blockedBy", FieldValue.arrayRemove(mAuth.getCurrentUser().getUid()));
                            } else {
                                //block .. add the uid to the array

                                updateData.put("blocked", FieldValue.arrayUnion(mUser.getUserId()));
                                updateOtherData.put("blockedBy", FieldValue.arrayUnion(mAuth.getCurrentUser().getUid()));
                            }
                        } else {
                            //block .. add the uid to the array
                            updateData.put("blocked", FieldValue.arrayUnion((mUser.getUserId())));
                            updateOtherData.put("blockedBy", FieldValue.arrayUnion(mAuth.getCurrentUser().getUid()));
                        }

                        FirebaseFirestore.getInstance().collection("users").document(mAuth.getCurrentUser().getUid()).update(updateData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        userAdapter.notifyDataSetChanged();
                                    }
                                });

                        FirebaseFirestore.getInstance().collection("users").document(mUser.getUserId()).update(updateOtherData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        userAdapter.notifyDataSetChanged();
                                    }
                                });
                    }
                });

            }
        }

    }
    UsersListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (UsersListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement UsersListener");
        }
    }

    public interface UsersListener{
        void gotoBlocked();
        void onSelectionCanceled();
    }
}
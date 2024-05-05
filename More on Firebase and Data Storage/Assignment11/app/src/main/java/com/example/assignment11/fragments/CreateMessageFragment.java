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
import android.widget.Toast;

import com.example.assignment11.R;
import com.example.assignment11.databinding.FragmentCreateMessageBinding;
import com.example.assignment11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


public class CreateMessageFragment extends Fragment {


    public User selectedUser;

    public CreateMessageFragment() {
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

    FragmentCreateMessageBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    CreateMessageListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CreateMessageListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement CreateMessageListener");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Message");

        if(selectedUser != null){
            binding.textViewReceiver.setText(selectedUser.getName());
        }

        binding.buttonSelectReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectUser();
            }
        });


        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.editTextForumTitle.getText().toString();
                String desc = binding.editTextForumDesc.getText().toString();
                String receiver = binding.textViewReceiver.getText().toString();
                if(title.isEmpty()){
                    Toast.makeText(getActivity(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(desc.isEmpty()){
                    Toast.makeText(getActivity(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (receiver.isEmpty() || receiver.equals("N/A")){
                    Toast.makeText(getActivity(), "Receiver cannot be empty", Toast.LENGTH_SHORT).show();

                }else {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    // rethink logic because how will the receiver know that they have a message if they are not the owner of the message
                    // maybe the message should be sent to the receiver's collection of messages aswell that way you do not need an arraylist to check deleted message.
                    // or the document id is the conversation id and the receiver can query the conversation id to get all the messages
                    DocumentReference docRef = db.collection("messages").document();

                    ArrayList<String> titlePrefixes = new ArrayList<>();
                    for (int i = 0; i <= title.length(); i++) {
                        titlePrefixes.add(title.substring(0, i));
                    }

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("title", title);
                    data.put("message", desc);
                    data.put("ownerId", auth.getCurrentUser().getUid());
                    data.put("receiverId", selectedUser.getUserId());
                    data.put("sender", auth.getCurrentUser().getDisplayName());
                    data.put("receiver", receiver);
                    data.put("conversationId", docRef.getId());
                    //check if the conversation already exists, if it does, find the conversation and get the orderNumber and increment it by 1
                    // or in conversation fragment order the messages by created_at
                    data.put("created_at", FieldValue.serverTimestamp());
                    data.put("docId", docRef.getId());
                    data.put("isRead", false);
                    data.put("isReply", false);
                    data.put("deletedBy", new ArrayList<String>());// add uid once they have chosen to delete it, when both uid are there, delete it all together
                    data.put("ids", new ArrayList<String>(){{add(auth.getCurrentUser().getUid()); add(selectedUser.getUserId());}});
                    data.put("titlePrefixes", titlePrefixes);
                    //data.put("created_at", FieldValue.serverTimestamp());

                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mListener.doneCreateMessage();
                            } else {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }


    public interface CreateMessageListener{
        void doneCreateMessage();
        void onSelectionCanceled();
        void gotoSelectUser();
    }
}
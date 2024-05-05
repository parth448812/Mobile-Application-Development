package com.example.assignment11.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.assignment11.databinding.ConversationRowItemBinding;
import com.example.assignment11.databinding.FragmentConversationBinding;
import com.example.assignment11.databinding.FragmentSelectUserBinding;
import com.example.assignment11.databinding.MessageRowItemBinding;
import com.example.assignment11.models.Message;
import com.example.assignment11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class ConversationFragment extends Fragment {

    private static final String ARG_PARAM_MESSAGE = "ARG_PARAM_MESSAGE";
    private Message mMessage;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListenerRegistration listenerRegistration;
    ConversationAdapter conversationAdapter;
    ArrayList<Message> mMessages = new ArrayList<>();
    String notUser;
    String notUserId;
    public ConversationFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Message message) {
        ConversationFragment fragment = new ConversationFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMessage = (Message) getArguments().getSerializable(ARG_PARAM_MESSAGE);
//            mMessage.setRead(true);
//            db.collection("messages").document(mMessage.getDocId()).update("read", true);
        }
        setHasOptionsMenu(true);

        if (mMessage.getReceiverId().equals(mAuth.getCurrentUser().getUid())) {
            mMessage.setRead(true);
            db.collection("messages").document(mMessage.getDocId()).update("isRead", true);
        }
        else {
            ;
        }



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //DocumentReference docRef = db.collection("messages").document(auth.getCurrentUser().getUid());
        listenerRegistration = db.collection("messages")
                //.whereArrayContainsAny("ids", Arrays.asList(mMessage.getOwnerId(), mMessage.getReceiverId()))// change for unique convo for each message
                .whereEqualTo("conversationId", mMessage.getConversationId())
                //.whereEqualTo("conversationId", mMessage.getConversationId())
        // find where conversation id is the same
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            mMessages.clear();
                            for (QueryDocumentSnapshot doc : value) {
                                Message message = doc.toObject(Message.class);
                                mMessages.add(message);
                                if (message.getReceiverId().equals(auth.getCurrentUser().getUid())) {
                                    notUser = message.getSender();
                                    notUserId = message.getOwnerId();
                                    binding.textViewConversationHead.setText(mMessage.getTitle() + " with " + notUser);
                                }
                                else {
                                    notUser = message.getReceiver();
                                    notUserId = message.getReceiverId();
                                    binding.textViewConversationHead.setText(mMessage.getTitle() + " with " + notUser);
                                }
                                if (message.getDeletedBy().contains(auth.getCurrentUser().getUid())) {
                                    mMessages.remove(message);
                                }
                            }
                            // Sort the ArrayList by timestamp
                            Collections.sort(mMessages, new Comparator<Message>() {
                                @Override
                                public int compare(Message m1, Message m2) {
                                    if (m1.getCreated_at() == null || m2.getCreated_at() == null)
                                        return 0;

                                    return m1.getCreated_at().compareTo(m2.getCreated_at());
                                }
                            });
                            conversationAdapter.notifyDataSetChanged();
                            binding.recyclerViewConversation.scrollToPosition(mMessages.size() - 1);
                        }
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

    FragmentConversationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConversationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Conversation");
        binding.textViewConversationHead.setText(mMessage.getTitle() + " with " + notUser);

        binding.recyclerViewConversation.setLayoutManager(new LinearLayoutManager(getContext()));
        conversationAdapter = new ConversationAdapter();
        binding.recyclerViewConversation.setAdapter(conversationAdapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
//                ((LinearLayoutManager)binding.recyclerViewConversation.getLayoutManager()).getOrientation());
//        binding.recyclerViewConversation.addItemDecoration(dividerItemDecoration);

//        binding.recyclerViewConversation.addItemDecoration(
//                new DividerItemDecoration(getContext(), ((LinearLayoutManager)binding.recyclerViewConversation.getLayoutManager()).getOrientation()) {
//                    @Override
//                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                        int position = parent.getChildAdapterPosition(view);
//                        // hide the divider for the last child
//                        if (position == parent.getAdapter().getItemCount() - 1) {
//                            outRect.setEmpty();
//                        } else {
//                            super.getItemOffsets(outRect, view, parent, state);
//                        }
//                    }
//                }
//        );

        binding.buttonReplySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.editTextReply.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a message", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    //rethink logic because how will the receiver know that they have a message if they are not the owner of the message
                    // maybe the message should be sent to the receiver's collection of messages aswell that way you do not need an arraylist to check deleted message.
                    // or the document id is the conversation id and the receiver can query the conversation id to get all the messages
                    DocumentReference docRef = db.collection("messages").document();

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("title", mMessage.getTitle());
                    data.put("message", message);
                    data.put("ownerId", auth.getCurrentUser().getUid());
                    data.put("receiverId", notUserId);
                    data.put("sender", auth.getCurrentUser().getDisplayName());
                    data.put("receiver", notUser);
                    data.put("conversationId", mMessage.getConversationId());
                    //check if the conversation already exists, if it does, find the conversation and get the orderNumber and increment it by 1
                    // or in conversation fragment order the messages by created_at
                    data.put("created_at", FieldValue.serverTimestamp());
                    data.put("docId", docRef.getId());
                    data.put("isRead", false);
                    data.put("isReply", true);
                    data.put("deletedBy", new ArrayList<String>());// add uid once they have chosen to delete it, when both uid are there, delete it all together
                    data.put("ids", new ArrayList<String>(){{add(auth.getCurrentUser().getUid()); add(notUserId);}});
                    data.put("titlePrefixes", mMessage.getTitlePrefixes());
                    //data.put("created_at", FieldValue.serverTimestamp());

                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                binding.editTextReply.setText("");
                                conversationAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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

    class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {
        @NonNull
        @Override
        public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConversationRowItemBinding binding = ConversationRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ConversationViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
            Message message = mMessages.get(position);
            holder.setupUI(message);
        }

        @Override
        public int getItemCount() {
            return mMessages.size();
        }

        class ConversationViewHolder extends RecyclerView.ViewHolder {
            ConversationRowItemBinding mBinding;
            Message nMessage;
            String mCurrentUser;
            public ConversationViewHolder(ConversationRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Message message){
                nMessage = message;
//                if (nMessage.getDeletedBy().contains(mCurrentUser)) {
//                    mBinding.getRoot().setVisibility(View.GONE);
//                    mBinding.getRoot().setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//                } else {
//                    mBinding.getRoot().setVisibility(View.VISIBLE);
//                    mBinding.getRoot().setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    //mBinding.getRoot().setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//                }

                if (nMessage.isReply()) {
                    mBinding.textViewReply.setText("Reply");
                }
                else {
                    mBinding.textViewReply.setText("");
                }


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                if (nMessage.getCreated_at() != null) {
                    if (nMessage.isRead()) {//set to true when they click on it to go to conversation
                        mBinding.textViewMessageReadDate.setText("Read | " + simpleDateFormat.format(message.getCreated_at().toDate()));
                    }
                    else {
                        mBinding.textViewMessageReadDate.setText("Unread | " + simpleDateFormat.format(message.getCreated_at().toDate()));
                    }
                } else {
                    mBinding.textViewMessageReadDate.setText("Unread | " + simpleDateFormat.format(System.currentTimeMillis()));
                }

                mCurrentUser = mAuth.getCurrentUser().getUid();
                //mBinding.textViewConversationTitle.setText(message.getTitle());
                //mBinding.textViewMessageCreatedByandTo.setText("To: " + message.getReceiver() + " | By: " + message.getSender());
                mBinding.textViewConversationText.setText(nMessage.getMessage());
                //Find a way to color it differently if it is read or not
                if (mCurrentUser.equals(nMessage.getOwnerId())) {
                    mBinding.cardColor.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));

                    notUserId = nMessage.getReceiverId();
                    notUser = nMessage.getReceiver();
                    //ConstraintLayout rl = (ConstraintLayout)findViewById(R.id.cardColor);
                } else {
                    mBinding.cardColor.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                    notUserId = nMessage.getOwnerId();
                    notUser = nMessage.getSender();
                }

                //rethink right now even if you deleted it, it is still visible to the one who deleted it and it can add the same user twice
                if(mCurrentUser.equals(nMessage.getOwnerId()) || (mCurrentUser.equals(nMessage.getReceiverId()))) {// you can delete if you sent it or not
                    mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    if (nMessage.getDeletedBy().contains(mCurrentUser)) {
                        mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                        mBinding.getRoot().setVisibility(View.GONE);
                    } else {
                        mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                        mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //delete the post
                                //refresh by getting the posts

                                nMessage.getDeletedBy().add(mCurrentUser);

                                if (nMessage.getDeletedBy().contains(nMessage.getOwnerId()) && nMessage.getDeletedBy().contains(nMessage.getReceiverId())){
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("messages").document(nMessage.docId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //removed to show the snapshot listener
                                            //getPosts();
                                        }
                                    });
                                }
                                else {
                                    //make it invisible or not something
                                    db.collection("messages").document(nMessage.getDocId()).update("deletedBy", nMessage.getDeletedBy()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //removed to show the snapshot listener
                                            //getPosts();
                                        }
                                    });
                                    mBinding.getRoot().setVisibility(View.GONE);
                                }

                            }
                        });
                    }
                } else {
                    mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }


            }
        }

    }



    ConversationListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (ConversationListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement ConversationListener");
        }
    }

    public interface ConversationListener{
        void onSelectionCanceled();
    }
}
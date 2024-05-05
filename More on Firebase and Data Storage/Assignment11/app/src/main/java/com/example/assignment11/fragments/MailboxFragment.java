package com.example.assignment11.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.assignment11.R;
import com.example.assignment11.databinding.FragmentMailboxBinding;
import com.example.assignment11.databinding.MessageRowItemBinding;
import com.example.assignment11.models.Message;
import com.example.assignment11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class MailboxFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String prefix, slicedTitle;


    public MailboxFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        db.collection("users").document(auth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (!document.exists()) {
                                User newUser = new User();
                                DocumentReference docRef = db.collection("users").document(auth.getCurrentUser().getUid());

                                HashMap<String, Object> data = new HashMap<>();
                                data.put("name", auth.getCurrentUser().getDisplayName());
                                Log.d("UsersFragment", user.getDisplayName() + user.getUid());
                                data.put("userId", auth.getCurrentUser().getUid());
                                data.put("docId", auth.getCurrentUser().getUid());
                                data.put("blocked", new ArrayList<String>());// add uid once they have chosen to block them
                                data.put("blockedBy", new ArrayList<String>()); // add uid once they have been blocked by them

                                docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Log.d("UsersFragment", "User document created");
                                        } else {
                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d("UsersFragment", "Failed to get user document: ", task.getException());
                        }
                    }
                });


        //Log.d("demo", "Fragment isAdded: " + isAdded());
        //Log.d("demo", "Fragment isVisible: " + isVisible());

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        //Log.d("demo", "onCreateOptionsMenu: 3");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("demo", "onOptionsItemSelected: 4");
        if(item.getItemId() == R.id.action_create){
            mListener.gotoCreateMessage();
            return true;
        } else if(item.getItemId() == R.id.action_logout) {
            mListener.logout();
            return true;
        } else if(item.getItemId() == R.id.action_users){
            mListener.gotoUsers();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    FragmentMailboxBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
        binding = FragmentMailboxBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    MailboxAdapter mailboxAdapter;
    ArrayList<Message> mMessages = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.recyclerViewMailbox.setLayoutManager(new LinearLayoutManager(getContext()));
        mailboxAdapter = new MailboxAdapter();
        binding.recyclerViewMailbox.setAdapter(mailboxAdapter);
        binding.recyclerViewMailbox.addItemDecoration(
                new DividerItemDecoration(getContext(), ((LinearLayoutManager)binding.recyclerViewMailbox.getLayoutManager()).getOrientation()) {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        int position = parent.getChildAdapterPosition(view);
                        // hide the divider for the last child
                        if (position == parent.getAdapter().getItemCount() - 1) {
                            outRect.setEmpty();
                        } else {
                            super.getItemOffsets(outRect, view, parent, state);
                        }
                    }
                }
        );


        binding.textViewTitle.setText(mAuth.getCurrentUser().getDisplayName()+"'s Mailbox");
        //prefix = binding.editTextPrefix.getText().toString();//figure out where to put it so that it updates as the user types

        EditText prefixEditText = view.findViewById(R.id.editTextPrefix); // what is view?
        prefixEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // update the prefix variable with the new text from the EditText
                prefix = s.toString();
                if (prefix.isEmpty()) {
                    //updateRecyclerViewWithPrefix(null); // issue where it does not work when prefix is null or full.
                    updateRecyclerViewWithPrefix(""); // work with a bit of lag
                } else {
                    updateRecyclerViewWithPrefix(prefix);
                }
                //updateRecyclerViewWithPrefix(prefix);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });

        getActivity().setTitle("Mailbox");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        listenerRegistration = db.collection("messages")
                .whereArrayContains("ids", mAuth.getCurrentUser().getUid())
//                .whereArrayContains("deletedBy", mAuth.getCurrentUser().getUid()) //invert logic
//                .whereNotIn("deletedBy", mAuth.getCurrentUser().getUid())
                //.whereEqualTo("title".substring(0, prefix.length()), prefix)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("demo", "Listen failed.", error);
                    return;
                }

                mMessages.clear();
                for (QueryDocumentSnapshot document : value) {
                    Message message = document.toObject(Message.class);
                    mMessages.add(message);
                    if (message.getDeletedBy().contains(auth.getCurrentUser().getUid())) {//try putting it all in the query
                        mMessages.remove(message);
                    }
//                    if (prefix != null && !(message.getTitle().startsWith(prefix))) { // this allows includes messsages with prefix later in the message title
//                        mMessages.remove(message);// potentially fixxable if you can use substring or slicing on the message title
//                    }
//                    if (!(message.getTitle().startsWith(prefix))) { // this allows includes messsages with prefix later in the message title
//                        mMessages.remove(message);// potentially fixxable if you can use substring or slicing on the message title
//                    }

                }
                Collections.sort(mMessages, new Comparator<Message>() {
                    @Override
                    public int compare(Message m1, Message m2) {
                        if (m1.getCreated_at() == null || m2.getCreated_at() == null)
                            return 0;

                        return m2.getCreated_at().compareTo(m1.getCreated_at());
                    }
                });
                mailboxAdapter.notifyDataSetChanged();


            }
        });
        setHasOptionsMenu(true);
    }
    private void updateRecyclerViewWithPrefix(String prefix) {
        CollectionReference messagesRef = FirebaseFirestore.getInstance().collection("messages");
//        messagesRef.whereGreaterThanOrEqualTo("title", prefix)
//                .whereLessThan("title", prefix + "\uf8ff")
        messagesRef.whereArrayContains("titlePrefixes", prefix)
                //.whereEqualTo("title".substring(0, prefix.length()), prefix)
                //.whereArrayContains("ids", mAuth.getCurrentUser().getUid())
                //.whereEqualTo("title", prefix)
                //.whereGreaterThanOrEqualTo("title", prefix)
                // only way to check from query is having an actual prefix var in db an Message.java, but seems too much
                // or you could create an array in createMessgaes where you generate all possible prefixes and store it in the db
                // then you can query the db for the prefix
                //.whereEqualTo("title".substring(0, prefix.length()), prefix) // what if instead of slicing down you add to the prefix
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            mMessages.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Message message = document.toObject(Message.class);
                                mMessages.add(message);
                                if (message.getDeletedBy().contains(auth.getCurrentUser().getUid())) {
                                    mMessages.remove(message); // removes message that user has deleted
                                }
                                if (!(message.getIds().contains(auth.getCurrentUser().getUid()))) {
                                    mMessages.remove(message); //removes message not involving user
                                }
//                                if (!(message.getTitle().startsWith(prefix))) { // this allows includes messsages with prefix later in the message title
//                                    mMessages.remove(message);// potentially fixxable if you can use substring or slicing on the message title
//                                }
//                                if (prefix != null && !(message.getTitle().startsWith(prefix))) { // this allows includes messsages with prefix later in the message title
//                                    mMessages.remove(message);// potentially fixxable if you can use substring or slicing on the message title
//                                }
//                                if (prefix != null){
//                                    slicedTitle = message.getTitle().substring(0, prefix.length());
//                                    if (!(slicedTitle.equals(prefix))) {
//                                        mMessages.remove(message);
//                                    }
//                                }

//                                if (!(slicedTitle.equals(prefix))) {
//                                    mMessages.remove(message);
//                                }



                            }
                            Collections.sort(mMessages, new Comparator<Message>() {
                                @Override
                                public int compare(Message m1, Message m2) {
                                    if (m1.getCreated_at() == null || m2.getCreated_at() == null)
                                        return 0;

                                    return m2.getCreated_at().compareTo(m1.getCreated_at());
                                }
                            });
                            mailboxAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("demo", "onDestroyView: ");

        if(listenerRegistration != null){
            listenerRegistration.remove();
        }
    }
    class MailboxAdapter extends RecyclerView.Adapter<MailboxAdapter.MailboxViewHolder> {
        @NonNull
        @Override
        public MailboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MessageRowItemBinding binding = MessageRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new MailboxViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull MailboxViewHolder holder, int position) {
            Message message = mMessages.get(position);
            holder.setupUI(message);
        }

        @Override
        public int getItemCount() {
            return mMessages.size();
        }

        class MailboxViewHolder extends RecyclerView.ViewHolder {
            MessageRowItemBinding mBinding;
            Message mMessage;
            String mCurrentUser;
            public MailboxViewHolder(MessageRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Message message){
                mMessage = message;

                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                // Here is the thing, right now it will not show any replies in the mailbox, but i need it show in the replies.
                // also I need to make sure the replies are unique to the message they are replying to.
                if (mMessage.isReply()) {
                    mBinding.textViewMessageReply.setText("Reply");
                }
                else {
                    mBinding.textViewMessageReply.setText("");
                }


                // do no think it is required anymore because of the snapshot listener if statement check
                if (mMessage.getDeletedBy().contains(mCurrentUser)) {
                    mBinding.getRoot().setVisibility(View.GONE);
                    mBinding.getRoot().setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                } else {
                    mBinding.getRoot().setVisibility(View.VISIBLE);
                    mBinding.getRoot().setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

                if (mMessage.getCreated_at() != null) { // causes logical error that resets to Unread
                    if (mMessage.isRead()) {//set to true when they click on it to go to conversation
                        mBinding.textViewMessageReadDate.setText("Read | " + simpleDateFormat.format(mMessage.getCreated_at().toDate()));
                    } else {
                        mBinding.textViewMessageReadDate.setText("Unread | " + simpleDateFormat.format(mMessage.getCreated_at().toDate()));
                    }
                }
                else {
                    mBinding.textViewMessageReadDate.setText("Unread | " + simpleDateFormat.format(message.getCreated_at()));
                }

//                if (message.isRead()) {//set to true when they click on it to go to conversation
//                    mBinding.textViewMessageReadDate.setText("Read | " + simpleDateFormat.format(message.getCreated_at().toDate()));
//                } else {
//                    mBinding.textViewMessageReadDate.setText("Unread | " + simpleDateFormat.format(message.getCreated_at().toDate()));
//                }

                mCurrentUser = mAuth.getCurrentUser().getUid();
                mBinding.textViewMessageTitle.setText(mMessage.getTitle());
                mBinding.textViewMessageCreatedByandTo.setText("To: " + mMessage.getReceiver() + " | By: " + mMessage.getSender());
                mBinding.textViewMessageText.setText(mMessage.getMessage());
                //Find a way to color it differently if it is read or not
                if (mCurrentUser.equals(mMessage.getOwnerId())) {
                    mBinding.cardColor.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
                    //ConstraintLayout rl = (ConstraintLayout)findViewById(R.id.cardColor);
                } else {
                    mBinding.cardColor.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                }

                if(mCurrentUser.equals(mMessage.getOwnerId()) || (mCurrentUser.equals(mMessage.getReceiverId()))) {// you can delete if you sent it or not
                    mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    if (mMessage.getDeletedBy().contains(mCurrentUser)) {
                        mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                        mBinding.getRoot().setVisibility(View.GONE);
                    } else {
                        mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                        mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //delete the post
                                //refresh by getting the posts

                                mMessage.getDeletedBy().add(mCurrentUser);

                                if (mMessage.getDeletedBy().contains(mMessage.getOwnerId()) && mMessage.getDeletedBy().contains(mMessage.getReceiverId())){
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("messages").document(mMessage.docId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //removed to show the snapshot listener
                                            //getPosts();
                                        }
                                    });
                                }
                                else {
                                    //make it invisible or not something
                                    db.collection("messages").document(mMessage.getDocId()).update("deletedBy", mMessage.getDeletedBy()).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoReply(mMessage);
                    }
                });

            }
        }

    }


    MailboxListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (MailboxListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement MailboxListener");
        }
        getActivity().invalidateOptionsMenu();
    }

    public interface MailboxListener {
        void gotoCreateMessage();
        void logout();
        void gotoUsers();
        void gotoReply(Message message);
    }
}
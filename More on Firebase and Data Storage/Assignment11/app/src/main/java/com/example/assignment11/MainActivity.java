package com.example.assignment11;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.assignment11.auth.LoginFragment;

import com.example.assignment11.auth.SignUpFragment;
import com.example.assignment11.fragments.BlockedUsersFragment;
import com.example.assignment11.fragments.ConversationFragment;
import com.example.assignment11.fragments.CreateMessageFragment;
import com.example.assignment11.fragments.MailboxFragment;
import com.example.assignment11.fragments.SelectUserFragment;
import com.example.assignment11.fragments.UsersFragment;
import com.example.assignment11.models.Message;
import com.example.assignment11.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        SignUpFragment.SignUpListener, MailboxFragment.MailboxListener, CreateMessageFragment.CreateMessageListener,
    SelectUserFragment.UserListener, BlockedUsersFragment.BlockedUsersListener, ConversationFragment.ConversationListener,
    UsersFragment.UsersListener {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(mAuth.getCurrentUser() == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new LoginFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new MailboxFragment())
                    .commit();
        }
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SignUpFragment())
                .commit();
    }


    @Override
    public void authCompleted() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new MailboxFragment())
                .commit();
    }

    @Override
    public void login() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }


    @Override
    public void gotoCreateMessage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CreateMessageFragment(), "create-message-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logout() {
        mAuth.signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoUsers() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new UsersFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoReply(Message message) {
        // work on logic for this to open up conversation fragment maybe just for one message or multiple
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.containerView, new ConversationFragment())
//                .commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ConversationFragment.newInstance(message))// new instance not fragment
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void doneCreateMessage() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onUserSelected(User user) {
        CreateMessageFragment fragment = (CreateMessageFragment) getSupportFragmentManager().findFragmentByTag("create-message-fragment");
        if(fragment != null){
            fragment.selectedUser = user;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSelectionCanceled() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SelectUserFragment())
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void gotoBlocked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new BlockedUsersFragment())
                .addToBackStack(null)
                .commit();
    }
}
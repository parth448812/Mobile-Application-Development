package edu.uncc.assignment05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.assignment05.fragments.AddUserFragment;
import edu.uncc.assignment05.fragments.SelectAgeFragment;
import edu.uncc.assignment05.fragments.SelectGenderFragment;
import edu.uncc.assignment05.fragments.SelectGroupFragment;
import edu.uncc.assignment05.fragments.SelectSortFragment;
import edu.uncc.assignment05.fragments.SelectStateFragment;
import edu.uncc.assignment05.fragments.UserDetailsFragment;
import edu.uncc.assignment05.fragments.UsersFragment;
import edu.uncc.assignment05.models.User;

public class MainActivity extends AppCompatActivity implements UsersFragment.UsersListener,
        AddUserFragment.AddUserListener, SelectGenderFragment.SelectGenderListener,
        SelectAgeFragment.SelectAgeListener,
        SelectStateFragment.SelectStateListener, SelectGroupFragment.SelectGroupListener,
        UserDetailsFragment.UsersDetailsListener, SelectSortFragment.SelectSortListener {

    private ArrayList<User> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new UsersFragment(mUsers), "User-fragment")
                .commit();
    }

    @Override
    public void sendSelectedUser(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UserDetailsFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToAddUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddUserFragment(), "addUser-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendtoSort() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectSortFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clearAll() {
        mUsers.clear();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new UsersFragment(mUsers))
                .commit();
    }

    @Override
    public void sendCreatedUser(User user) {
        mUsers.add(user);

        // getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); // Clear back stack allows for mutliple users
//        getSupportFragmentManager().popBackStack("User-fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.rootView, new UsersFragment(mUsers))
//                .addToBackStack(null)
//                .commit();
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("User-fragment");
        if(fragment != null){
            fragment.setSelectedSort();
            fragment.updateUsers(mUsers);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoGender() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectGenderFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoAge() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectAgeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoState() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectStateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoGroup() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectGroupFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendGender(String gender) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("addUser-fragment");
        if(fragment != null){
            fragment.setSelectedGender(gender);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelGender() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendAge(int age) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("addUser-fragment");
        if(fragment != null){
            fragment.setSelectedAge(age);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelAge() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendGroup(String group) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("addUser-fragment");
        if(fragment != null){
            fragment.setSelectedGroup(group);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelGroup() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendState(String state) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("addUser-fragment");
        if(fragment != null){
            fragment.setSelectedState(state);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelState() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void deleteUser(User user) {
        mUsers.remove(user);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new UsersFragment(mUsers))
                .commit();
    }

    @Override
    public void goBackToUsers() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new UsersFragment(mUsers))
                .commit();
    }

    @Override
    public void cancelSort() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSortSelection(String sort) {
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("User-fragment");
        if(fragment != null){
            fragment.setSelectedSelection(sort);
        }
        getSupportFragmentManager().popBackStack();
    }
}
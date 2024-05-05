// Assignment 06
// MainActivity.java
// Parth Patel and Janvi Nandwani

package edu.uncc.assignment06;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

import edu.uncc.assignment06.fragments.AddTaskFragment;
import edu.uncc.assignment06.fragments.SelectCategoryFragment;
import edu.uncc.assignment06.fragments.SelectPriorityFragment;
import edu.uncc.assignment06.fragments.TaskDetailsFragment;
import edu.uncc.assignment06.fragments.TasksFragment;
import edu.uncc.assignment06.models.Data;
import edu.uncc.assignment06.models.Task;

public class MainActivity extends AppCompatActivity implements SelectCategoryFragment.SelectCategoryListener,
        TasksFragment.TasksListener, TaskDetailsFragment.TasksDetailsListener, AddTaskFragment.AddTaskListener,
        SelectPriorityFragment.SelectPriorityListener {

    private ArrayList<Task> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // mTasks.addAll(Data.sampleTestTasks); //adding for testing
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.rootView, new SelectCategoryFragment(), "Category-fragment")
//                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new TasksFragment(mTasks), "Tasks-fragment")
                .commit();
    }


    @Override
    public void sendSelectedCategory(String category) {
        AddTaskFragment fragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("addTask-fragment");
        if(fragment != null){
            fragment.setSelectedCategory(category);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSelectedPriority(String category) {
        AddTaskFragment fragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("addTask-fragment");
        if(fragment != null){
            fragment.setSelectedPriority(category);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelPriority() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelCategory() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return mTasks;
    }

    @Override
    public void gotoAddTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddTaskFragment(), "addTask-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoTaskDetails(Task task) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, TaskDetailsFragment.newInstance(task))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void clearAll() {
        mTasks.clear();
    }

    @Override
    public ArrayList<Task> updateTasks() {
        return mTasks;
    }

    @Override
    public void sendCreatedTask(Task task) {
        mTasks.add(task);
//        TasksFragment fragment = (TasksFragment) getSupportFragmentManager().findFragmentByTag("Task-fragment");
//        if(fragment != null){
//            //fragment.updateTasks();
//        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectCategoryFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoPriority() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectPriorityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteTask(Task task) {
        mTasks.remove(task);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.rootView, new TasksFragment(mTasks))
//                .commit();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goBackToTasks() {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.rootView, new TasksFragment(mTasks))
//                .commit();
        getSupportFragmentManager().popBackStack();
    }
}
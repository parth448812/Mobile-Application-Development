package com.example.assignment_12;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment_12.databinding.EntryRowItemBinding;
import com.example.assignment_12.databinding.FragmentLoggedEntriesBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class LoggedEntriesFragment extends Fragment {


    EntryAdapter adapter;
    ArrayList<Entry> entries = new ArrayList<>();
    AppDatabase db;


    public LoggedEntriesFragment() {
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
        inflater.inflate(R.menu.main_menu, menu);
        //Log.d("demo", "onCreateOptionsMenu: 3");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("demo", "onOptionsItemSelected: 4");
        if(item.getItemId() == R.id.action_create){
            mListener.gotoAddLog();
            return true;
        } else if(item.getItemId() == R.id.action_visualize){
            mListener.gotoVisualize();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    FragmentLoggedEntriesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoggedEntriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Logged Entries");
        adapter = new EntryAdapter();
        binding.recyclerViewEntries.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewEntries.setAdapter(adapter);

        // get the entries from the database

        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "entries-db")
                .fallbackToDestructiveMigration() //new version of the database will delete the old database
                .allowMainThreadQueries()
                .build();

        loadAndDisplayEntries();

    }

    private void loadAndDisplayEntries(){
        entries.clear();
        entries.addAll(db.entryDao().getAll());
        sortEntriesByDateAndTime();
        adapter.notifyDataSetChanged();
    }

    private void sortEntriesByDateAndTime() {
        Collections.sort(entries, new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();

                cal1.set(o1.getYear(), o1.getMonth(), o1.getDay(), convertTo24HourFormat(o1.getHour(), o1.getAmPm()), o1.getMinute());
                cal2.set(o2.getYear(), o2.getMonth(), o2.getDay(), convertTo24HourFormat(o2.getHour(), o2.getAmPm()), o2.getMinute());

                Date date1 = cal1.getTime();
                Date date2 = cal2.getTime();

                return date1.compareTo(date2);
            }
        });
    }
    private int convertTo24HourFormat(int hour, String amPm) {
        if (amPm.equals("PM") && hour < 12) {
            hour += 12;
        } else if (amPm.equals("AM") && hour == 12) {
            hour = 0;
        }
        return hour;
    }


    class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder>{
        @NonNull
        @Override
        public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            EntryRowItemBinding rowBinding = EntryRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new EntryViewHolder(rowBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
            Entry grade = entries.get(position);
            holder.setupUI(grade);
        }

        @Override
        public int getItemCount() {
            return entries.size();
        }

        class EntryViewHolder extends RecyclerView.ViewHolder{
            EntryRowItemBinding mBinding;
            Entry mEntry;
            public EntryViewHolder(@NonNull EntryRowItemBinding rowBinding) {
                super(rowBinding.getRoot());
                this.mBinding = rowBinding;
            }
            public void setupUI(Entry entry){
                this.mEntry = entry;
//                int month = mEntry.getMonth();
//                int day = mEntry.getDay();
//                int year = mEntry.getYear();

                //mBinding.textViewEntryDate.setText(((month)+"/"+day+"/"+year));
                mBinding.textViewEntryDate.setText(mEntry.getDateAndTimeString());

                mBinding.textViewEntryWeight.setText(mEntry.getWeight() + " lbs");
                mBinding.textViewEntrySleepHours.setText(mEntry.getSleepHours() + " Hours of Sleep");
                mBinding.textViewEntryExerciseHours.setText(mEntry.getExerciseHours() + " Hours of Exercise");

                int sleepQuality = mEntry.getSleepQuality();
                if(sleepQuality == 1){
                    mBinding.textViewEntrySleepQuality.setText("Poor Sleep Quality");
                } else if(sleepQuality == 2){
                    mBinding.textViewEntrySleepQuality.setText("Fair Sleep Quality");
                } else if(sleepQuality == 3){
                    mBinding.textViewEntrySleepQuality.setText("Good Sleep Quality");
                } else if(sleepQuality == 4){
                    mBinding.textViewEntrySleepQuality.setText("Very Good Sleep Quality");
                } else if(sleepQuality == 5){
                    mBinding.textViewEntrySleepQuality.setText("Excellent Sleep Quality");
                }

                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.entryDao().delete(mEntry);
                        loadAndDisplayEntries();
                    }
                });
            }
        }
    }


    LoggedEntriesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (LoggedEntriesListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement LoggedEntriesListener");
        }
        getActivity().invalidateOptionsMenu();
    }
    public interface LoggedEntriesListener {
        void gotoAddLog();
        void gotoVisualize();

    }
}
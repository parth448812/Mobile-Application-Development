package com.example.assignment_12;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.chart.common.dataentry.ValueDataEntry;
//import com.anychart.core.Chart;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.assignment_12.databinding.ChartRowItemBinding;
import com.example.assignment_12.databinding.FragmentVisualizeProgressBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import com.example.assignment_12.Chart;


public class VisualizeProgressFragment extends Fragment {

    ArrayList<Entry> entries = new ArrayList<>();
    AppDatabase db;

    ArrayList<Chart> charts = new ArrayList<>();
    List<DataEntry> sleepHours = new ArrayList<>();
    List<DataEntry> sleepQualities = new ArrayList<>();
    List<DataEntry> exerciseHours = new ArrayList<>();
    List<DataEntry> weights = new ArrayList<>();
    ChartsAdapter adapter;

    public VisualizeProgressFragment() {
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

    FragmentVisualizeProgressBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisualizeProgressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Visualize Progress");


        loadEntries();

        adapter = new ChartsAdapter();
        binding.recyclerViewCharts.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewCharts.setAdapter(adapter);


    }


    private void loadEntries(){
        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "entries-db")
                .fallbackToDestructiveMigration() //new version of the database will delete the old database
                .allowMainThreadQueries()
                .build();
        entries.clear();
        entries.addAll(db.entryDao().getAll());
        loadChartData();

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

    private void loadChartData(){
        charts.clear();
        sleepHours.clear();
        sleepQualities.clear();
        exerciseHours.clear();
        weights.clear();

        //add method or code to order the entries by date
        sortEntriesByDateAndTime();

        for (Entry entry : entries){
            sleepHours.add(new ValueDataEntry(entry.getDateAndTimeString(), entry.getSleepHours()));
            sleepQualities.add(new ValueDataEntry(entry.getDateAndTimeString(), entry.getSleepQuality()));
            exerciseHours.add(new ValueDataEntry(entry.getDateAndTimeString(), entry.getExerciseHours()));
            weights.add(new ValueDataEntry(entry.getDateAndTimeString(), entry.getWeight()));
        }

        charts.add(new Chart("Sleep Hours", "#FF0000", sleepHours));
        charts.add(new Chart("Sleep Quality", "#00FF00", sleepQualities));
        charts.add(new Chart("Exercise Hours", "#0000FF", exerciseHours));
        charts.add(new Chart("Weight", "#FFFF00", weights));
    }

    class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ChartViewHolder>{

        @NonNull
        @Override
        public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ChartRowItemBinding rowBinding = ChartRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ChartViewHolder(rowBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
            Chart chart = charts.get(position);
            holder.setupUI(chart);
        }

        @Override
        public int getItemCount() {
            return charts.size();
        }

        class ChartViewHolder extends RecyclerView.ViewHolder{
            ChartRowItemBinding mBinding;
            Chart chart;

            public ChartViewHolder(ChartRowItemBinding rowBinding) {
                super(rowBinding.getRoot());
                this.mBinding = rowBinding;
            }

            public void setupUI(Chart chart){
//                mBinding.chartName.setText(chart.getName());
//                mBinding.anyChartView.setChart(createChart(chart));

                this.chart = chart;
                mBinding.anyChartView.setProgressBar(mBinding.progressBar);

                Cartesian cartesian = AnyChart.line();
                cartesian.animation(true);
                cartesian.title(chart.getName() + " Over Time");
                cartesian.padding(10d, 20d, 5d, 20d);

                cartesian.crosshair().enabled(true);
                cartesian.crosshair()
                        .yLabel(true)
                        .yStroke((Stroke) null, null, null, (String) null, (String) null);

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);


                cartesian.yAxis(0).title(chart.getName());
                cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

                Set set = Set.instantiate();
                set.data(chart.getSeriesData());
                Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

                Line series1 = cartesian.line(series1Mapping);
                series1.name(chart.getName());
                series1.hovered().markers().enabled(true);
                series1.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series1.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);

                series1.color(chart.getColor());

                cartesian.legend().enabled(true);
                cartesian.legend().fontSize(13d);
                cartesian.legend().padding(0d, 0d, 10d, 0d);

                mBinding.anyChartView.setChart(cartesian);



            }
        }
    }


//    private void loadWeightChart(){
//        //AnyChartView anyChartView = binding.anyChartViewWeight;
//        //anyChartView.setProgressBar(findViewById(R.id.progress_bar));
//
//        Cartesian cartesian = AnyChart.line();
//
//        cartesian.animation(true);
//        //cartesian.padding(10d, 20d, 5d, 20d);
//
////        cartesian.crosshair().enabled(true);
////        cartesian.crosshair()
////                .yLabel(true)
////                .yStroke((Stroke) null, null, null, (String) null, (String) null);
//
//        //cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
//
//        cartesian.title("Weight Over Time");
//
//        cartesian.yAxis(0).title("Weight(lbs)");
//        cartesian.xAxis(0).title("Date/Time");
//        //cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
//
//        List<DataEntry> seriesData = new ArrayList<>();
//
//        for (Entry entry : entries){
//            //seriesData.add(new CustomDataEntry(entry.getDateAndTimeString(), entry.getWeight()));
//        }
//
//
//        Set set = Set.instantiate();
//        set.data(seriesData);
//        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
//
//
//        Line series1 = cartesian.line(series1Mapping);
//        series1.name("Weight Over Time");
//        series1.hovered().markers().enabled(true);
//        series1.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d);
//        series1.tooltip()
//                .position("right")
//                .anchor(Anchor.LEFT_CENTER)
//                .offsetX(5d)
//                .offsetY(5d);
//
//
////        cartesian.legend().enabled(true);
////        cartesian.legend().fontSize(13d);
////        cartesian.legend().padding(0d, 0d, 10d, 0d);
//
//        binding.anyChartViewWeight.setChart(cartesian);
//    }



    VisualizeProgressListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (VisualizeProgressListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement VisualizeProgressListener");
        }
        getActivity().invalidateOptionsMenu();
    }
    public interface VisualizeProgressListener {
        void onSelectionCanceled();

    }
}
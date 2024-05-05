package com.example.assignment_12;

import com.anychart.chart.common.dataentry.DataEntry;

import java.util.ArrayList;
import java.util.List;

public class Chart
{
    String name, color;
    List<DataEntry> seriesData = new ArrayList<>();

    public Chart(String name, String color, List<DataEntry> seriesData) {
        this.name = name;
        this.color = color;
        this.seriesData = seriesData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<DataEntry> getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(List<DataEntry> seriesData) {
        this.seriesData = seriesData;
    }
}

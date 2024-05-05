package com.example.assignment_12;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "entry")
public class Entry {
    @PrimaryKey(autoGenerate = true)
    public int cid;
    public double weight, sleepHours, exerciseHours;
    public int sleepQuality, year, month, day, hour, minute;
    public String amPm;

    public Entry() {
    }

    public Entry(double weight, double sleepHours, double exerciseHours, int sleepQuality, int year, int month, int day, int hour, int minute, String amPm) {
        this.weight = weight;
        this.sleepHours = sleepHours;
        this.exerciseHours = exerciseHours;
        this.sleepQuality = sleepQuality;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.amPm = amPm;
    }

    public String getDateString() {
        return month + "/" + day + "/" + year;
    }
    public String getTimeString() {
        return hour + ":" + minute;
    }
    public String getDateAndTimeString() {
        return getDateString() + " at " + getTimeString() + " " + amPm;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getAmPm() {
        return amPm;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(double sleepHours) {
        this.sleepHours = sleepHours;
    }

    public double getExerciseHours() {
        return exerciseHours;
    }

    public void setExerciseHours(double exerciseHours) {
        this.exerciseHours = exerciseHours;
    }

    public int getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(int sleepQuality) {
        this.sleepQuality = sleepQuality;
    }
}

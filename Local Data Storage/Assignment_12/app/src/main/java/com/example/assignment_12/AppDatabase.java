package com.example.assignment_12;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Entry.class}, version = 1) // update version
public abstract class AppDatabase extends RoomDatabase {
    public abstract EntryDao entryDao();
}

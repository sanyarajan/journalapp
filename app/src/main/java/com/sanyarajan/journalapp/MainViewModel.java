package com.sanyarajan.journalapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;


import com.sanyarajan.journalapp.database.JournalDatabase;
import com.sanyarajan.journalapp.database.JournalEntry;

import java.util.List;

class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private final LiveData<List<JournalEntry>> journalEntries;

    public MainViewModel(Application application) {
        super(application);
        JournalDatabase database = JournalDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the journal entries from the DataBase");
        journalEntries = database.journalDao().loadAllJournalEntries();
    }

    public LiveData<List<JournalEntry>> getJournalEntries() {
        return journalEntries;
    }
}

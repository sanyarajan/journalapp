package com.sanyarajan.journalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sanyarajan.journalapp.database.JournalDatabase;
import com.sanyarajan.journalapp.database.JournalEntry;


class AddJournalEntryViewModel extends ViewModel {


    private final LiveData<JournalEntry> journalEntry;


    public AddJournalEntryViewModel(JournalDatabase database, int journalEntryId) {
        journalEntry = database.journalDao().loadJournalEntryById(journalEntryId);
    }

      public LiveData<JournalEntry> getJournalEntry() {
        return journalEntry;
    }
}

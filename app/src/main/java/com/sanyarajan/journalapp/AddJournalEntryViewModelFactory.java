package com.sanyarajan.journalapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sanyarajan.journalapp.database.JournalDatabase;


class AddJournalEntryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final JournalDatabase mDb;
    private final int mJournalEntryId;


    public AddJournalEntryViewModelFactory(JournalDatabase database, int taskId) {
        mDb = database;
        mJournalEntryId = taskId;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return modelClass.cast(new AddJournalEntryViewModel(mDb, mJournalEntryId));
    }
}

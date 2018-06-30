package com.sanyarajan.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sanyarajan.journalapp.database.JournalDatabase;
import com.sanyarajan.journalapp.database.JournalEntry;

import java.util.Date;

public class AddJournalEntryActivity extends AppCompatActivity {

    // Extra for the journal entry ID to be received in the intent
    public static final String EXTRA_JOURNAL_ENTRY_ID = "extraJournalEntryId";
    // Extra for the journal entry ID to be received after rotation
    private static final String INSTANCE_JOURNAL_ENTRY_ID = "instanceJournalEntryId";

    // Constant for default journal entry id to be used when not in update mode
    private static final int DEFAULT_JOURNAL_ENTRY_ID = -1;
    // Constant for logging
    private static final String TAG = AddJournalEntryActivity.class.getSimpleName();

    // Fields for views
    private EditText mJournalEntryTitleText;
    private EditText mJournalEntryText;

    private Button mButton;

    private int mJournalEntryId = DEFAULT_JOURNAL_ENTRY_ID;

    // Member variable for the Database
    private JournalDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal_entry);

        initViews();

        mDb = JournalDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_JOURNAL_ENTRY_ID)) {
            mJournalEntryId = savedInstanceState.getInt(INSTANCE_JOURNAL_ENTRY_ID, DEFAULT_JOURNAL_ENTRY_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_JOURNAL_ENTRY_ID)) {
            mButton.setText(R.string.update_button);
            if (mJournalEntryId == DEFAULT_JOURNAL_ENTRY_ID) {
                // populate the UI
                mJournalEntryId = intent.getIntExtra(EXTRA_JOURNAL_ENTRY_ID, DEFAULT_JOURNAL_ENTRY_ID);


                AddJournalEntryViewModelFactory factory = new AddJournalEntryViewModelFactory(mDb, mJournalEntryId);

                final AddJournalEntryViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddJournalEntryViewModel.class);


                viewModel.getJournalEntry().observe(this, new Observer<JournalEntry>() {
                    @Override
                    public void onChanged(@Nullable JournalEntry journalEntry) {
                        viewModel.getJournalEntry().removeObserver(this);
                        populateUI(journalEntry);
                    }
                });
            }
        }
    }

    private void populateUI(JournalEntry journalEntry) {
        if (journalEntry == null) {
            return;
        }

        mJournalEntryTitleText.setText(journalEntry.getJournalEntryTitle());
        mJournalEntryText.setText(journalEntry.getJournalEntryText());
    }

    private void initViews() {
        mJournalEntryTitleText = findViewById(R.id.editTextJournalEntryTitle);
        mJournalEntryText = findViewById(R.id.editTextJournalEntryText);

        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    private void onSaveButtonClicked() {
        String title = mJournalEntryTitleText.getText().toString();
        String text = mJournalEntryText.getText().toString();

        final Date date = new Date();

        final JournalEntry journalEntry = new JournalEntry(title, text, date, null);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mJournalEntryId == DEFAULT_JOURNAL_ENTRY_ID) {
                    // insert new task
                    mDb.journalDao().insertJournalEntry(journalEntry);
                } else {
                    //update task
                    journalEntry.setId(mJournalEntryId);
                    journalEntry.setUpdatedAt(date);
                    mDb.journalDao().updateJournalEntry(journalEntry);
                }
                finish();
            }
        });
    }
}

/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.sanyarajan.journalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sanyarajan.journalapp.database.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds journal data and the Context
    private List<JournalEntry> mJournalEntries;
    private final Context mContext;
    // Date formatter
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the JournalAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public JournalAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new JournalViewHolder that holds the view for each journal entry
     */
    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the journal_list_item to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.journal_list_item, parent, false);

        return new JournalViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        // Determine the values of the wanted data
        JournalEntry journalEntry = mJournalEntries.get(position);
        String title = journalEntry.getJournalEntryTitle();
        String createdAt = dateFormat.format(journalEntry.getCreatedAt());

        holder.journalTitleTextView.setText(title);
        holder.createdAtTextView.setText(createdAt);

        if(journalEntry.getUpdatedAt() == null){
            holder.updatedDateGroup.setVisibility(View.INVISIBLE);
        }
        else {
            String updatedAt = dateFormat.format(journalEntry.getUpdatedAt());
            holder.updatedAtTextView.setText(updatedAt);
            holder.updatedDateGroup.setVisibility(View.VISIBLE);
        }

    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mJournalEntries == null) {
            return 0;
        }
        return mJournalEntries.size();
    }

    public List<JournalEntry> getJournalEntries() {
        return mJournalEntries;
    }

    /**
     * When data changes, this method updates the list of journalEntries
     * and notifies the adapter to use the new values on it
     */
    public void setJournalEntries(List<JournalEntry> journalEntries) {
        mJournalEntries = journalEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        final TextView journalTitleTextView;
        final TextView updatedAtTextView;
        final TextView createdAtTextView;
        final Group updatedDateGroup;


        /**
         * Constructor for the JournalViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        JournalViewHolder(View itemView) {
            super(itemView);

            journalTitleTextView = itemView.findViewById(R.id.journal_entry_title);
            updatedAtTextView = itemView.findViewById(R.id.journal_entry_update_date);
            createdAtTextView = itemView.findViewById(R.id.journal_entry_creation_date);
            updatedDateGroup = itemView.findViewById(R.id.update_date_group);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mJournalEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
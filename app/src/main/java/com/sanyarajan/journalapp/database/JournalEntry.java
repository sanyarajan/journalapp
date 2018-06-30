package com.sanyarajan.journalapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "journal")
public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String journalEntryTitle;
    private String journalEntryText;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @Ignore
    public JournalEntry(String journalEntryTitle, String journalEntryText, Date createdAt, Date updatedAt) {
        this.journalEntryTitle = journalEntryTitle;
        this.journalEntryText = journalEntryText;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public JournalEntry(int id, String journalEntryTitle,String journalEntryText, Date createdAt, Date updatedAt) {
        this.id = id;
        this.journalEntryTitle = journalEntryTitle;
        this.journalEntryText = journalEntryText;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJournalEntryText() {
        return journalEntryText;
    }

    public void setJournalEntryText(String journalEntryText) {
        this.journalEntryText = journalEntryText;
    }



    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getJournalEntryTitle() {
        return journalEntryTitle;
    }

    public void setJournalEntryTitle(String journalEntryTitle) {
        this.journalEntryTitle = journalEntryTitle;
    }
}

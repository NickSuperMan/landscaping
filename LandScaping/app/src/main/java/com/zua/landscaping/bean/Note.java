package com.zua.landscaping.bean;

import java.util.Date;

/**
 * Created by roy on 4/26/16.
 */
public class Note {

    private int noteId;
    private int userId;
    private String noteTitle;
    private String noteContet;
    private Date noteTime;

    public String getNoteContet() {
        return noteContet;
    }

    public void setNoteContet(String noteContet) {
        this.noteContet = noteContet;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public Date getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(Date noteTime) {
        this.noteTime = noteTime;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteContet='" + noteContet + '\'' +
                ", noteId=" + noteId +
                ", userId=" + userId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteTime=" + noteTime +
                '}';
    }
}

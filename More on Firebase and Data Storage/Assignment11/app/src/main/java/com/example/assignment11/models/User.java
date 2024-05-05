package com.example.assignment11.models;

import java.util.ArrayList;

public class User {
    public String name, userId, docId;
    public ArrayList<String> blocked = new ArrayList<>();
    public ArrayList<String> blockedBy = new ArrayList<>();

    public User() {
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public User(String name, String userId, String docId, ArrayList<String> blocked, ArrayList<String> blockedBy) {
        this.name = name;
        this.userId = userId;
        this.docId = docId;
        this.blocked = blocked;
    }

    public ArrayList<String> getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(ArrayList<String> blockedBy) {
        this.blockedBy = blockedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getBlocked() {
        return blocked;
    }

    public void setBlocked(ArrayList<String> blocked) {
        this.blocked = blocked;
    }
    @Override
    public String toString() {
        return this.name;
    }
}

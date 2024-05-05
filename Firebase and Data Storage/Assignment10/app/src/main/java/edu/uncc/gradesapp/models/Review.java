package edu.uncc.gradesapp.models;

import com.google.firebase.Timestamp;

public class Review {
    public String review, ownerId, name, docId;
    public Timestamp createdAt;

    public Review() {
    }

    public Review(String review, String ownerId, String course_name, Timestamp createdAt, String docId) {
        this.review = review;
        this.ownerId = ownerId;
        this.name = course_name;
        this.createdAt = createdAt;
        this.docId = docId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner_id() {
        return ownerId;
    }

    public void setOwner_id(String owner_id) {
        this.ownerId = owner_id;
    }


    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}

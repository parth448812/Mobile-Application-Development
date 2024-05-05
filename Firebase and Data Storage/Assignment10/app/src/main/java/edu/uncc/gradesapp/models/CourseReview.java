package edu.uncc.gradesapp.models;

import java.util.ArrayList;

public class CourseReview {
    public ArrayList<String> likes;


    public int reviews;
    public String course_number, docId, ownerId;

    public CourseReview() {
    }

    public CourseReview(ArrayList<String> likes, int reviews, String course_number, String docId) {
        this.likes = likes;
        this.reviews = reviews;
        this.course_number = course_number;
        this.docId = docId;

    }

    public CourseReview(String course_number) {
        this.course_number = course_number;
    }

    public int getReviews() {
        return reviews;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public String getCourse_number() {
        return course_number;
    }

    public void setCourse_number(String course_number) {
        this.course_number = course_number;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }
}

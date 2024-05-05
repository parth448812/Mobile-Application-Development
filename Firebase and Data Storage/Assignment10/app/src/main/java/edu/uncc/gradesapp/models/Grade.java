package edu.uncc.gradesapp.models;

public class Grade {
    public double hours;
    public String letterGrade, name, number, owner_id, docId, course_name;

    public Grade() {
    }

    public Grade(String number, String letterGrade, String name, String course_name, double hours) {
        this.name = name;
        this.number = number;
        this.hours = hours;
        this.letterGrade = letterGrade;
        this.owner_id = owner_id;
        this.course_name = course_name;

    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }


    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "hours=" + hours +
                ", letterGrade='" + letterGrade + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", owner_id='" + owner_id + '\'' +
                '}';
    }
}
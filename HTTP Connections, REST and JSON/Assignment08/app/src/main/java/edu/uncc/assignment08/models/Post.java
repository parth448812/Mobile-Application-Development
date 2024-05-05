package edu.uncc.assignment08.models;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Post implements Serializable {
    public String created_by_name, post_text,created_at;
    public int post_id, created_by_uid;

    public Post() {
    }
    public Post(JSONObject jsonObject) throws JSONException {

        this.post_id = jsonObject.getInt("post_id");
        this.post_text = jsonObject.getString("post_text");
        this.created_at = jsonObject.getString("created_at");

        this.created_by_uid = jsonObject.getInt("created_by_uid");
        this.created_by_name = jsonObject.getString("created_by_name");

    }
    public String getCreated_by_name() {
        return created_by_name;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getCreated_by_uid() {
        return created_by_uid;
    }

    public void setCreated_by_uid(int created_by_uid) {
        this.created_by_uid = created_by_uid;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_by_name(String created_by_name) {
        this.created_by_name = created_by_name;
    }

    @Override
    public String toString() {
        return "Post{" +
                "created_by_name='" + created_by_name + '\'' +
                ", post_id='" + post_id + '\'' +
                ", created_by_uid='" + created_by_uid + '\'' +
                ", post_text='" + post_text + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}

package edu.uncc.assignment08.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class AuthResponse implements Serializable {
    String status, user_fullname, token;
    int user_id;
    /*

    {
    "status": "ok",
    "token": "wcUJRckNsODlsbkgyZUJS",
    "user_id": 1,
    "user_fullname": "Alice Smith"
}
     */
    public AuthResponse() {
    }

    public AuthResponse(JSONObject jsonObject) throws JSONException {

        this.status = jsonObject.getString("status");
        this.token = jsonObject.getString("token");
        this.user_id = jsonObject.getInt("user_id");
        this.user_fullname = jsonObject.getString("user_fullname");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

}
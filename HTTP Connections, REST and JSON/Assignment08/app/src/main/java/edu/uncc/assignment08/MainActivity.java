package edu.uncc.assignment08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uncc.assignment08.models.AuthResponse;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        SignUpFragment.SignUpListener, PostsFragment.PostsListener, CreatePostFragment.CreatePostListener {
    AuthResponse mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sharedPref.contains("auth")){
            String authStr = sharedPref.getString("auth", null);
            if(authStr == null){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.containerView, new LoginFragment())
                        .commit();
            } else {
                //edit so there is no need to use Gson library, but only Native JSON parsing (JSONObject and JSONArray)
//                Gson gson = new Gson();
//                mAuth = gson.fromJson(authStr, AuthResponse.class);
                try {
                    JSONObject authJson = new JSONObject(authStr);
                    mAuth = new AuthResponse();
                    mAuth.setToken(authJson.optString("token"));
                    //mAuth.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTAyNjgxMTcsImV4cCI6MTc0MTgwNDExNywianRpIjoiNVFYb3o2amxKME1aVkJ2TFFDSE9xbSIsInVzZXIiOjF9.3eD7fq0xWavUfIDsOJmU4RFgamvBOOEX0WTCVpdByY4");
                    mAuth.setUser_id(authJson.optInt("userId"));
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.containerView, PostsFragment.newInstance(mAuth))
                            .commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerView, new LoginFragment())
                    .commit();
        }

    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SignUpFragment())
                .commit();
    }

    @Override
    public void authCompleted(AuthResponse authResponse) {
        this.mAuth = authResponse;

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        JSONObject authJson = new JSONObject();

        try {
            authJson.put("token", authResponse.getToken());
            authJson.put("user_id", authResponse.getUser_id());
            editor.putString("auth", authJson.toString());
            editor.apply();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, PostsFragment.newInstance(authResponse))
                    .commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void login() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void logout() {
        mAuth = null;

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("auth");
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void createPost() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, CreatePostFragment.NewInstance(mAuth))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBackToPosts() {
        getSupportFragmentManager().popBackStack();
    }
}
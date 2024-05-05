package edu.uncc.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;

import edu.uncc.assignment08.databinding.FragmentCreatePostBinding;
import edu.uncc.assignment08.models.AuthResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreatePostFragment extends Fragment {
    public CreatePostFragment() {
        // Required empty public constructor
    }

    private static final String ARG_PARAM_AUTH = "ARG_PARAM_AUTH";
    AuthResponse mAuth;
    public static Fragment NewInstance(AuthResponse mAuth) {
        CreatePostFragment fragment = new CreatePostFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_AUTH, mAuth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAuth = (AuthResponse) getArguments().getSerializable(ARG_PARAM_AUTH);
        }
    }


    FragmentCreatePostBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBackToPosts();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postText = binding.editTextPostText.getText().toString();
                if(postText.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid post !!", Toast.LENGTH_SHORT).show();
                } else {
                    RequestBody formBody = new FormBody.Builder()
                            .add("post_text", postText)
                            .build();
                    Request request = new Request.Builder()
                            .url("https://www.theappsdr.com/posts/create")
                            .addHeader("Authorization", "BEARER "  + mAuth.getToken())
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mListener.goBackToPosts();
                                    }
                                });
                            } else {
                                Log.d("demo", "onResponse: error ");
                            }
                        }
                    });
                }
            }
        });


    getActivity().setTitle(R.string.create_post_label);
    }

    CreatePostListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreatePostListener) context;
    }

    interface CreatePostListener {
        void goBackToPosts();
    }
}
package edu.uncc.assignment08;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.assignment08.databinding.FragmentPostsBinding;
import edu.uncc.assignment08.databinding.PostRowItemBinding;
import edu.uncc.assignment08.models.AuthResponse;
import edu.uncc.assignment08.models.Post;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostsFragment extends Fragment {
    public PostsFragment() {
        // Required empty public constructor
    }

    FragmentPostsBinding binding;
    PostsAdapter postsAdapter;
    ArrayList<Post> mPosts = new ArrayList<>();
    int pageNum = 1;
    int pageSize = 2;
    private static final String ARG_PARAM_AUTH = "ARG_PARAM_AUTH";
    AuthResponse mAuth;

    public static Fragment newInstance(AuthResponse authResponse) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_AUTH, authResponse);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPosts();

        binding.buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createPost();
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });

        binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        postsAdapter = new PostsAdapter();
        binding.recyclerViewPosts.setAdapter(postsAdapter);

        binding.textViewPaging.setText("Showing Page "+pageNum+" out of "+pageSize);
        binding.textViewTitle.setText(mAuth.getUser_fullname());

        binding.imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageNum != 1){
                    pageNum --;
                    getPosts();
                    binding.textViewPaging.setText("Showing Page "+pageNum+" "+" out of "+pageSize);
                }

            }
        });

        binding.imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageNum != pageSize){
                    pageNum ++;
                    getPosts();
                    binding.textViewPaging.setText("Showing Page "+pageNum+" "+" out of "+pageSize);
                }
            }
        });

        getActivity().setTitle(R.string.posts_label);
    }

    private final OkHttpClient client = new OkHttpClient();
    void getPosts() {
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts?page="+pageNum)
                .addHeader("Authorization", "BEARER "  + mAuth.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()){

                    String body = response.body().string();
                    try {
                        mPosts.clear();
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("posts");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject postJsonObject = jsonArray.getJSONObject(i);
                            Post post = new Post(postJsonObject);
                            mPosts.add(post);
                        }
                        // = jsonObject.getInt("pageSize"); // total 18, but only 2 full


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                postsAdapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {

                }
            }
        });
    }
    class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
        @NonNull
        @Override
        public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PostRowItemBinding binding = PostRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new PostsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
            Post post = mPosts.get(position);
            holder.setupUI(post);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        class PostsViewHolder extends RecyclerView.ViewHolder {
            PostRowItemBinding mBinding;
            Post mPost;
            public PostsViewHolder(PostRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Post post){
                mPost = post;
                mBinding.textViewPost.setText(post.getPost_text());
                mBinding.textViewCreatedBy.setText(post.getCreated_by_name());
                mBinding.textViewCreatedAt.setText(post.getCreated_at());

                if(mPost.getCreated_by_uid() == (mAuth.getUser_id())){
                    mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //code to delete the forum..
                            String url = "https://www.theappsdr.com/posts/delete";
                            RequestBody formBody = new FormBody.Builder()
                                    .add("post_id", mPost.getPost_id()+"")
                                    .build();
                            Request request = new Request.Builder()
                                    .url(url)
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
                                                getPosts();
                                            }
                                        });

                                    } else {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), "Error deleting post !!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });

                        }
                    });
                } else {
                    mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }

            }
        }
    }


    PostsListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (PostsListener) context;
    }

    interface PostsListener{
        void logout();
        void createPost();
    }
}
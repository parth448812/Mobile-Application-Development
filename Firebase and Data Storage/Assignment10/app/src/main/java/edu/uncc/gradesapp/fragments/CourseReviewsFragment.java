package edu.uncc.gradesapp.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.CourseReviewRowItemBinding;
import edu.uncc.gradesapp.databinding.FragmentCourseReviewsBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.CourseReview;
import edu.uncc.gradesapp.models.Grade;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CourseReviewsFragment extends Fragment {
    public CourseReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    FragmentCourseReviewsBinding binding;
    ArrayList<CourseReview> mCourseReviews = new ArrayList<>();
    ArrayList<Course> mCourses = new ArrayList<>();
    ListenerRegistration listenerRegistration;

    CourseReviewsAdapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Course Reviews");
        getCourses();

        adapter = new CourseReviewsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //DocumentReference docRef = db.collection("grades").document();
        listenerRegistration = db.collection("courses").addSnapshotListener((value, error) -> {
            if(error != null){
                Toast.makeText(getActivity(), "Failed to get courses", Toast.LENGTH_SHORT).show();
            } else {
                mCourseReviews.clear();
                for (QueryDocumentSnapshot doc : value) {
                    CourseReview courseReview = doc.toObject(CourseReview.class);
                    mCourseReviews.add(courseReview);
                }

                //adapter.notifyDataSetChanged();
            }
        });
    }

    private final OkHttpClient client = new OkHttpClient();

    private void getCourses(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cci-courses")
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
                        //ArrayList<Course> tempCourses = new ArrayList<>();
                        mCourses.clear();
                        JSONObject json = new JSONObject(body);
                        JSONArray courses = json.getJSONArray("courses");
                        for (int i = 0; i < courses.length(); i++) {
                            Course course = new Course(courses.getJSONObject(i));
                            mCourses.add(course);
                            //tempCourses.add(course);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //mCourses.addAll(tempCourses);

                                adapter.notifyDataSetChanged();



                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Failed to get courses", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration != null){
            listenerRegistration.remove();
            mCourses.clear(); // Fixes issue that occurs with Pixel Emulator
        }
    }
    class CourseReviewsAdapter extends RecyclerView.Adapter<CourseReviewsAdapter.CourseReviewsViewHolder> {

        @NonNull
        @Override
        public CourseReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CourseReviewRowItemBinding itemBinding = CourseReviewRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new CourseReviewsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseReviewsViewHolder holder, int position) {
//            if (!mCourses.isEmpty()) {
//                holder.setupUI(mCourses.get(position));
//            }
            holder.setupUI(mCourses.get(position));
//            else{
//                getCourses();
//                // the call is asynchronous, so we need to wait for it to finish before we can call setupUI
//                holder.setupUI(mCourses.get(position));
//
//
////                ExecutorService executor = Executors.newSingleThreadExecutor();
////                Future<?> future = executor.submit(() -> getCourses());
////                try {
////                    future.get(); // This will block until getCourses() is finished
////                    holder.setupUI(mCourses.get(position));
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////                executor.shutdown();
//            }


        }

        @Override
        public int getItemCount() {
            return mCourses.size();
        }

        class CourseReviewsViewHolder extends RecyclerView.ViewHolder {
            CourseReviewRowItemBinding itemBinding;
            Course mCourse;
            CourseReview mCourseReview;
            public CourseReviewsViewHolder(CourseReviewRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
                this.itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoReviewCourse(mCourse);
                    }
                });
            }

            public void setupUI(Course course){
                FirebaseAuth auth = FirebaseAuth.getInstance();

                this.mCourse = course;
                //this.mCourseReview = new CourseReview(course.getNumber()); // testing theory
                //mCourseReviews.add(mCourseReview); //testing theory
                itemBinding.textViewCourseName.setText(course.getName());
                itemBinding.textViewCreditHours.setText(course.getHours() + " Credit Hours");
                itemBinding.textViewCourseNumber.setText(course.getNumber());
                for (CourseReview mCourseReview : mCourseReviews) {
                    if (mCourseReview.getCourse_number().equals(course.getNumber())) {
                        this.mCourseReview = mCourseReview;
                        //itemBinding.textViewCourseReviews.setText(mCourseReview.getReviews() + " Reviews");
                        itemBinding.textViewCourseReviews.setText(mCourseReview.getReviews() + " Reviews");
                        if(mCourseReview.getLikes() != null && mCourseReview.getLikes().size() > 0){

                            if(mCourseReview.getLikes().contains(auth.getCurrentUser().getUid())){
                                itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_full);
                            } else {
                                itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_empty);
                            }
                        } else {
                            itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_empty);
                        }

                        itemBinding.imageViewHeart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String, Object> updateData = new HashMap<>();
                                if(mCourseReview.getLikes() != null){
                                    if(mCourseReview.getLikes().contains(auth.getCurrentUser().getUid())){
                                        //unlike the current user .. remove uid from the array ..

                                        updateData.put("likes", FieldValue.arrayRemove(auth.getCurrentUser().getUid()));
                                        //FirebaseFirestore.getInstance().collection("courses").document(mCourseReview.getDocId()).update(updateData);
                                        //adapter.notifyDataSetChanged();
                                    } else {
                                        //like .. add the uid to the array
                                        //HashMap<String, Object> updateData = new HashMap<>();
                                        updateData.put("likes", FieldValue.arrayUnion(auth.getCurrentUser().getUid()));
                                        //FirebaseFirestore.getInstance().collection("courses").document(mCourseReview.getDocId()).update(updateData);
                                        //.notifyDataSetChanged();
                                    }
                                } else {
                                    //like .. add the uid to the array
                                    //HashMap<String, Object> updateData = new HashMap<>();
                                    updateData.put("likes", FieldValue.arrayUnion(auth.getCurrentUser().getUid()));
                                    //FirebaseFirestore.getInstance().collection("courses").document(mCourseReview.getDocId()).update(updateData);
                                    //adapter.notifyDataSetChanged();
                                }

                                FirebaseFirestore.getInstance().collection("courses").document(mCourseReview.getDocId()).update(updateData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                adapter.notifyDataSetChanged();
                                            }
                                        });



                            }
                        });




                        break;
                    }
                }







            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_cancel){
            mListener.onSelectionCanceled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    CourseReviewsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CourseReviewsListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement CourseReviewsListener");
        }
    }

    public interface CourseReviewsListener{
        void onSelectionCanceled();
        void gotoReviewCourse(Course course);
    }
}
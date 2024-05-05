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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentReviewCourseBinding;
import edu.uncc.gradesapp.databinding.ReviewRowItemBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.CourseReview;
import edu.uncc.gradesapp.models.Review;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReviewCourseFragment extends Fragment {
    private static final String ARG_PARAM_COURSE= "ARG_PARAM_COURSE";
    private Course mCourse;
    //private CourseReview mCourseReview;
    ArrayList<Review> mReviews = new ArrayList<>();
    ReviewsAdapter adapter;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String docId;
    ListenerRegistration listenerRegistration;
    Long reviews;
    long reviewsValue;
    int position;


    public ReviewCourseFragment() {
        // Required empty public constructor
    }

    public static ReviewCourseFragment newInstance(Course course) {
        ReviewCourseFragment fragment = new ReviewCourseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_COURSE, course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourse = (Course) getArguments().getSerializable(ARG_PARAM_COURSE);
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            //DocumentReference docRef = db.collection("courses").document(docId);


            db.collection("courses")
                    .whereEqualTo("course_number", mCourse.getNumber()) // replace with your course number
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    docId = document.getId(); // get the document ID
                                    Log.d(TAG, "Document ID: " + docId);


                                    DocumentReference docRef = db.collection("courses").document(docId);

                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    reviews = document.getLong("reviews");
                                                    if (reviews != null) {
                                                        reviewsValue = reviews.longValue();
                                                        // Continue with your code...
                                                    } else {
                                                        Log.d(TAG, "Reviews field is null");
                                                    }
                                                } else {
                                                    Log.d(TAG, "No such document");
                                                }
                                            } else {
                                                Log.d(TAG, "get failed with ", task.getException());
                                            }
                                        }
                                    });


                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    //DocumentReference docRef = db.collection("grades").document();
                                    listenerRegistration = docRef.collection("review").addSnapshotListener((value, error) -> {
                                        if(error != null){ //.orderby timestamp
                                            Toast.makeText(getActivity(), "Failed to get courses", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mReviews.clear();
                                            for (QueryDocumentSnapshot doc : value) {
                                                Review review = doc.toObject(Review.class);
                                                mReviews.add(review);
                                            }

                                            adapter.notifyDataSetChanged();
                                        }
                                    });



                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        setHasOptionsMenu(true);
    }

    FragmentReviewCourseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Review Course");

        binding.textViewCourseName.setText(mCourse.getName());
        binding.textViewCourseNumber.setText(mCourse.getNumber());
        binding.textViewCreditHours.setText(String.valueOf(mCourse.getHours()) + " Credit Hours");

        adapter = new ReviewsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewText = binding.editTextReview.getText().toString();
                if(reviewText.isEmpty()){
                    Toast.makeText(getActivity(), "Review cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseAuth auth = FirebaseAuth.getInstance();

                    //FirebaseFirestore reviewupdate = FirebaseFirestore.getInstance().collection("courses").document(docId);
                    HashMap<String, Object> updateData = new HashMap<>();
                    updateData.put("reviews", FieldValue.increment(1));
                    FirebaseFirestore.getInstance().collection("courses").document(docId).update(updateData);

                    DocumentReference docRef = db
                            .collection("courses")
                            .document(docId)
                            .collection("review")
                            .document();

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("review", reviewText);
                    data.put("ownerId", auth.getCurrentUser().getUid());
                    data.put("name", auth.getCurrentUser().getDisplayName());
                    data.put("createdAt", FieldValue.serverTimestamp());
                    data.put("docId", docRef.getId());

                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                binding.editTextReview.setText("");
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
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
        }
    }
    class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
        @NonNull
        @Override
        public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ReviewRowItemBinding itemBinding = ReviewRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ReviewViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
            if(!mReviews.isEmpty()) {
                Review review = mReviews.get(position);
                holder.setupUI(review);
            }

//            Review review = mReviews.get(position);
//            holder.setupUI(review);
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }

        class ReviewViewHolder extends RecyclerView.ViewHolder {
            ReviewRowItemBinding itemBinding;
            Review mReview;
            public ReviewViewHolder(ReviewRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }


            private void setupUI(Review review){
                this.mReview = review;
                itemBinding.textViewReview.setText(mReview.getReview());
                itemBinding.textViewUserName.setText(mReview.getName());

                position = getAdapterPosition();

                if(mReview.getCreatedAt() != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    itemBinding.textViewCreatedAt.setText(sdf.format(mReview.getCreatedAt().toDate()));
                } else {
                    itemBinding.textViewCreatedAt.setText("");
                }

                if(mReview.getOwner_id().equals(auth.getCurrentUser().getUid())){
                    itemBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            HashMap<String, Object> updateData = new HashMap<>();
                            updateData.put("reviews", reviewsValue - 1);// INCREMENT -1
                            FirebaseFirestore.getInstance().collection("courses").document(docId).update(updateData);

                            db.collection("courses")
                                    .document(docId)
                                    .collection("review")
                                    .document(mReview.getDocId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Remove the item from your data set
                                            int position1 = getAdapterPosition();
                                            mReviews.remove(position1);
                                            //adapter.notifyDataSetChanged();
                                            // Notify the adapter that an item has been removed
                                            notifyItemRemoved(position1);

                                            position = getAdapterPosition();
                                        }
                                    });


                        }
                    });
                } else {
                    itemBinding.imageViewDelete.setVisibility(View.INVISIBLE);
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

            //int position = getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION && position < mReviews.size()) {
//                // Perform the cancel operation
//                mListener.onSelectionCanceled();
//            }


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ReviewCourseListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (ReviewCourseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ReviewCourseListener");
        }
    }

    public interface ReviewCourseListener{
        void onSelectionCanceled();
    }
}
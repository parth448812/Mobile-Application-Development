package edu.uncc.gradesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentMyGradesBinding;
import edu.uncc.gradesapp.databinding.GradeRowItemBinding;
import edu.uncc.gradesapp.models.Grade;

public class MyGradesFragment extends Fragment {
    public MyGradesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_add){
            mListener.gotoAddGrade();
            return true;
        } else if(item.getItemId() == R.id.action_logout) {
            mListener.logout();
            return true;
        } else if(item.getItemId() == R.id.action_reviews){
            mListener.gotoViewReviews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    FragmentMyGradesBinding binding;
    GradeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<Grade> mGrades = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Grades");
        adapter = new GradeAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        calculateGPA();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DocumentReference docRef = db.collection("grades").document(auth.getCurrentUser().getUid());
//        listenerRegistration = db.collection("grades")
//                .whereEqualTo("owner_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if(error != null){
//                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            mGrades.clear();
//                            for (QueryDocumentSnapshot doc : value) {
//                                Grade grade = doc.toObject(Grade.class);
//                                mGrades.add(grade);
//                            }
//                            adapter.notifyDataSetChanged();
//                            calculateGPA();
//                        }
//                    }
//                });
        listenerRegistration = docRef.collection("myGrades")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            mGrades.clear();
                            for (QueryDocumentSnapshot doc : value) {
                                Grade grade = doc.toObject(Grade.class);
                                mGrades.add(grade);
                            }
                            adapter.notifyDataSetChanged();
                            calculateGPA();
                        }
                    }
                });
    }

    private void calculateGPA(){
        if(mGrades.size() == 0){
            binding.textViewGPA.setText("GPA: 4.0");
            binding.textViewHours.setText("Hours: 0.0");
        } else {
            double acc = 0.0;
            double hours = 0.0;
            for (Grade grade: mGrades) {
                double letterGrade = 0.0;
                if(grade.getLetterGrade().equals("A")){
                    letterGrade = 4.0;
                } else if(grade.getLetterGrade().equals("B")){
                    letterGrade = 3.0;
                } else if(grade.getLetterGrade().equals("C")){
                    letterGrade = 2.0;
                } else if(grade.getLetterGrade().equals("D")){
                    letterGrade = 1.0;
                }
                acc = acc + grade.getHours() * letterGrade;
                hours = hours + grade.getHours();
            }

            if(hours == 0) {
                binding.textViewGPA.setText("GPA: 4.0");
                binding.textViewHours.setText("Hours: 0.0");
            } else {
                acc = acc / hours;
                binding.textViewGPA.setText("GPA: " + String.format("%.2f", acc));
                binding.textViewHours.setText("Hours: " + String.format("%.2f", hours));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration != null){
            listenerRegistration.remove();
        }
    }

    class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder>{
        @NonNull
        @Override
        public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GradeRowItemBinding rowBinding = GradeRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new GradeViewHolder(rowBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
            Grade grade = mGrades.get(position);
            holder.setupUI(grade);
        }

        @Override
        public int getItemCount() {
            return mGrades.size();
        }

        class GradeViewHolder extends RecyclerView.ViewHolder{
            GradeRowItemBinding mBinding;
            Grade mGrade;
            public GradeViewHolder(@NonNull GradeRowItemBinding rowBinding) {
                super(rowBinding.getRoot());
                this.mBinding = rowBinding;
            }
            public void setupUI(Grade grade){
                this.mGrade = grade;
                mBinding.textViewCreditHours.setText(mGrade.getHours() + " Credit Hours");
                mBinding.textViewCourseName.setText(mGrade.getCourse_name());
                mBinding.textViewLetterGrade.setText(mGrade.getLetterGrade());
                mBinding.textViewCourseNumber.setText(mGrade.getNumber());
                mBinding.textViewSemester.setText(mGrade.getName());
                FirebaseAuth auth = FirebaseAuth.getInstance(); //potential issue cause im doing user id not doc id of the thing
                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseFirestore.getInstance().collection("grades").document(auth.getCurrentUser().getUid()).collection("myGrades")
                                .document(mGrade.getDocId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override // addoncomplete listener, why not snapshot
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                    }
                });
            }
        }
    }

    MyGradesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (MyGradesListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement MyGradesListener");
        }
    }

    public interface MyGradesListener {
        void gotoAddGrade();
        void logout();
        void gotoViewReviews();
    }
}
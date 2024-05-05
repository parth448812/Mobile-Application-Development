
package edu.uncc.gradesapp.fragments;

import static android.content.ContentValues.TAG;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentAddGradeBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.Grade;
import edu.uncc.gradesapp.models.LetterGrade;
import edu.uncc.gradesapp.models.Semester;

public class AddGradeFragment extends Fragment {
    public AddGradeFragment() {
        // Required empty public constructor
    }

    FragmentAddGradeBinding binding;
    public LetterGrade selectedLetterGrade;
    public Semester selectedSemester;
    public Course selectedCourse;

    String number, letterGrade, name, course_name;
    double hours;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddGradeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Grade");

        if(selectedCourse != null){
            binding.textViewCourse.setText(selectedCourse.getNumber());
            course_name = selectedCourse.getName();
            hours = selectedCourse.getHours();
        }

        if(selectedLetterGrade != null){
            binding.textViewGrade.setText(selectedLetterGrade.getLetterGrade());
        }

        if(selectedSemester != null){
            binding.textViewSemester.setText(selectedSemester.getName());
        }

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelAddGrade();
            }
        });

        binding.buttonSelectCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCourse();
            }
        });

        binding.buttonSelectLetterGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectLetterGrade();
            }
        });

        binding.buttonSelectSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectSemester();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do the usual if else statements to verify no empty edittext with toast, then do the code to add to the firebase db
                number = binding.textViewCourse.getText().toString();
                letterGrade = binding.textViewGrade.getText().toString();
                name = binding.textViewSemester.getText().toString();

                if(number.isEmpty()){
                    Toast.makeText(getActivity(), "Select Course !!", Toast.LENGTH_SHORT).show();
                } else if (letterGrade.isEmpty()){
                    Toast.makeText(getActivity(), "Select a Letter Grade !!", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()){
                    Toast.makeText(getActivity(), "Select a Semester !!", Toast.LENGTH_SHORT).show();
                } else {
                    //Grade grade = new Grade(number, letterGrade, name, course_name, hours);
                    // add it to the array list of Grades once you create it in myGrades.
                    // actually do not add it to the array list of grades cause you want to pull directly from the firebase db
                    // add it to the firebase db
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    DocumentReference docRef = db.collection("grades").document(auth.getCurrentUser().getUid());
                    DocumentReference newGradeRef = docRef.collection("myGrades").document();
                    // use course_id as document id
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("number", number);
                    data.put("letterGrade", letterGrade);
                    data.put("name", name);
                    data.put("course_name", course_name);
                    data.put("hours", hours);
                    data.put("ownerId", auth.getCurrentUser().getUid());
                    data.put("docId", newGradeRef.getId());

                    newGradeRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mListener.completedAddGrade();
                            } else {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }



    AddGradeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddGradeListener) {
            mListener = (AddGradeListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddGradeListener");
        }
    }

    public interface AddGradeListener {
        void cancelAddGrade();
        void completedAddGrade();
        void gotoSelectSemester();
        void gotoSelectCourse();
        void gotoSelectLetterGrade();
    }

}
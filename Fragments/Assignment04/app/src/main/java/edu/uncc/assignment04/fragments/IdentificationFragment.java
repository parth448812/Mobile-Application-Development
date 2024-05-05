package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.Response;
import edu.uncc.assignment04.databinding.FragmentIdentificationBinding;


public class IdentificationFragment extends Fragment {

    static Response response;

    public IdentificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentIdentificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentIdentificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Identification");

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                int selectedRole = binding.radioGroup.getCheckedRadioButtonId();

                String role = getString(R.string.student);
                if(selectedRole == R.id.radioButtonStudent){
                    role = getString(R.string.student);
                }
                else if(selectedRole == R.id.radioButtonEmployee){
                    role = getString(R.string.employee);
                }
                else if(selectedRole == R.id.radioButtonOther){
                    role = getString(R.string.other);
                }

                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter name!!", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Enter email!!", Toast.LENGTH_SHORT).show();
                } else {
                    response = new Response(name, email, role, null, null, null, null);
                    mListener.gotoDemographic(response);
                }

            }
        });

        getActivity().setTitle("Identification");
    }

    IdentificationFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (IdentificationFragmentListener) context;
    }

    public interface IdentificationFragmentListener{
        void gotoDemographic(Response response);
//        void gotoProfile(Response response); // is this needed here?
    }
}
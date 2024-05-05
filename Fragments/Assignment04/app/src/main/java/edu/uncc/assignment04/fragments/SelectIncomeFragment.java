package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectIncomeBinding;


public class SelectIncomeFragment extends Fragment {

    SeekBar seekBarIncome;
    TextView textViewHouseHoldIncome;

    public SelectIncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectIncomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectIncomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Income Status");
        // seekBarIncome = binding.findViewById(R.id.seekBarIncome);

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String income = binding.textViewHouseHoldIncome.getText().toString();
                mListener.sendIncome(income);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelIncome();
            }
        });
        binding.seekBarIncome.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                binding.textViewHouseHoldIncome.setText(getString(R.string.income_2));
                if (binding.seekBarIncome.getProgress() == 0){
                    binding.textViewHouseHoldIncome.setText(getString(R.string.income_0));
                } else if (binding.seekBarIncome.getProgress() == 1) {
                    binding.textViewHouseHoldIncome.setText(getString(R.string.income_1));
                } else if (binding.seekBarIncome.getProgress() == 2) {
                    binding.textViewHouseHoldIncome.setText(getString(R.string.income_2));
                } else if (binding.seekBarIncome.getProgress() == 3) {
                    binding.textViewHouseHoldIncome.setText(getString(R.string.income_3));
                } else if (binding.seekBarIncome.getProgress() == 4) {
                    binding.textViewHouseHoldIncome.setText(getString(R.string.income_4));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    SelectIncomeStatusFragmentListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectIncomeStatusFragmentListener) context;
    }

    public interface SelectIncomeStatusFragmentListener{
        void sendIncome(String education);
        void cancelIncome();
    }
}
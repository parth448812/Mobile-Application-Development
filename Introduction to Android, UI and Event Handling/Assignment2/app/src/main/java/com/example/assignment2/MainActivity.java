// Assignment 2
// MainActivity.java
// Parth Patel, Janvi Nandwani


package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewChooseCases;
    Button buttonDeal, buttonNoDeal;
    private final int[] imageViewCaseIds = {R.id.imageViewCase1, R.id.imageViewCase2, R.id.imageViewCase3, R.id.imageViewCase4, R.id.imageViewCase5, R.id.imageViewCase6, R.id.imageViewCase7, R.id.imageViewCase8, R.id.imageViewCase9, R.id.imageViewCase10};
    private final int[] imageViewRewardIds = {R.id.imageViewReward1, R.id.imageViewReward10, R.id.imageViewReward50, R.id.imageViewReward100, R.id.imageViewReward300, R.id.imageViewReward1000, R.id.imageViewReward10000, R.id.imageViewReward50000, R.id.imageViewReward100000, R.id.imageViewReward500000};
    private final int[] drawableRewardIds = {R.drawable.reward_1, R.drawable.reward_10, R.drawable.reward_50, R.drawable.reward_100, R.drawable.reward_300, R.drawable.reward_1000, R.drawable.reward_10000, R.drawable.reward_50000, R.drawable.reward_100000, R.drawable.reward_500000};
    private final int[] drawableOpenRewardIds = {R.drawable.reward_open_1, R.drawable.reward_open_10, R.drawable.reward_open_50, R.drawable.reward_open_100, R.drawable.reward_open_300, R.drawable.reward_open_1000, R.drawable.reward_open_10000, R.drawable.reward_open_50000, R.drawable.reward_open_100000, R.drawable.reward_open_500000};
    private final int[] drawableSuitcaseIds = {R.drawable.suitcase_position_1, R.drawable.suitcase_position_2, R.drawable.suitcase_position_3, R.drawable.suitcase_position_4, R.drawable.suitcase_position_5, R.drawable.suitcase_position_6, R.drawable.suitcase_position_7, R.drawable.suitcase_position_8, R.drawable.suitcase_position_9, R.drawable.suitcase_position_10};
    private final int[] drawableOpenSuitcaseIds = {R.drawable.suitcase_open_1, R.drawable.suitcase_open_10, R.drawable.suitcase_open_50, R.drawable.suitcase_open_100, R.drawable.suitcase_open_300, R.drawable.suitcase_open_1000, R.drawable.suitcase_open_10000, R.drawable.suitcase_open_50000, R.drawable.suitcase_open_100000, R.drawable.suitcase_open_500000};
    private List<Integer> dollarAmounts, shuffledIndices;
    private List<CaseInfo> caseInfos;
    private int remainingCasesToOpen, casesLeft;
    private int finalAmount;
    private boolean bankOfferPresented = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Deal or No Deal");

        textViewChooseCases = findViewById(R.id.textViewChooseCases);
        buttonDeal = findViewById(R.id.buttonDeal);
        buttonNoDeal = findViewById(R.id.buttonNoDeal);
        buttonDeal.setVisibility(View.GONE);
        buttonNoDeal.setVisibility(View.GONE);

        setupNewGame();



        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupNewGame();
            }
        });

        findViewById(R.id.buttonDeal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDeal.setVisibility(View.GONE);
                buttonNoDeal.setVisibility(View.GONE);
                textViewChooseCases.setText("You won $" + finalAmount);
            }
        });

        findViewById(R.id.buttonNoDeal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDeal.setVisibility(View.GONE);
                buttonNoDeal.setVisibility(View.GONE);

                if (casesLeft == 6) {
                    // Continue to the next round
                    remainingCasesToOpen = 4;
                    textViewChooseCases.setText("Choose " + remainingCasesToOpen + " Cases");
                    bankOfferPresented = false;
                } else {
                    // Final round logic
                    if (casesLeft == 2) {
                        // Final round: Allow the user to pick 1 case
                        remainingCasesToOpen = 1;
                        textViewChooseCases.setText("Choose " + remainingCasesToOpen + " Case");
                        bankOfferPresented = false;
                    }


                }
            }
        });
    }
    private void setupNewGame() {
        dollarAmounts = new ArrayList<>();
        dollarAmounts.add(1);
        dollarAmounts.add(10);
        dollarAmounts.add(50);
        dollarAmounts.add(100);
        dollarAmounts.add(300);
        dollarAmounts.add(1000);
        dollarAmounts.add(10000);
        dollarAmounts.add(50000);
        dollarAmounts.add(100000);
        dollarAmounts.add(500000);

        shuffledIndices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            shuffledIndices.add(i);
        }


        Collections.shuffle(shuffledIndices);

        caseInfos = new ArrayList<>();
        for (int i = 0; i < imageViewCaseIds.length; i++) {
            int shuffledIndex = shuffledIndices.get(i);

            int imageViewId = imageViewCaseIds[i];
            int drawableId = drawableOpenSuitcaseIds[shuffledIndex];
            int imageViewRewardId = imageViewRewardIds[shuffledIndex];
            int drawableRewardId = drawableOpenRewardIds[shuffledIndex];
            int amount = dollarAmounts.get(shuffledIndex);
            CaseInfo caseInfo = new CaseInfo(imageViewId, drawableId, imageViewRewardId, drawableRewardId, amount);
            caseInfos.add(caseInfo);
            ImageView imageView = findViewById(imageViewId);
            imageView.setImageResource(drawableSuitcaseIds[i]);

            ImageView imageViewReward = findViewById(imageViewRewardId);
            imageViewReward.setImageResource(drawableRewardIds[shuffledIndex]);


            imageView.setTag(caseInfo);
            imageView.setOnClickListener(this);

        }

        remainingCasesToOpen = 4;
        casesLeft = 10;
        textViewChooseCases.setText("Choose " + remainingCasesToOpen + " Cases");
        bankOfferPresented = false;

    }

    @Override
    public void onClick(View v) {
        if (!bankOfferPresented) {
            ImageView imageView = (ImageView) v;
            CaseInfo caseInfo = (CaseInfo) imageView.getTag();

            if (!caseInfo.isOpened()) {
                openCase(caseInfo);
            }
        }
    }

    private void openCase(CaseInfo caseInfo) {
        ImageView imageView = findViewById(caseInfo.getImageViewId());
        imageView.setImageResource(caseInfo.getDrawableId());
        ImageView imageViewReward = findViewById(caseInfo.getImageViewRewardId());
        imageViewReward.setImageResource(caseInfo.getDrawableRewardId());
        caseInfo.setOpened(true);

        //int amount = caseInfo.getAmount();

        remainingCasesToOpen--;
        casesLeft--;
        if ((remainingCasesToOpen == 0) && (casesLeft > 1)) {
            finalAmount = calculateFinalAmount();
            displayFinalOffer();
            bankOfferPresented = true;
            buttonDeal.setVisibility(View.VISIBLE);
            buttonNoDeal.setVisibility(View.VISIBLE);
        }
        else if (casesLeft == 1) {
            remainingCasesToOpen = 0;
            buttonNoDeal.setVisibility(View.GONE);
            buttonNoDeal.setVisibility(View.GONE);
            CaseInfo finalCase = null;
            for (CaseInfo cases : caseInfos) {
                if (!cases.isOpened()) {
                    finalCase = cases;
                    break;
                }
            }
            if (finalCase != null) {
                int finalCaseAmount = finalCase.getAmount();
                textViewChooseCases.setText("You won $" + finalCaseAmount);
            }
            bankOfferPresented = true;
        }
        else {
            textViewChooseCases.setText("Choose " + remainingCasesToOpen + " Cases");
            bankOfferPresented = false;
        }
    }

    private int calculateFinalAmount() {
        int totalValue = 0;
        int unopenedCases = 0;

        for (CaseInfo caseInfo : caseInfos) {
            if (!caseInfo.isOpened()) {
                totalValue += caseInfo.getAmount();
                unopenedCases++;
            }
        }

        if (unopenedCases > 0) {
            return (int)(0.6*(totalValue / unopenedCases));
        } else {

            return 0;
        }
    }

    private void displayFinalOffer() {
        String finalOfferText = "Bank Deal is $" + finalAmount;
        textViewChooseCases.setText(finalOfferText);


    }


}
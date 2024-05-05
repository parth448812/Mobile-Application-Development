// Assignment 2
// CaseInfo.java
// Parth Patel

package com.example.assignment2;

public class CaseInfo {
    private int imageViewId;
    private int drawableId;
    private int imageViewRewardId;
    private int drawableRewardId;
    private int amount;
    private boolean isOpened;

    public CaseInfo(int imageViewId, int drawableId, int imageViewRewardId, int drawableRewardId, int amount) {
        this.imageViewId = imageViewId;
        this.drawableId = drawableId;
        this.imageViewRewardId = imageViewRewardId;
        this.drawableRewardId = drawableRewardId;
        this.amount = amount;
        this.isOpened = false;
    }

    public int getImageViewId() {

        return imageViewId;
    }
    public int getImageViewRewardId() {

        return imageViewRewardId;
    }
    public void setImageViewId(int imageViewId) {

        this.imageViewId = imageViewId;
    }
    public void setImageViewRewardId(int imageViewRewardId) {

        this.imageViewRewardId = imageViewRewardId;
    }
    public int getDrawableId() {

        return drawableId;
    }
    public int getDrawableRewardId() {

        return drawableRewardId;
    }

    public void setDrawableId(int drawableRewardId) {

        this.drawableRewardId = drawableRewardId;
    }
    public void setDrawableRewardIdId(int drawableId) {

        this.drawableId = drawableId;
    }
    public int getAmount() {

        return amount;
    }

    public void setAmount(int amount) {

        this.amount = amount;
    }

    public boolean isOpened() {

        return isOpened;
    }

    public void setOpened(boolean opened) {

        isOpened = opened;
    }
}

package com.example.abreton.gaiadex;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Owner on 6/9/2016.
 */
public class Patch {
    private int mPatchID;
    private String mPatchBank;
    private int mPatchNum;
    private String mPatchTitle;
    private String mPatchCategory;
    private Bitmap mPatchBit;

    public Patch(){}

    public Patch(int patchID, String patchBank, int patchNum, String patchTitle, String patchCategory, Bitmap patchBit){
        this.mPatchID = patchID;
        this.mPatchBank = patchBank;
        this.mPatchNum = patchNum;
        this.mPatchTitle = patchTitle;
        this.mPatchCategory = patchCategory;
        this.mPatchBit = patchBit;
    }

    public int getPatchID() {
        return mPatchID;
    }

    public void setPatchID(int patchID) {
        mPatchID = patchID;
    }

    public int getPatchNum() {
        return mPatchNum;
    }

    public void setPatchNum(int patchNum) {
        mPatchNum = patchNum;
    }

    public String getPatchBank() {
        return mPatchBank;
    }

    public void setPatchBank(String patchBank) {
        mPatchBank = patchBank;
    }

    public String getPatchTitle() {
        return mPatchTitle;
    }

    public void setPatchTitle(String patchTitle) {
        mPatchTitle = patchTitle;
    }

    public String getPatchCategory() {
        return mPatchCategory;
    }

    public void setPatchCategory(String patchCategory) {
        mPatchCategory = patchCategory;
    }

    public Bitmap getPatchBit() {
        return mPatchBit;
    }

    public void setPatchBit(Bitmap patchBit) {
        mPatchBit = patchBit;
    }
}

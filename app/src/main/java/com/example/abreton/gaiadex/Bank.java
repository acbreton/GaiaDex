package com.example.abreton.gaiadex;

import android.graphics.Bitmap;

/**
 * Created by Owner on 6/8/2016.
 */
public class Bank {
    private char mBankName;
    private Bitmap mBankImage;

    public Bank(char bankName, Bitmap bankImage){
        this.mBankName = bankName;
        this.mBankImage = bankImage;
    }

    public char getBankName() {
        return mBankName;
    }

    public void setBankName(char bankName) {
        this.mBankName = bankName;
    }

    public Bitmap getBankImage() {
        return mBankImage;
    }

    public void setBankImage(Bitmap bankImage) {
        this.mBankImage = bankImage;
    }
}

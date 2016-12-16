package com.example.abreton.gaiadex;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.abreton.gaiadex.R;

/**
 * Created by Owner on 6/8/2016.
 */
public class BankViewHolder {
    ImageView bank;
    BankViewHolder(View v){
        bank = (ImageView) v.findViewById(R.id.bankView);
    }
}

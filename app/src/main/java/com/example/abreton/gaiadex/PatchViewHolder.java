package com.example.abreton.gaiadex;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Owner on 6/10/2016.
 */
public class PatchViewHolder {
    ImageView patchNumImage;
    TextView patchTitle;
    TextView patchCategory;

    PatchViewHolder(View v){
        patchNumImage = (ImageView) v.findViewById(R.id.patchNumImage);
        patchTitle = (TextView) v.findViewById(R.id.patchTitle);
        patchCategory = (TextView) v.findViewById(R.id.patchCategory);
    }
}

package com.example.abreton.gaiadex;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Owner on 6/9/2016.
 */
public class PatchView extends RelativeLayout {
    private ImageView mPatchNum;
    private TextView mPatchTitle;
    private TextView mPatchCategory;

    public static PatchView inflate(ViewGroup parent) {
        PatchView patchView = (PatchView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_patch_list, parent, false);
        return patchView;
    }

    public PatchView(Context context) {
        this(context, null);
    }

    public PatchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PatchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.single_patch, this, true);
        setupChildren();
    }

    private void setupChildren() {
        mPatchNum = (ImageView) findViewById(R.id.patchNumImage);
        mPatchTitle = (TextView) findViewById(R.id.patchTitle);
        mPatchCategory = (TextView) findViewById(R.id.patchCategory);
    }

    public void setItem(Patch patch) {
        mPatchNum.setImageBitmap(patch.getPatchBit());
        mPatchTitle.setText(patch.getPatchTitle());
        mPatchCategory.setText(patch.getPatchCategory());
    }

    public ImageView getImageView () {
        return mPatchNum;
    }

    public TextView getTitleTextView() {
        return mPatchTitle;
    }

}

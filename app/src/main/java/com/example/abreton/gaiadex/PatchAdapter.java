package com.example.abreton.gaiadex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Owner on 6/9/2016.
 */
public class PatchAdapter extends BaseAdapter {

    private ArrayList<Patch> patchData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ArrayList<Integer> patchImages = new ArrayList<>(Arrays.asList(R.drawable.ic_patch_one,
            R.drawable.ic_patch_two, R.drawable.ic_patch_three, R.drawable.ic_patch_four,
            R.drawable.ic_patch_five, R.drawable.ic_patch_six, R.drawable.ic_patch_seven, R.drawable.ic_patch_eight));

    PatchAdapter(Context context, ArrayList<Patch> patches) {
        this.context = context;
        this.patchData = patches;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return patchData.size();
    }

    @Override
    public Object getItem(int position) {
        return patchData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PatchViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.single_patch, null);
            holder = new PatchViewHolder(convertView);
            holder.patchNumImage = (ImageView) convertView.findViewById(R.id.patchNumImage);
            holder.patchCategory = (TextView) convertView.findViewById(R.id.patchCategory);
            convertView.setTag(holder);
        } else {
            holder = (PatchViewHolder) convertView.getTag();
        }

        Patch patch = patchData.get(position);
        holder.patchTitle.setText(patch.getPatchTitle());
        holder.patchCategory.setText(patch.getPatchCategory());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), patchImages.get(position));

        holder.patchNumImage.setImageBitmap(bitmap);

        return convertView;
    }
}

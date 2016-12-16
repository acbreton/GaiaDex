package com.example.abreton.gaiadex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Owner on 6/8/2016.
 */
public class BankButtonAdapter extends BaseAdapter {

    public ArrayList<Bank> banks = new ArrayList<>();

    public char[] bankNames = {'A','B','C','D','E','F','G','H'};

    public ArrayList<Integer> bankImages = new ArrayList<>(Arrays.asList(R.drawable.ic_bank_a,
            R.drawable.ic_bank_b, R.drawable.ic_bank_c, R.drawable.ic_bank_d,
            R.drawable.ic_bank_e,R.drawable.ic_bank_f,R.drawable.ic_bank_g,R.drawable.ic_bank_h));

    private Context context;

    BankButtonAdapter(Context context){
        this.context = context;

        // BUNDLING DEFAULT SIGNS //
        //This for loop adds images to signNames and then adds those objects to the array list
        for(int i = 0; i < bankImages.size();i++){
            try {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), bankImages.get(i));

                Bank bank = new Bank(bankNames[i],bitmap);
                banks.add(bank);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public BankButtonAdapter() {
        this.banks = banks;
    }

    public ArrayList<Bank> getList() {
        return banks;
    }

    @Override
    //size of array of signs
    public int getCount() {
        return banks.size();
    }

    @Override
    //gets each sign from the final array list of objects
    public Object getItem(int i) {
        return banks.get(i);
    }

    @Override
    //gets ID for signs in arraylist
    public long getItemId(int i) {
        return i;
    }

    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int i, View view, ViewGroup parent) {
        ImageView imageView;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        //getting size of window
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        // if it's not recycled, initialize some attributes
        imageView = new ImageView(context);

        try {
            if (view == null) {
                //size of each image on the main Activity
                imageView.setLayoutParams(new GridView.LayoutParams(290, 290));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } else {
                imageView = (ImageView) view;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //adds each sign to the grid on the Main Activity
        Bank temp = banks.get(i);
        imageView.setImageBitmap(temp.getBankImage());
        return imageView;
    }
}

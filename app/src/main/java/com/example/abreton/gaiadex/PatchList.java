package com.example.abreton.gaiadex;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PatchList extends AppCompatActivity {
    private TextView mBankLet;
    private ListView mPatches;
    private String bankStr;
    public Character bank;
    private ArrayList<Patch> patchData;

    private EditText mPatchNum;
    private EditText mPatchTitle;
    private EditText mPatchCategory;
    private Button addPatch;

    private int patchNum;
    private String oldPatchTitle;
    private String oldPatchCategory;
    private String patchTitle;
    private String patchCategory;

    private AlertDialog alert;
    private PatchAdapter patchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bank = getIntent().getExtras().getChar("name");
        bankStr = "Bank: "+String.valueOf(bank);
        mBankLet = (TextView)findViewById(R.id.bankLet);
        mBankLet.setText(bankStr);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        mPatches = (ListView)findViewById(R.id.listView);

        PatchDBHandler arr = new PatchDBHandler(this);
        patchData = arr.getPatchList(String.valueOf(bank));

        if(patchData.size() == 8){
            fab.hide();
        }

        patchAdapter = new PatchAdapter(PatchList.this, patchData);

        mPatches.setAdapter(patchAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPatches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                selectPatch(String.valueOf(bank), pos + 1);

                return true;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        patchAdapter.notifyDataSetChanged();
    }

    public void patchBox(View v) {
        alert = new AlertDialog.Builder(PatchList.this).create();
        alert.setTitle("Add Patch Details");

        LayoutInflater inflater = PatchList.this.getLayoutInflater();
        //this is what I did to added the layout to the alert dialog
        View layout = inflater.inflate(R.layout.patch_dialog,null);
        alert.setView(layout);

        mPatchNum = (EditText)layout.findViewById(R.id.userPatchNum);
        mPatchTitle = (EditText)layout.findViewById(R.id.userPatchTitle);
        mPatchCategory = (EditText)layout.findViewById(R.id.userPatchCategory);
        addPatch = (Button)layout.findViewById(R.id.addPatch);

        alert.show();
    }

    public void editBox(String bank, int pos){
        PatchDBHandler handler = new PatchDBHandler(this);

        alert = new AlertDialog.Builder(PatchList.this).create();
        alert.setTitle("Edit Patch Details");

        LayoutInflater inflater = PatchList.this.getLayoutInflater();
        //this is what I did to added the layout to the alert dialog
        View layout = inflater.inflate(R.layout.edit_dialog,null);
        alert.setView(layout);

        mPatchTitle = (EditText)layout.findViewById(R.id.userPatchTitle);
        mPatchCategory = (EditText)layout.findViewById(R.id.userPatchCategory);
        addPatch = (Button)layout.findViewById(R.id.addPatch);

        alert.show();

        mPatchTitle.setText(handler.getPatchTitle(bank, pos));
        mPatchCategory.setText(handler.getPatchCategory(bank, pos));

        patchNum = pos;
        oldPatchTitle = mPatchTitle.getText().toString();
        oldPatchCategory = mPatchCategory.getText().toString();
    }

    public void addPatchClick(View v){
        PatchDBHandler handler = new PatchDBHandler(this);
        Patch userPatch = new Patch();

        patchNum = Integer.parseInt(mPatchNum.getText().toString());
        patchTitle = mPatchTitle.getText().toString();
        patchCategory = mPatchCategory.getText().toString();

        if(handler.searchPatchTitle(patchTitle)){
            Toast.makeText(this, "That Patch Title already exists. Choose a unique name",
                    Toast.LENGTH_SHORT).show();
            alert.hide();
        } else {
            if (mPatchNum.getText().toString().matches("")|| patchTitle.matches("") || patchCategory.matches("")) {
                Toast.makeText(this, "Make sure you enter all text field", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    userPatch.setPatchID(handler.getRowCount() + 1);
                    userPatch.setPatchBank(String.valueOf(bank));
                    userPatch.setPatchNum(patchNum);
                    userPatch.setPatchTitle(patchTitle);
                    userPatch.setPatchCategory(patchCategory);

                    handler.addPatch(userPatch);
                    Toast.makeText(this, "Patch added",Toast.LENGTH_SHORT).show();
                    alert.hide();
                } catch (Exception e){
                    Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
             }
        }
    }

    public void editPatchClick(View v){
        PatchDBHandler handler = new PatchDBHandler(this);
        Patch userPatch = new Patch();

        patchTitle = mPatchTitle.getText().toString();
        patchCategory = mPatchCategory.getText().toString();

        if(handler.searchPatchTitle(patchTitle)){
            Toast.makeText(this, "That Patch Title already exists. Choose a unique name",
                    Toast.LENGTH_SHORT).show();
        } else {
            if (patchTitle.matches("") || patchCategory.matches("")) {
                Toast.makeText(this, "Make sure you enter all text fields", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    userPatch.setPatchID(Integer.parseInt(handler.getPatchItem("id", oldPatchTitle, oldPatchCategory)));
                    userPatch.setPatchBank(String.valueOf(bank));
                    userPatch.setPatchNum(patchNum);

                    patchTitle = mPatchTitle.getText().toString();
                    patchCategory = mPatchCategory.getText().toString();

                    userPatch.setPatchTitle(patchTitle);
                    userPatch.setPatchCategory(patchCategory);

                    handler.editPatch(userPatch);
                    Toast.makeText(this, "Patch edited",Toast.LENGTH_SHORT).show();
                    patchAdapter.notifyDataSetChanged();
                    alert.hide();
                } catch (Exception e){
                    Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }

    public void clearPatch(String clBank, int pos){
        PatchDBHandler handler = new PatchDBHandler(this);
        Patch userPatch = new Patch();

        try {
            userPatch.setPatchBank(clBank);
            userPatch.setPatchNum(pos);

            String title = handler.getPatchTitle(clBank, pos);
            String category = handler.getPatchCategory(clBank, pos);

            userPatch.setPatchTitle(title);
            userPatch.setPatchCategory(category);
            userPatch.setPatchID(Integer.parseInt(handler.getPatchItem("id", title, category)));

            handler.clearPatch(userPatch);

            Toast.makeText(this, "Patch cleared",Toast.LENGTH_SHORT).show();
            patchAdapter.notifyDataSetChanged();
            alert.hide();
        } catch (Exception e){
            Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void selectPatch(final String bank, final int pos) {
        final String[] optionsMenu = getResources().getStringArray(R.array.menuItems);

        //Opens dialog box so user can select from options
        AlertDialog.Builder builder = new AlertDialog.Builder(PatchList.this);
        builder.setTitle("Patch Options");
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            //This method below checks what user selects and performs an action based on
            // what they selected

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (optionsMenu[item].equals("Edit Patch")) {
                    editBox(bank, pos);
                } else if (optionsMenu[item].equals("Clear Patch")) {
                    clearPatch(bank, pos);
                } else if (optionsMenu[item].equals("Go Back")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}

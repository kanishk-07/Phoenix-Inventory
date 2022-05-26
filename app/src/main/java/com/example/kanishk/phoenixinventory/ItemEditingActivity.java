package com.example.kanishk.phoenixinventory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ItemEditingActivity extends AppCompatActivity {

    private EditText editText;
    private String oldText;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_editing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        ImageButton imgBtn = (ImageButton) findViewById(R.id.go_up_btn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                hideKeyboard(ItemEditingActivity.this);
            }
        });

        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("Position");
        final String heading = bundle.getString("ItemName");

        TextView itemName = (TextView) findViewById(R.id.item_name);
        itemName.setText(heading);

        SharedPreferences prefs = getSharedPreferences(FirstRunActivity.NAME_PREFS, Context.MODE_PRIVATE);
        final String username = prefs.getString(FirstRunActivity.DISPLAY_NAME_KEY, "Anonymous");

        final FloatingActionButton faab = (FloatingActionButton) findViewById(R.id.fab_item_1);
        ;
        final FloatingActionButton faab2 = (FloatingActionButton) findViewById(R.id.fab_item_2);
        faab2.setVisibility(View.INVISIBLE);

        editText = (EditText) findViewById(R.id.aboutEditItem);
        editText.setEnabled(false);

        final Button undo = (Button) findViewById(R.id.undo);
        undo.setVisibility(View.INVISIBLE);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(oldText);
            }
        });

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    oldText = dataSnapshot.child(heading).getValue().toString();
                    editText.setText(oldText);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ItemEditingActivity.this, "Error loading data Check your Internet Connection",
                        Toast.LENGTH_SHORT).show();
            }
        });


        faab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = editText.getText().toString();
                if(!username.equals("Kanishk")){
                    newText = newText + "\n\nby -"+username;
                }
//                String newText = editText.getText().toString();
//                if(!newText.equals(""))
//                {
//                    newText = newText + "\n\nby -"+username;
//                }
                undo.setVisibility(View.INVISIBLE);
                editText.setSelection(0,0);
                //Below is how to overwrite data on existing key
                mDatabaseReference.child(heading);
                Map<String, Object> userUpdates = new HashMap<String, Object>();
                userUpdates.put(heading,newText);
                mDatabaseReference.updateChildren(userUpdates);
                //Log.i("TEST","Data Sent");
                Toast.makeText(ItemEditingActivity.this,"Data Saved in the Cloud (^_^)",Toast.LENGTH_SHORT).show();

                /*
                To add data value to a key
                mDatabaseReference.child("about_message").push().setValues(newText);
                 */

                editText.setEnabled(false);
                faab.setVisibility(View.VISIBLE);
                faab2.setVisibility(View.INVISIBLE);
            }
        });
        faab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setEnabled(true);
                editText.selectAll();
                undo.setVisibility(View.VISIBLE);
                //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                faab2.setVisibility(View.VISIBLE);
                faab.setVisibility(View.INVISIBLE);
            }
        });

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

package com.example.kanishk.phoenixinventory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kanishk on 17-08-2017.
 */

public class AboutFragment extends Fragment {

    private DatabaseReference mDatabaseReference;
    private String oldText;
    EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        editText = (EditText) view.findViewById(R.id.aboutEdit);
        editText.setEnabled(false);

        final Button undo = (Button) view.findViewById(R.id.undo);
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
                oldText = dataSnapshot.child("about_message").getValue().toString();
                editText.setText(oldText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Error loading data Check your Internet Connection",
                        Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences prefs = getActivity().getSharedPreferences(FirstRunActivity.NAME_PREFS, Context.MODE_PRIVATE);
        final String username = prefs.getString(FirstRunActivity.DISPLAY_NAME_KEY,"Anonymous");

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);;
        final FloatingActionButton fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);
        fab2.setVisibility(View.INVISIBLE);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = editText.getText().toString()+"\n\nby -"+username;
//                String newText = editText.getText().toString();
//                if(!newText.equals(""))
//                {
//                    newText = newText + "\n\nby -"+username;
//                }
                undo.setVisibility(View.INVISIBLE);
                editText.setSelection(0,0);
                //Below is how to overwrite data on existing key
                mDatabaseReference.child("about_message");
                Map<String, Object> userUpdates = new HashMap<String, Object>();
                userUpdates.put("about_message",newText);
                mDatabaseReference.updateChildren(userUpdates);
                //Log.i("TEST","Data Sent");
                Toast.makeText(getActivity(),"Data Saved in the Cloud (^_^)",Toast.LENGTH_SHORT).show();

                /*
                To add data value to a key
                mDatabaseReference.child("about_message").push().setValues(newText);
                 */

                editText.setEnabled(false);
                fab.setVisibility(View.VISIBLE);
                fab2.setVisibility(View.INVISIBLE);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setEnabled(true);
                editText.selectAll();
                undo.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                fab2.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);
            }
        });

        hideKeyboard(getActivity());
        return view;
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

    @Override
    public void onResume() {
        super.onResume();
        editText.setText(oldText);
    }
}

package com.example.kanishk.phoenixinventory;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatBoxActivity extends AppCompatActivity {

    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private DatabaseReference mDatabaseReference;
    private ChatListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpDisplayName();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        ImageButton upbutton = (ImageButton)findViewById(R.id.go_up);
        upbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab_chat = (FloatingActionButton)findViewById(R.id.fab_chat);
        fab_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        mChatListView = (ListView) findViewById(R.id.chat_list_view);
        mInputText = (EditText) findViewById(R.id.messageInput);
    }

    private void setUpDisplayName() {
        SharedPreferences prefs = getSharedPreferences(FirstRunActivity.NAME_PREFS, Context.MODE_PRIVATE);
        mDisplayName = prefs.getString(FirstRunActivity.DISPLAY_NAME_KEY,"Anonymous");
    }

    private void sendMessage() {
        //Log.d("LOGTEST", "I send something");
        String input = mInputText.getText().toString();
        if(!input.equals("")) {
            InstantMessage chat = new InstantMessage(input, mDisplayName);
            mDatabaseReference.child("chat_box_messages").push().setValue(chat);
            mInputText.setText("");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new ChatListAdapter(this, mDatabaseReference, mDisplayName);
        mChatListView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.cleanup();
    }

}

package com.example.kanishk.phoenixinventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Locale;

public class FirstRunActivity extends AppCompatActivity {

    TextToSpeech speaker;
    public static String username;
    public static final String NAME_PREFS = "NamePrefs";
    public static final String DISPLAY_NAME_KEY = "Username";
    int result;
    Button DONE;
    EditText name_of_user;

    //SQLiteDatabase mybase = this.openOrCreateDatabase("mybase",MODE_PRIVATE,null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);

        //mybase.execSQL("CREATE TABLE IF NOT EXISTS table (name)");
        speaker = new TextToSpeech(FirstRunActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    result = speaker.setLanguage(Locale.ENGLISH);
                    //speaker.setSpeechRate(2.0f);
                }
            }
        });

        name_of_user = (EditText)findViewById(R.id.edit);
        name_of_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                username = name_of_user.getText().toString();
            }
        });

        DONE = (Button)findViewById(R.id.button);
        DONE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick();
            }
        });
        name_of_user.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onclick();
                return true;
            }
        });;

    }
    private void onclick() {
        username = name_of_user.getText().toString();
        speaker.speak("Hi "+username,TextToSpeech.QUEUE_FLUSH,null,null);
        SharedPreferences prefs = getSharedPreferences(NAME_PREFS,0);
        prefs.edit().putString(DISPLAY_NAME_KEY,username).apply();

        Intent FirstRunActivityOver = new Intent(FirstRunActivity.this, ItemListActivity.class);
        FirstRunActivityOver.putExtra("FIRST_NAME",username);
        finish();
        startActivity(FirstRunActivityOver);
    }


}

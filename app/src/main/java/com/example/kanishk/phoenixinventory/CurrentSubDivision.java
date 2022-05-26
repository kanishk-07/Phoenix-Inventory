package com.example.kanishk.phoenixinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class CurrentSubDivision extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_sub_division);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton upbutton = (ImageButton)findViewById(R.id.go_up);
        upbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemListActivity.FRAG_NO = 2;
                finish();
            }
        });

        ListView members = (ListView) findViewById(R.id.members);
        TextView sub_div_name = (TextView) findViewById(R.id.sub_division_name);
        TextView big = (TextView) findViewById(R.id.heading);
        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("Position");

        if(position == 0) {
            sub_div_name.setText(R.string.robo);
            big.setText(R.string.subRobo);

            String items[] = getResources().getStringArray(R.array.robotics_members);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, items);
            members.setAdapter(adapter);

        }

        else if(position == 1) {
            sub_div_name.setText(R.string.emb);
            big.setText(R.string.subEmbe);

            String items[] = getResources().getStringArray(R.array.embedded_members);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, items);
            members.setAdapter(adapter);
        }

        else if(position == 2) {
            sub_div_name.setText(R.string.dip);
            big.setText(R.string.subDip);

            String items[] = getResources().getStringArray(R.array.dip_members);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, items);
            members.setAdapter(adapter);
        }

        else if(position == 3) {
            sub_div_name.setText(R.string.quarks);
            big.setText(R.string.subQuarks);

            String items[] = getResources().getStringArray(R.array.quarks_members);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, items);
            members.setAdapter(adapter);
        }


    }

}

package com.example.kanishk.phoenixinventory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ItemListFragment extends Fragment {

    String user_name;
    boolean isFirstRun;
    SharedPreferences sp;
    //public SQLiteDatabase mybase = SQLiteDatabase.openDatabase()
    //Context context;
    //SharedPreferences prefe;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list_fragment, container, false);
        //return view;

        //Cursor c = FirstRunActivity.myBase.rawQuery("SELECT * FROM table",null)
        //Intent FirstRunActivityOver = getIntent();
        //username = FirstRunActivityOver.getStringExtra("FIRST_NAME");
        //username = getSharedPreferences("NAME",MODE_PRIVATE).getString("","");
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        isFirstRun = sp.getBoolean(getString(R.string.isFirst),true);
        if(isFirstRun) {
            edit.putBoolean(getString(R.string.isFirst), false);
            //SharedPreferences prefe = getActivity().getPreferences(Context.MODE_PRIVATE);
            sp.edit().putString("First_Name", FirstRunActivity.username).apply();
        }
        //SharedPreferences prefe = getActivity().getPreferences(Context.MODE_PRIVATE);
        user_name = sp.getString("First_Name","Oh cmnn now");

        //TextView test = (TextView) view.findViewById(R.id.test);
        //if(username.equals("")) {
        //test.setText(user_name);
        //test.setVisibility(View.GONE);
        //} else {
            //test.setText("NAH");
        //}

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChatBoxActivity.class);
                startActivity(intent);
            }
        });

        ListView item_list = (ListView) view.findViewById(R.id.item_list);
        final String items[] = getResources().getStringArray(R.array.Item_list);
        final String items_no_bullets[] = getResources().getStringArray(R.array.Item_list_with_no_bullet);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, items);
        item_list.setAdapter(adapter);
        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ItemEditingActivity.class);
                i.putExtra("Position",position);
                i.putExtra("ItemName",items_no_bullets[position]);
                startActivity(i);

            }
        });



        return view;
    }


}

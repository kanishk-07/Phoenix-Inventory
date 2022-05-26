package com.example.kanishk.phoenixinventory;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class MembersFragment extends Fragment {

    public static String[] Tiles;
    int[] imagess = {R.drawable.robotics,R.drawable.embedded,R.drawable.dip,R.drawable.quarks};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.members_fragment, container, false);
        ListView dept_list = (ListView)view.findViewById(R.id.dept_list);

        Resources res = getResources();
        Tiles = res.getStringArray(R.array.depts);

        ListAdapter adapter = new ListAdapter(getActivity(),Tiles,imagess);
        dept_list.setAdapter(adapter);

        dept_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), CurrentSubDivision.class);
                i.putExtra("Position", position);
                startActivity(i);
            }
        });

        return view;
    }
}
/*Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("Position");*/

package com.example.kanishk.phoenixinventory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kanishk on 20-08-2017.
 */

public class ListAdapter extends ArrayAdapter<String>
{
    private Context context;
    private int[] images;
    private String[] titleArray;
    ListAdapter(Context c, String[] titles,int imgs[])
    {
        super(c, R.layout.single_row,R.id.textView5,titles);
        this.context = c;
        this.images = imgs;
        this.titleArray = titles;
    }

    class ViewHolder {

        ImageView imageView;
        TextView textView;
        ViewHolder(View v)
        {
            imageView = (ImageView) v.findViewById(R.id.imageView2);
            textView = (TextView) v.findViewById(R.id.textView5);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//mat be nullable

        View row = convertView;
        ViewHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row,parent,false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        holder.imageView.setImageResource(images[position]);
        Log.d("WorkoutsApp","LINE 77");
        holder.textView.setText("\t\t"+titleArray[position]);

        return row;
    }
}


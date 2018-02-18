package com.johan.dev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by johan on 10.09.2017.
 */

public class ClientListAdapter extends BaseAdapter {

    private ArrayList<String> mListItems;
    private LayoutInflater mLayoutInflater;

    public ClientListAdapter(Context context, ArrayList<String> arrayList){
        mListItems = arrayList;
        //get layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        //getCount represents how many items are in list
        return mListItems.size();
    }

    @Override
    //get the data of an item from a spec. position
    //here we return null
    public Object getItem(int i){
        return null;
    }

    @Override
    public long getItemId(int i){
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        //check to see if the reused view is null or not
        //if is not null then reuse it
        if(view == null){
            view = mLayoutInflater.inflate(R.layout.list_item,null);
        }

        //get string item from pos from array list
        //to put in on the text view
        String stringItem = mListItems.get(position);
        if(stringItem != null){
            TextView itemName = (TextView) view.findViewById(R.id.list_item_text_view);
            if(itemName != null){
                itemName.setText(stringItem);
            }
        }

        return view;
    }

}

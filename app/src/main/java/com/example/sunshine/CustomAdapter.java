package com.example.sunshine;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<DataModel> dataModelArrayList;
    LayoutInflater layoutInflater = null;

    private static class ViewHolder
    {
        ImageView image;
        TextView tv_day, tv_kind, tv_maxTemp, tv_lowTemp;
    }

    ViewHolder viewHolder = null;

    public CustomAdapter(Activity activity, ArrayList<DataModel> dataModels)
    {
        this.activity = activity;
        this.dataModelArrayList = dataModels;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount()
    {
        return dataModelArrayList.size();
    }

    public Object getItem(int i)
    {
        return dataModelArrayList.get(i);
    }

    public long getItemId(int i)
    {
        return i;
    }

    public View getView(int position, View view, ViewGroup viewGroup)
    {
        final int pos = position;
        if (view == null)
        {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_row, null);
            viewHolder.image = (ImageView) view.findViewById(R.id.imageView_td_icon);
            viewHolder.tv_day = (TextView) view.findViewById(R.id.textView_day);
            viewHolder.tv_kind = (TextView) view.findViewById(R.id.textView_itemKind);
            viewHolder.tv_lowTemp = (TextView) view.findViewById(R.id.textView2_lowTemp);
            viewHolder.tv_maxTemp = (TextView) view.findViewById(R.id.textView_maxTemp);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.image.setImageResource(dataModelArrayList.get(pos).getImage());
        viewHolder.tv_day.setText(dataModelArrayList.get(pos).getDay());
        viewHolder.tv_kind.setText(dataModelArrayList.get(pos).getKindOfWeather());
        String maxTemp = Integer.toString(dataModelArrayList.get(pos).getMaxTemp()) + "\u00B0";
        String lowTemp = Integer.toString(dataModelArrayList.get(pos).getLowTemp()) + "\u00B0";
        viewHolder.tv_maxTemp.setText(maxTemp);
        viewHolder.tv_lowTemp.setText(lowTemp);
        Log.d("CustomAdapter", Integer.toString(dataModelArrayList.get(pos).getLowTemp()));
        return view;
    }
}
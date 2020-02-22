package com.example.weatherdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weatherdemo.R;

/**
 * Created by WeatherDemo
 * User: CeletialTao
 * Date: 2020/2/17
 * Time: 23:43
 */
public class ListItemAdapter extends BaseAdapter {
    private Context context;
    private String[] values;
    private String[] titles;

    public ListItemAdapter(String[] values, Context context) {
        this.context = context;
        this.values = values;
        titles = new String[]{"地点", "发布日期", "当前温度","天气", "舒适度", "湿度", "风力","风速", "雨量"};
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.value = (TextView) convertView.findViewById(R.id.value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(titles[position]);
        holder.value.setText(values[position]);
        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView value;
    }
}

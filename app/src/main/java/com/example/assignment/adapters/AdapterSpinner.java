package com.example.assignment.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment.R;
import com.example.assignment.models.Chapter;

import java.util.List;

public class AdapterSpinner extends ArrayAdapter<Chapter> {
    public AdapterSpinner(@NonNull Context context, int resource, @NonNull List<Chapter> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_selected_layout, parent, false);
        TextView titleChapter = convertView.findViewById(R.id.title_chapter);

        if (this.getItem(position) != null) {
            titleChapter.setText(this.getItem(position).getTitle());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_layout, parent, false);
        TextView titleChapter = convertView.findViewById(R.id.title_chapter);

        if (this.getItem(position) != null) {
            titleChapter.setText(this.getItem(position).getTitle());
        }

        return convertView;
    }
}

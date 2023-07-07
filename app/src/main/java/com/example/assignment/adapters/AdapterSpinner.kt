package com.example.assignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.assignment.R
import com.example.assignment.models.Chapter

class AdapterSpinner(context: Context, resource: Int, objects: List<Chapter?>) :
    ArrayAdapter<Chapter?>(context, resource, objects) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var newConvertView = convertView
        newConvertView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chapter_selected_layout, parent, false)
        val titleChapter = newConvertView.findViewById<TextView>(R.id.title_chapter)
        if (getItem(position) != null) {
            titleChapter.text = getItem(position)!!.title
        }
        return newConvertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var newConvertView = convertView
        newConvertView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chapter_layout, parent, false)
        val titleChapter = newConvertView.findViewById<TextView>(R.id.title_chapter)
        if (getItem(position) != null) {
            titleChapter.text = getItem(position)!!.title
        }
        return newConvertView
    }
}
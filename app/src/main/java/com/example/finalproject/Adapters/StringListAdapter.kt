package com.example.finalproject.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R

class StringListViewHolder(inflater: LayoutInflater, var parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)){
    private val listHere: GridLayout
    private var titleHere: TextView


    init{
        listHere = itemView.findViewById<GridLayout>(R.id.list_here)
        titleHere = itemView.findViewById<TextView>(R.id.list_header)
    }

        fun bind(listObject: ArrayList<String>, context: Context, title: String){
            titleHere.text = title
            var width = listHere.width
            for(item in listObject){
                var temp = TextView(context)
                temp.text =  item
                var textColor = context.resources.getColor(R.color.light)
                temp.textSize = 20.0F
                temp.width= width/2
                temp.setPadding(2,2,2,2)
                temp.gravity = Gravity.CENTER
                temp.setTextColor(textColor)
                listHere.addView(temp)
            }
            listObject.clear()
        }
}



class StringListAdapter(private val list: ArrayList<String>, var context: Context, var title: String) : RecyclerView.Adapter<StringListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StringListViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: StringListViewHolder, position: Int) {
        val listObject: ArrayList<String> = list
        holder.bind(listObject, context, title)
    }

    override fun getItemCount(): Int {
        return 1
    }
}
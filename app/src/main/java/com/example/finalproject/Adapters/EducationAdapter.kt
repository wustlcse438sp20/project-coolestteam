package com.example.finalproject.Adapters


import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Data.Education
import com.example.finalproject.R



class EducationViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.education_item, parent, false)){
    private val degree: TextView
    private val gpa: TextView
    private val graduation: TextView
    private val university: TextView

    init{
        degree = itemView.findViewById(R.id.degree)
        gpa = itemView.findViewById(R.id.gpa)
        graduation = itemView.findViewById(R.id.grad_year)
        university = itemView.findViewById(R.id.university)
    }

    fun bind(education: Education){
        degree.text = education.degree
        gpa.text = "GPA: " + education.gpa
        graduation.text = education.graduation
        university.text = education.university


    }
}

class EducationAdapter(private val list: ArrayList<Education>) : RecyclerView.Adapter<EducationViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EducationViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
        val educationObject: Education = list[position]
        holder.bind(educationObject)
    }

    override fun getItemCount(): Int = list.size

}
package com.example.finalproject.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Data.WorkExperience
import com.example.finalproject.R

class WorkExperienceViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.work_item, parent, false)){
    private val company: TextView
    private val position: TextView
    private val from: TextView
    private val to: TextView
    private val description: TextView


    init{
        company = itemView.findViewById(R.id.company)
        position = itemView.findViewById(R.id.position)
        description = itemView.findViewById(R.id.description)
        from = itemView.findViewById(R.id.from)
        to = itemView.findViewById(R.id.to)
    }

    fun bind(work: WorkExperience){
        company.text = work.company
        position.text = work.position
        description.text = work.description
        from.text = work.from.toString()
        to.text = work.to.toString()
    }
}

class WorkExperienceAdapter(private val list: ArrayList<WorkExperience>) : RecyclerView.Adapter<WorkExperienceViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkExperienceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WorkExperienceViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: WorkExperienceViewHolder, position: Int) {
        val workObject: WorkExperience = list[position]
        holder.bind(workObject)
    }

    override fun getItemCount(): Int = list.size

}
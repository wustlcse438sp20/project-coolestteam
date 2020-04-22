package com.example.finalproject.Adapters


import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Activities.EmployeeUI.isInDeleteMode
import com.example.finalproject.Data.Education
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


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

    fun bind(education: Education, list: ArrayList<Education>){
        degree.text = education.degree
        gpa.text = "GPA: " + education.gpa
        graduation.text = education.graduation
        university.text = education.university
        itemView.setOnClickListener {
            if(isInDeleteMode){
                var firestore = FirebaseFirestore.getInstance()
                var userDoc = firestore.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
//                Toast.makeText(it.context, )
                userDoc.update("educations", FieldValue.arrayRemove(education))
                        .addOnSuccessListener {
                            list.removeAt(bindingAdapterPosition)
                            isInDeleteMode = false
                            bindingAdapter?.notifyDataSetChanged()
                        }
                        .addOnFailureListener {it2 ->
                            Toast.makeText(it.context, "Failed to delete item",Toast.LENGTH_LONG).show()
                            isInDeleteMode = false
                        }

            }

        }


    }
}

class EducationAdapter(private val list: ArrayList<Education>) : RecyclerView.Adapter<EducationViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EducationViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
        val educationObject: Education = list[position]
        holder.bind(educationObject, list)
    }

    override fun getItemCount(): Int = list.size

}
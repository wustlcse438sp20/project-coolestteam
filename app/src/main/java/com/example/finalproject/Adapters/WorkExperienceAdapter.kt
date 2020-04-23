package com.example.finalproject.Adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Activities.EmployeeUI.isInDeleteMode
import com.example.finalproject.Data.WorkExperience
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class WorkExperienceViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.work_item, parent, false)) {
    private val company: TextView
    private val position: TextView

    private val description: TextView


    init {
        company = itemView.findViewById(R.id.company)
        position = itemView.findViewById(R.id.position)
        description = itemView.findViewById(R.id.description)
    }

    fun bind(work: WorkExperience, list: ArrayList<WorkExperience>) {
        company.text = work.company
        position.text = work.position
        description.text = work.description
        itemView.setOnClickListener {
            if (isInDeleteMode) {
                var firestore = FirebaseFirestore.getInstance()
                var userDoc = firestore.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
                userDoc.update("workExperiences", FieldValue.arrayRemove(work))
                        .addOnSuccessListener {
                            list.removeAt(bindingAdapterPosition)
                            isInDeleteMode = false
                            bindingAdapter?.notifyDataSetChanged()
                        }
                        .addOnFailureListener { it2 ->
                            Toast.makeText(it.context, "Failed to delete item", Toast.LENGTH_LONG).show()
                            isInDeleteMode = false
                        }
            }
        }
    }

}

class WorkExperienceAdapter(private val list: ArrayList<WorkExperience>) : RecyclerView.Adapter<WorkExperienceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkExperienceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WorkExperienceViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: WorkExperienceViewHolder, position: Int) {
        val workObject: WorkExperience = list[position]
        holder.bind(workObject, list)
    }

    override fun getItemCount(): Int = list.size

}
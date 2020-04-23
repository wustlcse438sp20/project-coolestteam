package com.example.finalproject.Adapters


import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Activities.EmployeeUI.isInDeleteMode
import com.example.finalproject.Data.Hobby
import com.example.finalproject.Data.TechnicalSkill
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StringListViewHolder(inflater: LayoutInflater, var parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
    private val listHere: GridLayout
    private var titleHere: TextView

    init {
        listHere = itemView.findViewById<GridLayout>(R.id.list_here)
        titleHere = itemView.findViewById<TextView>(R.id.list_header)
    }

    fun bind(listObject: ArrayList<String>, context: Context, title: String) {
        listHere.removeAllViews()

        titleHere.text = title
        var width = listHere.width
        for (item in listObject) {
            var temp = TextView(context)
            temp.text = item
            var textColor = context.resources.getColor(R.color.light)
            temp.textSize = 20.0F
            temp.width = width / 2
            temp.setPadding(2, 2, 2, 2)
            temp.gravity = Gravity.CENTER
            temp.setTextColor(textColor)
            listHere.addView(temp)
        }
        itemView.setOnClickListener {
            if (isInDeleteMode && listObject.size > 0) {
                var field = "technicalSkills"
                var fieldObj: Any = TechnicalSkill()
                if (title == "Hobbies") {
                    field = "hobbies"
                    fieldObj = Hobby()
                }
                var firestore = FirebaseFirestore.getInstance()

                var userDoc = firestore.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
                userDoc.update(mapOf(field to mutableListOf(fieldObj)))
                        .addOnSuccessListener {
                            listObject.removeAt(bindingAdapterPosition)
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


class StringListAdapter(private val list: ArrayList<String>, var context: Context, var title: String) : RecyclerView.Adapter<StringListViewHolder>() {
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
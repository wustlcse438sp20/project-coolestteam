package com.example.finalproject.Adapters
//
//import android.content.Intent
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.example.finalproject.Data.PostMatch
//import com.example.finalproject.Data.Posting
//import com.example.finalproject.R
//
////create the view holder
//class EmployeeHomeViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
//        RecyclerView.ViewHolder(inflater.inflate(R.layout.home_item_employee, parent, false)) {
//    private val companyName: TextView?
//    private val jobTitle: TextView?
//    private val jobSalary: TextView?
//    private val jobEducation: TextView?
//    private val homeContainer: LinearLayout?
//
//    //show matches in a list
//    init {
//        companyName = itemView.findViewById(R.id.companyNameHome)
//        jobTitle = itemView.findViewById(R.id.jobTitleHome)
//        jobSalary = itemView.findViewById(R.id.jobSalaryHome)
//        jobEducation = itemView.findViewById(R.id.jobEducationHome)
//
//        homeContainer = itemView.findViewById(R.id.homeContainer)
//    }
//
//    fun bind(post: Posting) {
//        companyName?.text = "Company: " + post.company
//        jobTitle?.text =  "Position: "+ post.position
//        jobSalary?.text = "Salary: " + post.salary.toString()
//        jobEducation?.text =  "Position: "+ post.education
//
//        homeContainer?.setOnClickListener {
//            //allow users to click on postings?
//        }
//    }
//}
//
////create the listener for the recycler view
//class EmployeeHomeAdapter(private val list: ArrayList<Posting>?) : RecyclerView.Adapter<EmployeeHomeViewHolder>() {
//    private var listEvents: ArrayList<Posting>? = list
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHomeViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        return EmployeeHomeViewHolder(inflater, parent)
//    }
//
//    //bind the object
//    override fun onBindViewHolder(holder: EmployeeHomeViewHolder, position: Int) {
//        val event: Posting = listEvents!!.get(position)
//        holder.bind(event)
//    }
//
//    //set the count
//    override fun getItemCount(): Int = list!!.size
//
//}
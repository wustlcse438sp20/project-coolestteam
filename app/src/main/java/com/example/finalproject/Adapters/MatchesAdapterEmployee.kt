package com.example.finalproject.Adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Data.PostMatch
import com.example.finalproject.Data.Posting
import com.example.finalproject.R

//create the view holder
class MatchesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.match_item_employee, parent, false)) {
    private val companyName: TextView?
    private val jobTitle: TextView?
    private val jobSalary: TextView?
    private val matchesContainer: LinearLayout?

    //show matches in a list
    init {
        companyName = itemView.findViewById(R.id.companyName)
        jobTitle = itemView.findViewById(R.id.jobTitle)
        jobSalary = itemView.findViewById(R.id.jobSalary)
        matchesContainer = itemView.findViewById(R.id.matchContainer)
    }

    fun bind(post: PostMatch) {
        companyName?.text = post.Company
        jobTitle?.text = post.Position
        jobSalary?.text = post.Salary.toString()

        matchesContainer?.setOnClickListener {
            //allow users to click on postings?
        }
    }
}

//create the listener for the recycler view
class MatchesAdapterEmployee(private val list: ArrayList<PostMatch>?) : RecyclerView.Adapter<MatchesViewHolder>() {
    private var listEvents: ArrayList<PostMatch>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MatchesViewHolder(inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        val event: PostMatch = listEvents!!.get(position)
        holder.bind(event)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size

}
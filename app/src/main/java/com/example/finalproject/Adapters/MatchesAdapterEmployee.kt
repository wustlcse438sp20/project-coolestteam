package com.example.finalproject.Adapters


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Data.PostMatch
import com.example.finalproject.Fragments.EmployeeMatchJobDisplayFragment
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
        companyName?.text = "Company: " + post.Company
        jobTitle?.text =  "Position: "+ post.Position
        jobSalary?.text = "Salary: " + post.Salary.toString()

        matchesContainer?.setOnClickListener {
//            Toast.makeText(it.context, post.toString(), Toast.LENGTH_LONG).show()
            val bundle = Bundle()
            bundle.putSerializable("post", post)
            var fragment = EmployeeMatchJobDisplayFragment()
            fragment.arguments = bundle
            val transaction: FragmentTransaction = (it.context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, fragment)
            transaction.commit()
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
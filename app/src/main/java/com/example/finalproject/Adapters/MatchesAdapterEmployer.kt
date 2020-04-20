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
class MatchesViewHolderEmployer(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.match_item_employer, parent, false)) {
    private val userName: TextView?
    private val userMajor: TextView?
    private val matchesContainer: LinearLayout?

    //show matches in a list
    init {
        userName = itemView.findViewById(R.id.companyName)
        userMajor = itemView.findViewById(R.id.jobTitle)
        matchesContainer = itemView.findViewById(R.id.matchContainerEmployer)
    }

    fun bind(post: PostMatch) {
        userName?.text = post.companyName
        userMajor?.text = post.position

        matchesContainer?.setOnClickListener {
            //allow users to click on postings?
//            var id = pl.id.toString()
//            val context = it.context
//            val intent = Intent(context, PlaylistActivity::class.java).apply {
//                putExtra("id", id)
//                putExtra("title", pl.title)
//                putExtra("description", pl.description)
//                putExtra("rating", pl.rating)
//                putExtra("genre", pl.genre)
//            }
//            context.startActivity(intent)
        }
    }
}

//create the listener for the recycler view
class EmployerMatchesAdapter(private val list: ArrayList<PostMatch>?) : RecyclerView.Adapter<MatchesViewHolderEmployer>() {
    private var listEvents: ArrayList<PostMatch>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolderEmployer {
        val inflater = LayoutInflater.from(parent.context)
        return MatchesViewHolderEmployer(inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: MatchesViewHolderEmployer, position: Int) {
        val event: PostMatch = listEvents!!.get(position)
        holder.bind(event)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size

}
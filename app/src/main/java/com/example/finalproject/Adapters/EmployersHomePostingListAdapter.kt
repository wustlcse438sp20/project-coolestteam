package com.example.finalproject.Adapters



import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Activities.EmployerUI.PostHomeEmployerActivity
import com.example.finalproject.R
import kotlinx.android.synthetic.main.employers_home_posting_item.view.*
import kotlinx.android.synthetic.main.posting_item.view.*

class EmployersHomePostingHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.employers_home_posting_item, parent, false)) {
    private val postingItemButton : Button


    init {
        postingItemButton = itemView.posting_item_button
    }

    fun bind(data: String) {
        postingItemButton.text = data
        postingItemButton.setOnClickListener{
            val docId = data
            val intent = Intent(it.context, PostHomeEmployerActivity::class.java)
            intent.putExtra("docId", docId)
            it.context.startActivity(intent)
        }

    }

}

//create the listener for the recycler view
class EmployersHomePostingListAdapter(private val list: MutableList<String>)
    : RecyclerView.Adapter<EmployersHomePostingHolder>() {
    private var listPostings: MutableList<String> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployersHomePostingHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EmployersHomePostingHolder(inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: EmployersHomePostingHolder, position: Int) {
        val data: String = listPostings!!.get(position)
        holder.bind(data)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size
}


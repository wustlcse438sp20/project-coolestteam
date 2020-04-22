package com.example.finalproject.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Data.Posting
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.posting_item.view.*

class LeaderboardHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.posting_item, parent, false)) {
    private val postingItemName : TextView
    private var postObj : Posting? = Posting()
    var db = FirebaseFirestore.getInstance()

    init {
        postingItemName = itemView.posting_item_name
    }

    fun bind(data: String) {
        var item = db.collection("Employers")
                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Postings").document(data)
        item.get().addOnSuccessListener { doc ->
            postObj = doc.toObject<Posting>()
            postingItemName.text= postObj!!.position
        }
    }

}

//create the listener for the recycler view
class PostingsListAdapter(private val list: MutableList<String>)
    : RecyclerView.Adapter<LeaderboardHolder>() {
    private var listPostings: MutableList<String> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardHolder {
        Log.d("here", "called the adapter2")
        val inflater = LayoutInflater.from(parent.context)
        return LeaderboardHolder(inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: LeaderboardHolder, position: Int) {
        val data: String = listPostings!!.get(position)
        holder.bind(data)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size
}
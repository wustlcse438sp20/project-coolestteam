package com.example.finalproject.Adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Data.Posting
import com.example.finalproject.Fragments.EmployerPostingMatches
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.posting_item.view.*

class LeaderboardHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.posting_item, parent, false)) {
    private val postingItemName: TextView
    private var postObj: Posting? = Posting()
    var db = FirebaseFirestore.getInstance()

    init {
        postingItemName = itemView.posting_item_name
    }

    fun bind(data: String, itemsShouldBeClickable: Boolean) {
        var item = db.collection("Employers")
                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Postings").document(data)

        item.get().addOnSuccessListener { doc ->
            postObj = doc.toObject<Posting>()
            postObj!!.id = data
            postObj!!.companyid = FirebaseAuth.getInstance().currentUser!!.uid
            postingItemName.text = postObj!!.position
            if (itemsShouldBeClickable) {
                itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putSerializable("post", postObj)
                    var fragment = EmployerPostingMatches()
                    fragment.arguments = bundle
                    val transaction: FragmentTransaction = (it.context as FragmentActivity).supportFragmentManager.beginTransaction()
                    transaction.add(R.id.fragment_container, fragment)
                    transaction.commit()
                }
            }
        }
    }
}

//create the listener for the recycler view
class PostingsListAdapter(private val list: MutableList<String>, private var itemsShouldBeClickable: Boolean)
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
        holder.bind(data, itemsShouldBeClickable)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size
}
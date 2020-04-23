package com.example.finalproject.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Adapters.EmployerMatchesAdapter
import com.example.finalproject.Data.EmployeeMatch
import com.example.finalproject.Data.Posting
import com.example.finalproject.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_matches_employer.*
import kotlinx.android.synthetic.main.fragment_employer_posting_matches.view.*


class EmployerPostingMatches : Fragment() {
    private lateinit var db: FirebaseFirestore
    private var matchList = ArrayList<EmployeeMatch>()
    private lateinit var employerMatchAdapter: EmployerMatchesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.fragment_employer_posting_matches, container, false)
        var post: Posting = arguments!!.getSerializable("post") as Posting

        activity!!.postings_matchesRecyclerViewEmployer.visibility = View.GONE
        v.company_name.text = post.company
        db = FirebaseFirestore.getInstance()
        viewManager = LinearLayoutManager(this.context)
        employerMatchAdapter = EmployerMatchesAdapter(matchList)

        recyclerView = v.findViewById<RecyclerView>(R.id.matchesRecyclerView).apply {
            layoutManager = viewManager
            adapter = employerMatchAdapter
        }


        var matchesRef = db.collection("Employers").document(post.companyid).collection("Postings").document(post.id).collection("Matches")
        matchesRef.get()
                .addOnSuccessListener { documents ->
                    for (doc in documents) {
                        matchList.add(doc.toObject<EmployeeMatch>())
                    }
                    employerMatchAdapter.notifyDataSetChanged()
                }


        return v
    }

}

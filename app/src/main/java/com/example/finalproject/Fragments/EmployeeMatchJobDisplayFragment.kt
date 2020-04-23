package com.example.finalproject.Fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.finalproject.Activities.EmployeeUI.MatchesEmployeeActivity
import com.example.finalproject.Data.PostMatch
import com.example.finalproject.Data.Posting
import com.example.finalproject.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_matches_employee.*
import kotlinx.android.synthetic.main.fragment_job_posting.*


class EmployeeMatchJobDisplayFragment : Fragment() {
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.fragment_job_posting, container, false)
        var post: PostMatch = arguments!!.getSerializable("post") as PostMatch

        var backBtn = v.findViewById<ImageButton>(R.id.backBtn)
        backBtn.setOnClickListener {
            closeFragment(it)
        }

        db = FirebaseFirestore.getInstance()
        loadData(post.employerId, post.id)
        activity!!.matchesRecyclerView.visibility = View.GONE
        return v
    }

    fun loadData(employerId: String, id: String) {
        Log.d("blah", employerId)
        Log.d("blah", id)
        var employerRef = db.collection("Employers").document(employerId).collection("Postings").document(id)
        employerRef.get()
                .addOnSuccessListener { doc ->
                    Log.d("blah", doc.data.toString())
                    var posting = doc.toObject<Posting>()!!
                    renderPosting(posting)
                }
                .addOnFailureListener {
                    Log.d("Error", "Failed to load")
                }

    }

    fun renderPosting(post: Posting) {
        company.text = post.company
        position.text = post.position
        salary.text = post.salary.toString()
        email.text = post.email

    }

    fun closeFragment(view: View){
        activity!!.matchesRecyclerView.visibility = View.VISIBLE
        var bl: MatchesEmployeeActivity = this.activity as MatchesEmployeeActivity
        bl.closeFragment(this)
    }
}

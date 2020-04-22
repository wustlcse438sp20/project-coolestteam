package com.example.finalproject.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.finalproject.Data.PostMatch
import com.example.finalproject.R
import com.google.firebase.firestore.FirebaseFirestore


class EmployeeMatchJobDisplayFragment : Fragment() {
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var post: PostMatch = arguments!!.getSerializable("post") as PostMatch

        db = FirebaseFirestore.getInstance()
        loadData(post.employerId, post.id)
//        val post = arguments!!.getString("post")
//        Toast.makeText(this.context, post.toString(), Toast.LENGTH_LONG).show()
        // Inflate the layout for this fragment
//        Log.d("blah", post.toString())
        return inflater.inflate(R.layout.fragment_job_posting, container, false)
    }

    fun loadData(employerId: String, id: String){
        Log.d("blah", employerId)
        Log.d("blah", id)
        var employerRef = db.collection("Employers").document(employerId).collection("Postings").document(id)
        employerRef.get()
                .addOnSuccessListener {doc ->
                    Log.d("blah", doc.data.toString())
                }
                .addOnFailureListener {
                    Log.d("Error", "Failed to load")
                }

    }

}

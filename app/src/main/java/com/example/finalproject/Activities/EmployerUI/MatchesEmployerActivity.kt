package com.example.finalproject.Activities.EmployerUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.Adapters.PostingsListAdapter
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_matches_employer.*
import kotlinx.android.synthetic.main.activity_matches_employer.create_job_button
import kotlinx.android.synthetic.main.activity_matches_employer.home_button
import kotlinx.android.synthetic.main.activity_matches_employer.logout_button
import kotlinx.android.synthetic.main.activity_matches_employer.match_button
import kotlinx.android.synthetic.main.activity_matches_employer.profile_button
import kotlinx.android.synthetic.main.activity_profile_employer.*

class MatchesEmployerActivity : AppCompatActivity() {
    private lateinit var logoutButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var createJobButton: ImageButton
    private lateinit var matchButton: ImageButton
    private lateinit var homeButton: ImageButton

    lateinit var postingList: MutableList<String>
    lateinit var recyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches_employer)

        logoutButton = logout_button
        profileButton = profile_button
        createJobButton = create_job_button
        matchButton = match_button
        homeButton = home_button
        logoutButton.setOnClickListener { v -> changeActivity(v, LoginActivity::class.java, true) }
        createJobButton.setOnClickListener { v -> changeActivity(v, CreateJobActivity::class.java, false) }
        profileButton.setOnClickListener { v -> changeActivity(v, ProfileEmployerActivity::class.java, false) }
        matchButton.setOnClickListener { v -> changeActivity(v, MatchesEmployerActivity::class.java, false)}
        homeButton.setOnClickListener{ v -> changeActivity(v, HomeEmployerActivity::class.java, false)}

        var db = FirebaseFirestore.getInstance()
        var auth = FirebaseAuth.getInstance()
        postingList = arrayListOf()

        var doc = db.collection("Employers").document(auth.currentUser!!.uid)
        doc.get().addOnSuccessListener { document ->
            Log.d("here", "${document.data!!.get("company").toString()}")
            //var companyName = document.data!!.get("company").toString()
            doc.collection("Postings").get()
                .addOnSuccessListener { result ->
                    for (document2 in result) {
                        postingList.add(document2.id)
                        Log.d("here", "${postingList}")
                    }
                    recyclerView = postings_matchesRecyclerViewEmployer
                    var itemsShouldBeClickable = true
                    var adapter = PostingsListAdapter(postingList,itemsShouldBeClickable)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this)
                }
                .addOnFailureListener { exception ->
                    Log.d("here", "Error getting documents: ", exception)
                }
        }


    }
    fun changeActivity(view: View, activity: Class<*>, isLogout: Boolean){
        val intent = Intent(this, activity)
        if(isLogout){
            FirebaseAuth.getInstance().signOut()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}

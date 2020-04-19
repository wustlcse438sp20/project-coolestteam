package com.example.finalproject.Activities.EmployeeUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.Adapters.EmployeeMatchesAdapter
import com.example.finalproject.Data.Employee
import com.example.finalproject.Data.PostMatch
import com.example.finalproject.Data.Posting
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_matches_employee.*

class MatchesEmployeeActivity : AppCompatActivity() {
    private lateinit var logoutButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var homeButton: ImageButton

    lateinit var firestore: FirebaseFirestore
    lateinit var query: Query
    lateinit var adapter : EmployeeMatchesAdapter
    lateinit var recycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches_employee)
        logoutButton = logout_button
        profileButton = profile_button
        homeButton = home_button
        logoutButton.setOnClickListener { v -> changeActivity(v, LoginActivity::class.java, true) }
        homeButton.setOnClickListener { v -> changeActivity(v, HomeEmployeeActivity::class.java, false) }
        profileButton.setOnClickListener { v -> changeActivity(v, ProfileEmployeeActivity::class.java, false) }

        firestore = FirebaseFirestore.getInstance()

        query = firestore.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Matches").whereArrayContains("Interested", true)

        var postingList: ArrayList<PostMatch> = arrayListOf()
        firestore.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Matches").whereArrayContains("Interested", true).get().addOnSuccessListener { result ->
            for (document in result) {
                postingList.add(document.toObject<PostMatch>())
            }
            recycler = matchesRecyclerView
            adapter = EmployeeMatchesAdapter(postingList)
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(this)
        }.addOnFailureListener { exception -> Log.w("TAG", "ERROR", exception) }
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

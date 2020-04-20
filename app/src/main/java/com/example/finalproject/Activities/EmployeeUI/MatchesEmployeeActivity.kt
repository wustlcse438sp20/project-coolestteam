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
import com.example.finalproject.Adapters.MatchesAdapterEmployee
import com.example.finalproject.Data.PostMatch
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
    lateinit var adapter : MatchesAdapterEmployee
    lateinit var recycler : RecyclerView
    private var postingList = ArrayList<PostMatch>()

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

        recycler = matchesRecyclerView
        adapter = MatchesAdapterEmployee(postingList)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        query = firestore.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Matches").whereArrayContains("Interested", true)


        firestore.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Matches").get().addOnSuccessListener { result ->
                    Log.d("check success", "s check")
                    for (document in result) {
                        Log.d("check", document.data.toString())
                        postingList.add(document.toObject<PostMatch>())
                    }
                    adapter.notifyDataSetChanged()

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

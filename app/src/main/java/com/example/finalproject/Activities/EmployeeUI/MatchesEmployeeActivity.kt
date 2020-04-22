package com.example.finalproject.Activities.EmployeeUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.ActivityUtil
import com.example.finalproject.Adapters.MatchesAdapterEmployee
import com.example.finalproject.Data.Employee
import com.example.finalproject.Data.PostMatch
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home_employee.home_button
import kotlinx.android.synthetic.main.activity_home_employee.logout_button
import kotlinx.android.synthetic.main.activity_home_employee.profile_button
import kotlinx.android.synthetic.main.activity_matches_employee.*
import kotlinx.android.synthetic.main.activity_matches_employee.user_display_name

class MatchesEmployeeActivity : AppCompatActivity() {
    private lateinit var logoutButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var displayName: TextView
    private lateinit var currentEmployee: Employee
    private lateinit var db: FirebaseFirestore

    lateinit var firestore: FirebaseFirestore
    lateinit var adapter : MatchesAdapterEmployee
    lateinit var recycler : RecyclerView
    private var postingList = ArrayList<PostMatch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches_employee)

        logoutButton = logout_button
        profileButton = profile_button
        homeButton = home_button
        displayName = user_display_name
        logoutButton.setOnClickListener { v -> changeActivity(v, LoginActivity::class.java, true) }
        homeButton.setOnClickListener { v -> changeActivity(v, HomeEmployeeActivity::class.java, false) }
        profileButton.setOnClickListener { v -> changeActivity(v, ProfileEmployeeActivity::class.java, false) }

        firestore = FirebaseFirestore.getInstance()
        db = Firebase.firestore

        // prepare recycler
        recycler = matchesRecyclerView
        adapter = MatchesAdapterEmployee(postingList)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        // get all matches for this user for recycler
        firestore.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Matches").get().addOnSuccessListener { result ->
                    Log.d("check success", "s check")
                    for (document in result) {
                        Log.d("check", document.data.toString())
                        var curDoc = document.toObject<PostMatch>()

                        //check if match
                        firestore.collection("Employers").document(curDoc.employerId)
                                .collection("Postings").document(curDoc.id)
                                .collection("Matches").get().addOnSuccessListener { res ->
                                    for(r in res){
                                        Log.d("check id field", r.data.get("id").toString())
                                        Log.d("check current user", FirebaseAuth.getInstance().currentUser!!.uid)
                                        if (r.data.get("id")!!.equals(FirebaseAuth.getInstance().currentUser!!.uid)){
                                            postingList.add(curDoc)
                                        }
                                    }
                                    adapter.notifyDataSetChanged()
                                }
                    }
        }.addOnFailureListener { exception -> Log.w("TAG", "ERROR", exception) }

        // call load profile to update banner
        loadProfile()
    }

    // gets user info for the banner
    fun loadProfile(){
        var currentUser = FirebaseAuth.getInstance().currentUser
        var uid = currentUser!!.uid
        var userDoc = db.collection("Employees").document(uid)
        userDoc.get()
                .addOnSuccessListener{ docSnap->
                    Log.d("blah", docSnap.data.toString())
                    currentEmployee = docSnap.toObject<Employee>()!!
                    Log.d("blah", currentEmployee.toString())
                    renderProfile()
                }
                .addOnFailureListener { e->
                    Log.d("blah", e.toString())
                    currentEmployee = Employee()
                }
    }

    //Display user profile
    fun renderProfile(){
        displayName.text = "Matches for " + currentEmployee.name
        var profilePicView: ImageView = user_profile_image_matches
        if(currentEmployee.general.pic != "") {
            Picasso.get().load(currentEmployee.general.pic).into(profilePicView)
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
    fun closeFragment(fragment: Fragment){
        var util = ActivityUtil()
        util.removeFragmentFromActivity(supportFragmentManager, fragment)
    }
}

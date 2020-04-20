package com.example.finalproject.Activities.EmployeeUI



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.Activities.MainActivity
import com.example.finalproject.Data.Employee
import com.example.finalproject.Data.PostMatch
import com.example.finalproject.Data.Posting
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_create_posting.*
import kotlinx.android.synthetic.main.activity_home_employee.*
import kotlinx.android.synthetic.main.activity_home_employee.logout_button
import kotlinx.android.synthetic.main.activity_home_employee.match_button
import kotlinx.android.synthetic.main.activity_home_employee.profile_button
import kotlinx.android.synthetic.main.activity_matches_employee.*
import java.util.HashMap


class HomeEmployeeActivity : AppCompatActivity(){
    private lateinit var logoutButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var matchButton: ImageButton

    lateinit var firestore: FirebaseFirestore
    lateinit var query: Query

    //lateinit var auth: FirebaseAuth
    //private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_employee)
        logoutButton = logout_button
        profileButton = profile_button
        matchButton = match_button
        logoutButton.setOnClickListener { v -> changeActivity(v, LoginActivity::class.java, true) }
        profileButton.setOnClickListener { v -> changeActivity(v, ProfileEmployeeActivity::class.java, false) }
        matchButton.setOnClickListener { v -> changeActivity(v, MatchesEmployeeActivity::class.java, false)}

        firestore = FirebaseFirestore.getInstance()

        query = firestore.collection("Employers").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Matches").whereArrayContains("Interested", true)

        var postingList: ArrayList<Posting> = arrayListOf()
        firestore.collection("Employers").get().addOnSuccessListener { result ->
                    for (document in result) {
                        firestore.collection("Employers").document(document.id)
                                .collection("Postings").get()
                                .addOnSuccessListener { documents ->
                                    Log.d("check success", "main check")
                                    for (doc in documents) {
                                        postingList.add(doc.toObject<Posting>())
                                    }
                        }
                    }

            for(post in postingList){
                Log.d("a thing in postingList", "home pList thing")
            }
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
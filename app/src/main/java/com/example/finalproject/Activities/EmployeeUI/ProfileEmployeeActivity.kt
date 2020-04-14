package com.example.finalproject.Activities.EmployeeUI


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile_employee.*

class ProfileEmployeeActivity : AppCompatActivity() {
    private lateinit var logoutButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var matchButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_employee)

        logoutButton = logout_button
        homeButton = home_button

        matchButton = match_button
        logoutButton.setOnClickListener { v -> changeActivity(v, LoginActivity::class.java, true) }
        homeButton.setOnClickListener { v -> changeActivity(v, HomeEmployeeActivity::class.java, false) }
        matchButton.setOnClickListener { v -> changeActivity(v, MatchesEmployeeActivity::class.java, false)}


        //Goal:        Get data from firebase -> Create classes ->  Create listview adapter
        val db = Firebase.firestore
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser!!.uid
        val userDoc = db.collection("Employees").document(uid)
//        userDoc.get()
//                .addOnSuccessListener { res ->
//
////                    Log.d("blah", res.data.toString())
//                    for(doc in res){
//                        Log.d("blah", "${doc.id} => ${doc.data}")
//                    }
//                }
//                .addOnFailureListener { e ->
//                    Log.d("blah", e.toString())
//                }
        userDoc.collection("Hobbies").get()
                .addOnSuccessListener { docs ->
                    for(doc in docs){
                        Log.d("blah", doc.data.toString())
                    }
                }
        userDoc.collection("Education").get()
                .addOnSuccessListener { docs ->
                    for(doc in docs){
                        Log.d("blah", doc.data.toString())
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

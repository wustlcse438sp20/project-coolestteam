package com.example.finalproject.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.finalproject.Activities.EmployeeUI.HomeEmployeeActivity
import com.example.finalproject.Activities.EmployerUI.HomeEmployerActivity
import com.example.finalproject.Data.Employee
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var logoutButton: ImageButton
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        Log.d("blah", auth.currentUser.toString()+"AT Main")

        db = FirebaseFirestore.getInstance()
        var employeeRef = db.collection("Employees")
        var employerRef = db.collection("Employers")
        var uid = auth.currentUser?.uid.toString()

        employeeRef.document(uid).get()
                .addOnSuccessListener {doc->
                    if(doc.exists()){
                        //User is an employee, start employee UI
                        startNewActivity(HomeEmployeeActivity::class.java)
                    }
                    else{
                        //Not Employee
                        Log.d("blah", "User is not an employee")
                        employerRef.document(uid).get()
                                .addOnSuccessListener { doc ->
                                    if(doc.exists()){
                                        //User is an employer, start employer UI
                                        startNewActivity(HomeEmployerActivity::class.java)
                                    }
                                    else{
                                        //User not in database -> Came from github let them choose
                                        var employeeBtn: Button = employee_btn
                                        var employerBtn: Button = employer_btn
                                        employeeBtn.setOnClickListener { v ->
                                            accountTypeSelected(v, true)
                                        }
                                        employerBtn.setOnClickListener { v ->
                                            accountTypeSelected(v, false)
                                        }
                                    }
                                }
                                .addOnFailureListener {e ->
                                    Log.d("blah", "Error: " + e.toString())
                                }
                    }
                }
                .addOnFailureListener{e ->
                    Log.d("blah", "Error: " + e.toString())
                }

//        val settings = FirebaseFirestoreSettings.Builder()
//                .setTimestampsInSnapshotsEnabled(true)
//                .build()
//        db.setFirestoreSettings(settings)
//
//        if(auth.currentUser == null) {
//            val intent = Intent(this, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            finish()
//        }else{
//            Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show()
//            var db = FirebaseFirestore.getInstance()
//            var employeeRef = db.collection("Employees")
//            var uid = auth.currentUser?.uid.toString()
//            employeeRef.document(uid).get()
//                    .addOnSuccessListener { doc->
//                        if(doc.exists()){
//                            //Load employee activity
//                            val intent = Intent(this, HomeEmployeeActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        }
//                        else{
//                            //Load employer activity
//                            val intent = Intent(this, HomeEmployerActivity::class.java)
//                            startActivity(intent)
//                            finish()
//
//                        }
//                    }
//                    .addOnFailureListener{
//                        Log.d("blah", "FAIL")
//                    }
//
//        }
//        setContentView(R.layout.activity_main)


        logoutButton = logout_button

        logoutButton.setOnClickListener{
            auth.signOut()
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun accountTypeSelected(view: View, isEmployee: Boolean){
//        var collectionRef = if(isEmployee)  db.collection("Employees") else db.collection("Employers")
//        var currentUser = auth.currentUser

//        var uid = currentUser?.uid.toString()
//        val newUserMap: MutableMap<String, Any> = HashMap()
//        var newEmployee = Employee(
//                currentUser?.displayName.toString(),
//                "",
//                "",
//                21
//        )
//        collectionRef.document(uid).set(data)
        if(isEmployee){
            startNewActivity(EmployeeProfileActivity::class.java)
        }
        else{
            startNewActivity(EmployerProfileActivity::class.java)
        }
    }



    fun startNewActivity(activity: Class<*>){
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }


}

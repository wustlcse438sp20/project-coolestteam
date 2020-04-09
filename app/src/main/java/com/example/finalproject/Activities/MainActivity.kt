package com.example.finalproject.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import com.example.finalproject.Activities.EmployeeUI.HomeEmployeeActivity
import com.example.finalproject.Activities.EmployerUI.HomeEmployerActivity
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var logoutButton: ImageButton
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build()
        db.setFirestoreSettings(settings)

        if(auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show()
            var db = FirebaseFirestore.getInstance()
            var employeeRef = db.collection("Employees")
            var uid = auth.currentUser?.uid.toString()
            employeeRef.document(uid).get()
                    .addOnSuccessListener { doc->
                        if(doc.exists()){
                            //Load employee activity
                            val intent = Intent(this, HomeEmployeeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            //Load employer activity
                            val intent = Intent(this, HomeEmployerActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                    }
                    .addOnFailureListener{
                        Log.d("blah", "FAIL")
                    }

        }
        setContentView(R.layout.activity_main)


        logoutButton = logout_button

        logoutButton.setOnClickListener{
            auth.signOut()
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}

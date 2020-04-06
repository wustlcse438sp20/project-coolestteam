package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.Data.Employee
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.HashMap


class SignupActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var emailSignUp: EditText
    private lateinit var passwordSignUp: EditText

    private lateinit var signUpButton: Button



    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        db.setFirestoreSettings(settings)

        emailSignUp = newEmail
        passwordSignUp = newPassword

        signUpButton = signup_btn

        signUpButton.setOnClickListener{
            var emailSign: String = emailSignUp.text.toString()
            var passwordSign: String = passwordSignUp.text.toString()




            if(emailSign == "" || passwordSign == ""){
                Toast.makeText(this, "Please enter valid email or password", Toast.LENGTH_LONG).show()
            }
            else{
                auth.createUserWithEmailAndPassword(emailSign, passwordSign).addOnCompleteListener(this, OnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                        val userId = task.result!!.user!!.uid
                        val intent = Intent(this, EmployeeProfileActivity::class.java)
                        intent.putExtra("uid", userId)
                        startActivity(intent)
                        finish()
                    }else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }

        }


    }
}



package com.example.finalproject.Data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable


data class PostMatch(var Company: String = "",
                     var Interested: Boolean = true,
                     var Position : String = "" ,
                     var Salary : Int = 0,
                     var employerId: String = "",
                     var id: String = ""):Serializable{
    fun updatDatabase(){
        val db = Firebase.firestore
        val userRef = db.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Matches").document(this.id)
        userRef.set(this, SetOptions.merge())
                .addOnSuccessListener {  Log.d("TAG", "User Updated") }
                .addOnFailureListener{e -> Log.w("TAG", "ERROR", e)}
    }
}

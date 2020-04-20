package com.example.finalproject.Data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class PostMatch(var companyName: String, var Interested: Boolean, var position : String, var salary : Int = 0, var id: String = ""){
    fun updatDatabase(){
        val db = Firebase.firestore
        val userRef = db.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Matches").document(this.id)
        userRef.set(this, SetOptions.merge())
                .addOnSuccessListener {  Log.d("TAG", "User Updated") }
                .addOnFailureListener{e -> Log.w("TAG", "ERROR", e)}
    }
}

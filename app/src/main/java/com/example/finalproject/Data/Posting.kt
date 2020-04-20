package com.example.finalproject.Data

import android.util.Log
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Posting (
    var company: String = "",
    var position: String ="",
    var education: String = "",
    var salary: Int = 0,
    var id : String = ""
) {
    fun updateDatabase() {
        val db = Firebase.firestore
        db.collection("Employers").get().addOnSuccessListener { result ->
            for (document in result) {
                val userRef = db.collection("Employers").document(document.id)
                        .collection("Postings").document(this.id)
                userRef.set(this, SetOptions.merge())
                        .addOnSuccessListener { Log.d("TAG", "User Updated") }
                        .addOnFailureListener { e -> Log.w("TAG", "ERROR", e) }
            }
        }
    }
}

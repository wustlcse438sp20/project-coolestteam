package com.example.finalproject.Activities.EmployerUI


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.Adapters.PostingsListAdapter
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_employee.*
import kotlinx.android.synthetic.main.activity_profile_employer.*
import kotlinx.android.synthetic.main.activity_profile_employer.home_button
import kotlinx.android.synthetic.main.activity_profile_employer.logout_button
import kotlinx.android.synthetic.main.activity_profile_employer.match_button
import kotlinx.android.synthetic.main.activity_profile_employer.profile_button

class ProfileEmployerActivity : AppCompatActivity() {
    private lateinit var logoutButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var createJobButton: ImageButton
    private lateinit var matchButton: ImageButton
    private lateinit var homeButton: ImageButton

    private lateinit var company: TextView
    private lateinit var liason: TextView
    private lateinit var contact: TextView
    private lateinit var profile: TextView

    lateinit var postingList: MutableList<String>
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_employer)
        logoutButton = logout_button
        profileButton = profile_button
        createJobButton = create_job_button
        matchButton = match_button
        homeButton = home_button
        logoutButton.setOnClickListener { v -> changeActivity(v, LoginActivity::class.java, true) }
        createJobButton.setOnClickListener { v ->
            changeActivity(
                v,
                CreateJobActivity::class.java,
                false
            )
        }
        profileButton.setOnClickListener { v ->
            changeActivity(
                v,
                ProfileEmployerActivity::class.java,
                false
            )
        }
        matchButton.setOnClickListener { v ->
            changeActivity(
                v,
                MatchesEmployerActivity::class.java,
                false
            )
        }
        homeButton.setOnClickListener { v ->
            changeActivity(
                v,
                HomeEmployerActivity::class.java,
                false
            )
        }

        //text for textviews
        company = employer_text
        liason = liason_text
        profile = profile_text
        contact = contact_text

        profile.text = "Employer Profile"

        var db = FirebaseFirestore.getInstance()
        var auth = FirebaseAuth.getInstance()
        postingList = arrayListOf()

        var doc = db.collection("Employers").document(auth.currentUser!!.uid)
        doc.get().addOnSuccessListener { document ->
            company.text = "Employer: " + document.data!!.get("company").toString()
            liason.text  = "Liason: " + document.data!!.get("name").toString()
            contact.text  = "Contact: " + document.data!!.get("email").toString()

                    Log.d("here", "${document.data!!.get("company").toString()}")
            //var companyName = document.data!!.get("company").toString()
            doc.collection("Postings").get()
                .addOnSuccessListener { result ->
                    for (document2 in result) {
                        postingList.add(document2.id)
                        Log.d("here", "${postingList}")
                    }
                    recyclerView = postings_recycler_view
                    var adapter = PostingsListAdapter(postingList)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this)
                }
                .addOnFailureListener { exception ->
                    Log.d("here", "Error getting documents: ", exception)
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

package com.example.finalproject.Activities.EmployeeUI

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import kotlinx.android.synthetic.main.activity_matches_employee.*

class MatchesEmployeeActivity : AppCompatActivity() {

    private lateinit var profileButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var projectButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches_employee)
        profileButton = profile_button
        homeButton = home_button
        projectButton = project_button

        homeButton.setOnClickListener { v -> employeeHomeClicked(v) }
        profileButton.setOnClickListener { v -> employeeProfileClicked(v) }
        projectButton.setOnClickListener { v -> employeeProjectClicked(v) }

    }

    fun employeeHomeClicked(view: View){
        val intent = Intent(this, HomeEmployeeActivity::class.java)
        startActivity(intent)
    }
    fun employeeProfileClicked(view: View){
        val intent = Intent(this, ProfileEmployeeActivity::class.java)
        startActivity(intent)
    }
    fun employeeProjectClicked(view: View){
        val intent = Intent(this, ProjectsEmployeeActivity::class.java)
        startActivity(intent)
    }



}

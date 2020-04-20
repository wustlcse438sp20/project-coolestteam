package com.example.finalproject.Fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.finalproject.Activities.EmployeeUI.ProfileEmployeeActivity
import com.example.finalproject.Data.Education
import com.example.finalproject.Data.Hobby
import com.example.finalproject.Data.TechnicalSkill
import com.example.finalproject.Data.WorkExperience
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.employee_add_profile_section.*


class EmployeeAddProfileSection: Fragment(){
    lateinit var spinner: Spinner
    lateinit var hobbyInput: LinearLayout
    lateinit var skillInput: LinearLayout





    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        var v = inflater.inflate(R.layout.employee_add_profile_section, container, false)
        hobbyInput = v.findViewById<LinearLayout>(R.id.hobby_input)
        skillInput = v.findViewById<LinearLayout>(R.id.skill_input)
        spinner= v.findViewById<Spinner>(R.id.section_spinner)
        ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.section_options, R.layout.spinner_item ).also{
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = SpinnerActivity(this)
        return v
    }




    class SpinnerActivity(val frag: Fragment) : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            Log.d("blah", parent.selectedItem.toString())
            var selection = parent.selectedItem.toString()
            this.displayView(selection)
            var submit: Button = frag.submitSection
            submit.setOnClickListener { it
                var obj: Any= getInputData(selection, it);
                if(obj != "") {
                    var dbSelection = ""
                    if(selection == "Hobby") dbSelection = "hobbies"
                    if(selection == "Education") dbSelection = "educations"
                    if(selection == "Technical Skill") dbSelection = "technicalSkills"
                    if(selection == "Work Experience") dbSelection ="workExperiences"
                    var firebase = FirebaseAuth.getInstance()
                    var db = Firebase.firestore
                    var uid = firebase.currentUser!!.uid
                    var userDoc = db.collection("Employees").document(uid)
                    userDoc.update(dbSelection, FieldValue.arrayUnion(obj))
                            .addOnCompleteListener {
                                Toast.makeText(view.context, "Update Successful", Toast.LENGTH_LONG).show()
                                if(selection == "Hobby") {
                                    frag.hobby_type.text?.clear()
                                }
                                else if(selection == "Technical Skill") {
                                    frag.skill_skill.text?.clear()
                                    frag.skill_proficiency.text?.clear()
                                }
                                else if(selection == "Work Experience"){
                                    frag.work_company.text?.clear()
                                    frag.work_position.text?.clear()
                                    frag.work_description.text?.clear()
                                }
                                else if(selection == "Education"){
                                    frag.education_university.text?.clear()
                                    frag.education_degree.text?.clear()
                                    frag.education_gpa.text?.clear()
                                    frag.education_graduation.text?.clear()
                                }

                                var bl: ProfileEmployeeActivity = frag.activity as ProfileEmployeeActivity
                                bl.closeFragment(frag)
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error, could not update your profile", Toast.LENGTH_LONG).show()
                            }

                }
            }

        }


        fun displayView(selection: String ){
            var hobbyInput = frag.view?.findViewById<LinearLayout>(R.id.hobby_input)
            var skillInput =frag.view?.findViewById<LinearLayout>(R.id.skill_input)
            var workInput =frag.view?.findViewById<LinearLayout>(R.id.work_input)
            var educationInput= frag.view?.findViewById<LinearLayout>(R.id.education_input)
            hobbyInput?.visibility = View.GONE
            skillInput?.visibility = View.GONE
            workInput?.visibility = View.GONE
            educationInput?.visibility = View.GONE
            when(selection){
                "Hobby" -> hobbyInput?.visibility=View.VISIBLE
                "Technical Skill" -> skillInput?.visibility=View.VISIBLE
                "Work Experience" -> workInput?.visibility=View.VISIBLE
                "Education" -> educationInput?.visibility = View.VISIBLE
            }

        }

        override fun onNothingSelected(parent: AdapterView<*>) {
        }


        fun getInputData(selection: String, view: View): Any{
            var obj: Any = Any()

            if(selection == "Hobby"){
                var type = frag.hobby_type.text.toString()
                if(type == ""){
                    Toast.makeText(view.context, "Invalid input", Toast.LENGTH_LONG).show()
                    return ""
                }
                else{
                    obj = Hobby(type)
                }
            }

            else if(selection == "Technical Skill"){
                var skill = frag.skill_skill.text.toString()
                var proficiency = frag.skill_proficiency.text.toString().toIntOrNull()
                if(skill == ""){
                    Toast.makeText(view.context, "Invalid input", Toast.LENGTH_LONG).show()
                    return ""
                }
                else{
                    obj = TechnicalSkill(skill, proficiency)
                }
            }
            else if(selection == "Work Experience"){
                var company = frag.work_company.text.toString()
                var position = frag.work_position.text.toString()
                var description = frag.work_description.text.toString()
//                var started = Date(frag.work_started.text.toString())
//                var ended = Date(frag.work_ended.text.toString())
                if(company == "" || position == "" || description == ""){
                    Toast.makeText(view.context, "Invalid input", Toast.LENGTH_LONG).show()
                    return ""
                }
                else{
                    obj = WorkExperience(company, description, null, null, position)
                }
            }

            else if(selection == "Education"){
                var uni = frag.education_university.text.toString()
                var deg = frag.education_degree.text.toString()
                var gpa = frag.education_gpa.text.toString()
                var grad = frag.education_graduation.text.toString()
                if(uni =="" || deg =="" || gpa == "" || grad == ""){
                    Toast.makeText(view.context, "Invalid input", Toast.LENGTH_LONG).show()
                    return ""
                }
                else{
                    obj = Education(deg, gpa, grad, uni)
                }
            }

            return obj
        }
    }


}



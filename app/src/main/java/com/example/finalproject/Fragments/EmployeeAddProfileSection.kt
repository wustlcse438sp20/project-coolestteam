package com.example.finalproject.Fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.finalproject.R
import kotlinx.android.synthetic.main.employee_add_profile_section.view.*
import java.text.DateFormat
import java.util.*


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
            Toast.makeText(frag.activity?.applicationContext, "HELLO1", Toast.LENGTH_LONG).show()
            this.displayView(selection)
        }


        fun displayView(selection: String ){
            var hobbyInput = frag.view?.findViewById<LinearLayout>(R.id.hobby_input)
            var skillInput =frag.view?.findViewById<LinearLayout>(R.id.skill_input)
            var workInput =frag.view?.findViewById<LinearLayout>(R.id.work_input)
            hobbyInput?.visibility = View.GONE
            skillInput?.visibility = View.GONE
            workInput?.visibility = View.GONE
            when(selection){
                "Hobby" -> hobbyInput?.visibility=View.VISIBLE
                "Technical Skill" -> skillInput?.visibility=View.VISIBLE
                "Work Experience" -> workInput?.visibility=View.VISIBLE

            }

        }

        override fun onNothingSelected(parent: AdapterView<*>) {
        }
    }
}



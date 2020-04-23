package com.example.finalproject.Data

import java.io.Serializable


data class PostMatch(var Company: String = "",
                     var Interested: Boolean = true,
                     var Position: String = "",
                     var Salary: Int = 0,
                     var employerId: String = "",
                     var id: String = "") : Serializable

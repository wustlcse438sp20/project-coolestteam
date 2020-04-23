package com.example.finalproject.Data

import java.io.Serializable

data class Posting(
        var company: String = "",
        var position: String = "",
        var education: String = "",
        var salary: Int = 0,
        var id: String = "",
        var companyid: String = "",
        var email: String = ""
) : Serializable

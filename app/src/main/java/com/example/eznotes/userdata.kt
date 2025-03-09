package com.example.eznotes

import com.google.firebase.Timestamp

data class userdata(
    var title:String = "",
    var desc :String="",
    var timestamp: Timestamp? = null
)

package com.example.demoapplication.model

import com.google.gson.annotations.SerializedName

data class TweetBody(
    @SerializedName("tweet") val tweet:String
)

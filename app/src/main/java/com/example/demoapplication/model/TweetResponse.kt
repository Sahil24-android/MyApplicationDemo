package com.example.demoapplication.model

import com.google.gson.annotations.SerializedName

data class TweetResponse(
    @SerializedName("tweet" ) var tweet : String? = null,
    @SerializedName("_id"   ) var Id    : String? = null,
    @SerializedName("__v"   ) var _v    : Int?    = null
)

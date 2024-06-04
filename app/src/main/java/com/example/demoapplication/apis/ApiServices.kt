package com.example.demoapplication.apis

import com.example.demoapplication.model.LoginUserBody
import com.example.demoapplication.model.RegisterUserResponse
import com.example.demoapplication.model.TweetBody
import com.example.demoapplication.model.TweetResponse
import com.example.demoapplication.model.UserRegisterBody
import com.example.demoapplication.model.WelcomeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServices {

    @POST("register")
    suspend fun registerUser(
        @Body user: UserRegisterBody
    ):Response<RegisterUserResponse>

    @POST("login")
    suspend fun login(
        @Body loginUserBody: LoginUserBody
    ):Response<RegisterUserResponse>

//    @GET("welcome")
//    suspend fun getWelcomeMessage(
//        @Header("x-api-key") apiKey:String
//    ):Response<WelcomeResponse>

    @POST("tweets")
    suspend fun postTweet(
        @Header("x-api-key") apiKey:String,
        @Body tweet: TweetBody
    ):Response<TweetResponse>

    @GET("tweets")
    suspend fun getTweets(
        @Header("x-api-key") apiKey:String
    ):Response<ArrayList<TweetResponse>>
}
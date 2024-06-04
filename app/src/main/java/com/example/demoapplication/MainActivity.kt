package com.example.demoapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoapplication.adapter.TweetAdapter
import com.example.demoapplication.databinding.ActivityMainBinding
import com.example.demoapplication.model.TweetBody
import com.example.demoapplication.viewmodel.UserViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var preferenceManager: PreferenceManager
    private var token: String? = null
    private lateinit var tweetAdapter: TweetAdapter
    private val loader by lazy { CustomProgressDialog(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        token = preferenceManager.getUserData()!!.token
        binding.logout.setOnClickListener {
            preferenceManager.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }

        tweetAdapter = TweetAdapter()
        binding.rvTweets.adapter = tweetAdapter
        binding.rvTweets.layoutManager = LinearLayoutManager(this)
        binding.rvTweets.setHasFixedSize(true)


        userViewModel.isLoading.observe(this){
            if (it){
                loader.show()
            }else{
                loader.dismiss()
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            userViewModel.getTweets(token!!)
            binding.refreshLayout.isRefreshing = false
        }
        userViewModel.getTweets(token!!)
        userViewModel.allTweetResponse.observe(this){reponse ->
            if (reponse.isNotEmpty()){
                tweetAdapter.setList(reponse)
            }
        }



        binding.floatingActionButton.setOnClickListener {
            showCustomDialog()
        }
    }


    private fun showCustomDialog(
    ) {
        val dialogView =
            LayoutInflater.from(this)
                .inflate(R.layout.custome_dialog, null)

        val post:MaterialButton = dialogView.findViewById(R.id.post)
        val cancel:MaterialButton = dialogView.findViewById(R.id.cancel)
        val tweet:TextInputEditText = dialogView.findViewById(R.id.tweet)

        val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(dialogView)
            .setTitle("Post Tweet")
            .create()


        post.setOnClickListener {
            if (tweet.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter tweet", Toast.LENGTH_SHORT).show()
            } else {
                val tweetBody = TweetBody(tweet.text.toString())
                userViewModel.postTweet(token!!,tweetBody)
                userViewModel.tweetResponse.observe(this){response ->
                    if (response != null){
                        Toast.makeText(this, "Tweet posted", Toast.LENGTH_SHORT).show()
                        userViewModel.getTweets(token!!)
                        Handler(Looper.getMainLooper()).postDelayed({
                            dialog.dismiss()
                        }, 1000)
                    }
                }
            }
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
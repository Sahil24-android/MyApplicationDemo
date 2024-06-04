package com.example.demoapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.demoapplication.databinding.ActivityLoginBinding
import com.example.demoapplication.model.LoginUserBody
import com.example.demoapplication.model.RegisterUserResponse
import com.example.demoapplication.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var preferenceManager: PreferenceManager
    private val loader by lazy { CustomProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }


        binding.login.setOnClickListener {
            if (binding.email.text.toString().isEmpty() || binding.password.text.toString()
                    .isEmpty()
            ) {
                binding.email.error = "Email is required"
                binding.password.error = "Password is required"
            } else {
                val loginUserBody = LoginUserBody(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
                userViewModel.login(loginUserBody)

            }
        }

        userViewModel.loginUserResponse.observe(this){response ->
            if (response != null){
                preferenceManager.setUserData(
                    RegisterUserResponse(
                    response.firstName,
                    response.lastName,
                    response.email,
                        response.id,
                    response.token
                )
                )
                preferenceManager.setLoginStatus(true)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }


        userViewModel.isLoading.observe(this){
            if (it){
                loader.show()
            }else{
                loader.dismiss()
            }
        }
    }
}
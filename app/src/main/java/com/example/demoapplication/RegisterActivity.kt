package com.example.demoapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.demoapplication.databinding.ActivityRegisterBinding
import com.example.demoapplication.model.RegisterUserResponse
import com.example.demoapplication.model.UserRegisterBody
import com.example.demoapplication.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var preferenceManager: PreferenceManager
    private val loader by lazy { CustomProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel= ViewModelProvider(this)[UserViewModel::class.java]
        preferenceManager  = PreferenceManager(this)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding.register.setOnClickListener {
            if(binding.firstName.text.toString().isEmpty() ||
                binding.lastName.text.toString().isEmpty() ||
                binding.email.text.toString().isEmpty() ||
                binding.password.text.toString().isEmpty() ||
                binding.confirmPassword.text.toString().isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }else if(binding.password.text.toString() != binding.confirmPassword.text.toString()){
                binding.confirmPassword.error = "Password does not match"
            }else{
                val body = UserRegisterBody(
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString(),
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
                userViewModel.registerUser(body)

            }
        }

        userViewModel.registerUserResponse.observe(this){response ->
            if (response != null){
//                preferenceManager.setUserData(RegisterUserResponse(
//                    response.firstName,
//                    response.lastName,
//                    response.email,
//                    response.token
//                ))
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
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
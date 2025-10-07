package com.example.cineflix.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cineflix.databinding.WelcomepageBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: WelcomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WelcomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to Sign Up
        binding.button.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // Navigate to Sign In
        binding.button2.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}

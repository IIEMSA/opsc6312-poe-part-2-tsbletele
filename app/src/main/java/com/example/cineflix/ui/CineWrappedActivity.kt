package com.example.cineflix.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cineflix.R
import com.example.cineflix.viewmodel.CineWrappedViewModel

class CineWrappedActivity : AppCompatActivity() {

    private lateinit var viewModel: CineWrappedViewModel
    private lateinit var shareButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cine_wrapped)

        shareButton = findViewById(R.id.shareCineWrapped)

        viewModel = ViewModelProvider(this)[CineWrappedViewModel::class.java]

        // TODO: Observe cine persona & hours watched
        // TODO: Update UI accordingly

        shareButton.setOnClickListener {
            // TODO: Implement share functionality (export text/image of stats)
        }
    }
}

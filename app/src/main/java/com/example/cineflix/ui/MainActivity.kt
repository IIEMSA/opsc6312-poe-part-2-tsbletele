package com.example.cineflix.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cineflix.R
import com.example.cineflix.adapters.MovieAdapter
import com.example.cineflix.data.network.RetrofitClient
import com.example.cineflix.databinding.ActivityMainBinding
import com.example.cineflix.model.Movie
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private val api by lazy { RetrofitClient.api }
    private val apiKey = "83258d157014d8e69ded25cc93f1e565"

    private lateinit var trendingAdapter: MovieAdapter
    private lateinit var popularAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var mixesAdapter: MovieAdapter

    private val trending = mutableListOf<Movie>()
    private val popular = mutableListOf<Movie>()
    private val upcoming = mutableListOf<Movie>()
    private val mixes = mutableListOf<Movie>()

    private val uiScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var searchJob: Job? = null

    private val voiceLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val matches = result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val text = matches?.firstOrNull().orEmpty()
            binding.searchBar.setText(text)
            performSearch(text)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
            return
        }

        setupRecyclerViews()
        setupSearch()
        setupVoiceSearch()
        setupBottomNav(binding.bottomNavigation)
        setupCineWrappedBanner()

        loadAllSections()
    }

    override fun onDestroy() {
        super.onDestroy()
        uiScope.cancel()
    }

    private fun setupRecyclerViews() {
        trendingAdapter = MovieAdapter(trending)
        popularAdapter = MovieAdapter(popular)
        upcomingAdapter = MovieAdapter(upcoming)
        mixesAdapter = MovieAdapter(mixes)

        binding.recyclerTrending.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerTrending.adapter = trendingAdapter

        binding.recyclerPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerPopular.adapter = popularAdapter

        binding.recyclerUpcoming.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerUpcoming.adapter = upcomingAdapter

        binding.recyclerMixes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMixes.adapter = mixesAdapter
    }

    private fun setupSearch() {
        binding.searchBar.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(v.text.toString())
                true
            } else false
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                val query = s?.toString()?.trim().orEmpty()
                if (query.isBlank()) return
                searchJob = uiScope.launch {
                    delay(600)
                    performSearch(query)
                }
            }
        })
    }

    private fun setupVoiceSearch() {
        binding.voiceButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a movie name")
            }
            voiceLauncher.launch(intent)
        }
    }

    private fun setupBottomNav(bottomNav: BottomNavigationView) {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.nav_browse -> {
                    startActivity(Intent(this, BrowseMoviesActivity::class.java))
                    true
                }
                R.id.nav_mixes -> {

                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupCineWrappedBanner() {
        binding.cineWrappedBanner.visibility = android.view.View.VISIBLE
        binding.viewCineWrappedButton.setOnClickListener {
            startActivity(Intent(this, CineWrappedActivity::class.java))
        }
    }

    private fun loadAllSections() {
        uiScope.launch { loadSection(trending, trendingAdapter) { api.getTrending(apiKey) } }
        uiScope.launch { loadSection(popular, popularAdapter) { api.getPopularMovies(apiKey) } }
        uiScope.launch { loadSection(upcoming, upcomingAdapter) { api.getUpcomingMovies(apiKey) } }

        // Mixes = combination
        uiScope.launch {
            try {
                val trendingList = withContext(Dispatchers.IO) { api.getTrending(apiKey).results }
                val popularList = withContext(Dispatchers.IO) { api.getPopularMovies(apiKey).results }
                mixes.clear()
                mixes.addAll((trendingList + popularList).distinctBy { it.id }.take(20))
                mixesAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error loading mixes", e)
            }
        }
    }

    private suspend fun loadSection(
        targetList: MutableList<Movie>,
        adapter: MovieAdapter,
        loader: suspend () -> com.example.cineflix.model.MovieResponse
    ) {
        try {
            val response = withContext(Dispatchers.IO) { loader() }
            targetList.clear()
            targetList.addAll(response.results)
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error loading section", e)
        }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) return
        uiScope.launch {
            try {
                val res = withContext(Dispatchers.IO) { api.searchMovies(apiKey, query) }
                trending.clear()
                trending.addAll(res.results)
                trendingAdapter.notifyDataSetChanged()
                Toast.makeText(this@MainActivity, "Showing results for \"$query\"", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                val msg = if (e is HttpException) "HTTP ${e.code()}" else e.localizedMessage
                Toast.makeText(this@MainActivity, "Search error: $msg", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package com.yudahendriawan.secondsubmissionfundamentalandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yudahendriawan.secondsubmissionfundamentalandroid.R
import com.yudahendriawan.secondsubmissionfundamentalandroid.databinding.ActivitySplashscreenBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val job = MainScope().launch {
            delay(1000L)
            startActivity(Intent(this@SplashscreenActivity, SearchGithubUsersActivity::class.java))
            finish()
        }

        job.start()

    }
}
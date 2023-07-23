package com.example.demo_quizapp.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.demo_quizapp.databinding.ActivityDifficultyBinding
import com.example.demo_quizapp.fragment.FragmentPlay
import com.example.demo_quizapp.utils.Commonclass

class DifficultyActivity : AppCompatActivity(),FragmentPlay.OnCallbackReceived {
    private lateinit var binding: ActivityDifficultyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDifficultyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        binding.TVTotal.visibility = View.GONE

        binding.IgSettings1.setOnClickListener {
            Commonclass().startmusic(it.context)
            val intent = Intent(it.context, SettingActivity::class.java)
            startActivity(intent)

        }
        binding.IgHome.setOnClickListener {
            Commonclass().startmusic(it.context)
            val intent = Intent(it.context, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun update(score: Int) {
        binding.TVTotal.visibility = View.VISIBLE
        Log.e(TAG, "Update: $score")
        binding.TVTotal.text = "Score: $score"
    }
}
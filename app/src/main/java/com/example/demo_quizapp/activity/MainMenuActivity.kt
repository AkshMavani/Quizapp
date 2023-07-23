package com.example.demo_quizapp.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.demo_quizapp.databinding.ActivityHighScoreBinding
import com.example.demo_quizapp.utils.Commonclass



class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHighScoreBinding
    private var score:Int = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHighScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    @SuppressLint("SetTextI18n")
    private fun init(){
        val cmn=Commonclass()

        binding.BtnPlay.setOnClickListener {
            cmn.startmusic(this)
            val intent = Intent(this, DifficultyActivity::class.java)
            startActivity(intent)
        }
        binding.HighscoreSetting.setOnClickListener {
            cmn.startmusic(this)
            val intent=Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }
        binding.HighscoreExit.setOnClickListener {
            cmn.startmusic(this)
           finish()
            moveTaskToBack(true)
        }

    }
    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val scoreData = sharedPreferences.getInt("Score",score)
        Log.e(TAG, "highScore of main menu  : $scoreData")
        binding.Tvhigh1.text = "High " +
                "Score $scoreData"
    }
}
package com.example.demo_quizapp.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_quizapp.databinding.ActivityGameOverBinding
import com.example.demo_quizapp.utils.Commonclass


class GameOverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameOverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun init() {

        binding.btnreset1.setOnClickListener {
            Commonclass().startmusic(this)
            val intent=Intent(this,DifficultyActivity::class.java)
            startActivity(intent)
        }

        binding.BtnBack1.setOnClickListener {
            Commonclass().startmusic(this)
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        val data = intent.getStringExtra("score")
        binding.TvScore1.text = "Your Score:$data"
        val score = data!!.toInt()
        Log.e(ContentValues.TAG, "score game over: $score")
        if (score == 20) {
            binding.view1.setBackgroundResource(com.example.demo_quizapp.R.drawable.bgratingbar)
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val scoreData = score.let { sharedPreferences.getInt("Score", it) }
            Log.e(ContentValues.TAG, "highScore game over  : $scoreData")
            if (score >= scoreData) {
                val sp = PreferenceManager.getDefaultSharedPreferences(this)
                val editor = sp.edit()
                editor.putInt("Score", score)
                editor.apply()
            }
            binding.RatingBar1.rating = 5F
            binding.textView1.text="Congratulation!"
            binding.TvScore1.setTextColor(com.example.demo_quizapp.R.color.green)
            binding.textView1.setTextColor(com.example.demo_quizapp.R.color.green)

        }else {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val scoreData = score.let { sharedPreferences.getInt("Score", it) }
            Log.e(ContentValues.TAG, "highScore game over  : $scoreData")
            binding.TvSuccessFully.visibility=View.GONE
            if (score >= scoreData) {
                val sp = PreferenceManager.getDefaultSharedPreferences(this)
                val editor = sp.edit()
                editor.putInt("Score", score)
                editor.apply()
            }
            val db: Double = score.toDouble() * 5 / 20
            Log.e("score", "init: $db")
            binding.RatingBar1.rating = db.toFloat()
        }
    }
}
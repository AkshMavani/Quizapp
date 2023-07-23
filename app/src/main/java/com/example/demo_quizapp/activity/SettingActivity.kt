package com.example.demo_quizapp.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.demo_quizapp.databinding.ActivitySettingBinding
import com.example.demo_quizapp.utils.Commonclass


class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
     init()
    }
    private fun init(){
        val cmn=Commonclass()
       val data=cmn.getprefrancevalue(this)
        if (data){
            binding.Btnon.setBackgroundResource(com.example.demo_quizapp.R.drawable.shapegreen)
            binding.Btnoff.setBackgroundResource(com.example.demo_quizapp.R.drawable.shape)
            binding.Btnon.setTextColor(Color.WHITE)
            binding.Btnoff.setTextColor(Color.BLACK)

        }else{
            binding.Btnon.setBackgroundResource(com.example.demo_quizapp.R.drawable.shape)
            binding.Btnoff.setBackgroundResource(com.example.demo_quizapp.R.drawable.shapegreen)
            binding.Btnon.setTextColor(Color.BLACK)
            binding.Btnoff.setTextColor(Color.WHITE)
        }
        binding.Btnon.setOnClickListener {
            cmn.prefrancestart(this)
            cmn.startmusic(this)
            binding.Btnon.setBackgroundResource(com.example.demo_quizapp.R.drawable.shapegreen)
            binding.Btnoff.setBackgroundResource(com.example.demo_quizapp.R.drawable.shape)
            binding.Btnon.setTextColor(Color.WHITE)
            binding.Btnoff.setTextColor(Color.BLACK)
        }
        binding.Btnoff.setOnClickListener {
            cmn.stopprefrance(this)
            cmn.startmusic(this)
            binding.Btnon.setBackgroundResource(com.example.demo_quizapp.R.drawable.shape)
            binding.Btnoff.setBackgroundResource(com.example.demo_quizapp.R.drawable.shapegreen)
            binding.Btnon.setTextColor(Color.BLACK)
            binding.Btnoff.setTextColor(Color.WHITE)
        }
        binding.BtnReset.setOnClickListener {
            val sp = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = sp.edit()
            editor.putInt("Score", 0)
            editor.apply()
            cmn.startmusic(this)
            Log.e("TAG", "inside init:")
        }
        binding.IgBack.setOnClickListener {
          cmn.startmusic(this)
          finish()
        }
    }
}

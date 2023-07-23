package com.example.demo_quizapp.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.demo_quizapp.R


class Commonclass {
    private var flag=false
    var score:Int?=null
    fun startmusic(context: Context){
        val data = getprefrancevalue(context)
        if (data){
            val mp: MediaPlayer = MediaPlayer.create(context, R.raw.sound)
            mp.start()
            Handler().postDelayed(Runnable{
                mp.stop()
                mp.release()
            },1000)
            mp.setOnCompletionListener { mp -> mp.release() }
        }
    }
   fun prefrancestart(context: Context){
       val sp = PreferenceManager.getDefaultSharedPreferences(context)
       val editor = sp.edit()
       editor.putBoolean("music", true)
       editor.apply()
   }
    fun stopprefrance(context: Context){
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sp.edit()
        editor.putBoolean("music", false)
        editor.apply()
    }
    fun getprefrancevalue(context: Context):Boolean{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val data = sharedPreferences.getBoolean("music", flag)
        Log.e(TAG, "btn  data: $data")
        return data
    }
}

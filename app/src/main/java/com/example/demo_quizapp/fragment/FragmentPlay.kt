package com.example.demo_quizapp.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.demo_quizapp.R
import com.example.demo_quizapp.activity.GameOverActivity
import com.example.demo_quizapp.databinding.FragmentPlayBinding
import com.example.demo_quizapp.model.Result
import com.example.demo_quizapp.utils.Commonclass
import com.example.demo_quizapp.utils.ViewPagerAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type


// TODO: Rename parameter arguments, choose names that match

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
const val api = "https://opentdb.com/api.php"

open class FragmentPlay : Fragment() {
    var mCallback: OnCallbackReceived? = null
    private var flag: Boolean = false
    private var buttonSelection: Boolean = false
    var position = 0
    private var score = 0
    var result: List<Result>? = null
    private var optionList: ArrayList<String>? = ArrayList()
    private var selected: String? = null
    private lateinit var _binding: FragmentPlayBinding
    private var mViewPagerAdapter: ViewPagerAdapter? = null
    private var mViewPager: ViewPager? = null


    private val binding get() = _binding
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding= FragmentPlayBinding.inflate(inflater,container,false)
        init()
        return _binding.root
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentPlay().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            mCallback = activity as OnCallbackReceived
        }catch (_:Exception){

        }
    }
    @SuppressLint("SetTextI18n")
    private fun init(){
        val cmn = Commonclass()
        val bundle = this.arguments
        val easyData = bundle?.getBoolean("Easy", false)
        Log.e("TAG", "easyData: $easyData")


        if (easyData == true) {
            AndroidNetworking.initialize(context)
            AndroidNetworking.get(api)
                .addQueryParameter("amount", "20")
                .addQueryParameter("category", "24")
                .addQueryParameter("difficulty", "easy")
                .addQueryParameter("type", "multiple")
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
                    override fun onResponse(response: JSONObject?) {
                        val information: JSONArray = response!!.getJSONArray("results")
                        val gson = Gson()
                        val type: Type = object : TypeToken<List<Result>>() {}.type
                        result = gson.fromJson(information.toString(), type)
                        Log.e(ContentValues.TAG, "onResponse: $result")
                        binding.progressBar.visibility = View.GONE
                        binding.TvQ.text = "No question"
                        binding.BtnOp1.text = "No option"
                        binding.BtnOp2.text = "No option"
                        binding.Btnop3.text = "No option"
                        binding.BtnOp4.text = "No option"
                        binding.BtnNext.setOnClickListener {
                            Toast.makeText(context,"No Next Question", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onError(anError: ANError?) {
                        Log.e("TAG", "onError: $anError")
                    }
                })
        }
        else {
            AndroidNetworking.initialize(context)
            AndroidNetworking.get(api)
                .addQueryParameter("amount", "20")
                .addQueryParameter("category", "24")
                .addQueryParameter("difficulty", "medium")
                .addQueryParameter("type", "multiple")
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
                    override fun onResponse(response: JSONObject?) {
                        val information: JSONArray = response!!.getJSONArray("results")
                        val gson = Gson()
                        val type: Type = object : TypeToken<List<Result>>() {}.type
                        result = gson.fromJson(information.toString(), type)
                        //   result?.let { Collections.shuffle(it) }
                        Log.e(ContentValues.TAG, "onResponse: $result")
                        if (result == null) {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Please check your internet connection",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.TvQ.text = result!![position].question
                            optionList?.clear()
                            optionList?.add(result!![position].correct_answer)
                            optionList?.addAll(result!![position].incorrect_answers)
                            Log.e(ContentValues.TAG, "data: $optionList")
                            binding.BtnOp1.text = optionList!![0]
                            binding.BtnOp2.text = optionList!![1]
                            binding.Btnop3.text = optionList!![2]
                            binding.BtnOp4.text = optionList!![3]
                            mCallback?.update(score)
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("TAG", "onError: $anError")
                    }
                })
        }
        binding.BtnNext.setOnClickListener {
            cmn.startmusic(it.context)
            if (!flag) {
                Toast.makeText(it.context, "select option", Toast.LENGTH_SHORT).show()
            } else {
                if (result!![position].correct_answer == selected) {
                    score++
                    mCallback?.update(score)
                    //  binding.TVTotal.text = "Score: $score"
                }
                if (position == result!!.size - 1) {
                    val scoreData = score.toString()
                    val intent = Intent(it.context, GameOverActivity::class.java)
                    intent.putExtra("score", scoreData)
                    startActivity(intent)
                } else {
                    binding.O1.setBackgroundResource(R.drawable.shape)
                    binding.O2.setBackgroundResource(R.drawable.shape)
                    binding.O3.setBackgroundResource(R.drawable.shape)
                    binding.O4.setBackgroundResource(R.drawable.shape)
                    binding.BtnOp1.setTextColor(Color.BLACK)
                    binding.BtnOp2.setTextColor(Color.BLACK)
                    binding.Btnop3.setTextColor(Color.BLACK)
                    binding.BtnOp4.setTextColor(Color.BLACK)
                    position += 1
                    optionList?.clear()
                    optionList?.add(result!![position].correct_answer)
                    optionList?.addAll(result!![position].incorrect_answers)
                    optionList?.shuffle()
                    binding.TvQ.text = result!![position].question
                    //    mViewPager = view?.findViewById(R.id.vp)
                    // mViewPagerAdapter = ViewPagerAdapter(it.context, arrayOf(result!!.get(position).question))
                    mViewPager?.adapter = mViewPagerAdapter
                    binding.BtnOp1.text = optionList!![0]
                    binding.BtnOp2.text = optionList!![1]
                    binding.Btnop3.text = optionList!![2]
                    binding.BtnOp4.text = optionList!![3]
                    binding.TvCount.text = "Question ${position + 1}"
                    Log.e(ContentValues.TAG, "result${result!!.size} ")
                    flag = false
                    buttonSelection = false
                }
            }
        }
        binding.O1.setOnClickListener {
            cmn.startmusic(it.context)
            binding.O1.setBackgroundResource(R.drawable.shapegreen)
            binding.O2.setBackgroundResource(R.drawable.shape)
            binding.O3.setBackgroundResource(R.drawable.shape)
            binding.O4.setBackgroundResource(R.drawable.shape)
            binding.BtnOp1.setTextColor(Color.WHITE)
            binding.BtnOp2.setTextColor(Color.BLACK)
            binding.Btnop3.setTextColor(Color.BLACK)
            binding.BtnOp4.setTextColor(Color.BLACK)
            flag = true
            selected = binding.BtnOp1.text.toString()

        }
        binding.O2.setOnClickListener {
            cmn.startmusic(it.context)
            binding.O2.setBackgroundResource(R.drawable.shapegreen)
            binding.O1.setBackgroundResource(R.drawable.shape)
            binding.O3.setBackgroundResource(R.drawable.shape)
            binding.O4.setBackgroundResource(R.drawable.shape)
            binding.BtnOp1.setTextColor(Color.BLACK)
            binding.BtnOp2.setTextColor(Color.WHITE)
            binding.Btnop3.setTextColor(Color.BLACK)
            binding.BtnOp4.setTextColor(Color.BLACK)
            flag = true
            selected = binding.BtnOp2.text.toString()

        }
        binding.O3.setOnClickListener {
            cmn.startmusic(it.context)
            binding.O3.setBackgroundResource(R.drawable.shapegreen)
            binding.O2.setBackgroundResource(R.drawable.shape)
            binding.O1.setBackgroundResource(R.drawable.shape)
            binding.O4.setBackgroundResource(R.drawable.shape)
            binding.BtnOp1.setTextColor(Color.BLACK)
            binding.BtnOp2.setTextColor(Color.BLACK)
            binding.Btnop3.setTextColor(Color.WHITE)
            binding.BtnOp4.setTextColor(Color.BLACK)
            flag = true
            selected = binding.Btnop3.text.toString()
        }
        binding.O4.setOnClickListener {
            cmn.startmusic(it.context)
            binding.O4.setBackgroundResource(R.drawable.shapegreen)
            binding.O2.setBackgroundResource(R.drawable.shape)
            binding.O1.setBackgroundResource(R.drawable.shape)
            binding.O3.setBackgroundResource(R.drawable.shape)
            binding.BtnOp1.setTextColor(Color.BLACK)
            binding.BtnOp2.setTextColor(Color.BLACK)
            binding.Btnop3.setTextColor(Color.BLACK)
            binding.BtnOp4.setTextColor(Color.WHITE)
            flag = true
            selected = binding.BtnOp4.text.toString()
        }
    }

    interface OnCallbackReceived {
        fun update(score:Int)
    }
}
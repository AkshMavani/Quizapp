package com.example.demo_quizapp.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.demo_quizapp.R
import com.example.demo_quizapp.databinding.FragmentDifficultyBinding
import com.example.demo_quizapp.utils.Commonclass


// TODO: Rename parameter arguments, choose names that match

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragemntDifficulty : Fragment() {
  private lateinit var _binding: FragmentDifficultyBinding
    private val binding get() = _binding
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment = Fragment()
        val bundle = Bundle()
        bundle.putBoolean("key", true)
        fragment.arguments = bundle

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
       _binding= FragmentDifficultyBinding.inflate(inflater,container,false)

        binding.ClEasy.setOnClickListener {
            Commonclass().startmusic(it.context)
            val data=Bundle()
            data.putBoolean("Easy",true)
            val fragmentPlay=FragmentPlay()
            fragmentPlay.arguments=data
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView3,fragmentPlay)?.commit()
        }
        binding.ClMedium.setOnClickListener {
            Commonclass().startmusic(it.context)
            val fragment=FragmentPlay()
        val fragmentTransaction: FragmentTransaction =requireFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView3,fragment).commit()

        }

        binding.ClHard.setOnClickListener {
            Commonclass().startmusic(it.context)
                val data=Bundle()
                data.putBoolean("Easy",true)
                val fragmentPlay=FragmentPlay()
                fragmentPlay.arguments=data
                fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView3,fragmentPlay)?.commit()
        }

        return binding.root
       // return inflater.inflate(R.layout.fragment_difficulty, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragemntDifficulty().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
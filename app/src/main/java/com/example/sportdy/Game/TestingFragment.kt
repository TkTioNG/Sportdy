package com.example.sportdy.Game


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.sportdy.R
import com.example.sportdy.DrawerLocker
import androidx.appcompat.app.AppCompatActivity





/**
 * A simple [Fragment] subclass.
 */
class TestingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //(activity as DrawerLocker).setDrawerEnabled(false)
        val actionbar = (activity as AppCompatActivity).supportActionBar!!
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDefaultDisplayHomeAsUpEnabled(true)

        return inflater.inflate(R.layout.fragment_testing, container, false)
    }
}

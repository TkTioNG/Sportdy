package com.example.sportdy.Game


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager

import com.example.sportdy.R
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 */
class GameFragment() : Fragment() {

    private lateinit var gameFragmentAdapter: GameFragmentAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
//    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_game, container, false)

//        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        gameFragmentAdapter = GameFragmentAdapter(childFragmentManager)

        viewPager = view.findViewById(R.id.mainPager)
        viewPager.adapter = gameFragmentAdapter

//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//
//            override fun onPageScrollStateChanged(state: Int) {
//            }
//
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//
//            }
//            override fun onPageSelected(position: Int) {
//                //viewModel.onSwipe(position)
//            }
//        })

        tabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        return view
    }
}

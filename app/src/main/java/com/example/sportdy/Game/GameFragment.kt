package com.example.sportdy.Game


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        gameFragmentAdapter = GameFragmentAdapter(childFragmentManager)

        viewPager = view.findViewById(R.id.mainPager)
        viewPager.adapter = gameFragmentAdapter

        tabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        return view
    }

    companion object {
        const val FROM_FIND_GAME_FRAGMENT = 0
        const val FROM_MY_GAME_FRAGMENT = 1
        const val FROM_HISTORY_FRAGMENT = 2

        const val ADD_GAME_REQUEST_CODE = 1001
        const val EDIT_GAME_REQUEST_CODE = 1002
    }

}

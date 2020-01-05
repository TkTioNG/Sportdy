package com.example.sportdy.Game

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class GameFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment = when(position) {
            0 -> FindGameFragment()
            1 -> MyGameFragment()
            2 -> HistoryFragment()
            else -> FindGameFragment()
        }
        return fragment
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Find Game"
            1 -> "My Game"
            2 -> "History"
            else -> "Find Game"
        }
    }
}


package com.example.sportdy.Friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.example.sportdy.R
import kotlinx.android.synthetic.main.activity_main.*

class FriendFragment : Fragment() {

    companion object {
        fun newInstance() = FriendFragment()
    }

    private lateinit var viewModel: FriendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friend, container, false)

        activity!!.title = "Friend"

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(FriendViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

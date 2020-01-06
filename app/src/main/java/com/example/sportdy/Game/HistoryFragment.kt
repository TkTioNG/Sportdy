package com.example.sportdy.Game


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportdy.Database.SportGame
import com.example.sportdy.Database.SportGameViewModel

import com.example.sportdy.R

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment(), GameAdapter.OnGameClickListener {

    private lateinit var sportGameViewModel: SportGameViewModel
    //private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        //navController = activity!!.findNavController(R.id.mainHostFragment)

//        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewGame)
//        val adapter = GameAdapter(activity!!.applicationContext, this)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

//        sportGameViewModel = ViewModelProvider(this).get(SportGameViewModel::class.java)
//
//        sportGameViewModel.historySportGames.observe(viewLifecycleOwner,
//            Observer { sportGameList: List<SportGame> ->
//                sportGameList.let {
//                    if (it.isNotEmpty()) {
//                        adapter.setSportGame(it)
//                        Toast.makeText(activity!!.applicationContext, "Number: " + it.size, Toast.LENGTH_SHORT).show()
//                    }
//                }
//            })

//        val buttonLol = view.findViewById<Button>(R.id.buttonLOL)
//        buttonLol.setOnClickListener { if (activity != null) activity!!.findViewById<ViewPager>(R.id.mainPager).currentItem = 1 }
//
//        val btnTesting = view.findViewById<Button>(R.id.btnTesting)
//        btnTesting.setOnClickListener { navController.navigate(R.id.action_gameFragment_to_testingFragment) }

        return view
    }


    override fun onGameClick(position: Int) {
        Log.d("FindGame","Clicked ${position}")
        //sportGameViewModel.historySportGames.value!!.get(position)
        //navController.navigate(R.id.action_gameFragment_to_testingFragment)
    }

}

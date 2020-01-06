package com.example.sportdy.Game


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.example.sportdy.R
import com.example.sportdy.DrawerLocker
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat


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

        val view = inflater.inflate(R.layout.fragment_testing, container, false)

        val tvGDGameName = view.findViewById<TextView>(R.id.tvGDGameName)
        val tvGDHosterName = view.findViewById<TextView>(R.id.tvGDHosterName)
        val tvGDGameType = view.findViewById<TextView>(R.id.tvGDGameType)
        val tvGDGameDate = view.findViewById<TextView>(R.id.tvGDGameDate)
        val tvGDGameTime = view.findViewById<TextView>(R.id.tvGDGameTime)
        val tvGDLocation = view.findViewById<TextView>(R.id.tvGDLocation)
        val tvGDStreet1 = view.findViewById<TextView>(R.id.tvGDStreet1)
        val tvGDStreet2 = view.findViewById<TextView>(R.id.tvGDStreet2)
        val tvGDArea = view.findViewById<TextView>(R.id.tvGDArea)
        val tvGDPostcode = view.findViewById<TextView>(R.id.tvGDPostcode)
        val tvGDState = view.findViewById<TextView>(R.id.tvGDState)
        val tvGDMaxPpl = view.findViewById<TextView>(R.id.tvGDMaxPpl)
        val tvGDNowPpl = view.findViewById<TextView>(R.id.tvGDNowPpl)
        val tvGDDesc     = view.findViewById<TextView>(R.id.tvGDDesc)

        tvGDGameName.text = arguments?.getString("gameName")
        tvGDHosterName.text = arguments?.getString("hosterName")
        tvGDGameType.text = arguments?.getString("gameType")
        tvGDGameDate.text = SimpleDateFormat("dd MMM yyyy").format(arguments?.getLong("gameDate", 0))
        var time = arguments?.getInt("gameTime", 0)
        var hour = time!! / 60
        var amPm : String = "AM"
        if (hour > 12) {
            amPm = "PM"
            hour -= 12
        }
        tvGDGameTime.text = String.format("%02d %02d %s", hour, time % 60, amPm)
        tvGDLocation.text = arguments?.getString("location")
        tvGDStreet1.text = arguments?.getString("street1")
        tvGDStreet2.text = arguments?.getString("street2")
        tvGDArea.text = arguments?.getString("area")
        tvGDPostcode.text = arguments?.getInt("postcode",0).toString()
        tvGDState.text = arguments?.getString("state")
        tvGDMaxPpl.text = arguments?.getInt("maxppl", 10).toString()
        tvGDNowPpl.text = arguments?.getInt("nowppl", 1).toString()
        tvGDDesc.text = arguments?.getString("description").toString()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toast.makeText(activity!!.applicationContext,"Surprise", Toast.LENGTH_SHORT).show()
        super.onCreate(savedInstanceState)
    }
}

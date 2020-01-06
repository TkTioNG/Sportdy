package com.example.sportdy.Game

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.sportdy.R
import kotlinx.android.synthetic.main.activity_add_game.*
import java.text.SimpleDateFormat
import java.util.*

class AddGameActivity : AppCompatActivity() {

    private var valid: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val spGameType = findViewById<Spinner>(R.id.spGameType)
        ArrayAdapter.createFromResource(
            this,
            R.array.GameType,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spGameType.adapter = adapter
        }

        val calendar = Calendar.getInstance()

        val date = DatePickerDialog.OnDateSetListener { _view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateLabel(calendar.time)
        }

        val tvGameDate = findViewById<TextView>(R.id.tvGameDate)
        tvGameDate.setOnClickListener {
            DatePickerDialog(
                this,
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val time = TimePickerDialog.OnTimeSetListener { _view, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            updateTimeLabel(hourOfDay, minute)
        }

        val tvGameTime = findViewById<TextView>(R.id.tvGameTime)
        tvGameTime.setOnClickListener {
            val tp = TimePickerDialog(
                this,
                R.style.Theme_MaterialComponents_Light_Dialog,
                time,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
            tp.show()
        }

        val btnSaveGame = findViewById<Button>(R.id.btnAddGame)
        btnSaveGame.setOnClickListener(onAddGame())

    }

    private fun onAddGame(): View.OnClickListener? {

        return View.OnClickListener {
            if (checkAddValid()) {
                val game_name = tvGameName.text.toString()
                val game_type = spGameType.selectedItem.toString()
                val game_date =
                    SimpleDateFormat("dd MMM yyyy").parse(tvGameDate.text.toString())!!.time

                var time = 0
                var tvGameTime = tvGameTime.text.toString().split(':')
                var tvGameTime2 = tvGameTime[1].split(' ')
                time = tvGameTime[0].toInt() * 60 + tvGameTime2[0].toInt()
                if (tvGameTime2[1].equals("PM") && tvGameTime[0].toInt() <= 12)
                    time += 12 * 60

                val game_time = time
                val game_location = tvLocation.text.toString()
                val game_street1 = tvStreet1.text.toString()
                val game_street2 = tvStreet2.text.toString()
                val game_area = tvArea.text.toString()
                val game_postcode = tvPostcode.text.toString().toInt()
                val game_state = tvState.text.toString()
                val game_max_ppl = tvMaxPpl.text.toString().toInt()
                val game_now_ppl = 1
                val game_desc = tvDesc.text.toString()
                val game_hostername = tvHosterName.text.toString()

                val intent = Intent()
                intent.putExtra(ADD_GAME_NAME, game_name)
                intent.putExtra(ADD_GAME_TYPE, game_type)
                intent.putExtra(ADD_GAME_DATE, game_date)
                intent.putExtra(ADD_GAME_TIME, game_time)
                intent.putExtra(ADD_GAME_LOCATION, game_location)
                intent.putExtra(ADD_GAME_STREET1, game_street1)
                intent.putExtra(ADD_GAME_STREET2, game_street2)
                intent.putExtra(ADD_GAME_AREA, game_area)
                intent.putExtra(ADD_GAME_POSTCODE, game_postcode)
                intent.putExtra(ADD_GAME_STATE, game_state)
                intent.putExtra(ADD_GAME_MAXPPL, game_max_ppl)
                intent.putExtra(ADD_GAME_NOWPPL, game_now_ppl)
                intent.putExtra(ADD_GAME_DESC, game_desc)
                intent.putExtra(ADD_GAME_HOSTERNAME, game_hostername)

                setResult(Activity.RESULT_OK, intent)
                finish()

            } else {
                Toast.makeText(
                    applicationContext,
                    "Please Fill Up All Colummns Correctly",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkAddValid(): Boolean {
        valid = false


        return valid
    }

    private fun updateDateLabel(date: Date) {
        val sdf = SimpleDateFormat("dd MMM yyyy").format(date)
        val tvGameDate = findViewById<TextView>(R.id.tvGameDate)
        tvGameDate.setText(sdf)
    }

    private fun updateTimeLabel(hour: Int, minute: Int) {
        var ampm = "AM"
        var newHour = hour
        if (hour >= 12) {
            newHour -= 12
            ampm = "PM"
        }
        val sdf = String.format("%02d:%02d %s", newHour, minute, ampm)
        val tvGameTime = findViewById<TextView>(R.id.tvGameTime)
        tvGameTime.setText(sdf)
    }

    companion object {
        const val ADD_GAME_NAME = "com.example.sportdy.Game.GAME_NAME"
        const val ADD_GAME_TYPE = "com.example.sportdy.Game.GAME_TYPE"
        const val ADD_GAME_DATE = "com.example.sportdy.Game.GAME_DATE"
        const val ADD_GAME_TIME = "com.example.sportdy.Game.GAME_TIME"
        const val ADD_GAME_LOCATION = "com.example.sportdy.Game.GAME_LOCATION"
        const val ADD_GAME_STREET1 = "com.example.sportdy.Game.GAME_STREET1"
        const val ADD_GAME_STREET2 = "com.example.sportdy.Game.GAME_STREET2"
        const val ADD_GAME_AREA = "com.example.sportdy.Game.GAME_AREA"
        const val ADD_GAME_POSTCODE = "com.example.sportdy.Game.GAME_POSTCODE"
        const val ADD_GAME_STATE = "com.example.sportdy.Game.GAME_STATE"
        const val ADD_GAME_MAXPPL = "com.example.sportdy.Game.GAME_MAXPPL"
        const val ADD_GAME_NOWPPL = "com.example.sportdy.Game.GAME_NOWPPL"
        const val ADD_GAME_DESC = "com.example.sportdy.Game.GAME_DESC"
        const val ADD_GAME_HOSTERNAME = "com.example.sportdy.Game.GAME_HOSTERNAME"
    }

}

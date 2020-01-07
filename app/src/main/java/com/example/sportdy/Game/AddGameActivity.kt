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
import com.example.sportdy.Game.GameFragment.Companion.EDIT_GAME_REQUEST_CODE
import com.example.sportdy.Game.TestingFragment.Companion.EDIT_GAME_CODE
import com.example.sportdy.R
import kotlinx.android.synthetic.main.activity_add_game.*
import java.text.SimpleDateFormat
import java.util.*

class AddGameActivity : AppCompatActivity() {

    private var valid: Boolean = true

    private lateinit var tvGameName: TextView
    private lateinit var tvHosterName: TextView
    private lateinit var spGameType: Spinner
    private lateinit var tvGameDate: TextView
    private lateinit var tvGameTime: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvStreet1: TextView
    private lateinit var tvStreet2: TextView
    private lateinit var tvArea: TextView
    private lateinit var tvPostcode: TextView
    private lateinit var tvState: TextView
    private lateinit var tvMaxPpl: TextView
    private lateinit var tvDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        spGameType = findViewById<Spinner>(R.id.spGameType)
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

        tvGameDate = findViewById<TextView>(R.id.tvGameDate)
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

        tvGameTime = findViewById<TextView>(R.id.tvGameTime)
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

        tvGameName = findViewById(R.id.tvGameName)
        tvHosterName = findViewById(R.id.tvHosterName)
        tvLocation = findViewById(R.id.tvLocation)
        tvStreet1 = findViewById(R.id.tvStreet1)
        tvStreet2 = findViewById(R.id.tvStreet2)
        tvArea = findViewById(R.id.tvArea)
        tvPostcode = findViewById(R.id.tvPostcode)
        tvState = findViewById(R.id.tvState)
        tvMaxPpl = findViewById(R.id.tvMaxPpl)
        tvDesc = findViewById(R.id.tvDesc)

        val btnSaveGame = findViewById<Button>(R.id.btnAddGame)
        btnSaveGame.setOnClickListener(onAddGame())

        if (intent.getIntExtra(EDIT_GAME_CODE, 0) == EDIT_GAME_REQUEST_CODE) {
            toolbar.title = "Update Sport Game"
            setUpdateGame()
        }
    }

    private fun setUpdateGame() {
        val game_name = intent.getStringExtra(ADD_GAME_NAME)
        val game_type = intent.getStringExtra(ADD_GAME_TYPE)
        val game_date = intent.getStringExtra(ADD_GAME_DATE)
        val game_time = intent.getStringExtra(ADD_GAME_TIME)
        val location = intent.getStringExtra(ADD_GAME_LOCATION)
        val street1 = intent.getStringExtra(ADD_GAME_STREET1)
        val street2 = intent.getStringExtra(ADD_GAME_STREET2)
        val area = intent.getStringExtra(ADD_GAME_AREA)
        val postcode = intent.getStringExtra(ADD_GAME_POSTCODE)
        val state = intent.getStringExtra(ADD_GAME_STATE)
        val maxppl = intent.getStringExtra(ADD_GAME_MAXPPL)
        val game_desc = intent.getStringExtra(ADD_GAME_DESC)
        val hoster_name = intent.getStringExtra(ADD_GAME_HOSTERNAME)

        spGameType = findViewById(R.id.spGameType)
        tvGameDate = findViewById(R.id.tvGameDate)
        tvGameTime = findViewById(R.id.tvGameTime)

        spGameType.setSelection(1)

        tvGameName.text = game_name
        tvGameDate.text = game_date
        tvGameTime.text = game_time
        tvLocation.text = location
        tvStreet1.text = street1
        tvStreet2.text = street2
        tvArea.text = area
        tvPostcode.text = postcode
        tvState.text = state
        tvMaxPpl.text = maxppl
        tvDesc.text = game_desc
        tvHosterName.text = hoster_name
        tvHosterName.isEnabled = false

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
        valid = true

        val tvHosterName = findViewById<TextView>(R.id.tvHosterName)
        val tvArea = findViewById<TextView>(R.id.tvArea)
        val tvGameName = findViewById<TextView>(R.id.tvGameName)
        val tvGameDate = findViewById<TextView>(R.id.tvGameDate)
        val tvGameTime = findViewById<TextView>(R.id.tvGameTime)
        val tvLocation = findViewById<TextView>(R.id.tvLocation)
        val tvPostcode = findViewById<TextView>(R.id.tvPostcode)
        val tvState = findViewById<TextView>(R.id.tvState)
        val tvStreet1 = findViewById<TextView>(R.id.tvStreet1)
        val tvStreet2 = findViewById<TextView>(R.id.tvStreet2)


        if(!tvHosterName.text.isNotEmpty()) {
            tvHosterName.error = "Hoster Name is Required"
            valid = false
        }
        if(!tvArea.text.isNotEmpty()) {
            tvArea.error = "Game Area is Required"
            valid = false
        }
        if (!tvGameName.text.isNotEmpty()) {
            tvGameName.error = "Game Name is Required"
            valid = false
        }
        if (!tvGameDate.text.isNotEmpty()) {
            tvGameDate.error = "Game Date is Required"
            valid = false
        }
        if (!tvGameTime.text.isNotEmpty()) {
            tvGameTime.error = "Game Time is Required"
            valid = false
        }
        if (!tvLocation.text.isNotEmpty()) {
            tvLocation.error = "Game Location is Required"
            valid = false
        }
        if (!tvPostcode.text.isNotEmpty()) {
            tvPostcode.error = "Postcode is Required"
            valid = false
        }
        else if (tvPostcode.text.toString().toInt() < 10000 || tvPostcode.text.toString().toInt() > 99999) {
            tvPostcode.error = "Input should be 5 digits"
            valid = false
        }
        if (!tvState.text.isNotEmpty()) {
            tvState.error = "State is Required"
            valid = false
        }
        if (!tvStreet1.text.isNotEmpty()) {
            tvStreet1.error = "Street is Required"
            valid = false
        }

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

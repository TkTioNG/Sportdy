package com.example.sportdy.Login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportdy.MainActivity
import com.example.sportdy.R
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    private val sharedPrefFile = "users"

    lateinit var username:EditText
    lateinit var password: EditText


    private var valid: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener(onLogin())

        val registerBtn = findViewById<Button>(R.id.registerBtn)
        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    private fun onLogin(): View.OnClickListener? {
        return View.OnClickListener {

            if(checkAddValid()){
                val url = getString(R.string.url_server) + getString(R.string.url_Users_read_one) +
                        "?Username=" + username.text

                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener { response ->
                        // Process the JSON
                        try{
                            if(response != null){
                                val strResponse = response.toString()
                                val jsonResponse  = JSONObject(strResponse)
                                var userpass = jsonResponse.getString("Password")

                                if (password.text.toString() != userpass) {

                                    Toast.makeText(applicationContext, "Wrong Password"+ userpass , Toast.LENGTH_LONG).show()
                                    password.text.clear()

                                }else{
                                    val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                                        Context.MODE_PRIVATE)
                                    val name:String = username.text.toString()
                                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                                    editor.putString("name_key",name)
                                    editor.apply()
                                    editor.commit()
                                    val start = Intent(this,MainActivity::class.java)
                                    startActivity(start)
                                    finish()
                                }


                            }
                        }catch (e:Exception){
                            Log.d("Main", "Response-1: %s".format(e.message.toString()))
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.d("Main", "Response-2: %s".format(error.message.toString()))
                        Toast.makeText(applicationContext, "Username is used by other users", Toast.LENGTH_LONG).show()
                    }
                )

                //Volley request policy, only one time request
                jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    10, //no retry
                    1f
                )
                UserSingleton.getInstance(applicationContext).addToRequestQueue(jsonObjectRequest)


//                val ref = FirebaseDatabase.getInstance().getReference("Users")
//                val valueEventListener = object : ValueEventListener {
//                    override fun onDataChange(dataSnapshot: DataSnapshot)
//                    {
//                    val user = dataSnapshot.getValue(Users::class.java)
//                    if(password.equals(user!!.password)){
//                        Toast.makeText(applicationContext,"login Successfully", Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(applicationContext,"Enter the correct pin", Toast.LENGTH_SHORT).show()}
//                    }
//
//                    override fun onCancelled(databaseError: DatabaseError) {
//                    }
//                }
//                ref.addListenerForSingleValueEvent(valueEventListener)

            }
        }
    }
    private fun checkAddValid(): Boolean {
        valid = true

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)



        if (!username.text.isNotEmpty()) {
            username.error = "Username is Required"
            valid = false
        }
        if (!password.text.isNotEmpty()) {
            password.error = "Password is Required"
            valid = false
        }

        return valid
    }




}

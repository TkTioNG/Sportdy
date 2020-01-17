package com.example.sportdy.Login

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import android.util.Patterns
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
import com.example.sportdy.R
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {
    private var valid: Boolean = true
    lateinit var editTextUsername:EditText
    lateinit var editTextFullName: EditText
    lateinit var editTextEmail: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextEmail = findViewById(R.id.editTextEmail)
        password = findViewById(R.id.editTextPass)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener(onRegister())
    }

    private fun onRegister(): View.OnClickListener? {

        return View.OnClickListener {
            if(checkRegValid()){
                val username = editTextUsername.text.toString()
                val fullname = editTextFullName.text.toString()
                val email = editTextEmail.text.toString()
                val password = editTextPass.text.toString()

//                val ref = FirebaseDatabase.getInstance().getReference("Users")
//                val user = Users(username, fullname, email, password)
//
//                ref.child(username).setValue(user).addOnCompleteListener{
//
//                    Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
//                }

                val url = getString(R.string.url_server) + getString(R.string.url_Users_create) +
                        "?Username=" + username + "&Full_Name=" + fullname + "&Email=" + email +"&Password=" + password

                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener { response ->
                        // Process the JSON
                        try{
                            if(response != null){
                                val strResponse = response.toString()
                                val jsonResponse  = JSONObject(strResponse)
                                val success: String = jsonResponse.get("success").toString()

                                if(success.equals("1")){
                                    Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                                    finish()
                                    //Add record to user list
                                    //userList.add(user)
                                }else{
                                    Toast.makeText(applicationContext, "Record not saved", Toast.LENGTH_LONG).show()
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
                    0, //no retry
                    1f
                )
                UserSingleton.getInstance(applicationContext).addToRequestQueue(jsonObjectRequest)
          }
        }
    }
    private fun checkRegValid(): Boolean {
        valid = true

        val editTextUsername = findViewById<TextView>(R.id.editTextUsername)
        val editTextFullname = findViewById<TextView>(R.id.editTextFullName)
        val editTextEmail = findViewById<TextView>(R.id.editTextEmail)
        val editTextPassword = findViewById<TextView>(R.id.editTextPass)
        val editTextConfirmPassword = findViewById<TextView>(R.id.editTextConfirmPassword)

        if (!editTextUsername.text.isNotEmpty()) {
            editTextUsername.error = "Username is Required"
            valid = false
        }
        if (!editTextFullname.text.isNotEmpty()) {
            editTextFullname.error = "Full name is Required"
            valid = false
        }
        if (!editTextEmail.text.isNotEmpty()) {
            editTextEmail.error = "Email is Required"
            valid = false
        }else if(!isEmailValid(editTextEmail.text)){
            editTextEmail.error = "Wrong Email Format"
            valid = false
        }
        if (!editTextPassword.text.isNotEmpty()) {
            editTextPassword.error = "Password is Required"
            valid = false
        }
        if (!editTextConfirmPassword.text.isNotEmpty()) {
            editTextConfirmPassword.error = "Password does not match"
            valid = false
        }
        if(editTextPassword.text.toString() != editTextConfirmPassword.text.toString()){
            editTextConfirmPassword.error = "Password does not match"
            editTextPassword.text=""
            editTextConfirmPassword.text=""
            valid = false
        }
        return valid
    }
    fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()}
}

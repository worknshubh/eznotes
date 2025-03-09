package com.example.eznotes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class signupScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup_screen)
        var user_mail = findViewById<EditText>(R.id.email)
        var user_pass = findViewById<EditText>(R.id.password)
        var conf_pass = findViewById<EditText>(R.id.confirmpassword)
        var signup = findViewById<Button>(R.id.signup)
        var progress = findViewById<ProgressBar>(R.id.progressBar)
        var login_redirect = findViewById<TextView>(R.id.loginredirect)
        signup.setOnClickListener{
            var isvalidated:Boolean = validateuser(user_mail, user_pass, conf_pass,progress)
            if(!isvalidated){

            }
            else{
                importtofirebase(user_mail,user_pass,progress,signup)
            }
        }


        login_redirect.setOnClickListener{
            val intent = Intent(this,loginScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun importtofirebase(userMail: EditText, userPass: EditText,progress: ProgressBar,signup : Button) {
        progress.visibility = View.VISIBLE
        signup.visibility = View.GONE
        val email = userMail.text.toString()
        val pass = userPass.text.toString()
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this){ task->
            if(task.isSuccessful){
                Toast.makeText(this, "Account Created Successfully Kindly check Email", Toast.LENGTH_SHORT).show()
                firebaseAuth.currentUser?.sendEmailVerification()
            }
            else{
                val errormessage = task.exception?.message
                Log.d("ERR", "importtofirebase: $errormessage")
                Toast.makeText(this, "Sign Up Failed : $errormessage", Toast.LENGTH_SHORT).show()
            }
        }
        progress.visibility = View.GONE
        signup.visibility = View.VISIBLE
    }




    fun validateuser(userMail: EditText, userPass: EditText,confPass: EditText,progress:ProgressBar):Boolean{
        if(!Patterns.EMAIL_ADDRESS.matcher(userMail.text.toString()).matches()){
            userMail.setError("Invalid Email Address")
            return false;
        }

        if (userPass.length()<6){
            userPass.setError("Password Length should be > 6")
            return false
        }
        if(userPass.text.toString()!=confPass.text.toString()){
            confPass.setError("Password does not match")
            return false
        }
        return true
    }
}
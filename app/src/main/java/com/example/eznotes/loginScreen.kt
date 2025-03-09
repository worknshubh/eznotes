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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class loginScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)
        var user_mail = findViewById<EditText>(R.id.email)
        var user_pass = findViewById<EditText>(R.id.password)
        var login = findViewById<Button>(R.id.login)
        var progress = findViewById<ProgressBar>(R.id.progressBar)
        var signup_redirect = findViewById<TextView>(R.id.signupredirect)

        login.setOnClickListener{
            var isvalidated:Boolean = validateuser(user_mail, user_pass)
            if(!isvalidated){

            }
            else{
                loginofirebase(user_mail,user_pass,progress,login)
            }
        }
        signup_redirect.setOnClickListener {
            val intent = Intent(this, signupScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateuser(userMail: EditText, userPass: EditText): Boolean {
        if(!Patterns.EMAIL_ADDRESS.matcher(userMail.text.toString()).matches()){
            userMail.setError("Invalid Email Address")
            return false;
        }

        if (userPass.length()<6){
            userPass.setError("Password Length should be > 6")
            return false
        }
        return true
    }

    private fun loginofirebase(userMail: EditText, userPass: EditText, progress: ProgressBar, login: Button) {
        progress.visibility = View.VISIBLE
        login.visibility = View.GONE
        val email = userMail.text.toString()
        val pass = userPass.text.toString()
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this){ task->
            if(task.isSuccessful){
                if (firebaseAuth.currentUser?.isEmailVerified() == false){
                    Toast.makeText(this, "Email haven't verified yet", Toast.LENGTH_SHORT).show()
                }
                else{
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
            else{
                val errormessage = task.exception?.message
                Log.d("ERR", "importtofirebase: $errormessage")
                Toast.makeText(this, "Login Failed : $errormessage", Toast.LENGTH_SHORT).show()
            }
        }
        progress.visibility = View.GONE
        login.visibility = View.VISIBLE
    }
}
package com.example.eznotes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class splashscreen : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splashscreen)
        var firebaseUser = FirebaseAuth.getInstance()

            Handler(Looper.getMainLooper()).postDelayed({
                if(firebaseUser.currentUser==null) {
                    var intent = Intent(this, signupScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 3000)


    }
}
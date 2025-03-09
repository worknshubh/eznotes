package com.example.eznotes

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class notes : AppCompatActivity() {
    var userArrayList = ArrayList<userdata>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)
        val db = Firebase.firestore
        val currentuser = FirebaseAuth.getInstance().currentUser
        var savebtn = findViewById<FloatingActionButton>(R.id.save)
        var notestitle = findViewById<EditText>(R.id.editTextText)
        var notesdesc = findViewById<EditText>(R.id.desc)
        var time = Timestamp.now()


            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            supportActionBar?.hide()


        savebtn.setOnClickListener{
            var notedata = userdata(notestitle.text.toString(),notesdesc.text.toString(),time)
            if (currentuser != null) {
                if (notestitle.text.toString() == "" || notesdesc.text.toString()== ""){
                    Toast.makeText(this,"Field Cannot be Empty",Toast.LENGTH_SHORT).show()
                }
                else{
                    savenotetofirebase(notedata,db, currentuser.uid)

                }

            }
            else{
                Toast.makeText(this,"User Not Logged In",Toast.LENGTH_SHORT).show()
            }

        }
    }
// Storing data into firebase

    private fun savenotetofirebase(notedata: userdata, db: FirebaseFirestore,uid:String) {
        db.collection("Users").document(uid).collection("notes")
            .add(notedata)
            .addOnSuccessListener {
        Toast.makeText(this, "Noted Down Successfully", Toast.LENGTH_SHORT).show()
    }
            .addOnFailureListener{
                Toast.makeText(this, "Failed to Note it Down", Toast.LENGTH_SHORT).show()
            }
    }


}

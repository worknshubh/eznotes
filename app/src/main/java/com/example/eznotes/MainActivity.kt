package com.example.eznotes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var user: FirebaseAuth
    private lateinit var adapter: myAdapter
    var userArrayList = ArrayList<userdata>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // for hiding status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
            db = FirebaseFirestore.getInstance()
                user = FirebaseAuth.getInstance()

        userArrayList = arrayListOf()

        //importing recyclerview and setting it up with myadapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        adapter = myAdapter(this,userArrayList) // Initialize adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        fetchnotesfromfirestore()
        // for creating a new note
        var fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            var intent = Intent(this, notes::class.java)
            startActivity(intent)
        }


    }

    private fun fetchnotesfromfirestore() {
        val currentUser = user.currentUser
        if (currentUser != null) {
            db.collection("Users").document(currentUser.uid).collection("notes").orderBy("timestamp",
                Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        // Handle error (e.g., show a Toast)
                        return@addSnapshotListener
                    }

                    if (snapshots != null) {
                        userArrayList.clear() // Clear the old list

                        for (document in snapshots) {
                            val note = document.toObject(userdata::class.java)
                            userArrayList.add(note)
                        }

                        adapter.notifyDataSetChanged() // Refresh RecyclerView
                    }
                }
        }
    }

}

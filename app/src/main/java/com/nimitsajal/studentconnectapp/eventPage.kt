package com.nimitsajal.studentconnectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main_feed.*

class eventPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_page)

        var username = intent.getStringExtra("username")
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if(user != null){
            val user_table = db.collection("User Table").document(user.uid.toString())
            user_table.get().addOnSuccessListener { result ->
                if(result != null){
                    username = result.getString("Username").toString()
                    Log.d("profilePage", username.toString())
                }
                else{
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
            }
        }

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        btnChat.setOnClickListener {
            val intent = Intent(this, currentChats::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
            finish()
        }

        btnProfile.setOnClickListener {
            val intent = Intent(this, profilePage::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
            finish()
        }

        btnFeed.setOnClickListener {
            val intent = Intent(this, mainFeed::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
            finish()
        }
    }
}
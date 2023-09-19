package com.example.whatsapp_clone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase

    private lateinit var senderUid:String
    private lateinit var receiverUid:String

    private lateinit var senderRoom:String
    private lateinit var receiverRoom:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()

        senderUid = FirebaseAuth.getInstance().uid.toString()

        binding.imageView2.setOnClickListener {
            if(binding.messageBox.text.isEmpty()){
                Toast.makeText(this,"Please Enter your Message",Toast.LENGTH_LONG).show()
            }else{}
        }

    }
}
package com.example.whatsapp_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.whatsapp_clone.activity.NumberActivity
import com.example.whatsapp_clone.adapter.ViewPagerAdapter
import com.example.whatsapp_clone.databinding.ActivityMainBinding
import com.example.whatsapp_clone.ui.CallFragment
import com.example.whatsapp_clone.ui.ChatFragment
import com.example.whatsapp_clone.ui.StatusFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding ?= null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null){
            startActivity(Intent(this,NumberActivity::class.java))
            finish()
        }

        val fragmentArrayList = ArrayList<Fragment>()

        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())

        val adapter = ViewPagerAdapter(this,supportFragmentManager,fragmentArrayList)

        binding!!.viewPagger.adapter = adapter

        binding!!.tabs.setupWithViewPager(binding!!.viewPagger)

    }
}
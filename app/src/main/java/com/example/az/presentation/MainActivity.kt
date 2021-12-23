package com.example.az.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import com.example.az.R
import com.example.az.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavBar()

    }

    private fun initNavBar() {
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    // Respond to navigation item 1 click
                    d("click", "1")
                    true
                }
                R.id.item2 -> {
                    d("click", "2")
                    // Respond to navigation item 2 click
                    true
                }
                R.id.item3 -> {
                    d("click", "3")
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }
    }

}
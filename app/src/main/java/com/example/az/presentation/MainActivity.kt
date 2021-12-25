package com.example.az.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.az.R
import com.example.az.databinding.ActivityMainBinding
import com.example.az.extensions.showSnackBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setSupportActionBar(binding.bottomNav)
        navController = findNavController(R.id.navHostFragment)

        initNav()
        initFab()
    }

    private fun initNav() {
        binding.bottomNav.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
//                    binding.root.showSnackBar("\"navigation_home is clicked!\"")
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_airports -> {
//                    binding.root.showSnackBar("\"navigation_airports is clicked!\"")
                    navController.navigate(R.id.navigation_airports)
                    true
                }
                R.id.navigation_restrictions -> {
//                    binding.root.showSnackBar("\"navigation_restrictions is clicked!\"")
                    navController.navigate(R.id.navigation_restrictions)
                    true
                }
                else -> false
            }
        }
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
//            binding.root.showSnackBar("\"navigation_home is clicked!\"")
            navController.navigate(R.id.navigation_login)
        }
    }

}
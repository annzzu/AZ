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
        setSupportActionBar(binding.bottomNav)
        navController = findNavController(R.id.navHostFragment)

//        initNav()
    }

//    private fun initNav(){
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.navHostFragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home , R.id.navigation_airports , R.id.navigation_restrictions
//            )
//        )
//        setupActionBarWithNavController(navController , appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                toast("navigation_home is clicked!")
                navController.navigate(R.id.navigation_home)
            }
            R.id.navigation_airports -> {
                toast("navigation_airports is clicked!")
                navController.navigate(R.id.navigation_airports)
            }
            R.id.navigation_restrictions -> {
                toast("navigation_restrictions is clicked!")
                navController.navigate(R.id.navigation_restrictions)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    private fun toast(test: String) {
        Toast.makeText(this , test , Toast.LENGTH_SHORT).show()
    }
}
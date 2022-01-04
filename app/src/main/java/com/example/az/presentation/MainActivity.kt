package com.example.az.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.az.R
import com.example.az.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        setSupportActionBar(binding.bottomNav)
        navController = findNavController(R.id.navHostFragment)

        initFab()
    }

//    private fun initNav() {
//        binding.bottomNav.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.navigation_home -> {
////                    binding.root.showSnackBar("\"navigation_home is clicked!\"")
//                    navController.navigate(R.id.navigation_home)
//                    true
//                }
//                R.id.navigation_airports -> {
////                    binding.root.showSnackBar("\"navigation_airports is clicked!\"")
//                    navController.navigate(R.id.navigation_airports)
//                    true
//                }
//                R.id.navigation_restrictions -> {
////                    binding.root.showSnackBar("\"navigation_restrictions is clicked!\"")
//                    navController.navigate(R.id.navigation_restrictions)
//                    true
//                }
//                else -> false
//            }
//        }
//    }

    private fun initFab() {
        binding.fab.setOnClickListener {
//            binding.root.showSnackBar("\"navigation_home is clicked!\"")
            navController.navigate(R.id.navigation_login)
        }
    }


//
//    private fun setUpBottomNavigation() {
//        binding.run {
//            findNavController(R.id.navHostFragment).addOnDestinationChangedListener(
//                this@MainActivity
//            )
//        }
//
//        binding.bottomAppBar.apply {
//            setOnMenuItemClickListener(this@MainActivity)
//        }
//
//        binding.fab.apply {
//            setShowMotionSpecResource(R.animator.fab_show)
//            setHideMotionSpecResource(R.animator.fab_hide)
//
//        }
//    }

}
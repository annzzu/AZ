package com.example.az.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.az.R
import com.example.az.databinding.ActivityMainBinding
import com.example.az.extensions.DRAWABLES
import com.example.az.extensions.setImageUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFab()

    }

    private fun initFab() {
        binding.run {
            findNavController(R.id.navHostFragment).addOnDestinationChangedListener(
                this@MainActivity
            )
        }
    }

    override fun onDestinationChanged(
        controller: NavController ,
        destination: NavDestination ,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.navigation_login -> {
                setFabForAuth()
            }
            R.id.navigation_signup -> {
                setFabForAuth()
            }
            else -> setFabForHome()
        }
    }

    private fun setFabForAuth() {
        setFabIconDestination(DRAWABLES.ic_covid_19 , R.id.navigation_home)
    }

    private fun setFabForHome() {
        setFabIconDestination(DRAWABLES.ic_user , R.id.navigation_login)
    }

    private fun setFabIconDestination(icon: Int , navigation: Int) {
        binding.fab.apply {
            setImageResource(icon)
            setOnClickListener {
                findNavController(R.id.navHostFragment).navigate(navigation)
            }
        }
    }
}
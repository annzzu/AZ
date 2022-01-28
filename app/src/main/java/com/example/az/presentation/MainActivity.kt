package com.example.az.presentation

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.az.R
import com.example.az.databinding.ActivityMainBinding
import com.example.az.extensions.DRAWABLES
import com.example.az.extensions.IDS
import com.example.az.presentation.base.BaseActivity
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity() , NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private val navController by lazy { findNavController(R.id.navHostFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        motions()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFab()
    }

    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.navHostFragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    private fun motions(){
        currentNavigationFragment?.apply {
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
        }
    }

    private fun initFab() {
        binding.run {
            navController.addOnDestinationChangedListener(
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
            IDS.navigation_login -> {
                setFabForAuth()
            }
            IDS.navigation_signup -> {
                setFabForAuth()
            }
            IDS.navigation_userHome -> {
                setFabForAuth()
            }
            IDS.navigation_restrictions -> {
                setFabForAuth()
            }
            IDS.navigation_restrictionForm -> {
                setFabForAuth()
            }
            IDS.navigation_about -> {
                setFabForAuth()
            }
            IDS.navigation_restriction -> {
                setFabForAuth()
            }
            else -> {
                lifecycleScope.launch {
                    setFabForHome(authPrefsManager.readAuthToken())
                }
            }
        }
    }

    private fun setFabForAuth() {
        setFabIconDestination(DRAWABLES.ic_covid_19 , R.id.navigation_home)
    }

    private fun setFabForHome(token: String?) {
        if (token.isNullOrBlank()) {
            setFabIconDestination(DRAWABLES.ic_user , R.id.navigation_login)
        } else {
            setFabIconDestination(DRAWABLES.ic_user , R.id.navigation_userHome)
        }
    }

    private fun setFabIconDestination(icon: Int , navigation: Int) {
        binding.fab.apply {
            setImageResource(icon)
            setOnClickListener {
                motions()
                navController.navigate(navigation)
            }
        }
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}
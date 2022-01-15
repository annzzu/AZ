package com.example.az.presentation

import android.os.Bundle
import android.util.Log.d
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.az.R
import com.example.az.databinding.ActivityMainBinding
import com.example.az.extensions.DRAWABLES
import com.example.az.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() , NavController.OnDestinationChangedListener {

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
                controller.popBackStack()
            }
            R.id.navigation_signup -> {
                setFabForAuth()
                controller.popBackStack()
            }
            R.id.navigation_userHome -> {
                setFabForAuth()
                controller.popBackStack()
            }
            R.id.navigation_restrictions -> {
                setFabForAuth()
                controller.popBackStack()
            }
            R.id.navigation_restrictionForm -> {
                setFabForAuth()
                controller.popBackStack()
            }
            R.id.navigation_about -> {
                setFabForAuth()
                controller.popBackStack()
            }
            R.id.navigation_restriction -> {
                setFabForAuth()
                controller.popBackStack()
            }
            else -> {
                lifecycleScope.launch {
                    setFabForHome(authPrefsManager.readAuthToken())
                }
                controller.popBackStack()
            }
        }
    }

    private fun setFabForAuth() {
        setFabIconDestination(DRAWABLES.ic_covid_19 , R.id.navigation_home)
    }

    private fun setFabForHome(token: String?) {
        if (token.isNullOrBlank()) {
            d("testing AZ" , "Dalogindi - $token")
            setFabIconDestination(DRAWABLES.ic_user , R.id.navigation_login)
        } else {
            d("testing AZ" , "arrrrrrrr - $token")
            setFabIconDestination(DRAWABLES.ic_user , R.id.navigation_userHome)
        }
    }

    private fun setFabIconDestination(icon: Int , navigation: Int) {
        binding.fab.apply {
//            setShowMotionSpecResource(R.animator.fab_show)
//            setHideMotionSpecResource(R.animator.fab_hide)
            setImageResource(icon)
            setOnClickListener {
                findNavController(R.id.navHostFragment).navigate(navigation)
            }
        }
    }
}
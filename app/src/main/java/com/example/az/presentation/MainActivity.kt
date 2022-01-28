package com.example.az.presentation

import android.animation.ObjectAnimator
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.az.R
import com.example.az.databinding.ActivityMainBinding
import com.example.az.extensions.DRAWABLES
import com.example.az.extensions.IDS
import com.example.az.extensions.invisible
import com.example.az.extensions.showSnackBar
import com.example.az.presentation.base.BaseActivity
import com.example.az.presentation.user.UserViewModel
import com.example.az.utils.network.NetworkStatus
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity() , NavController.OnDestinationChangedListener {

    private val networkViewModel: NetworkViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy { findNavController(IDS.navHostFragment) }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        motions()
        initIntro()
        initFab()



        initNetwork()
    }

    private fun initDoAsync() {

    }

    @ExperimentalCoroutinesApi
    private fun initNetwork() {
        lifecycleScope.launch {
            networkViewModel.networkStatusActive.collectLatest {
                binding.root.showSnackBar("Internet Connection - ${if (it == NetworkStatus.Available) "Yes" else "No"}")
            }
        }

    }

    private fun initIntro() {
        lifecycleScope.launch {
            val isFirstTimeLaunch = authPrefsManager.isFirstTimeLaunch()
            if (isFirstTimeLaunch) {
                navigationWithMotion(IDS.navigationIntroSlide)
                authPrefsManager.setAnotherTimeLaunch()
            }
        }
    }

    private fun initSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val iconAnimator =
                ObjectAnimator.ofFloat(splashScreenView.iconView , View.ROTATION , -360f , 0f)
            iconAnimator.duration = 600L
            iconAnimator.start()
            val splashScreenAnimator = ObjectAnimator.ofFloat(
                splashScreenView.view ,
                View.TRANSLATION_Y ,
                0f ,
                splashScreenView.view.height.toFloat()
            )
            splashScreenAnimator.interpolator = AnticipateInterpolator()
            splashScreenAnimator.duration = 600L
            splashScreenAnimator.doOnEnd { splashScreenView.remove() }
            splashScreenAnimator.start()
        }
    }

    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(IDS.navHostFragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    private fun motions() {
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
            IDS.navigationIntroSlide -> {
                binding.fab.invisible()
            }
            else -> {
                lifecycleScope.launch {
                    setFabForHome(authPrefsManager.readAuthToken())
                }
            }
        }
    }

    private fun setFabForAuth() {
        setFabIconDestination(DRAWABLES.ic_covid_19 , IDS.navigation_home)
    }

    private fun setFabForHome(token: String?) {
        if (token.isNullOrBlank()) {
            setFabIconDestination(DRAWABLES.ic_user , IDS.navigation_login)
        } else {
            setFabIconDestination(DRAWABLES.ic_user , IDS.navigation_userHome)
        }
    }

    private fun setFabIconDestination(icon: Int , navigation: Int) {
        binding.fab.apply {
            setImageResource(icon)
            setOnClickListener {
                navigationWithMotion(navigation)
            }
        }
    }

    private fun navigationWithMotion(navigation: Int) {
        motions()
        navController.navigate(navigation)
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}
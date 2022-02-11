package com.example.az.presentation

import android.animation.ObjectAnimator
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.az.databinding.ActivityMainBinding
import com.example.az.extensions.*
import com.example.az.presentation.base.BaseActivity
import com.example.az.presentation.network.NetworkFragment
import com.example.az.presentation.network.NetworkViewModel
import com.example.az.utils.network.NetworkStatus
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity() , NavController.OnDestinationChangedListener {

    private val networkViewModel: NetworkViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy { findNavController(IDS.navHostFragment) }

    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(IDS.navHostFragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMain()
    }

    @ExperimentalCoroutinesApi
    private fun initMain() {
        initIntro()
        initFab()
        initNetwork()
    }

    @ExperimentalCoroutinesApi
    private fun initNetwork() {
        lifecycleScope.launch {
            networkViewModel.networkStatusActive.collectLatest {
                if (it is NetworkStatus.Unavailable) {
                    navigationWithMotion(IDS.navigation_network)
                } else {
                    if (currentNavigationFragment is NetworkFragment) {
                        binding.fab.visible()
                        supportFragmentManager.popBackStackImmediate()
                    }
                }
            }
        }

    }

    private fun initIntro() {
        lifecycleScope.launch {
            val isFirstTimeLaunch = authPrefsManager.isFirstTimeLaunch()
            if (isFirstTimeLaunch) {
                binding.fab.invisible()
                navigationWithMotion(IDS.navigationIntroSlide)
                authPrefsManager.setAnotherTimeLaunch()
            }
        }
    }

    private fun initSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.iconView.getSplashIconRotationAnimation()
            splashScreenView.getSplashViewRotationAnimation()
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
            IDS.navigation_login , IDS.navigation_signup , IDS.navigation_userHome ,
            IDS.navigation_restrictions , IDS.navigation_restrictionForm ,
            IDS.navigation_restriction -> setFabForAuth()
            IDS.navigation_network , IDS.navigationIntroSlide -> binding.fab.invisible()
            else ->
                lifecycleScope.launch {
                    setFabForHome(authPrefsManager.readAuthToken())
                }
        }
    }

    override fun onBackPressed() {
        if (currentNavigationFragment is NetworkFragment) {
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(STRINGS.unable_to_go_back))
                .setPositiveButton(getString(STRINGS.ok)) { _ , _ ->
                    binding.root.showSnackBar(getString(STRINGS.ok))
                }
                .show()
        } else {
            super.onBackPressed()
        }
    }

    private fun setFabForAuth() {
        binding.fab.visible()
        setFabIconDestination(DRAWABLES.ic_covid_19 , IDS.navigation_home)
    }

    private fun setFabForHome(token: String?) {
        binding.fab.visible()
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
                getFabIconAnimation()
                navigationWithMotion(navigation)
            }
        }
    }

    private fun navigationWithMotion(navigation: Int) {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        navController.navigate(navigation)
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}
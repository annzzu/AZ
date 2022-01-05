package com.example.az.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.example.az.data.local.AuthPrefsManager
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var authPrefsManager: AuthPrefsManager

}
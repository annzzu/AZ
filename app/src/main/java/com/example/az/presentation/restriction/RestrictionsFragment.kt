package com.example.az.presentation.restriction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.az.databinding.FragmentRestrictionFormBinding
import com.example.az.databinding.FragmentRestrictionsBinding
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RestrictionsFragment :
    BaseFragment<FragmentRestrictionsBinding>(FragmentRestrictionsBinding::inflate) {
    override fun init() {
    }
}
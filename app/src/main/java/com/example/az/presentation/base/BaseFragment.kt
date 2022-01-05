package com.example.az.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.example.az.data.local.AuthPrefsManager
import com.example.az.presentation.user.UserViewModel
import javax.inject.Inject

typealias  Inflate<T> = (LayoutInflater , ViewGroup , Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    @Inject
    lateinit var authPrefsManager: AuthPrefsManager

    private var _binding: VB? = null
    val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater , container!! , false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        init()
    }

    open fun init(){}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        stop()
    }

    open fun stop() {}
}
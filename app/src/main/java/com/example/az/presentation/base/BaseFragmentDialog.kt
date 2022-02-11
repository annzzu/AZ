package com.example.az.presentation.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.az.R
import com.example.az.databinding.FragmentNationalitiesBinding
import com.example.az.utils.callbacks.ClickCallBack
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

typealias  DialogInflate<T> = (LayoutInflater , ViewGroup? , Boolean) -> T


abstract class BaseFragmentDialog<VB : ViewBinding>(private val inflate: DialogInflate<VB>) :
    DialogFragment() {

    var clickCallBack: ClickCallBack? = null

    private var _binding: VB? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = inflate.invoke(inflater , container  , false)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        init()
    }

    open fun init() {
        initRV()
        observer()
    }

    open fun initRV() {}

    open fun observer() {}

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT ,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
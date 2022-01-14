package com.example.az.presentation.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment


typealias ClickCallBack = (string: String) -> Unit

abstract class BaseFragmentDialog : DialogFragment() {

    var clickCallBack: ClickCallBack? = null

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
    }

}
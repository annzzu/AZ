package com.example.az.presentation.auth.fragment

import android.util.Log.d
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.az.databinding.FragmentLoginBinding
import com.example.az.extensions.*
import com.example.az.model.user.User
import com.example.az.presentation.auth.LoginViewModel
import com.example.az.presentation.base.BaseFragment
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun init() {
        listeners()
    }

    private fun listeners() {
        listenerETs()
        formValidation()
        with(binding) {
            btnLogin.setOnClickListener {
                root.hideKeyboard()
                login()
            }
            btnSignupTab.setOnClickListener {
                openSingUp()
            }
            logo.getRotationAnimation()
            btnRetry.setOnClickListener {
                etEmail.text = null
                etPassword.text = null
                btnRetry.invisible()
            }
        }
    }

    private fun listenerETs() {
        with(binding) {
            etEmail.doAfterTextChanged {
                observeFields()
                tvNothingFound.invisible()
            }
            etPassword.doAfterTextChanged {
                observeFields()
                tvNothingFound.invisible()
            }
        }
    }

    private fun observeFields() {
        with(binding) {
            viewModel.userValidation(
                etEmail.text.toString() ,
                etPassword.text.toString()
            )
        }
    }

    private fun formValidation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userForm.collectLatest { userForm ->
                with(binding) {
                    btnLogin.isEnabled = userForm.isDataValid
                    userForm.emailError?.let {
                        etilEmail.error = getString(it)
                    } ?: run {
                        etilEmail.error = null
                    }
                    userForm.passwordError?.let {
                        etilPassword.error = getString(it)
                    } ?: run {
                        etilPassword.error = null
                    }
                }
            }
        }
    }

    private fun login() {
        observeLoginRequest()
        observeLoginCollector()
    }

    private fun observeLoginRequest() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.login(
                User(email = etEmail.text.toString() , password = etPassword.text.toString())
            )
        }
    }

    private fun observeLoginCollector() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.login.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        btnRetry.visible()
                        tvNothingFound.visible()
                        progressBar.invisible()
                        tvNothingFound.text = getString(STRINGS.error)
                        root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> {
                        root.isEnabled = false
                        tvNothingFound.invisible()
                        btnRetry.invisible()
                        progressBar.visible()
                    }
                    is Resource.Success -> {
                        tvNothingFound.invisible()
                        progressBar.invisible()
                        root.showSnackBar(getString(STRINGS.successful_login))
                        openHome()
                    }
                }
            }
        }
    }

    private fun openHome() {
        findNavController().navigate(IDS.navigation_home)
        childFragmentManager.popBackStack()
    }

    private fun openSingUp() = findNavController().navigate(
        LoginFragmentDirections.actionLoginFragmentToSignupFragment()
    )
}
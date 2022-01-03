package com.example.az.presentation.auth.fragment

import android.util.Log.d
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.az.R
import com.example.az.databinding.FragmentLoginBinding
import com.example.az.extensions.*
import com.example.az.model.user.User
import com.example.az.presentation.auth.LoginAdapter
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
        binding.btnLogin.setOnClickListener {
            binding.root.hideKeyboard()
            login()
            binding.root.showSnackBar("logged")
        }
    }

    private fun listenerETs() {
        with(binding) {
            etEmail.doAfterTextChanged {
                observeFields()
            }
            etPassword.doAfterTextChanged {
                observeFields()
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
                with (binding){
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
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.login(
                    User(email = etEmail.text.toString() , password = etPassword.text.toString())
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.login.collect {
                when(it){
                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
                    is Resource.Loading -> TODO()
                    is Resource.Success -> openHome()
                }
            }
        }
    }

    private fun openHome() = findNavController().navigate(R.id.home)
}
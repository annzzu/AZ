package com.example.az.presentation.auth.fragment

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.az.R
import com.example.az.databinding.LoginTabFragmentBinding
import com.example.az.extensions.showSnackBar
import com.example.az.model.user.User
import com.example.az.presentation.airport.AirportsViewModel
import com.example.az.presentation.auth.LoginViewModel
import com.example.az.presentation.base.BaseFragment
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginTabFragment: BaseFragment<LoginTabFragmentBinding>(LoginTabFragmentBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun init() {
        listeners()
    }

    private fun listeners() {
        binding.btnLogin.setOnClickListener {
//            login()
        }
        formValidation()
    }

    private fun formValidation() {
        observeFields()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userForm.collect { userForm ->
                binding.btnLogin.isEnabled = userForm.isDataValid
                userForm.emailError?.let {
                    binding.etilEmail.error = getString(it)
                }
                userForm.passwordError?.let {
                    binding.etilPassword.error = getString(it)
                }
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

//    private fun login() {
//        with(binding) {
//            viewLifecycleOwner.lifecycleScope.launch {
//                viewModel.login(
//                    User(email = etEmail.text.toString() , password = etPassword.text.toString())
//                )
//            }
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.login.collect {
//                when(it){
//                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
//                    is Resource.Loading -> TODO()
//                    is Resource.Success -> openHome()
//                }
//            }
//        }
//    }

    private fun openHome() = findNavController().navigate(R.id.home)
}
package com.example.az.presentation.auth.fragment


import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.az.databinding.FragmentSignupBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.hideKeyboard
import com.example.az.extensions.showSnackBar
import com.example.az.model.user.User
import com.example.az.presentation.auth.LoginViewModel
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.nationality.NationalityFragmentDialog
import com.example.az.presentation.vaccine.VaccinesFragmentDialog
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun init() {
        listeners()
    }

    private fun listeners() {
        listenerETs()
        formValidation()
        with(binding) {
            btnSignup.setOnClickListener {
                root.hideKeyboard()
                signup()
            }
            btnLoginTab.setOnClickListener {
                openLogin()
            }
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
            btnNationality.setOnClickListener {
                openNationalityDialog()
            }
            btnVaccine.setOnClickListener {
                openVaccineDialog()
            }
            btnNationality.setOnLongClickListener {
                btnNationality.text = getString(STRINGS.nationality)
                nationality = ""
                true
            }
            btnVaccine.setOnLongClickListener {
                btnVaccine.text = getString(STRINGS.vaccine)
                vaccine = ""
                true
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
                    btnSignup.isEnabled = userForm.isDataValid
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

    private fun signup() {
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.signup(
                    User(
                        email = etEmail.text.toString() ,
                        password = etPassword.text.toString() ,
                        data = User.Data(
                            vaccine = vaccine ,
                            nationality = nationality ,
                        )
                    )
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signup.collect {
                when (it) {
                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        binding.root.hideKeyboard()
                        binding.root.showSnackBar(getString(STRINGS.successful_sign_up))
                        openLogin()
                    }
                }
            }
        }
    }

    private fun openLogin() = findNavController().navigate(
        SignupFragmentDirections.actionNavigationSignupToNavigationLogin()
    )

    private var vaccine = ""
    private var nationality = ""


    private fun openNationalityDialog() {
        val dialog = NationalityFragmentDialog()
        dialog.show(childFragmentManager , null)
        dialog.clickCallBack = {
            nationality = it
            binding.btnNationality.text = it
        }
    }
    private fun openVaccineDialog() {
        val dialog = VaccinesFragmentDialog()
        dialog.show(childFragmentManager , null)
        dialog.clickCallBack = {
            vaccine = it
            binding.btnVaccine.text = it
        }
    }
}
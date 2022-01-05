package com.example.az.presentation.user


import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.az.databinding.FragmentUserHomeBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.showSnackBar
import com.example.az.presentation.auth.fragment.LoginFragmentDirections
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserHomeFragment : BaseFragment<FragmentUserHomeBinding>(
    FragmentUserHomeBinding::inflate
) {
    override fun init() {
        listeners()
        setUserInfo()
        getUserTravelInfo()
    }

    private fun listeners(){
        with(binding){
            btnLogout.setOnClickListener{
                logout()
            }
            btnBack.setOnClickListener{
                openHome()
            }
        }
    }
    private fun setUserInfo(){
        viewLifecycleOwner.lifecycleScope.launch {
            authPrefsManager.preferencesFlow.collectLatest { user->
                with(binding) {
                  tvEmail.text = user.email
                  tvNationality.text = user.data?.nationality
                  tvVaccine.text =  user.data?.vaccine
                }
            }
        }
    }

    private fun logout(){
        viewLifecycleOwner.lifecycleScope.launch {
            binding.root.showSnackBar(getString(STRINGS.logout))
            authPrefsManager.logout()
            openHome()
        }
    }

    private fun openHome() = findNavController().navigate(
        UserHomeFragmentDirections.actionNavigationUserHomeToNavigationHome()
    )

    private fun getUserTravelInfo(){

    }
}
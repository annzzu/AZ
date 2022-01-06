package com.example.az.presentation.user


import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentUserHomeBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.getName
import com.example.az.extensions.showSnackBar
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.travel_plans.TravelPlanAdapter
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserHomeFragment : BaseFragment<FragmentUserHomeBinding>(
    FragmentUserHomeBinding::inflate
) {

    private lateinit var travelPlanAdapter: TravelPlanAdapter
    private val userViewModel: UserViewModel by activityViewModels()

    override fun init() {
        listeners()
        setUserInfo()
        getUserTravelPlans()
    }

    private fun listeners() {
        with(binding) {
            btnLogout.setOnClickListener {
                logout()
            }
            btnBack.setOnClickListener {
                openHome()
            }
        }
    }

    private fun setUserInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            authPrefsManager.preferencesFlow.collectLatest { user ->
                with(binding) {
                    tvEmail.text = user.email?.getName()
                    tvNationality.text = user.data?.nationality
                    tvVaccine.text = user.data?.vaccine
                }
                if (!user.token.isNullOrBlank()) {
                    userViewModel.getTravelPlan(user.token)
                    observeTravelPlan()
                }
            }
        }
    }

    private fun observeTravelPlan() {
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.travelPlans.collectLatest {
                when (it) {
                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        travelPlanAdapter.submitList(it.data?.travelPlans)
                    }
                }
            }
        }
    }

    private fun logout() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.root.showSnackBar(getString(STRINGS.logout))
            authPrefsManager.logout()
            openHome()
        }
    }

    private fun openHome() = findNavController().navigate(
        UserHomeFragmentDirections.actionNavigationUserHomeToNavigationHome()
    )

    private fun getUserTravelPlans() {
        binding.rvTravelPlan.apply {
            travelPlanAdapter = TravelPlanAdapter()
            adapter = travelPlanAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.VERTICAL , false)
        }
        travelPlanAdapter.clickTravelPlan = {
            openTravelPlan(it)
        }
    }

    private fun openTravelPlan(travelPlan: TravelPlan) {

    }
}
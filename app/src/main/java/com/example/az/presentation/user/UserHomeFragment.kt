package com.example.az.presentation.user


import android.os.Build
import android.util.Log.d
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentUserHomeBinding
import com.example.az.extensions.*
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.travel_plans.TravelPlanAdapter
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserHomeFragment : BaseFragment<FragmentUserHomeBinding>(
    FragmentUserHomeBinding::inflate
) {

    private lateinit var travelPlanAdapter: TravelPlanAdapter
    private val userViewModel: UserViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
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
            logo.getRotationAnimation()
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
                    userViewModel.getTravelPlan()
                    observeTravelPlan()
                }
            }
        }
    }

    private fun observeTravelPlan() {
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.travelPlans.collect {
                when (it) {
                    is Resource.Error -> {
                        binding.tvNothingFound.visible()
                        binding.tvNothingFound.text = getString(STRINGS.error)
                        binding.root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visible()
                    }
                    is Resource.Success -> {
                        binding.progressBar.invisible()
                        val data = it.data?.travelPlans

                        if (data!!.isNotEmpty()){
                            binding.tvNothingFound.invisible()
                            travelPlanAdapter.submitList(data)

                        }else{
                            binding.tvNothingFound.visible()
                            binding.tvNothingFound.text = getString(STRINGS.nothing_found)
                        }
                    }
                }
            }
        }
    }

    private fun logout() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.root.showSnackBar(getString(STRINGS.logout))
            authPrefsManager.logout()
            childFragmentManager.popBackStack()
            openHome()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserTravelPlans() {
        binding.rvTravelPlan.apply {
            travelPlanAdapter = TravelPlanAdapter()
            adapter = travelPlanAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.VERTICAL , false)
        }
        travelPlanAdapter.clickTravelPlan = {
            openTravelPlanDetails(it)
//            for dialog
//            openTravelPlan(it)
        }
    }

    private fun openTravelPlan(travelPlan: TravelPlan) {
        //        for dialog
        val dialog = TravelPlanBottomSheetFragment()
        dialog.bottomSheetFragment(travelPlan)
        dialog.show(childFragmentManager , "AndroidCenter")
        dialog.clickSeeMore = {
            d("testing AZ" , "daaWira ashkarad \n $travelPlan")
            openTravelPlanDetails(it)
        }
    }

    private fun openHome() = findNavController().navigate(
        UserHomeFragmentDirections.actionNavigationUserHomeToNavigationHome()
    )

    private fun openTravelPlanDetails(travelPlan: TravelPlan) = findNavController().navigate(
        UserHomeFragmentDirections.actionNavigationUserHomeToTravelPlanFragment(travelPlan)
    )
}
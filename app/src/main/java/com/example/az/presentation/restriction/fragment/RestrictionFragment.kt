package com.example.az.presentation.restriction.fragment


import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentRestrictionBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.invisible
import com.example.az.extensions.showSnackBar
import com.example.az.extensions.visible
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.restriction.RestrictionViewModel
import com.example.az.presentation.restriction.adapter.RestrictionAdapter
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RestrictionFragment :
    BaseFragment<FragmentRestrictionBinding>(FragmentRestrictionBinding::inflate) {

    private val viewModel: RestrictionViewModel by activityViewModels()
    private lateinit var restrictionAdapter: RestrictionAdapter

    override fun init() {
        listeners()
        binding.tvFrom.text = viewModel.restrictionRequestForm.from
        binding.tvTo.text = viewModel.restrictionRequestForm.to
        binding.tvTransfer.text = viewModel.restrictionRequestForm.transfer
        observers()
    }

    private fun listeners() = with(binding) {
        btnRetry.setOnClickListener {
            observeRestrictionRequest()
        }

        btnBack.setOnClickListener {
            openForm()
        }

        rvRestrictions.apply {
            restrictionAdapter = RestrictionAdapter()
            adapter = restrictionAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.VERTICAL , false)
        }
    }

    private fun observers() {
        observeRestrictionRequest()
        observeRestrictionCollector()
    }

    private fun openForm() {
        findNavController().navigate(RestrictionFragmentDirections.actionNavigationRestrictionToNavigationRestrictionForm())
    }

    private fun observeRestrictionRequest() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getRestriction(viewModel.restrictionRequestForm)
        }
    }

    private fun observeRestrictionCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.restriction.collectLatest {
                with (binding) {
                    when (it) {
                        is Resource.Error -> {
                            tvNothingFound.visible()
                            tvNothingFound.text = getString(STRINGS.error)
                            progressBar.invisible()
                            root.showSnackBar(it.message!!)
                            btnRetry.visible()
                        }
                        is Resource.Loading -> {
                            tvNothingFound.invisible()
                            progressBar.visible()
                            btnRetry.invisible()
                        }
                        is Resource.Success -> {
                            btnRetry.invisible()
                            progressBar.invisible()
                            it.data?.restrictionList?.let { value ->
                                restrictionAdapter.submitList(value)
                                rvRestrictions.startLayoutAnimation()
                                tvNothingFound.invisible()
                            } ?: run {
                                tvNothingFound.visible()
                            }
                        }
                    }
                }

            }
        }
    }


}

package com.example.az.presentation.restriction.fragment


import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentRestrictionBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.invisible
import com.example.az.extensions.showSnackBar
import com.example.az.extensions.visible
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.restriction.ConvertToRestrictionKotlin
import com.example.az.presentation.restriction.RestrictionViewModel
import com.example.az.presentation.restriction.adapter.RestrictionAdapter
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RestrictionFragment :
    BaseFragment<FragmentRestrictionBinding>(FragmentRestrictionBinding::inflate) ,
    ConvertToRestrictionKotlin {

    private val args: RestrictionFragmentArgs by navArgs()
    private val viewModel: RestrictionViewModel by activityViewModels()
    private lateinit var restrictionAdapter: RestrictionAdapter

    override fun init() {
        listeners()
        binding.tvFrom.text = args.restrictionRequest.from
        binding.tvTo.text = args.restrictionRequest.to
        observers()
    }

    private fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().navigate(RestrictionFragmentDirections.actionNavigationRestrictionToNavigationRestrictionForm())
        }
        rvRestrictions.apply {
            restrictionAdapter = RestrictionAdapter()
            adapter = restrictionAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.VERTICAL , false)
        }
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getRestriction(args.restrictionRequest)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.restriction.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        binding.tvNothingFound.visible()
                        binding.tvNothingFound.text = getString(STRINGS.error)
                        binding.root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> {
                        binding.tvNothingFound.invisible()
                        binding.progressBar.visible()
                    }
                    is Resource.Success -> {
                        binding.tvNothingFound.invisible()
                        binding.progressBar.invisible()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.restrictionList.collectLatest {
                it.let { list ->
                    if (list.isNotEmpty()) {
                        restrictionAdapter.submitList(list)
                        binding.tvNothingFound.invisible()
                    } else {
                        binding.tvNothingFound.visible()
                    }
                }
            }
        }
    }
}

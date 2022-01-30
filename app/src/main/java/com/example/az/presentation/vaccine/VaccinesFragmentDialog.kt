package com.example.az.presentation.vaccine

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentNationalitiesBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.invisible
import com.example.az.extensions.showSnackBar
import com.example.az.extensions.visible
import com.example.az.presentation.base.BaseFragmentDialog
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VaccinesFragmentDialog : BaseFragmentDialog<FragmentNationalitiesBinding>(
    FragmentNationalitiesBinding::inflate
) {
    private lateinit var vaccineAdapter: VaccineAdapter
    private val viewModel: VaccineViewModel by viewModels()

    private fun changeView() = with(binding) {
        hNationality.text = getString(STRINGS.vaccines)
    }

    override fun initRV() {
        changeView()
        binding.rvNationality.apply {
            vaccineAdapter = VaccineAdapter()
            adapter = vaccineAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.HORIZONTAL , false)
        }
        vaccineAdapter.clickCallBack = {
            clickCallBack?.invoke(it)
            dismiss()
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.vaccines.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        binding.progressBar.invisible()
                        binding.tvNothingFound.visible()
                        binding.tvNothingFound.text = getString(STRINGS.error)
                        binding.root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> binding.progressBar.visible()
                    is Resource.Success -> {
                        binding.progressBar.invisible()
                        val data = it.data?.vaccines
                        if (data!!.isNotEmpty()) {
                            binding.tvNothingFound.invisible()
                            vaccineAdapter.submitList(data)
                        } else {
                            binding.tvNothingFound.visible()
                            binding.tvNothingFound.text = getString(STRINGS.nothing_found)
                        }
                    }
                }
            }
        }
    }
}
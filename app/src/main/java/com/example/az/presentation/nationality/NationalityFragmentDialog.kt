package com.example.az.presentation.nationality


import androidx.fragment.app.*
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
class NationalityFragmentDialog :
    BaseFragmentDialog<FragmentNationalitiesBinding>(FragmentNationalitiesBinding::inflate) {

    private lateinit var nationalityAdapter: NationalityAdapter

    private val viewModel by viewModels<NationalityViewModel>()

    override fun initRV() {
        binding.rvNationality.apply {
            nationalityAdapter = NationalityAdapter()
            adapter = nationalityAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.HORIZONTAL , false)
        }
        nationalityAdapter.clickCallBack = {
            clickCallBack?.invoke(it)
            dismiss()
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nationalities.collectLatest {
                with(binding) {
                    when (it) {
                        is Resource.Error -> {
                            tvNothingFound.visible()
                            tvNothingFound.text = getString(STRINGS.error)
                            progressBar.invisible()
                            root.showSnackBar(it.message!!)
                        }
                        is Resource.Loading -> {
                            progressBar.visible()
                        }
                        is Resource.Success -> {
                            progressBar.invisible()
                            val data = it.data?.nationalities
                            if (data!!.isNotEmpty()) {
                                tvNothingFound.invisible()
                                nationalityAdapter.submitList(data)
                            } else {
                                tvNothingFound.visible()
                                tvNothingFound.text = getString(STRINGS.nothing_found)
                            }
                        }
                    }
                }
            }
        }
    }
}
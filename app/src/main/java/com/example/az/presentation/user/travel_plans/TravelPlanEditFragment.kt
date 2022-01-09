package com.example.az.presentation.user.travel_plans

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.az.databinding.FragmentTravelPlanEditBinding
import com.example.az.databinding.FragmentUserHomeBinding
import com.example.az.extensions.*
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.UserViewModel
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class TravelPlanEditFragment : BaseFragment<FragmentTravelPlanEditBinding>(
    FragmentTravelPlanEditBinding::inflate
) , DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {
    private val args: TravelPlanFragmentArgs by navArgs()
    private val viewModel: UserViewModel by activityViewModels()


    override fun init() {
        setInfo()
        listeners()
        observers()
    }

    private fun setInfo() {
        with(binding) {
            args.travelPlan?.let {
                viewModel.source = it.source ?: ""
                viewModel.destination = it.destination ?: ""
                viewModel.date = it.date ?: ""
                tvSource.text = it.source
                tvDestination.text = it.destination
                tvDate.text = it.date?.getDateNextLine()
                btnBack.setOnClickListener {
                    openBack(args.travelPlan!!)
                }
            } ?: run {
                btnBack.gone()
            }
        }
    }

    private fun listeners() = with(binding) {
        btnTravelPlanSave.setOnClickListener {
            checkChoose()
        }
        cardDate.setOnClickListener {
            openDateDialog()
        }
        cardSource.setOnClickListener {
            openAirportDialog()
        }
        cardDestination.setOnClickListener {
            openAirportDialog()
        }
    }

    private fun openAirportDialog() {

    }

    private fun checkChoose() {
        args.travelPlan?.let {
            if (it.source != viewModel.source || it.destination != viewModel.destination || it.date != viewModel.date) {
                it.source = viewModel.source
                it.destination = viewModel.destination
                it.date = viewModel.date
                btnTravelPlanSave(it , true)
            } else {
                binding.root.showSnackBar(getString(STRINGS.change_data_first))
            }
        } ?: run {
            if (viewModel.source.isBlank() || viewModel.destination.isBlank() || viewModel.date.isBlank()) {
                binding.root.showSnackBar(getString(STRINGS.choose_data_first))
            } else {
                btnTravelPlanSave(
                    TravelPlan(
                        source = viewModel.source ,
                        destination = viewModel.destination ,
                        date = viewModel.date
                    ) , false
                )
            }
        }
    }

    private fun observers() {

    }

    private fun openBack(travelPlan: TravelPlan) {
        findNavController().navigate(
            TravelPlanEditFragmentDirections.actionNavigationTravelPlanEditToNavigationTravelPlan(
                travelPlan
            )
        )
    }

    private fun btnTravelPlanSave(travelPlan: TravelPlan , update: Boolean) {
        if (update) {
            update(travelPlan)
        } else {
            create(travelPlan)
        }

    }

    private fun create(travelPlan: TravelPlan) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.createTravelPlan(travelPlan)
            viewModel.createPlan.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        binding.root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        it.data?.travelPlan?.let { travelPlan ->
                            openBack(travelPlan)
                        }
                    }
                }
            }
        }
    }

    private fun update(travelPlan: TravelPlan) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateTravelPlan(travelPlan)
            viewModel.updatePlan.collectLatest {
                when (it) {
                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        it.data?.travelPlan?.let { travelPlan ->
                            openBack(travelPlan)
                        }
                    }
                }
            }
        }
    }

    private fun openDateDialog() {
        getDatePicker()
        getTimePicker()
    }

    private fun getDatePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            this.requireContext() ,
            this ,
            cal.get(Calendar.YEAR) ,
            cal.get(Calendar.MONTH) ,
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun getTimePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            this.requireContext() ,
            this ,
            cal.get(Calendar.YEAR) ,
            cal.get(Calendar.MONTH) ,
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDateSet(p0: DatePicker? , year: Int , month: Int , dayOfMonth: Int) {
        val cal = Calendar.getInstance()
        viewModel.date = "$year-${month.plusOnePutFirstZero()}-${dayOfMonth.putFirstZero()}T"
        TimePickerDialog(
            this.requireContext() ,
            this ,
            cal.get(Calendar.HOUR) ,
            cal.get(Calendar.MINUTE) ,
            true
        ).show()
    }


    override fun onTimeSet(p0: TimePicker? , hour: Int , minute: Int) {
        viewModel.date+="${hour.plusOnePutFirstZero()}:${minute.putFirstZero()}:00"
        binding.tvDate.text = viewModel.date.getDateNextLine()

    }
}
package com.example.az.presentation.user.travel_plans

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.az.databinding.FragmentTravelPlanEditBinding
import com.example.az.utils.enums.AirportChooseType
import com.example.az.extensions.*
import com.example.az.domain.model.travel_plan.TravelPlan
import com.example.az.domain.model.travel_plan.TravelPlanRequest
import com.example.az.presentation.airport.AirportsFragmentDialog
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
    }

    private fun setInfo() {
        with(binding) {
            args.travelPlan?.let {
                travelPlanRequestForm.apply {
                    id = it.id
                    source = it.source
                    destination = it.destination
                    transfer = it.transfer
                    date = it.date
                    travelDate = it.travelDate
                }
                tvSource.text = getString(STRINGS.from_x , it.source)
                tvDestination.text = getString(STRINGS.to_x , it.destination)

                tvTransfer.text =
                    if (!it.transfer.isNullOrBlank()) getString(STRINGS.transfer_x , it.transfer)
                    else getString(STRINGS.transfer)

                tvDate.text = it.travelDate?.getDateNextLine()
                btnBack.setOnClickListener {
                    openBack(args.travelPlan!!)
                }
            } ?: run {
                btnBack.invisible()
            }
        }
    }

    private fun listeners() = with(binding) {
        btnTravelPlanSave.setOnClickListener {
            checkChoose()
        }
        setOnClickFun()
        setOnLongClickFun()
    }

    private fun setOnClickFun() = with(binding) {
        cardDate.setOnClickListener {
            openDateDialog()
        }
        cardSource.setOnClickListener {
            openAirportDialog(AirportChooseType.FROM)
        }
        cardDestination.setOnClickListener {
            openAirportDialog(AirportChooseType.TO)
        }
        cardTransfer.setOnClickListener {
            openAirportDialog(AirportChooseType.TRANSFER)
        }
        btnTravelPlanSave.setOnClickListener {
            checkChoose()
        }
    }

    private fun setOnLongClickFun() = with(binding) {
        cardSource.setOnLongClickListener {
            deleteForm(AirportChooseType.FROM)
            true

        }
        cardDestination.setOnLongClickListener {
            deleteForm(AirportChooseType.TO)
            true
        }
        cardTransfer.setOnLongClickListener {
            deleteForm(AirportChooseType.TRANSFER)
            true
        }

    }

    private fun deleteForm(type: AirportChooseType) = with(binding) {
        with(travelPlanRequestForm) {
            when (type) {
                AirportChooseType.FROM -> {
                    tvSource.deleteText()
                    source = null
                }
                AirportChooseType.TO -> {
                    tvDestination.deleteText()
                    destination = null
                }
                AirportChooseType.TRANSFER -> {
                    tvTransfer.deleteText()
                    transfer = null
                }
            }
        }
    }

    private fun getIfSimilar(
        travelPlan: TravelPlan ,
        travelPlanRequest: TravelPlanRequest
    ): TravelPlan =
        travelPlan.copy(
            source = travelPlanRequest.source ,
            destination = travelPlanRequest.destination ,
            transfer = travelPlanRequest.transfer ,
            travelDate = travelPlanRequest.travelDate ,
        )

    private fun checkChoose() {
        args.travelPlan?.let {
            if (it != getIfSimilar(it , travelPlanRequestForm)) {
                btnTravelPlanSave(true)
            } else {
                binding.root.showSnackBar(getString(STRINGS.change_data_first))
            }
        } ?: run {
            if (travelPlanRequestForm.source.isNullOrBlank() ||
                travelPlanRequestForm.destination.isNullOrBlank() ||
                travelPlanRequestForm.travelDate.isNullOrBlank()
            ) {
                binding.root.showSnackBar(getString(STRINGS.choose_data_first))
            } else {
                btnTravelPlanSave(
                    false
                )
            }
        }
    }

    private fun openBack(travelPlan: TravelPlan) {
        findNavController().navigate(
            TravelPlanEditFragmentDirections.actionNavigationTravelPlanEditToNavigationTravelPlan(
                travelPlan
            )
        )
    }

    private fun btnTravelPlanSave(update: Boolean) =
        if (update) update() else create()

    private fun create() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.createTravelPlan(travelPlanRequestForm)
            viewModel.createPlan.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        binding.progressBar.visible()
                        binding.root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> binding.progressBar.visible()
                    is Resource.Success -> {
                        binding.progressBar.invisible()
                        it.data?.travelPlan?.let { travelPlan ->
                            openBack(travelPlan)
                        }
                    }
                }
            }
        }
    }

    private fun update() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateTravelPlan(travelPlanRequestForm)
            viewModel.updatePlan.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        binding.progressBar.visible()
                        binding.root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> {
                        binding.root.isEnabled = false
                        binding.progressBar.visible()
                    }
                    is Resource.Success -> {
                        binding.progressBar.invisible()
                        it.data?.travelPlan?.let { travelPlanResponse ->
                            travelPlanResponse.id = travelPlanRequestForm.id
                            openBack(travelPlanResponse)
                        }
                    }
                }
            }
        }
    }

    private fun openDateDialog() = getDatePicker()

    private fun getDatePicker() {
        travelPlanRequestDate = null
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
        travelPlanRequestDate = "$year-${month.plusOnePutFirstZero()}-${dayOfMonth.putFirstZero()}T"
        TimePickerDialog(
            this.requireContext() ,
            this ,
            cal.get(Calendar.HOUR) ,
            cal.get(Calendar.MINUTE) ,
            true
        ).show()
    }

    override fun onTimeSet(p0: TimePicker? , hour: Int , minute: Int) {
        travelPlanRequestForm.travelDate =
            travelPlanRequestDate + "${hour.plusOnePutFirstZero()}:${minute.putFirstZero()}:00"
        binding.tvDate.text = travelPlanRequestForm.travelDate?.getDateNextLine()
    }

    private fun openAirportDialog(type: AirportChooseType) {
        val dialog = AirportsFragmentDialog()
        dialog.show(childFragmentManager , null)
        dialog.clickCallBack = {
            with(travelPlanRequestForm) {
                when (type) {
                    AirportChooseType.FROM -> {
                        if (destination.equals(it) || transfer?.containsAirport(it) == true) {
                            differentRouteAlert(it)
                        } else {
                            source = it
                            binding.tvSource.text = getString(STRINGS.from_x , it)
                        }
                    }
                    AirportChooseType.TO -> {
                        if (source.equals(it) || transfer?.containsAirport(it) == true
                        ) {
                            differentRouteAlert(it)
                        } else {
                            destination = it
                            binding.tvDestination.text = getString(STRINGS.to_x , it)
                        }
                    }
                    AirportChooseType.TRANSFER -> {
                        if (source.equals(it) || destination.equals(it) ||
                            transfer?.containsAirport(it) == true
                        ) {
                            differentRouteAlert(it)
                        } else {
                            transfer = if (!transfer.isNullOrBlank()) transfer.plus(",$it") else it
                            binding.tvTransfer.text =
                                getString(STRINGS.transfer_x , transfer)
                        }
                    }
                }
            }
        }
    }

    private fun differentRouteAlert(it: String) =
        binding.root.showSnackBar(getString(STRINGS.choose_different_route , it))

    var travelPlanRequestForm = TravelPlanRequest()
    var travelPlanRequestDate: String? = null
}

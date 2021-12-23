package com.example.az.presentation.airport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.az.databinding.ItemAirportBinding
import com.example.az.model.airport.Airport

typealias ClickAirportCallBack = (airportCity: String) -> Unit

class AirportAdapter() : RecyclerView.Adapter<AirportAdapter.ViewHolder>() {
    var clickAirportCallBack: ClickAirportCallBack? = null
    private var airports = mutableListOf<Airport>()

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int) =
        ViewHolder(

            ItemAirportBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: AirportAdapter.ViewHolder ,
        position: Int
    ) =
        holder.onBind(airports[position])

    inner class ViewHolder(private val binding: ItemAirportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: Airport) {
            binding.tvCity.text = model.city
            binding.tvCountry.text = model.country
            binding.tvCode.text = model.code
            binding.root.setOnClickListener {
                clickAirportCallBack?.invoke(model.code!!)
            }
        }
    }

    override fun getItemCount() = airports.size

    fun setData(movies: List<Airport>) {
        this.airports = movies.toMutableList()
        notifyDataSetChanged()
    }
}
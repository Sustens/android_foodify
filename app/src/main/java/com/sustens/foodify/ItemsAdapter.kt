package com.sustens.foodify

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sustens.foodify.databinding.DataItemBinding
import com.sustens.foodify.model.ItemsResponseItem
import java.math.RoundingMode
import java.text.DecimalFormat

private lateinit var context: Context

class ItemsAdapter(
    var items: List<ItemsResponseItem>
) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = DataItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(var binding: DataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: ItemsResponseItem
        ) {
            binding.apply {
                item.apply {
                    tvId.text = ID
                    tvFat.text = roundOffDecimal(FAT).toString()
                    tvCo2.text = energy_100g
                    tvProt.text = roundOffDecimal(PROT).toString()
                    tvCarbon.text = roundOffDecimal(carbon_footprint_100g).toString()
                    tvName.text = product_name

                    if(carbon_footprint_100g<=40){
                        tvCarbon.setTextColor(Color.GREEN)
                    } else if(carbon_footprint_100g<=80){
                        tvCarbon.setTextColor(Color.YELLOW)
                    } else {
                        tvCarbon.setTextColor(Color.RED)
                    }

                    if(FAT<=3.5){
                        tvFat.setTextColor(Color.GREEN)
                    } else if(carbon_footprint_100g<=10){
                        tvFat.setTextColor(Color.YELLOW)
                    } else {
                        tvFat.setTextColor(Color.RED)
                    }



                }
            }
        }

        fun roundOffDecimal(number: Double): Double? {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            return df.format(number).toDouble()
        }

    }

}
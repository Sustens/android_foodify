package com.sustens.foodify

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sustens.foodify.databinding.DataItemBinding
import com.sustens.foodify.model.ItemsResponseItem

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
                    tvFat.text = FAT.split('.')[0]
                    tvCo2.text = CO2_rating.split('.')[0]
                    tvProt.text = PROT.split('.')[0]
                    tvWater.text = WATER.split('.')[0]
                    tvCarbon.text = carbon_footprint_100g.split('.')[0]
                    tvCategory.text = category.toString()
                    tvEnergy.text = energy_100g.split('.')[0]
                    tvName.text = product_name.toString()
                    tvPrice.text = price_per_100g.toString()

                }
            }
        }

    }

}
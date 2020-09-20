package com.sustens.foodify

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sustens.foodify.model.ItemsResponseItem
import kotlinx.android.synthetic.main.product_info_cardview.view.*
import java.math.RoundingMode
import java.text.DecimalFormat


class RecyclerVIewAdapter(var products: ArrayList<ItemsResponseItem>) :
    RecyclerView.Adapter<RecyclerVIewAdapter.ProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ProductViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.product_info_cardview, parent, false)

    )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.item_title
        private val sust = view.sustscore
        private val co2 = view.carbon_ft
        private val prot = view.prot_tv
        private val fat = view.fat_tv



        fun bind(
            item: ItemsResponseItem
        ) {
            itemView.apply {
                item.apply {
                    title.text = product_name
                    fat.text = roundOffDecimal(FAT).toString()
                    co2.text = energy_100g
                    prot.text = roundOffDecimal(PROT).toString()
                    sust.text = roundOffDecimal(carbon_footprint_100g).toString()


                    if(carbon_footprint_100g<=40){
                        sust.setTextColor(Color.GREEN)
                    } else if(carbon_footprint_100g<=80){
                        sust.setTextColor(Color.BLACK)
                    } else {
                        sust.setTextColor(Color.BLACK)
                    }

                    if(FAT<=3.5){
                        fat.setTextColor(Color.GREEN)
                    } else if(carbon_footprint_100g<=10){
                        fat.setTextColor(Color.BLACK)
                    } else {
                        fat.setTextColor(Color.BLACK)
                    }

                    if(carbon_footprint_100g<=40 && FAT <=3.5){
                        card_view.setCardBackgroundColor(Color.parseColor("#9CCC65"))
                    } else if(carbon_footprint_100g >80 && FAT>10) {
                        card_view.setCardBackgroundColor(Color.parseColor("#ef5350"))
                    }
                    else {
                        card_view.setCardBackgroundColor(Color.parseColor("#FFEE58"))
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
package com.sustens.foodify
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import android.widget.RelativeLayout
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sustens.foodify.model.ItemsResponseItem
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    companion object {
        var sel_objs  =  ArrayList<ItemsResponseItem>()
    }



    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerVIewAdapter.ProductViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)



        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager


        adapter = RecyclerVIewAdapter(sel_objs)
        recycler_view.adapter = adapter



    }
}
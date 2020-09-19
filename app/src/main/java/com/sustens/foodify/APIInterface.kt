package com.sustens.foodify

import com.sustens.foodify.model.ItemsResponse
import retrofit2.Call
import retrofit2.http.GET




/**
 * Created by Ahmed Mostafa on 9/19/2020.
 */
interface APIInterface {

    @GET("/dataset.json")
    fun getItems(): Call<ItemsResponse>
}
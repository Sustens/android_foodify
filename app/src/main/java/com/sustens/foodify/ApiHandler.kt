package com.sustens.foodify

import android.util.Log
import okhttp3.*

import java.io.IOException


class ApiHandler {

     private val username = "hackzurich2020"
     private val password = "uhSyJ08KexKn4ZFS"

    private val client = OkHttpClient()

    fun ApiHandler() {


        run("https://hackzurich-api.migros.ch/products?1222")
    }

     fun call(){
         // just for future if required
         Log.d("ApiHandler", "reached call function")
         run("https://hackzurich-api.migros.ch/products?1222")
     }

    fun run(url: String) {
        val credential = Credentials.basic(username, password)
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization",credential)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ApiHandler", "Call failure")
            }
            override fun onResponse(call: Call, response: Response) {
                //handle response
                Log.d("ApiHandler", "response: "+ response.body)


            }
        })


    }
}
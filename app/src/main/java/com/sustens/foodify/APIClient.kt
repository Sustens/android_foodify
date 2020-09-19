package com.sustens.foodify

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Ahmed Mostafa on 9/19/2020.
 */
object APIClient {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            var client = OkHttpClient()
            client = OkHttpClient.Builder().addInterceptor { chain ->
                val request: Request =
                    chain.request().newBuilder().build()
                chain.proceed(request)
            }.build()

            retrofit = Retrofit.Builder()
                .baseUrl("https://foodify-61bff.firebaseio.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit as Retrofit
    }
}
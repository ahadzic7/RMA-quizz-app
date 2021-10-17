package ba.etf.rma21.projekat.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiAdapter {
    val retrofit : Api = Retrofit.Builder().baseUrl(ApiConfig.getbaseURL()).addConverterFactory(GsonConverterFactory.create()).build().create(Api::class.java)
}
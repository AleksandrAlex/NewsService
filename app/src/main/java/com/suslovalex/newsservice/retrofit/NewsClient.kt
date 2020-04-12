package com.suslovalex.newsservice.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsClient {
    private var retrofit: Retrofit? = null

    val instance: Retrofit
    get() {
        if (retrofit==null){
            retrofit = Retrofit.Builder()
                .baseUrl("http://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }

 //   fun getAPINews(): APINews{
 //       return retrofit!!.create(APINews::class.java)
 //   }

}
package com.suslovalex.newsservice.retrofit

import io.reactivex.Observable
import retrofit2.http.GET

interface APINews {
    @GET("v2/top-headlines?country=ru&category=technology&apiKey=26e7164f91404030a051e442b76aaf77")
    fun getTechnologyNews(): Observable<News>

    @GET("v2/top-headlines?country=ru&category=sports&apiKey=26e7164f91404030a051e442b76aaf77")
    fun getSportNews(): Observable<News>

    @GET("v2/top-headlines?country=ru&category=science&apiKey=26e7164f91404030a051e442b76aaf77")
    fun getScienceNews(): Observable<News>

    @GET("v2/top-headlines?country=ru&category=health&apiKey=26e7164f91404030a051e442b76aaf77")
    fun getHealthNews(): Observable<News>

    @GET("v2/top-headlines?country=ru&category=entertainment&apiKey=26e7164f91404030a051e442b76aaf77")
    fun getEntertainmentNews(): Observable<News>
}
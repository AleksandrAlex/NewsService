package com.suslovalex.newsservice.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Source(
    val id: Any,
    val name: String
): Serializable
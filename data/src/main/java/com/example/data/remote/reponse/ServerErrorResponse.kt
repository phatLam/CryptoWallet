package com.example.data.remote.reponse

import com.google.gson.annotations.SerializedName

data class ServerErrorResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("errors")
    val errors: List<Any>?,
)
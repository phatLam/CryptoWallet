package com.example.data.remote.error

import com.example.data.remote.reponse.ServerErrorResponse
import com.example.domain.core.error.ApiFailure
import com.example.domain.core.error.FailureHandler
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class RemoteFailureHandler : FailureHandler {
    override fun handleThrowable(throwable: Throwable) = when (throwable) {
        is IOException -> {
            ApiFailure.Connection
        }
        is HttpException -> {
            handleHttpException(throwable)
        }
        else -> {
            ApiFailure.Unknown(throwable)
        }
    }

    private fun handleHttpException(httpException: HttpException): ApiFailure.Server {
        val code = httpException.code()
        val errorBody = httpException.response()?.errorBody()?.string()
        val errorMessage = try {
            val serverErrorResponse = Gson().fromJson(errorBody, ServerErrorResponse::class.java)
            serverErrorResponse.message.orEmpty()
        } catch (parseException: Exception) {
            parseException.printStackTrace()
            ""
        }
        return ApiFailure.Server(
            code = code,
            errorMessage = errorMessage
        )
    }
}
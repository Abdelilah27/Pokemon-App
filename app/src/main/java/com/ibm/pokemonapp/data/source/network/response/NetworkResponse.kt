package com.ibm.pokemonapp.data.source.network.response

import okhttp3.Headers
import java.io.IOException

sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T?) : NetworkResponse<T, Nothing>()

    /**
     * Success response with body
     */
    data class SuccessWithHeaderOnly<T : Any>( val headers: Headers) : NetworkResponse<T, Nothing>()


    /**
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
}

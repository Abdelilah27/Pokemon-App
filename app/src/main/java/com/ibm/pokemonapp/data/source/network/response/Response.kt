package com.ibm.pokemonapp.data.source.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Response {
    @Parcelize
    class ErrorResponse(val id: Int, val message: String) : Response(), Parcelable

}


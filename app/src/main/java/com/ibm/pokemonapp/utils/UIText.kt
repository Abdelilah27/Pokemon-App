package com.ibm.pokemonapp.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIText {
    data class DynamicString(
        val value: String
    ) : UIText()

    data class StringResource(
        @StringRes val id: Int,
        val args: List<Any>
    ) : UIText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args.toTypedArray())
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id, *args.toTypedArray())
        }
    }
}



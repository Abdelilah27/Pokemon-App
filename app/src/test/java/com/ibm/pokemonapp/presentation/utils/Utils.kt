package com.ibm.pokemonapp.presentation.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
fun runTest(
    test: suspend CoroutineScope.() -> Unit
) = kotlinx.coroutines.test.runTest(context = UnconfinedTestDispatcher()) {
    test()
}
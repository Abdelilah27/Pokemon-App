package com.ibm.pokemonapp.domain.usecase.base

import com.ibm.pokemonapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface BaseUseCase<T : Any?, R : Any?> {
    suspend operator fun invoke(param: T): Flow<Resource<R>>
}
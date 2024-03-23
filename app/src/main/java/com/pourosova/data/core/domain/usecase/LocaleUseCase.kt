package com.pourosova.data.core.domain.usecase

import com.pourosova.data.core.domain.base.BaseUseCase

interface CoroutineBaseUseCase<Params, Type>: BaseUseCase {
    suspend fun execute(params: Params):Type
}

interface LocalLiveDataBaseUseCase <Params, Type>: BaseUseCase {
    fun execute(params: Params? = null):Type
}

interface RoomUseCaseNonParams<Type> : BaseUseCase {
    suspend fun execute(): Type
}
package com.pourosova.data.core.domain.base

import kotlinx.coroutines.flow.Flow

/**
 * Implement this UseCase when you do not need to observe the value
 * @Srizan
 * */
interface RoomSuspendableUseCase<Params, ReturnType> : BaseUseCase {
    suspend operator fun invoke(params: Params) : ReturnType
}

/**
 * Implement this UseCase when you need to observe the value
 * @Srizan
 * */
interface RoomCollectableUseCase<Params, ReturnType> : BaseUseCase {
    operator fun invoke(params: Params): Flow<ReturnType>
}
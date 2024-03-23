package com.pourosova.data.core.domain

import com.pourosova.data.core.domain.base.ApiResult
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun fetchBusReservationHistory(params : String) : Flow<ApiResult<List<String>>>
}
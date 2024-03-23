package com.pourosova.data.core.data.repoimpl

import com.pourosova.data.core.data.NetworkBoundResource
import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepoImpl @Inject constructor(
    private val networkBoundResources: NetworkBoundResource
) : AppRepository {
    override suspend fun fetchBusReservationHistory(params: String): Flow<ApiResult<List<String>>> {
        TODO("Not yet implemented")
    }
}
package com.pourosova.data.core.domain.base


sealed class DataValidationResult{
    data object Success : DataValidationResult()
    data class Failure<T>(val ioErrorResult: T) : DataValidationResult()
}
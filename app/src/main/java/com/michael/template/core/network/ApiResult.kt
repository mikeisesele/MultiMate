package com.michael.template.core.network

import android.util.Log
import com.nb.benefitspro.core.network.data.model.exceptions.DataContentNullException

/**
 * A sealed wrapper class to wrap the results of all API calls
 */
sealed class ApiResult<out T> {
    class Success<out T>(val data: T) : ApiResult<T>()
    class Failure(val throwable: Throwable, val message: String? = null) : ApiResult<Nothing>()
}

inline fun <T> ApiResult<T>.whenSuccess(block: (result: T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success && data != null) {
        block(this.data)
    }
    return this
}

fun <T> ApiResult<T>.unwrap(): T {
    if (this is ApiResult.Success && data != null) {
        return this.data
    } else {
        // we should never get here but just to make sure...
        throw DataContentNullException("this happens during unwrapping", "[check earlier logs]")
    }
}

inline fun <T> ApiResult<T>.whenError(block: (error: Throwable) -> Unit): ApiResult<T> {
    if (this is ApiResult.Failure) {
        block(this.throwable)
    }
    return this
}

inline fun <T, R> ApiResult<T>.map(mapper: (T) -> R): ApiResult<R> =
    when (this) {
        is ApiResult.Success -> ApiResult.Success(mapper(data))
        is ApiResult.Failure -> ApiResult.Failure(ApiFailureException(this.throwable))
    }

inline fun <T, R> ApiResult<T>.mapWithFallback(mapper: (T) -> R, fallback: () -> R): ApiResult<R> =
    when (this) {
        is ApiResult.Success -> ApiResult.Success(mapper(data))
        is ApiResult.Failure -> {
            Log.e(this.toString(), "ApiResult was Failure with: $throwable, fallback called")
            ApiResult.Success(fallback())
        }
    }

data class ApiFailureException(val originalException: Throwable) :
    RuntimeException("API call has failed with the following exception: ${originalException.message}")

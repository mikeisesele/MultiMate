package com.michael.template.core.network

import com.michael.template.core.network.model.RetrofitApiError
import com.nb.benefitspro.core.network.data.model.exceptions.GeneralApiException
import com.nb.benefitspro.core.network.data.model.exceptions.UnauthorizedException
import com.squareup.moshi.JsonAdapter
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class is used as a wrapper around a [Response] which adds additional functionality that is
 * needed, such as analytics event logging via [EventLogger].
 * This is used as part of the REST [RefreshTokenApi], and can be used with further REST apis.
 * After a call has been made we appropriately map an [ApiResult.Success] or [ApiResult.Failure],
 * and if needed log an analytics event.
 *
 * @param eventLogger an implementation of an [EventLogger] that logs [AnalyticsEvent]s
 * @param adapter a [JsonAdapter] which parses errors returned into a [RetrofitApiError]
 */
@Suppress("TooGenericExceptionCaught")
@Singleton
class BaseApi @Inject constructor(
    private val adapter: JsonAdapter<RetrofitApiError>,
) {
    fun <T : Any> asResult(response: Response<T>): ApiResult<T> {
        return try {
            return apiResult(response)
        } catch (e: Exception) {
            when (e) {
                is IOException -> ApiResult.Failure(e) // Network error
                is HttpException -> { // Unexpected non-2xx error
                    e.response()?.let {
                        ApiResult.Failure(e, it.errorBody()?.string())
                    } ?: ApiResult.Failure(e)
                }

                else -> ApiResult.Failure(e) // Unknown Error
            }
        }
    }

    private fun <T : Any> apiResult(response: Response<T>): ApiResult<T> {
        return when {
            response.isSuccessful && response.body() != null -> ApiResult.Success(response.body()!!)
            else -> {
                val errorMessage = parseErrorMessage(response)
                when (response.code()) {
                    HTTP_UNAUTHORIZED -> {
                        return ApiResult.Failure(UnauthorizedException(), errorMessage)
                    }
                }
                ApiResult.Failure(GeneralApiException(errorMessage), errorMessage)
            }
        }
    }

    private fun <T> parseErrorMessage(response: Response<T>): String {
        val errorJson = response.errorBody()?.string().orEmpty()
        val apiError = try {
            adapter.fromJson(errorJson)
        } catch (e: Exception) {
            null
        }
        return apiError?.error ?: errorJson
    }
}

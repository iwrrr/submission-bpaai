package id.hwaryun.shared.data.network

import id.hwaryun.shared.domain.GetUserTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        runBlocking {
            getUserTokenUseCase().first { tokenResponse ->
                val token = tokenResponse.payload
                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                true
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}
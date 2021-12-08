package com.yudahendriawan.secondsubmissionfundamentalandroid.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {

        private const val AUTHORIZATION = "Authorization"
        private const val TOKEN = "token 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"
        private const val BASE_URL = "https://api.github.com/"

        fun getApiService(): ApiInterface {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client2 = OkHttpClient.Builder().apply {
                addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header(
                            AUTHORIZATION,
                            TOKEN
                        )
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
                addInterceptor(loggingInterceptor)
            }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client2)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }

}
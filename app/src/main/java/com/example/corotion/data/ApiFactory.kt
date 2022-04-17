package com.example.corotion.data

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val DEFAULT_MAX_REQUEST = 30

    private fun buildOkHttpClient(context: Context): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor).build()

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = DEFAULT_MAX_REQUEST
        builder.dispatcher(dispatcher)

        return builder.build()
    }

    fun <S> createService(serviceClass: Class<S>, context: Context, baseURl: String): S {

        val gson = GsonBuilder()
            .create()

        val okhttpClient = buildOkHttpClient(context)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURl)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(serviceClass)
    }
}

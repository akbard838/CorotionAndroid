package com.example.corotion.data

import com.example.corotion.data.response.SummaryResponse
import retrofit2.Call
import retrofit2.http.GET

interface CorotionApiClient {
    @GET("summary")
    fun getCoronaSummary(): Call<SummaryResponse>
}
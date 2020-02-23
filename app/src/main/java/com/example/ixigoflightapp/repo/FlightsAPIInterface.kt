package com.example.ixigoflightapp.repo

import com.example.ixigoflightapp.data.FlightsData
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface FlightsAPIInterface {

    @GET("http://www.mocky.io/v2/5979c6731100001e039edcb3/")
    fun getFlightsList() :Observable<FlightsData>

}
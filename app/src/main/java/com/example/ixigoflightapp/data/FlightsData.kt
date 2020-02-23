package com.example.ixigoflightapp.data

import com.google.gson.annotations.SerializedName

data class FlightsData(
    @SerializedName("flights") val flights : List<Flight>?,
    @SerializedName("appendix") val appendix : Appendix?
)

data class Flight (

    @SerializedName("originCode") val originCode : String?,
    @SerializedName("destinationCode") val destinationCode : String?,
    @SerializedName("departureTime") val departureTime : Long,
    @SerializedName("arrivalTime") val arrivalTime : Long,
    @SerializedName("fares") val fares : List<Fares>?,
    @SerializedName("airlineCode") val airlineCode : String?,
    @SerializedName("class") val className : String?
)

data class Fares (
    @SerializedName("providerId") val providerId : Int?,
    @SerializedName("fare") val fare : Int?
)


data class Appendix (
    @SerializedName("airlines") val airlines : Map<String,String>?,
    @SerializedName("airports") val airports : Map<String,String>?,
    @SerializedName("providers") val providers : Map<Int,String>?
)

data class FlightDetailsVHData(val departureTime :String,
                           val arrivalTime :String,
                           var fare :Int?,
                           val providerName :String,
                           val airlineName :String
                          )


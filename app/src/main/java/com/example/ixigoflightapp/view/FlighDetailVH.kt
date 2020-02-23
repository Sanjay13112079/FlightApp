package com.example.ixigoflightapp.view

import android.view.View
import com.example.imagesandvideo.recyclerview.GenericVH
import com.example.ixigoflightapp.data.FlightDetailsVHData
import kotlinx.android.synthetic.main.flight_detials_vh.view.*

class FlighDetailVH(itemView :View) :GenericVH<FlightDetailsVHData>(itemView) {

    var rootView :View?=null

    init {
        rootView=itemView
    }

    override fun bindData(data: FlightDetailsVHData?) {

        rootView?.airline_name?.text=data?.airlineName

        rootView?.departure?.text="Departure"
        rootView?.departure_time?.text=data?.departureTime

        rootView?.arrival?.text="Arrival"
        rootView?.arrival_time?.text=data?.arrivalTime

        rootView?.fare_value?.text=data?.fare?.toString()
        rootView?.trip_provider?.text=data?.providerName

    }
}
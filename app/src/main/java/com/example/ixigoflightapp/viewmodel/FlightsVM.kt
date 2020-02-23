package com.example.ixigoflightapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesandvideo.recyclerview.FeedItem
import com.example.imagesandvideo.recyclerview.ItemTypeHandler
import com.example.ixigoflightapp.repo.FlightsRepo
import com.example.ixigoflightapp.data.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class FlightsVM :ViewModel(),Comparator<FeedItem<FlightDetailsVHData>> {

      var flightData :MutableLiveData<Resource<FlightsData?>>
      var flighsFeedItemList :List<FeedItem<FlightDetailsVHData>> = ArrayList()

      var repo: FlightsRepo =
          FlightsRepo()

      var sortPosiiton=-1;

    /**
     * calling API in constructor so that even if device is rotated , re fetch of data can be stopped
     */
    init {
        flightData=repo.getFlightsData()
    }

     fun fetchData():MutableLiveData<Resource<FlightsData?>>
     {
         return flightData
     }


    /**
     * get list  of Flights
     */
    fun getFlightsDetailsFeedItemList(data :FlightsData?) :List<FeedItem<*>>?
    {
        if(data==null || data?.flights==null) return null

        val appendix=data.appendix

        var list=ArrayList<FeedItem<FlightDetailsVHData>>()

        for(item in data.flights)
        {
            if(item.fares!=null && !item.fares.isEmpty()) {
                for (fare in item?.fares) {

                    var departureTime=getTimeFromTimeStamp(item.departureTime)
                    var arrivalTime=getTimeFromTimeStamp(item.arrivalTime)
                    var tripFare=fare?.fare
                    var providerName=getProviderNameFromId(fare.providerId,appendix)
                    var airlineName=getAirlineNameFromId(item.airlineCode,appendix)

                    var flighDetails = FlightDetailsVHData(departureTime,arrivalTime,tripFare,providerName,airlineName)

                    list.add(FeedItem(flighDetails,ItemTypeHandler.ItemViewType.FLIGHT_VH))
                }
            }
        }

        flighsFeedItemList=list

        return list
    }

    /**
     * date from timestamp
     */
    fun getDatefromTimeStamp(timeStmap : Long?) :String
    {
        if(timeStmap!=null) {
            var date = SimpleDateFormat("\"EEEEEE, MMM d, yyyy\"").format(timeStmap)
            return date
        }
        else return " "
    }

    /**
     * time from timestamp
     */
    fun getTimeFromTimeStamp(timeStamp :Long?) :String
    {
        if(timeStamp!=null)
        {
            var time= SimpleDateFormat("h:mm a").format(timeStamp)
            return time
        }
        else return " "

    }

    /**
     * booking provider from provider id
     */
    fun getProviderNameFromId(provierId :Int?,appendix :Appendix?) :String
    {
        if(provierId==null || appendix==null) return " "
        return appendix?.providers?.get(provierId)!!
    }

    /**
     * Airline name from airline code
     */
    fun getAirlineNameFromId(airlineId :String?,appendix :Appendix?) :String
    {
        if(airlineId==null || appendix==null) return " "
        return appendix?.airlines?.get(airlineId)!!
    }


    fun getDate( data :FlightsData):String?
    {
        return getDatefromTimeStamp(data?.flights?.get(0)?.departureTime!!)
    }

    /**
     * sort the list
     */
    fun doSorting(postion:Int) :List<FeedItem<FlightDetailsVHData>>
    {
        sortPosiiton=postion

        var feedList =ArrayList<FeedItem<FlightDetailsVHData>>()
        feedList.addAll(flighsFeedItemList)
        Collections.sort(feedList,this)
        return  feedList
    }

    override fun compare(o1: FeedItem<FlightDetailsVHData>?, o2: FeedItem<FlightDetailsVHData>?): Int {
        when(sortPosiiton)
        {
            1-> return o1?.data?.fare!!- o2?.data?.fare!!


            2-> return o2?.data?.fare!!- o1?.data?.fare!!


            else -> return o1?.data?.fare!!- o2?.data?.fare!!
        }

    }

}
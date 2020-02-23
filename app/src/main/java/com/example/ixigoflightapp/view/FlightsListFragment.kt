package com.example.ixigoflightapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesandvideo.recyclerview.FeedItem
import com.example.imagesandvideo.recyclerview.GenericRVAdapter
import com.example.ixigoflightapp.R
import com.example.ixigoflightapp.data.FlightsData
import com.example.ixigoflightapp.data.Resource
import com.example.ixigoflightapp.data.Status
import com.example.ixigoflightapp.viewmodel.FlightsVM
import kotlinx.android.synthetic.main.flights_list_fragment.*

class FlightsListFragment :Fragment(),View.OnClickListener {

    lateinit var viewModel : FlightsVM
    lateinit var feedItemList :ArrayList<FeedItem<*>>
    lateinit var mAdapter :GenericRVAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.flights_list_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        fetchData()
    }

    fun initViews()
    {
        viewModel= ViewModelProviders.of(this).get(FlightsVM::class.java)
        feedItemList=ArrayList()
        mAdapter=GenericRVAdapter(feedItemList,this)
        var layoutmanager=LinearLayoutManager(activity)
        flights_rv.layoutManager=layoutmanager
        flights_rv.adapter=mAdapter

        var sortList= arrayOf(resources.getString(R.string.filter_here),resources.getString(R.string.low_to_high), resources.getString(R.string.high_to_low))
        var spinnerAdapter=ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item,sortList);
        sort_spinner?.adapter=spinnerAdapter
        sort_spinner.onItemSelectedListener=spinnerSelectionListener

    }

    /**
     * Trigger for API
     */
    fun fetchData(){
        viewModel.fetchData()?.observe(this,Observer<Resource<FlightsData?>>{
            when(it.status)
            {
                Status.LOADING ->{
                    //loading can be shown
                }

                Status.ERROR ->{
                    //error handle
                }

                Status.SUCCESS ->{
                   mapDatatUI(it.data)
                }
            }
        })
    }

    /**
     * map the receieved data to UI
     */
    fun mapDatatUI(data :FlightsData?)
    {
        if(data==null || data.flights==null || data.flights.isEmpty())
        {
            trip_route.visibility=View.GONE
            trip_date.visibility=View.GONE
            divider.visibility=View.GONE
            sort_spinner?.visibility=View.GONE

            return
        }

        trip_route.visibility=View.VISIBLE
        trip_date.visibility=View.VISIBLE
        divider.visibility=View.VISIBLE
        sort_spinner?.visibility=View.VISIBLE


        trip_route?.text=resources.getString(R.string.delhi_to_bombay)
        trip_date?.text=viewModel.getDate(data)

        feedItemList?.addAll(viewModel.getFlightsDetailsFeedItemList(data)!!)
        mAdapter.notifyDataSetChanged()



    }

    /**
     * spinner item click control
     */
    var spinnerSelectionListener=object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            feedItemList.clear()

            when(position)
            {
                0 -> feedItemList.addAll(viewModel.flighsFeedItemList)

                1 -> feedItemList.addAll(viewModel.doSorting(1))

                2 -> feedItemList.addAll(viewModel.doSorting(2))

            }

            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onClick(v: View?) {}
}
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
import kotlinx.android.synthetic.main.fragment_flight_list.*

class FlightsListFragment : Fragment(), View.OnClickListener {

    lateinit var viewModel: FlightsVM
    lateinit var feedItemList: ArrayList<FeedItem<*>>
    lateinit var mAdapter: GenericRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(FlightsVM::class.java)
        feedItemList = ArrayList()
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flight_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        fetchData()
    }

    private fun initViews() {
        mAdapter = GenericRVAdapter(feedItemList, this)
        flights_rv.layoutManager = LinearLayoutManager(activity)
        flights_rv.adapter = mAdapter

        val sortList = arrayOf(
            resources.getString(R.string.filter_here),
            resources.getString(R.string.low_to_high),
            resources.getString(R.string.high_to_low)
        )
        val spinnerAdapter = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            sortList
        );
        sort_spinner?.adapter = spinnerAdapter
        sort_spinner.onItemSelectedListener = spinnerSelectionListener

    }

    /**
     * Trigger for API
     */
    private fun fetchData() {
        viewModel.fetchData().observe(this, Observer<Resource<FlightsData?>> {
            when (it.status) {
                Status.LOADING -> {
                    showView(false)
                }

                Status.ERROR -> {
                    showView(false)
                }

                Status.SUCCESS -> {
                    mapDatatUI(it.data)
                }
            }
        })
    }

    /**
     * map the receieved data to UI
     */
    private fun mapDatatUI(data: FlightsData?) {
        if (data?.flights == null || data.flights.isEmpty()) {
            showView(false)
            return
        }

        showView(true)

        trip_route?.text = resources.getString(R.string.delhi_to_bombay)
        trip_date?.text = viewModel.getDate(data)

        feedItemList.addAll(viewModel.getFlightsDetailsFeedItemList(data)!!)
        mAdapter.notifyDataSetChanged()


    }

    /**
     * spinner item click control
     */
    var spinnerSelectionListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                feedItemList.clear()

                when (position) {
                    0 -> feedItemList.addAll(viewModel.flighsFeedItemList)

                    1 -> feedItemList.addAll(viewModel.doSorting(1))

                    2 -> feedItemList.addAll(viewModel.doSorting(2))

                }

                mAdapter.notifyDataSetChanged()
            }
        }

    private fun showView(isShow: Boolean) {
        if (isShow) {
            trip_route.visibility = View.VISIBLE
            trip_date.visibility = View.VISIBLE
            divider.visibility = View.VISIBLE
            sort_spinner?.visibility = View.VISIBLE
        } else {
            trip_route.visibility = View.GONE
            trip_date.visibility = View.GONE
            divider.visibility = View.GONE
            sort_spinner?.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {}
}
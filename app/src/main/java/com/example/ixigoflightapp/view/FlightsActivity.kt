package com.example.ixigoflightapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ixigoflightapp.R

class FlightsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        naigateToFlightsFrgament()
    }

    fun naigateToFlightsFrgament()
    {
        var frgament= FlightsListFragment()
        supportFragmentManager?.beginTransaction()?.add(R.id.container,frgament)?.commit()
    }

}

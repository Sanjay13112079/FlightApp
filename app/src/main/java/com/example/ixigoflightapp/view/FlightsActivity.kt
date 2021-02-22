package com.example.ixigoflightapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ixigoflightapp.R

class FlightsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        naigateToFlightsFrgament()
    }

    private fun naigateToFlightsFrgament() {
        val frgament = FlightsListFragment()
        supportFragmentManager.beginTransaction().add(R.id.container, frgament).commit()
    }

}

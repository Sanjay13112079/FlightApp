package com.example.ixigoflightapp.repo

import androidx.lifecycle.MutableLiveData
import com.example.ixigoflightapp.data.FlightsData
import com.example.ixigoflightapp.data.Resource
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FlightsRepo {

    fun getFlightsData():MutableLiveData<Resource<FlightsData?>>
    {
        val output=MutableLiveData<Resource<FlightsData?>>()
          output.value=Resource.loading(null)

                var client = UnsafeCertificate.getUnsafeOkHttpClient()

                var retrofit= Retrofit.Builder()
                    .baseUrl("https://www.mocky.io/v2/5979c6731100001e039edcb3/")
                    .addConverterFactory(GsonConverterFactory.create(Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync()) // using retrofit with rxJava
                    .client(client).build()

                var apiInterface=retrofit.create(FlightsAPIInterface::class.java)

                   apiInterface.getFlightsList()
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(
                      {  data -> output.value=Resource.success(data) }, { error ->output.value=Resource.error(null) }
                  )

                return output
    }
}
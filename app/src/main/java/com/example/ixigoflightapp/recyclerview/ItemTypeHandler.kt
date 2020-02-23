package com.example.imagesandvideo.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ixigoflightapp.R
import com.example.ixigoflightapp.view.FlighDetailVH

class ItemTypeHandler {

    //using enum to classify viewholder types
     enum class ItemViewType(id :Int)
     {
         FLIGHT_VH(0);

         var id :Int?=null

         init {
             this.id=id
         }
     }

     companion object
     {

         fun getType(id :Int) :Int?
         {
             var itemtypeArray=
                 ItemViewType.values()
             for(itemType in itemtypeArray)
             {
                 if(itemType?.id==id) return itemType.id
             }

             return null
         }

     //create view holder for recycler view
     fun createViewHolder (inflater :LayoutInflater,parent :ViewGroup,type :Int? ): GenericVH<Any>?
     {

             if(type==null) return null
             var viewHolder : GenericVH<Any>?=null

             when(type)
             {
                 ItemViewType.FLIGHT_VH.id ->
                 {
                     viewHolder= FlighDetailVH(
                         inflater.inflate(
                             R.layout.flight_detials_vh,
                             parent,
                             false
                         )
                     ) as GenericVH<Any>
                 }

             }

             return viewHolder
         }
     }





}
package fr.isen.guillot.androidrestaurant.model

import com.google.gson.annotations.SerializedName


data class MenuResponse (

  @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()

)
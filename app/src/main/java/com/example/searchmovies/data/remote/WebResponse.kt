package com.example.searchmovies.data.remote

import com.example.searchmovies.data.ResponseItem
import com.google.gson.annotations.SerializedName

data class WebResponse(@SerializedName("results") val responses: List<ResponseItem>)
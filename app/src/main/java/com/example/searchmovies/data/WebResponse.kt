package com.example.searchmovies.data

import com.google.gson.annotations.SerializedName

data class WebResponse(@SerializedName("results") val responses: List<ResponseItem>)
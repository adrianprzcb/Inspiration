package com.adrian.inspiration_aquoteeveryhour

import com.google.gson.annotations.SerializedName

data class InspirationResponse(
    @SerializedName("status") var status: String,
    @SerializedName("quote") var quote: String,
    @SerializedName("author") var author: String
)



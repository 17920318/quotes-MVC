package dev.cardoso.quotesmvvm.data.model

import com.google.gson.annotations.SerializedName

data class QuoteResponse (
    @SerializedName("success")
    var success:Boolean,
    @SerializedName("message")
    var message:String,
    @SerializedName("data")
    var data: List<QuoteModel>
        )
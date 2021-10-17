package ba.etf.rma21.projekat.data.api

import com.google.gson.annotations.SerializedName

class Message(
    @SerializedName("message") val message: String?,
    @SerializedName("changed") val changed: Boolean?
)
package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
@Entity(tableName = "Pitanje")
data class Pitanje (
        @PrimaryKey @ColumnInfo(name = "idZaBazu") var idZaBazu: Int,

        @SerializedName("id")
    val id: Int?,
        @ColumnInfo(name = "naziv") @SerializedName("naziv")
    val naziv: String?,
        @ColumnInfo(name = "tekstPitanja") @SerializedName("tekstPitanja")
    val tekstPitanja: String?,
        @ColumnInfo(name = "opcije") @SerializedName("opcije") @TypeConverters(Converters::class)
    val opcije: List<String>,
        @ColumnInfo(name = "tacan") @SerializedName("tacan")
    val tacan: Int,
        @ColumnInfo(name = "kvizId") var kvizId: Int?
)